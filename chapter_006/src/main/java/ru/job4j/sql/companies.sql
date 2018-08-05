CREATE TABLE company (
  id integer NOT NULL,
  name character varying,
  CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person (
  id integer NOT NULL,
  name character varying,
  company_id integer,
  CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company VALUES
  (1, 'Oracle'),
  (2, 'Google'),
  (5, 'Yandex');

INSERT INTO person VALUES
  (1, 'Aleksandr', 1),
  (2, 'Andrey', 2),
  (3, 'Sergey', 5),
  (4, 'person4', 5),
  (5, 'person5', 1),
  (6, 'person4', 2),
  (7, 'person4', 2),
  (8, 'person4', 5),
  (9, 'person4', 1),
  (10, 'person4', 1),
  (11, 'person4', 1);

SELECT p.name AS person_name, c.name AS company_name
FROM person AS p JOIN company AS c
    ON p.company_id = c.id
WHERE p.company_id != 5;

WITH persons_count AS (
  SELECT c.name AS company_name, COUNT(p.name) AS persons
  FROM person AS p JOIN company AS c
      ON p.company_id = c.id
  GROUP BY company_name)
SELECT company_name, persons
FROM persons_count
WHERE persons = (SELECT MAX(persons) FROM persons_count);