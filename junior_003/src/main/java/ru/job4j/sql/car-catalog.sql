CREATE DATABASE "CarCatalog"
WITH
ENCODING = 'UTF8';

CREATE TABLE body (
  name character varying PRIMARY KEY
);

CREATE TABLE engine (
  name character varying PRIMARY KEY
);

CREATE TABLE transmission (
  name character varying PRIMARY KEY
);

CREATE TABLE car (
  name character varying PRIMARY KEY,
  body character varying REFERENCES body,
  engine character varying REFERENCES engine,
  transmission character varying REFERENCES transmission
);

INSERT INTO body VALUES
  ('Hatchback'),
  ('Sedan'),
  ('SUV'),
  ('Coupe'),
  ('Convertible'),
  ('Wagon'),
  ('Van'),
  ('Jeep');

INSERT INTO engine VALUES
  ('2GR'),
  ('1GR'),
  ('2UZ'),
  ('21127'),
  ('K24W2'),
  ('2GR-FE'),
  ('PE-VPS'),
  ('J35Z');

INSERT INTO transmission VALUES
  ('S7A7'),
  ('U660E'),
  ('SEV'),
  ('K40'),
  ('L48'),
  ('H5'),
  ('S53'),
  ('W57'),
  ('T');

INSERT INTO car VALUES
  ('Camry', 'Sedan', '2GR-FE', 'U660E'),
  ('Accord', 'Sedan', 'K24W2', 'H5'),
  ('Car', 'Hatchback', '21127', 'T');

SELECT c.name, c.body, c.engine, c.transmission FROM car AS c
  INNER JOIN body AS b ON c.body = b.name
  INNER JOIN engine As e ON c.engine = e.name
  INNER JOIN transmission t ON c.transmission = t.name;

-- Детали не использующиеся в машине "Camry"
WITH camry AS (SELECT * FROM car WHERE name = 'Camry')
SELECT b.name AS car_part FROM body AS b
  LEFT OUTER JOIN camry AS c ON b.name = c.body
WHERE c.name IS NULL
UNION All
    SELECT e.name AS car_part FROM engine AS e
      LEFT OUTER JOIN camry AS c ON e.name = c.engine
    WHERE c.name IS NULL
UNION ALL
    SELECT t.name AS car_part FROM transmission AS t
      LEFT OUTER JOIN camry AS c ON t.name = c.transmission
    WHERE c.name IS NULL;







