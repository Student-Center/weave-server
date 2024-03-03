ALTER TABLE weave.university ADD COLUMN display_name varchar(255);

UPDATE weave.university
SET weave.university.display_name = weave.university.name;

ALTER TABLE weave.university MODIFY COLUMN display_name varchar(255) not null;
