CREATE TABLE users
 (id INTEGER PRIMARY KEY,
 name VARCHAR,
 age INTEGER,
 driver_licence BOOLEAN,
 car_id INTEGER REFERENCES cars(id)
  );

CREATE TABLE cars
 (id INTEGER PRIMARY KEY,
 brand VARCHAR,
 model VARCHAR,
 price NUMERIC
 );