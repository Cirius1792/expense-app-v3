CREATE TABLE expense ( id VARCHAR PRIMARY KEY,
                        description VARCHAR(255),
                        amount VARCHAR(65) NOT NULL,
                        owner_id VARCHAR(100) NOT NULL,
                        group_id VARCHAR(100) NOT NULL
                        );
CREATE TABLE person (
                        id VARCHAR PRIMARY KEY,
                        username VARCHAR(255)
                        );
CREATE TABLE expense_group (
    id VARCHAR PRIMARY KEY,
    name VARCHAR(255),
    OWNER VARCHAR(100) NOT NULL
    );
CREATE TABLE group_member (
    group_id VARCHAR,
    member VARCHAR(100),
    PRIMARY KEY (group_id, member)
    );


