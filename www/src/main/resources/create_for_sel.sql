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

INSERT INTO planes (model_name)
VALUES ('Boeing 777-300'),
       ('Boeing 767-300'),
       ('Sukhoi Superjet-100'),
       ('Airbus A320-200'),
       ('Boeing 737-300'),
       ('MC-21');

INSERT INTO airports (country, city)
VALUES ('Russia', 'Moscow'),
       ('Russia', 'Saint Petersburg'),
       ('Russia', 'Novosibirsk'),
       ('Russia', 'Kazan'),
       ('USA', 'New York'),
       ('China', 'Beijing'),
       ('Canada', 'Toronto'),
       ('Syria', 'Damascus'),
       ('Belarus', 'Minsk');

INSERT INTO airlines (name, email, phone_number)
VALUES ('Aeroflot', NULL, '88004445555'),
       ('S7 Airlines', NULL, '88007000707'),
       ('Nordwind Airlines', NULL, '84957305080'),
       ('Pegas Fly', NULL, '84954784944'),
       ('Utair', NULL, '88002340088');