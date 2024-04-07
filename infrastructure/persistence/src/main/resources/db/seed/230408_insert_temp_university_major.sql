-- university
INSERT INTO university(id, name, display_name, domain_address, created_at, updated_at)
VALUES
    (UNHEX(REPLACE('018d7782-f110-76d9-b27f-99a11f11b029','-','')),'위브대학교','위브대','gmail.com','2023-04-08T00:00', '2023-04-08T00:00');

-- major
INSERT INTO major(id, univ_id, name, created_at)
VALUES
    (UNHEX(REPLACE('018d7a0a-2af7-75de-9c79-0758d4be6156','-','')),UNHEX(REPLACE('018d7782-f110-76d9-b27f-99a11f11b029','-','')),'위브학과','2023-04-08T00:10');
