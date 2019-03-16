DELETE FROM cities
WHERE id IN (
  SELECT id FROM cities
    EXCEPT
  SELECT MIN(id) FROM cities
  GROUP BY name
);