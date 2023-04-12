DROP TABLE IF EXISTS planes CASCADE;
DROP TABLE IF EXISTS flights CASCADE;
DROP TABLE IF EXISTS airports CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS passengers CASCADE;
DROP TABLE IF EXISTS bonuses CASCADE;
DROP TABLE IF EXISTS airlines CASCADE;

CREATE TABLE IF NOT EXISTS planes(
                                     id SERIAL PRIMARY KEY,
                                     model_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS airports(
                                       id SERIAL PRIMARY KEY,
                                       country TEXT NOT NULL,
                                       city TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS passengers(
                                         id SERIAL PRIMARY KEY,
                                         full_name TEXT NOT NULL,
                                         address TEXT,
                                         email TEXT NOT NULL,
                                         phone_number TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS airlines(
                                       id SERIAL PRIMARY KEY,
                                       name TEXT NOT NULL,
                                       email TEXT,
                                       phone_number TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS flights(
                                      id SERIAL PRIMARY KEY,
                                      airline_id INTEGER REFERENCES airlines ON DELETE CASCADE NOT NULL,
                                      airport_id_dep INTEGER REFERENCES airports ON DELETE CASCADE NOT NULL,
                                      airport_id_arr INTEGER REFERENCES airports ON DELETE CASCADE NOT NULL,
                                      aircraft_id INTEGER REFERENCES planes ON DELETE CASCADE NOT NULL,
                                      time_dep TIMESTAMP NOT NULL,
                                      time_arr TIMESTAMP NOT NULL,
                                      cnt_seats INTEGER CHECK ( cnt_seats > 0 ) NOT NULL,
                                      cnt_available_seats INTEGER CHECK ( cnt_available_seats >= 0 ) NOT NULL
);

CREATE TABLE IF NOT EXISTS tickets(
                                      id SERIAL PRIMARY KEY,
                                      flight_id INTEGER REFERENCES flights ON DELETE CASCADE NOT NULL,
                                      status TEXT NOT NULL,
                                      user_id INTEGER REFERENCES passengers ON DELETE CASCADE NOT NULL,
                                      price INTEGER CHECK ( price > 0 ) NOT NULL
);

CREATE TABLE IF NOT EXISTS bonuses(
                                      bonus_id SERIAL PRIMARY KEY,
                                      user_id INTEGER REFERENCES passengers ON DELETE CASCADE NOT NULL,
                                      airline_id INTEGER REFERENCES Airlines ON DELETE  CASCADE NOT NULL,
                                      bonus_card TEXT DEFAULT '+',
                                      cnt_km INTEGER CHECK ( cnt_km >= 0 ) NOT NULL,
                                      cnt_used INTEGER CHECK ( cnt_used >= 0 ) NOT NULL
);
