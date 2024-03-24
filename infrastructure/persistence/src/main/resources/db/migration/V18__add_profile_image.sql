alter table `user`
    drop column avatar;

create table user_profile_image
(
    id        binary(16)   not null,
    user_id   binary(16)   not null,
    extension varchar(255) not null,
    image_url varchar(255) not null,
    primary key (id),
    index user_profile_image_user_id_index (user_id)
);
