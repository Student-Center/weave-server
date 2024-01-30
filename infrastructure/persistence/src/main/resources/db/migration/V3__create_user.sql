-- 유저 --
create table `user`
(
    id            binary(16)   not null,
    nickname      varchar(255) not null,
    email         varchar(255) not null,
    gender        varchar(255) not null,
    mbti          varchar(255) not null,
    birth_year    integer      not null,
    university_id binary(16)   not null,
    major_id      binary(16)   not null,
    avatar        varchar(255),
    registered_at datetime(6)  not null,
    updated_at    datetime(6)  not null,
    primary key (id),
    index idx_university_id (university_id),
    index idx_major_id (major_id)
) engine = InnoDB
  default charset = utf8mb4;
