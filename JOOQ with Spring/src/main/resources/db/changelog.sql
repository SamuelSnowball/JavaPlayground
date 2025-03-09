-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE author (
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE book (
  id int NOT NULL AUTO_INCREMENT,
  author_id int NOT NULL,
  title varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (author_id) references author(id)
);

-- INSERT INTO author(1, 'Hello', 'Koding');
-- INSERT INTO author(2, 'jOOQ', 'Example');