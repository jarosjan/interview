CREATE TABLE tb_project
(
    id      uuid         NOT NULL,
    user_id BIGINT       NOT NULL,
    name    VARCHAR(120) NOT NULL,
    PRIMARY KEY (id, user_id),
    FOREIGN KEY (user_id) REFERENCES tb_user (id)
);