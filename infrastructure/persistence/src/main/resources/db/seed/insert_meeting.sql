INSERT INTO weave.user (id, nickname, email, gender, mbti, birth_year, university_id, major_id,
                        registered_at, updated_at, is_univ_verified)
VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000001'), '김철수', 'chulsu@example.com', 'MAN',
        'INTJ', 1995, UUID_TO_BIN('018d7782-f10e-73d3-8ebf-e12fbbec0b17'),
        UUID_TO_BIN('018d79c8-fdff-7921-8111-000bc8c9e559'), NOW(), NOW(), 1),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), '박민수', 'younghee@example.com',
        'MAN', 'ENFP', 1998, UUID_TO_BIN('018d7782-f10e-73d3-8ebf-e12fbbec0b17'),
        UUID_TO_BIN('018d79c8-fdff-7921-8111-000bc8c9e559'), NOW(), NOW(), 1),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000003'), '윤성원', 'minsu@example.com', 'WOMAN',
        'ISTJ', 1996, UUID_TO_BIN('018d7782-f10e-73d3-8ebf-e12fbbec0b18'),
        UUID_TO_BIN('018d79c8-fe02-7cff-b453-97ef1fdbc6da'), NOW(), NOW(), 1),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000004'), '최지은', 'jieun@example.com', 'WOMAN',
        'ESFJ', 1999, UUID_TO_BIN('018d7782-f10e-73d3-8ebf-e12fbbec0b18'),
        UUID_TO_BIN('018d79c8-fe02-7cff-b453-97ef1fdbc6da'), NOW(), NOW(), 1);

INSERT INTO weave.meeting_team (id, team_introduce, member_count, location, status, gender)
VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000001'), '함께 즐거운 시간 보내실 분들 모집합니다!', 2,
        'KONDAE_SEONGSU', 'PUBLISHED', 'MALE'),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), '맛집 투어 하실 분들 구해요~', 2,
        'KONDAE_SEONGSU', 'PUBLISHED', 'FEMALE');

INSERT INTO weave.meeting_member (id, meeting_team_id, user_id, role)
VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000001'), 'LEADER'),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000002'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000003'), 'MEMBER'),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000003'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000002'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), 'LEADER'),
       (UUID_TO_BIN('00000000-0000-0000-0000-000000000004'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000002'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000004'), 'MEMBER');

INSERT INTO weave.meeting (id, receiving_team_id, requesting_team_id, status, created_at,
                           finished_at)
VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), 'COMPLETED', NOW(), NOW());

INSERT INTO weave.chat_room (id, meeting_id, requesting_team_id, receiving_team_id, created_at)
VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000001'),
        UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), NOW());
