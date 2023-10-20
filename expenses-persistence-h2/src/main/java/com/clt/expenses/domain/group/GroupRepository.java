package com.clt.expenses.domain.group;

import java.util.List;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class GroupRepository {
  private final DatabaseClient databaseClient;

  public GroupRepository(DatabaseClient databaseClient) {
    this.databaseClient = databaseClient;
  }

  public Mono<GroupEntity> findById(String id) {
    Mono<GroupEntity> groupEntityMono =
        databaseClient
            .sql("SELECT id, name, owner " + "FROM expense_group " + "WHERE id = :id")
            .bind("id", id)
            .map(
                row ->
                    new GroupEntity(
                        row.get("id", String.class),
                        row.get("name", String.class),
                        row.get("owner", String.class)))
            .one();
    Mono<List<String>> membersMono =
        databaseClient
            .sql(
                """
                        SELECT member
                        FROM group_member
                        WHERE group_id = :id
                        """)
            .bind("id", id)
            .map(row -> row.get("member", String.class))
            .all()
            .collectList();
    return Mono.zip(
        groupEntityMono,
        membersMono,
        (g, ms) -> new GroupEntity(g.getId(), g.getName(), g.getOwner(), ms));
  }

  public void save(GroupEntity entity) {
    databaseClient
        .sql(
            """
                        INSERT INTO expense_group (ID, NAME, OWNER)
                        VALUES( :id, :name, :owner)
                        """)
        .bind("id", entity.getId())
        .bind("name", entity.getName())
        .bind("owner", entity.getOwner())
        .fetch()
        .rowsUpdated()
        .concatWith(
            Flux.fromIterable(entity.getMembers())
                .flatMap(
                    m ->
                        databaseClient
                            .sql(
                                """
                        INSERT INTO group_member (group_id, member )
                        VALUES( :groupId, :member)
                        """)
                            .bind("groupId", entity.getId())
                            .bind("member", m)
                            .fetch()
                            .rowsUpdated()))
        .subscribe();
  }
}
