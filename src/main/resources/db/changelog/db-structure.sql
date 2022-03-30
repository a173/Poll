--liquibase formatted sql

--changeset author:morozkin-ai:create-t_user-table
CREATE TABLE t_user (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL
);
--rollback DROP TABLE t_user;

--changeset author:morozkin-ai:create-t_role-table
CREATE TABLE t_role (
    title VARCHAR(255) PRIMARY KEY
);
--rollback DROP TABLE t_role;

--changeset author:morozkin-ai:create-t_user_role_link-table
CREATE TABLE t_user_role_link (
    user_id BIGINT REFERENCES t_user(id),
    role VARCHAR(255) REFERENCES t_role(title),
    PRIMARY KEY (user_id, role)
);
--rollback DROP TABLE t_user_role_link;

--changeset author:morozkin-ai:add-roles
INSERT INTO
    t_role
VALUES
    ('USER'),
    ('ADMIN');
--rollback TRUNCATE t_role;

--changeset author:morozkin-ai:add-admin
CREATE extension IF NOT EXISTS pgcrypto;
INSERT INTO
    t_user (id, username, password)
VALUES
    (1, 'admin', crypt('123', gen_salt('bf', 8)));

INSERT INTO
    t_user_role_link(user_id, role)
VALUES
    (1, 'USER'),
    (1, 'ADMIN');
--rollback TRUNCATE t_user;
--rollback TRUNCATE t_user_role;

--changeset author:morozkin-ai:create-t_poll-table
CREATE TABLE t_poll (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date_from TIMESTAMPTZ NOT NULL,
    date_to TIMESTAMPTZ NOT NULL,
    description TEXT NOT NULL
);
--rollback DROP TABLE t_poll;

--changeset author:morozkin-ai:create-t_user_poll_link-table
CREATE TABLE t_user_poll_link (
    user_id BIGINT REFERENCES t_user(id),
    poll_id BIGINT REFERENCES t_poll(id),
    PRIMARY KEY (user_id, poll_id)
);
--rollback DROP TABLE t_user_poll_link;

--changeset author:morozkin-ai:create-t_question_type-table
CREATE TABLE t_question_type (
    type VARCHAR(255) PRIMARY KEY
);
--rollback DROP TABLE t_question_type;

--changeset author:morozkin-ai:add-question_type
INSERT INTO
    t_question_type
VALUES
    ('SINGLE'),
    ('MULTIPLE'),
    ('OPEN')
--rollback TRUNCATE t_question_type;

--changeset author:morozkin-ai:create-t_question-table
CREATE TABLE t_question (
    id BIGSERIAL PRIMARY KEY,
    body TEXT NOT NULL,
    poll_id BIGINT REFERENCES t_poll(id),
    type VARCHAR(255) REFERENCES t_question_type(type)
);
--rollback DROP TABLE t_question;

--changeset author:morozkin-ai:create-t_answer-table
CREATE TABLE t_answer (
    id BIGSERIAL PRIMARY KEY,
    body TEXT NOT NULL,
    question_id BIGINT REFERENCES t_question(id)
);
--rollback DROP TABLE t_answer;

--changeset author:morozkin-ai:create-t_user_answer_link-table
CREATE TABLE t_user_answer_link (
    user_id BIGINT REFERENCES t_user(id),
    answer_id BIGINT REFERENCES t_answer(id),
    PRIMARY KEY (user_id, answer_id)
);
--rollback DROP TABLE t_user_answer_link;