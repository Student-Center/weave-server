create table meeting_team_member_summary
(
    id              binary(16)   not null,
    meeting_team_id binary(16)   not null,
    team_mbti       varchar(255) not null,
    min_age         int          not null,
    max_age         int          not null,
    created_at      timestamp    not null,
    primary key (id),
    unique meeting_team_id (meeting_team_id)
) engine = innodb
  default charset = utf8mb4;
