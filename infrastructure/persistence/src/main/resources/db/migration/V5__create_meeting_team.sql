-- 미팅 팀 --
create table meeting_team
(
    id             binary(16)   not null,
    team_introduce varchar(255) not null,
    leader_user_id binary(16)   not null,
    member_count   int          not null,
    location       varchar(255) not null,
    primary key (id),
    index (leader_user_id)
);

-- 미팅 팀 멤버 --
create table meeting_team_member
(
    id              binary(16) not null,
    meeting_team_id binary(16) not null,
    user_id         binary(16) not null,
    primary key (id),
    index (meeting_team_id),
    index (user_id)
);
