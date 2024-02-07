ALTER TABLE user_sil
    DROP INDEX user_id;

ALTER TABLE user_sil
    ADD UNIQUE INDEX user_id_unique (user_id);
