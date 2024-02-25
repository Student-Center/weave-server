create table weave.meeting
(
    id                 binary(16)   not null,
    requesting_team_id binary(16)   not null,
    receiving_team_id  binary(16)   not null,
    status             varchar(255) not null,
    created_at         datetime(6)  not null,
    finished_at        datetime(6),
    primary key (id),
    index meeting_requesting_meeting_team_id_index (requesting_team_id),
    index meeting_receiving_meeting_team_id_index (receiving_team_id),
    unique unique_meeting_request (requesting_team_id, receiving_team_id)
);

create table weave.meeting_attendance
(
    id                binary(16)  not null,
    meeting_id        binary(16)  not null,
    meeting_member_id binary(16)  not null,
    is_attend         boolean     not null,
    created_at        datetime(6) not null,
    updated_at        datetime(6) not null,
    primary key (id),
    index meeting_attendance_meeting_id_index (meeting_id),
    index meeting_attendance_member_id_index (meeting_member_id),
    unique unique_meeting_attendance (meeting_id, meeting_member_id)
);

