CREATE DATABASE "Products"
WITH
ENCODING = 'UTF8';

CREATE TABLE product (
  id serial PRIMARY KEY,
  name text NOT NULL,
  type_id integer REFERENCES type,
  expired_date date NOT NULL,
  price money
);

CREATE TABLE type (
  id serial PRIMARY KEY,
  name text NOT NULL
);

INSERT INTO type VALUES
  (0, 'Тип 0'),
  (1, 'Тип 1'),
  (2, 'Тип 2'),
  (3, 'СЫР'),
  (4, 'МОЛОКО');

INSERT INTO product VALUES
  (0, 'Продукт 0', 0, '2020-01-21', 123),
  (1, 'Продукт мороженное', 1, '2018-07-01', 47),
  (2, 'Замороженное филе', 2, '2019-07-01', 980),
  (3, 'Продукт 3', 3, '2020-01-21', 123),
  (4, 'Продукт 4', 3, '2020-07-15', 123),
  (5, 'Продукт 5', 0, '2025-08-07', 123),
  (6, 'Продукт 6', 1, '2018-06-25', 123),
  (7, 'Продукт 7', 1, '2018-06-25', 123),
  (8, 'Продукт 8', 4, '2018-09-11', 55),
  (9, 'Продукт 9', 4, '2018-08-23', 32);

SELECT p.name, t.name AS type_name, p.expired_date, p.price
FROM product AS p INNER JOIN (SELECT * FROM type WHERE name = 'СЫР') AS t
    ON p.type_id = t.id;

SELECT * FROM product
WHERE name ILIKE  'мороженное'
      OR name ILIKE 'мороженное %'
      OR name ILIKE '% мороженное %'
      OR name ILIKE '% мороженное,%'
      OR name ILIKE '% мороженное.'
      OR name ILIKE '% мороженное';

SELECT * FROM product
  WHERE date_trunc('month', expired_date)
        = date_trunc('month', now() + interval '1 months');

SELECT * FROM product
WHERE (SELECT MAX(price) FROM product) = product.price;

SELECT COUNT(p.name)
FROM product AS p INNER JOIN (SELECT * FROM type WHERE name = 'СЫР') AS t
    ON p.type_id = t.id;

SELECT p.id, p.name, t.name AS type_name, p.expired_date, p.price
FROM product AS p INNER JOIN
  (SELECT * FROM type WHERE name IN ('СЫР', 'МОЛОКО')) AS t
    ON p.type_id = t.id;

SELECT  t.name, COUNT(p.name)
FROM product AS p INNER JOIN type AS t
    ON p.type_id = t.id
GROUP BY t.id
HAVING  COUNT(p.name) < 10;

SELECT p.name, t.name AS type_name, p.expired_date, p.price
FROM product AS p INNER JOIN type AS t
    ON p.type_id = t.id;