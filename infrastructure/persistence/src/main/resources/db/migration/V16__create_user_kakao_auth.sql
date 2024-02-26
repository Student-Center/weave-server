create table user_kakao_auth
(
    id       binary(16) primary key,
    user_id  binary(16)   not null,
    kakao_id varchar(255) not null,
    unique unique_user_id (user_id),
    unique unique_kakao_id (kakao_id),
    unique unique_user_id_kakao_id (user_id, kakao_id)
) engine = InnoDB
  default charset = utf8mb4;
