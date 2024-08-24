CREATE TABLE tb_user
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(200) NOT NULL,
    password VARCHAR(129) NOT NULL,
    name     VARCHAR(120) NULL
);
