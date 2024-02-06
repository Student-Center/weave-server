create table user_sil
(
    id      binary(16) not null,
    user_id binary(16) not null,
    amount  int        not null,
    primary key (id),
    index (user_id)
)
