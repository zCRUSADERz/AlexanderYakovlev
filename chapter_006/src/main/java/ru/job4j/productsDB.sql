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

SELECT product.name, type.name AS type_name, expired_date, price
FROM product INNER JOIN (SELECT * FROM type WHERE name = 'СЫР') AS type
    ON product.type_id = type.id;

SELECT * FROM product
WHERE name ILIKE 'мороженное'
      OR name ILIKE 'мороженное %'
      OR name ILIKE '% мороженное %'
      OR name ILIKE '% мороженное,%'
      OR name ILIKE '% мороженное.'
      OR name ILIKE '% мороженное';

SELECT * FROM product
WHERE EXTRACT(EPOCH FROM expired_date) - EXTRACT(EPOCH FROM now()) <= 2592000
      AND EXTRACT(EPOCH FROM expired_date) - EXTRACT(EPOCH FROM now()) > 0;

SELECT * FROM product
WHERE (SELECT MAX(price) FROM product) = product.price;

SELECT COUNT(product.name)
FROM product INNER JOIN (SELECT * FROM type WHERE name = 'СЫР') AS type
    ON product.type_id = type.id;

SELECT product.id, product.name, type.name AS type_name, expired_date, price
FROM product INNER JOIN (SELECT * FROM type WHERE name IN ('СЫР', 'МОЛОКО')) AS type
    ON product.type_id = type.id;

SELECT name, p_count
FROM (SELECT  type.name, COUNT(product.name) AS p_count
      FROM product INNER JOIN type
          ON product.type_id = type.id
      GROUP BY type.id) AS count
WHERE p_count < 10;

SELECT product.name, type.name AS type_name, product.expired_date, product.price
FROM product INNER JOIN type ON product.type_id = type.id;