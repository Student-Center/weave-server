-- 유저
INSERT INTO user(id, nickname, email, gender, mbti, birth_year, university_id, major_id, height,
                 animal_type, kakao_id, is_univ_verified, registered_at, updated_at)
VALUES (UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')), '남자1호',
        'man1@team-weave.me', 'MAN', 'ESTJ', 2000,
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man1', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563');

-- 유저 auth info
INSERT INTO user_auth_info(id, user_id, email, social_login_provider, registered_at)
VALUES (UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')), 'man1@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563');

-- 유저 sil
INSERT INTO user_sil(id, user_id, amount)
VALUES (UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')), 120);

-- 학교 인증
INSERT INTO user_university_verification_info(id, user_id, university_id, university_email, verified_at)
VALUES (UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018df06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man3@snu.ac.kr',
        '2024-02-16 01:15:52.445563');
