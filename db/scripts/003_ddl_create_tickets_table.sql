CREATE TABLE if not exists tickets (
    id SERIAL PRIMARY KEY,
    visitor_id INT NOT NULL REFERENCES visitors(id),
    session_id INT NOT NULL REFERENCES movie_sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL
);