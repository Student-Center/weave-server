create table meeting_team_member_summary
(
    id                         binary(16)   not null,
    meeting_team_id            binary(16)   not null,
    team_mbti                  varchar(255) not null,
    youngest_member_birth_year int          not null,
    oldest_member_birth_year   int          not null,
    created_at                 timestamp    not null,
    primary key (id),
    unique meeting_team_id (meeting_team_id)
) engine = innodb
  default charset = utf8mb4;
