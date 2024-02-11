alter table user add column is_univ_verified bool not null default false

create table user_university_verification_info
(
    id                  binary(16)   not null,
    user_id             binary(16)   not null,
    university_id       binary(16)   not null,
    university_email    varchar(255) not null collate utf8mb4_unicode_ci,
    verified_at         datetime(6)  not null,
    primary key (id),
    unique key user_university_verification_info_user_id_uindex (user_id),
    unique key user_university_verification_info_university_email_uindex (university_email)
) engine = InnoDB
  default charset = utf8mb4;
