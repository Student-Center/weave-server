INSERT INTO meeting_team(id, team_introduce, member_count, location, status, gender)
VALUES (UNHEX(REPLACE('1312d9d2-01aa-42b3-bbe1-04dc81a2ffdb', '-', '')), '졸려', 2, 'BUSAN',
        'WAITING', 'WOMAN');

INSERT INTO meeting_member(id, meeting_team_id, user_id, role)
VALUES (UNHEX(REPLACE('a2370f3a-d8a6-4f7e-a7e4-3421e0b60d39', '-', '')),
        UNHEX(REPLACE('1312d9d2-01aa-42b3-bbe1-04dc81a2ffdb', '-', '')),
        UNHEX(REPLACE('0b2a4583-ddd2-11ee-a9bb-0242ac12358b', '-', '')), 'LEADER'),
       (UNHEX(REPLACE('a2380f3a-d8a6-4f7e-a7e4-3425e0b60d40', '-', '')),
        UNHEX(REPLACE('1312d9d2-01aa-42b3-bbe1-04dc81a2ffdb', '-', '')),
        UNHEX(REPLACE('0b2a4583-ddd2-11ee-a9bb-0242ac12358e', '-', '')), 'MEMBER');

INSERT INTO meeting_team_member_summary(id, meeting_team_id, team_mbti, youngest_member_birth_year,
                                        oldest_member_birth_year, created_at)
VALUES (UNHEX(REPLACE('8357a478-4d87-4f69-8d4d-d84b4f8eb9fb', '-', '')),
        UNHEX(REPLACE('1312d9d2-01aa-42b3-bbe1-04dc81a2ffdb', '-', '')), 'INFP', 2003, 2001,
        '2024-02-14 01:15:52.445563')
