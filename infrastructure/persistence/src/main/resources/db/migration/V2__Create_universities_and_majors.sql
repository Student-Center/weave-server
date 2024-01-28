-- 대학 정보 --
create table university
(
    id              binary(16)   not null,
    name            varchar(255) not null,
    domain_address  varchar(255) not null,
    logo_address    varchar(255) not null,
    created_at      datetime(6)  not null,
    updated_at      datetime(6)  not null,
    primary key (id),
    unique key university_name_uindex (name),
) engine = InnoDB
  default charset = utf8mb4;

-- 학과 정보 --
create table major
(
    id          binary(16)   not null,
    univ_id     binary(16)   not null,
    name        varchar(255) not null,
    created_at  datetime(6)  not null,
    primary key (id),
    unique key major_univ_id_and_name_uindex (univ_id, name),
    ) engine = InnoDB
    default charset = utf8mb4;
