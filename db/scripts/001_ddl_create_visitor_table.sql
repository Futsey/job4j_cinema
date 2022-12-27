CREATE TABLE if not exists visitors (
   id SERIAL PRIMARY KEY,
   userName TEXT NOT NULL,
   password TEXT NOT NULL,
   email TEXT NOT NULL UNIQUE,
   phoneNumber TEXT NOT NULL UNIQUE,
   created timestamp
);