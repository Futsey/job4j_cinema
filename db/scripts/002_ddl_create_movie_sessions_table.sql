CREATE TABLE if not exists movie_sessions (
   id SERIAL PRIMARY KEY,
   poster bytea,
   movietitle TEXT NOT NULL,
   created timestamp
);