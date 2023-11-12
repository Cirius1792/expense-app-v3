package com.clt.expenses.domain.group;

import com.clt.domain.group.Group;
import io.r2dbc.spi.Readable;

import java.util.List;
import java.util.function.Function;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class GroupRepository {
    public static final String SELECT_GROUP_FROM_EXPENSE_GROUP_WHERE_ID =
            """
                    SELECT id, name, owner
                    FROM expense_group
                    WHERE id = :id
                    """;
    public static final String SELECT_MEMBER_FROM_GROUP_MEMBER_WHERE_GROUP_ID =
            """
                    SELECT member
                    FROM group_member
                    WHERE group_id = :id
                    """;
    public static final String SELECT_GROUP_FROM_EXPENSE_GROUP_WHERE_MEMBER_IN =
            """
                    SELECT id, name, owner
                    FROM expense_group g JOIN group_member m on m.member= :memberId and g.id = m.group_id
                    """;

    private final DatabaseClient databaseClient;

    public GroupRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<GroupEntity> findById(String id) {
        Mono<GroupEntity> groupEntityMono =
                databaseClient
                        .sql(SELECT_GROUP_FROM_EXPENSE_GROUP_WHERE_ID)
                        .bind("id", id)
                        .map(groupRowMapper())
                        .one();
        Mono<List<String>> membersMono = getMembersMono(id);
        return Mono.zip(
                groupEntityMono,
                membersMono,
                (g, ms) -> new GroupEntity(g.getId(), g.getName(), g.getOwner(), ms));
    }

    private Mono<List<String>> getMembersMono(String id) {
        return databaseClient
                .sql(SELECT_MEMBER_FROM_GROUP_MEMBER_WHERE_GROUP_ID)
                .bind("id", id)
                .map(memberRowMapper())
                .all()
                .collectList();
    }

    public Flux<GroupEntity> findByMember(String userId) {
        return databaseClient
                .sql(SELECT_GROUP_FROM_EXPENSE_GROUP_WHERE_MEMBER_IN)
                .bind(0, userId)
                .map(groupRowMapper())
                .all()
                .flatMap(
                        g ->
                                getMembersMono(g.getId())
                                        .map(ms -> new GroupEntity(g.getId(), g.getName(), g.getOwner(), ms)));
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
                                .flatMap(m -> this.addMember(entity.getId(), m)))
                .subscribe();
    }

    public Mono<Long> addMember(String groupId, String memberId) {
        return databaseClient
                .sql(
                        """
                                INSERT INTO group_member (group_id, member )
                                VALUES( :groupId, :member)
                                """)
                .bind("groupId", groupId)
                .bind("member", memberId)
                .fetch()
                .rowsUpdated();
    }

    private static Function<Readable, String> memberRowMapper() {
        return row -> row.get("member", String.class);
    }

    private static Function<Readable, GroupEntity> groupRowMapper() {
        return row ->
                new GroupEntity(
                        row.get("id", String.class),
                        row.get("name", String.class),
                        row.get("owner", String.class));
    }

    public Mono<Void> addMember(String groupId, List<String> members) {
        return Flux.fromIterable(members)
                .flatMap(m -> addMember(groupId, members))
                .last();
    }
}
