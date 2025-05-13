DROP SCHEMA IF EXISTS getirtestdb;
CREATE SCHEMA IF NOT EXISTS getirtestdb;

DROP TABLE borrow_records IF EXISTS;
DROP TABLE Book IF EXISTS;

CREATE TABLE Book
(
    id           UUID PRIMARY KEY,
    title        VARCHAR(255),
    author       VARCHAR(255),
    isbn         VARCHAR(255),
    quantity     INT,
    is_available BOOLEAN
);
