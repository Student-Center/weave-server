-- 유저 인증 정보 --
create table user_auth_info
(
    id                    binary(16)   not null,
    user_id               binary(16)   not null,
    email                 varchar(255) not null,
    social_login_provider varchar(255) not null,
    registered_at         datetime(6)  not null,
    primary key (id),
    unique key user_auth_info_user_id_uindex (user_id),
    unique key user_auth_info_email_uindex (email)
) engine = InnoDB
  default charset = utf8mb4;
