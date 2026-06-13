--Dimensiones: Para nuestro modelo estrella vamos a definir las 

--Dimensión Película
CREATE TABLE dim_film (
  film_key   SERIAL   PRIMARY KEY,
  film_id    INT      NOT NULL,     -- clave natural
  title      TEXT     NOT NULL,
  category   TEXT,                   -- nombre de categoría
  actors     TEXT,                    -- lista concatenada de actores
  UNIQUE (film_id)
);

--Dimensión Fecha 
CREATE TABLE dim_date (
  date_key   SERIAL   PRIMARY KEY,
  full_date  DATE     NOT NULL,
  day        INT      NOT NULL,
  month      INT      NOT NULL,
  year       INT      NOT NULL,
  UNIQUE (full_date)
);

--Dimensión Lugar
CREATE TABLE dim_location (
  location_key  SERIAL   PRIMARY KEY,
  address_id    INT      NOT NULL,   -- clave natural de address
  city          TEXT,                -- nombre de ciudad
  country       TEXT,                 -- nombre de país
  UNIQUE (address_id)
);

--Dimensión Sucursal
CREATE TABLE dim_store (
  store_key        SERIAL   PRIMARY KEY,
  store_id         INT      NOT NULL,   -- clave natural de store
  manager_staff_id INT,                  -- ID del staff a cargo
  address_id       INT,                   -- referencia a la dirección
  UNIQUE (store_id)
);

--Tabla de Hechos
-- Tabla de Hechos: Rentals
CREATE TABLE fact_rental (
  fact_key       SERIAL      PRIMARY KEY,
  date_key       INT         NOT NULL  REFERENCES dim_date(date_key),
  film_key       INT         NOT NULL  REFERENCES dim_film(film_key),
  location_key   INT         NOT NULL  REFERENCES dim_location(location_key),
  store_key      INT         NOT NULL  REFERENCES dim_store(store_key),
  rental_count   INT         NOT NULL,
  total_amount   NUMERIC(12,2) NOT NULL,

  --Lei que esto mejora la unicidad:
  UNIQUE (date_key, film_key, location_key, store_key)
);
 
--Función: load_dim_film()
--Descripción: Poblar la dimension de film
--Retorno: cant de lineas agregadas, para saber que tan cargada va la dim
CREATE OR REPLACE FUNCTION load_dim_film()
RETURNS INT LANGUAGE plpgsql AS $$
DECLARE
  rows_ins INT;
BEGIN
  INSERT INTO dim_film (film_id, title, category, actors)
    SELECT fl.fid, fl.title, fl.category, fl.actors
      FROM public.film_list AS fl
  ON CONFLICT (film_id) DO UPDATE
    SET title    = EXCLUDED.title,
        category = EXCLUDED.category,
        actors   = EXCLUDED.actors;
  GET DIAGNOSTICS rows_ins = ROW_COUNT;
  RETURN rows_ins;
END;
$$;


--Función: load_dim_date()
--Descripción: Poblar la dimension de fecha
--Retorno: cant de lineas agregadas, para saber que tan cargada va la dim
CREATE OR REPLACE FUNCTION load_dim_date()
RETURNS INT LANGUAGE plpgsql AS $$
DECLARE
  rows_ins INT;
BEGIN
  INSERT INTO dim_date (full_date, day, month, year)
    SELECT DISTINCT CAST(r.rental_date AS DATE),
           EXTRACT(DAY   FROM r.rental_date)::INT,
           EXTRACT(MONTH FROM r.rental_date)::INT,
           EXTRACT(YEAR  FROM r.rental_date)::INT
      FROM public.rental AS r
  ON CONFLICT (full_date) DO NOTHING;
  GET DIAGNOSTICS rows_ins = ROW_COUNT;
  RETURN rows_ins;
END;
$$;

--Función: load_dim_location()
--Descripción: Poblar la dimension de lugares
--Retorno: cant de lineas agregadas, para saber que tan cargada va la dim
CREATE OR REPLACE FUNCTION load_dim_location()
RETURNS INT LANGUAGE plpgsql AS $$
DECLARE
  rows_ins INT;
BEGIN
  INSERT INTO dim_location (address_id, city, country)
    SELECT a.address_id, c.city, cy.country
      FROM public.address AS a
      JOIN public.city    AS c  ON a.city_id    = c.city_id
      JOIN public.country AS cy ON c.country_id = cy.country_id
  ON CONFLICT (address_id) DO UPDATE
    SET city    = EXCLUDED.city,
        country = EXCLUDED.country;
  GET DIAGNOSTICS rows_ins = ROW_COUNT;
  RETURN rows_ins;
END;
$$;


--Función: load_dim_store()
--Descripción: Poblar la dimension de tienda
--Retorno: cant de lineas agregadas, para saber que tan cargada va la dim
CREATE OR REPLACE FUNCTION load_dim_store()
RETURNS INT LANGUAGE plpgsql AS $$
DECLARE
  rows_ins INT;
BEGIN
  INSERT INTO dim_store (store_id, manager_staff_id, address_id)
    SELECT s.store_id, s.manager_staff_id, s.address_id
      FROM public.store AS s
  ON CONFLICT (store_id) DO UPDATE
    SET manager_staff_id = EXCLUDED.manager_staff_id,
        address_id       = EXCLUDED.address_id;
  GET DIAGNOSTICS rows_ins = ROW_COUNT;
  RETURN rows_ins;
END;
$$;

--Función: load_fact_rental()
--Descripción: Poblar la tabla de hechos
--Retorno: cant de lineas agregadas, para saber que tan cargada va la tabla de hechos
CREATE OR REPLACE FUNCTION load_fact_rental()
RETURNS INT LANGUAGE plpgsql AS $$
DECLARE
  rows_ins INT;
BEGIN
  INSERT INTO fact_rental (date_key, film_key, location_key, store_key, rental_count, total_amount)
    SELECT 
      dd.date_key,
      df.film_key,
      dl.location_key,
      ds.store_key,
      COUNT(*)           AS rental_count,
      SUM(p.amount)::NUMERIC(12,2) AS total_amount
    FROM public.rental   AS r
    JOIN public.payment  AS p ON r.rental_id = p.rental_id
    JOIN dim_date        AS dd ON dd.full_date      = CAST(r.rental_date AS DATE)
    JOIN public.inventory AS i ON r.inventory_id   = i.inventory_id
    JOIN dim_film        AS df ON df.film_id       = i.film_id
    JOIN dim_store       AS ds ON ds.store_id      = i.store_id
    JOIN dim_location    AS dl ON dl.address_id    = ds.address_id
   GROUP BY dd.date_key, df.film_key, dl.location_key, ds.store_key
  ON CONFLICT (date_key, film_key, location_key, store_key) DO UPDATE
    SET rental_count = EXCLUDED.rental_count,
        total_amount = EXCLUDED.total_amount;
  GET DIAGNOSTICS rows_ins = ROW_COUNT;
  RETURN rows_ins;
END;
$$;

SELECT load_dim_date();
SELECT load_dim_film();
SELECT load_dim_location();
SELECT load_dim_store();
SELECT load_fact_rental();

SELECT COUNT(*) FROM dim_date;
SELECT COUNT(*) FROM dim_film;
SELECT COUNT(*) FROM dim_location;
SELECT COUNT(*) FROM dim_store;
SELECT COUNT(*) AS registros_de_venta FROM fact_rental;

SELECT * FROM dim_date ORDER BY full_date DESC LIMIT 5;
SELECT * FROM fact_rental ORDER BY total_amount DESC LIMIT 5;

