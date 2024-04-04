create table chat_room
(
    id                 binary(16),
    meeting_id         binary(16),
    receiving_team_id  binary(16),
    requesting_team_id binary(16),
    created_at         timestamp,
    primary key (id),
    index (meeting_id),
    index (receiving_team_id),
    index (requesting_team_id)
) engine = InnoDB
  default charset = utf8mb4;
