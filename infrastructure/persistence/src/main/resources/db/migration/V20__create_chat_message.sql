CREATE TABLE chat_message
(
    id          BINARY(16)   NOT NULL,
    room_id     BINARY(16)   NOT NULL,
    sender_id   BINARY(16)   NOT NULL,
    sender_type VARCHAR(255) NOT NULL,
    contents    JSON         NOT NULL,
    created_at  DATETIME     NOT NULL,
    PRIMARY KEY (id),
    INDEX (room_id),
    INDEX (sender_id),
    INDEX (created_at)
);
