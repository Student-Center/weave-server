-- 유저
INSERT INTO user(id, nickname, email, gender, mbti, birth_year, university_id, major_id, height,
                 animal_type, kakao_id, is_univ_verified, registered_at, updated_at)
VALUES (UNHEX(REPLACE('018db06e-c6d8-7407-07d0-77a1dff25b2a', '-', '')), '남자1', 'man1@snu.ac.kr',
        'MAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man1', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-17d0-77a1dff25b2a', '-', '')), '남자2', 'man2@snu.ac.kr',
        'MAN', 'ISFJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man2', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-27d0-77a1dff25b2a', '-', '')), '남자3', 'man3@snu.ac.kr',
        'MAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man3', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-37d0-77a1dff25b2a', '-', '')), '남자4', 'man4@snu.ac.kr',
        'MAN', 'ESFP', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man4', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-47d0-77a1dff25b2a', '-', '')), '남자5', 'man5@snu.ac.kr',
        'MAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man5', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-57d0-77a1dff25b2a', '-', '')), '남자6', 'man6@snu.ac.kr',
        'MAN', 'ENFJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man6', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-67d0-77a1dff25b2a', '-', '')), '남자7', 'man7@snu.ac.kr',
        'MAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man7', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-77d0-77a1dff25b2a', '-', '')), '남자8', 'man8@snu.ac.kr',
        'MAN', 'ISFJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man8', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')), '남자9', 'man9@snu.ac.kr',
        'MAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man9', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-97d0-77a1dff25b2a', '-', '')), '남자0', 'man0@snu.ac.kr',
        'MAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 181, 'PUPPY',
        'kakao-id-man0', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8700-77a1dff25b2a', '-', '')), '여자1', 'woman1@snu.ac.kr',
        'WOMAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman1', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8710-77a1dff25b2a', '-', '')), '여자2', 'woman2@snu.ac.kr',
        'WOMAN', 'INTP', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman2', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8720-77a1dff25b2a', '-', '')), '여자3', 'woman3@snu.ac.kr',
        'WOMAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman3', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8730-77a1dff25b2a', '-', '')), '여자4', 'woman4@snu.ac.kr',
        'WOMAN', 'ENTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman4', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8740-77a1dff25b2a', '-', '')), '여자5', 'woman5@snu.ac.kr',
        'WOMAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman5', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8750-77a1dff25b2a', '-', '')), '여자6', 'woman6@snu.ac.kr',
        'WOMAN', 'ISTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman6', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8760-77a1dff25b2a', '-', '')), '여자7', 'woman7@snu.ac.kr',
        'WOMAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman7', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8770-77a1dff25b2a', '-', '')), '여자8', 'woman8@snu.ac.kr',
        'WOMAN', 'INTP', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman8', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8780-77a1dff25b2a', '-', '')), '여자9', 'woman9@snu.ac.kr',
        'WOMAN', 'ESTJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman9', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8790-77a1dff25b2a', '-', '')), '여자0', 'woman0@snu.ac.kr',
        'WOMAN', 'INFJ', 2000, UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')),
        UNHEX(REPLACE('018d79c8-fe04-72fb-b929-b81766d945a9', '-', '')), 160, 'PUPPY',
        'kakao-id-woman0', true, '2024-02-14 01:15:52.445563', '2024-02-14 01:15:52.445563');

-- 유저 auth info
INSERT INTO user_auth_info(id, user_id, email, social_login_provider, registered_at)
VALUES (UNHEX(REPLACE('018db06e-06d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-07d0-77a1dff25b2a', '-', '')), 'man1@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-16d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-17d0-77a1dff25b2a', '-', '')), 'man2@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-26d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-27d0-77a1dff25b2a', '-', '')), 'man3@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-36d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-37d0-77a1dff25b2a', '-', '')), 'man4@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-46d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-47d0-77a1dff25b2a', '-', '')), 'man5@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-56d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-57d0-77a1dff25b2a', '-', '')), 'man6@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-66d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-67d0-77a1dff25b2a', '-', '')), 'man7@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-76d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-77d0-77a1dff25b2a', '-', '')), 'man8@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-86d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')), 'man9@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-87d0-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-97d0-77a1dff25b2a', '-', '')), 'man0@snu.ac.kr', 'KAKAO',
        '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8700-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8700-77a1dff25b2a', '-', '')), 'woman1@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8710-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8710-77a1dff25b2a', '-', '')), 'woman2@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8720-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8720-77a1dff25b2a', '-', '')), 'woman3@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8730-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8730-77a1dff25b2a', '-', '')), 'woman4@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8740-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8740-77a1dff25b2a', '-', '')), 'woman5@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8750-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8750-77a1dff25b2a', '-', '')), 'woman6@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8760-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8760-77a1dff25b2a', '-', '')), 'woman7@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8770-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8770-77a1dff25b2a', '-', '')), 'woman8@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8780-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8780-77a1dff25b2a', '-', '')), 'woman9@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-96d8-7407-8790-77a1dff25b3a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8790-77a1dff25b2a', '-', '')), 'woman0@snu.ac.kr',
        'KAKAO', '2024-02-14 01:15:52.445563');

-- 유저 sil
INSERT INTO user_sil(id, user_id, amount)
VALUES (UNHEX(REPLACE('018db06e-c6d8-7407-07d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-07d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-17d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-17d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-27d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-27d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-37d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-37d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-47d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-47d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-57d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-57d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-67d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-67d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-77d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-77d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-87d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-97d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-97d0-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8700-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8700-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8710-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8710-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8720-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8720-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8730-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8730-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8740-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8740-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8750-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8750-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8760-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8760-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8770-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8770-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8780-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8780-77a1dff25b2a', '-', '')), 120),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8790-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8790-77a1dff25b2a', '-', '')), 120);

-- 학교 인증
INSERT INTO user_university_verification_info(id, user_id, university_id, university_email, verified_at)
VALUES (UNHEX(REPLACE('018db06e-c6d8-7407-07d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-07d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man1@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-17d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-17d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man2@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-27d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-27d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man3@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-37d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-37d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man4@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-47d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-47d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man5@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-57d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-57d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man6@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-67d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-67d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man7@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-77d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-77d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man8@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-87d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-87d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man9@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-97d0-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-97d0-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'man0@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8700-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8700-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman1@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8710-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8710-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman2@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8720-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8720-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman3@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8730-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8730-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman4@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8740-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8740-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman5@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8750-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8750-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman6@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8760-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8760-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman7@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8770-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8770-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman8@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8780-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8780-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman9@snu.ac.kr',
        '2024-02-16 01:15:52.445563'),
       (UNHEX(REPLACE('018db06e-c6d8-7407-8790-77a1dff25b4a', '-', '')),
        UNHEX(REPLACE('018db06e-c6d8-7407-8790-77a1dff25b2a', '-', '')),
        UNHEX(REPLACE('018d7782-f10e-73d3-8ebf-e12fbbec0b1b', '-', '')), 'woman0@snu.ac.kr',
        '2024-02-16 01:15:52.445563');
