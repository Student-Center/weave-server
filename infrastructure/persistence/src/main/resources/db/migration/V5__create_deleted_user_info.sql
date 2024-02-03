-- 삭제된 유저 정보 --
create table deleted_user_info
(
    id                    binary(16)   not null primary key,
    email                 varchar(255) not null,
    social_login_provider varchar(255) not null,
    reason                varchar(255),
    registered_at         datetime     not null,
    deleted_at            datetime     not null
);
