CREATE TABLE if not exists tickets (
    id SERIAL PRIMARY KEY,
    visitor_id INT NOT NULL REFERENCES visitors(id),
    movie_session_id INT NOT NULL REFERENCES movie_sessions(id),
    pos_row INT NOT NULL,
    pos_cell INT NOT NULL
);