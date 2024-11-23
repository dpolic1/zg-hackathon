CREATE TABLE events
(
    id           SERIAL PRIMARY KEY,
    short_name   VARCHAR(255),
    long_name    VARCHAR(255),
    english_name VARCHAR(255)
);