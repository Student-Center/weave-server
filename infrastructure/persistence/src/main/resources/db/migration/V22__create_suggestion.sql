create table suggestion
(
    id       binary(16) primary key,
    user_id  binary(16) not null,
    contents text       not null
) engine = InnoDB,
  char set utf8mb4;
