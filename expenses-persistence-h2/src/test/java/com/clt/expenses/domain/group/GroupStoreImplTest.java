package com.clt.expenses.domain.group;

import com.clt.domain.group.Group;
import com.clt.domain.group.GroupStore;
import com.clt.domain.group.ImmutableGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;

@SpringBootTest(classes = GroupTestConfiguration.class)
class GroupStoreImplTest {
    private static final String GROUP_ID = "id";
    private static final String GROUP_NAME = "My Group";
    private static final String OWNER_ID = "own-id";
    private static final String MEMBER_1 = "m-1";

    private static final String MEMBER_2 = "m-2";

    private static final Group EXPECTED_GROUP = ImmutableGroup.builder()
            .id(GROUP_ID)
            .name(GROUP_NAME)
            .owner(OWNER_ID)
            .addMembers(MEMBER_1, MEMBER_2, OWNER_ID)
            .build();

    @Autowired
    DatabaseClient database;
    @Autowired
    GroupStore groupStore;

    @BeforeEach
    void setUp() {

        Hooks.onOperatorDebug();

        var statements =
                Arrays.asList( //
                        "DROP TABLE IF EXISTS expense_group;",
                        """
                                CREATE TABLE expense_group (
                                id VARCHAR PRIMARY KEY, 
                                name VARCHAR(255), 
                                OWNER VARCHAR(100) NOT NULL 
                                );
                                """,
                        "DROP TABLE IF EXISTS group_member;",
                        """
                                CREATE TABLE group_member (
                                group_id VARCHAR, 
                                member VARCHAR(100),
                                PRIMARY KEY (group_id, member)
                                );
                                """
                );

        statements.forEach(
                it ->
                        database
                                .sql(it) //
                                .fetch() //
                                .rowsUpdated() //
                                .as(StepVerifier::create) //
                                .expectNextCount(1) //
                                .verifyComplete());
    }

    @DisplayName("Should retrieve a group by id")
    @Test
    void retrieve_group_by_id_test() {
        database.sql("""
                        INSERT INTO expense_group (ID, NAME, OWNER) 
                        VALUES( :id, :name, :owner)
                        """
                ).bind("id", GROUP_ID)
                .bind("name", GROUP_NAME)
                .bind("owner", OWNER_ID)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        database.sql("""
                        INSERT INTO group_member (group_id, member ) 
                        VALUES( :groupId, :member)
                        """
                ).bind("groupId", GROUP_ID)
                .bind("member", OWNER_ID)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        database.sql("""
                        INSERT INTO group_member (group_id, member ) 
                        VALUES( :groupId, :member)
                        """
                ).bind("groupId", GROUP_ID)
                .bind("member", MEMBER_1)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        database.sql("""
                        INSERT INTO group_member (group_id, member) 
                        VALUES( :groupId, :member)
                        """
                ).bind("groupId", GROUP_ID)
                .bind("member", MEMBER_2)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        groupStore.retrieve(GROUP_ID)
                .as(StepVerifier::create)
                .expectNext(EXPECTED_GROUP)
                .verifyComplete();

    }


}