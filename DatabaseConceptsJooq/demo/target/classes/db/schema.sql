-- supermarket schema and items table with basic fields

CREATE SCHEMA IF NOT EXISTS supermarket;

CREATE TABLE IF NOT EXISTS supermarket.items (
    id          BIGSERIAL       PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2)  NOT NULL DEFAULT 0.00,
    quantity    INTEGER         NOT NULL DEFAULT 0,
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);
