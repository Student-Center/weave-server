CREATE TABLE chat_message
(
    id          BINARY(16)   NOT NULL,
    room_id     BINARY(16)   NOT NULL,
    sender_id   BINARY(16)   NOT NULL,
    sender_type VARCHAR(255) NOT NULL,
    created_at  DATETIME     NOT NULL,
    PRIMARY KEY (id),
    INDEX (room_id, sender_id),
    INDEX (created_at)
);

CREATE TABLE chat_message_content
(
    id              BINARY(16)   NOT NULL,
    chat_message_id BINARY(16)   NOT NULL,
    type            VARCHAR(255) NOT NULL,
    value           VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    INDEX (chat_message_id)
);
