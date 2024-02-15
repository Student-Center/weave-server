create table weave.meeting_request
(
    id              binary(16)   not null,
    requestingMeetingTeamId binary(16)   not null,
    receivingMeetingTeamId binary(16)   not null,
    status varchar(255) not null,
    createdAt datetime(6)  not null,
    respondAt datetime(6),
    primary key (id),
    index requesting_meeting_team_id_index (requestingMeetingTeamId),
    index receiving_meeting_team_id_index (receivingMeetingTeamId)
);
