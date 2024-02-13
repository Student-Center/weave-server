drop table weave.meeting_team_member;
create table weave.meeting_member
(
    id              binary(16)   not null,
    meeting_team_id binary(16)   not null,
    user_id         binary(16)   not null,
    role            varchar(255) not null,
    primary key (id),
    index meeting_member_meeting_team_id_index (meeting_team_id),
    index meeting_member_user_id_index (user_id),
    unique unique_meeting_member (meeting_team_id, user_id)
);

alter table weave.meeting_team
    drop column leader_user_id;
