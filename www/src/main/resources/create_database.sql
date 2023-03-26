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

INSERT INTO planes (id, model_name)
    VALUES (1, 'Boeing 777-300'),
           (2, 'Boeing 767-300'),
           (3, 'Sukhoi Superjet-100'),
           (4, 'Airbus A320-200'),
           (5, 'Boeing 737-300');

INSERT INTO airports (id, country, city)
VALUES (1, 'Russia', 'Moscow'),
       (2, 'Russia', 'Saint Petersburg'),
       (3, 'Russia', 'Novosibirsk'),
       (4, 'Russia', 'Kazan'),
       (5, 'USA', 'New York'),
       (6, 'China', 'Beijing');

INSERT INTO passengers (id, full_name, address, email, phone_number)
VALUES (1, 'Davey Johns', NULL, 'dj@gmail.com', '+177777777'),
       (2, 'Ivanov Mikhail', NULL, 'mikhail@m.ru', '+77777778'),
       (3, 'Stepanov Alexey', NULL, 'alexey@m.ru', '+77777779'),
       (4, 'Petrov Vladimir', NULL, 'vladimir@m.ru', '+77777738'),
       (5, 'Ignatov Daniil', NULL, 'daniil@m.ru', '+77777798');

INSERT INTO airlines (id, name, email, phone_number)
VALUES (1, 'Aeroflot', NULL, '88004445555'),
       (2, 'S7 Airlines', NULL, '88007000707'),
       (3, 'Nordwind Airlines', NULL, '84957305080'),
       (4, 'Pegas Fly', NULL, '84954784944'),
       (5, 'Utair', NULL, '88002340088');

INSERT INTO flights (id, airline_id, airport_id_dep, airport_id_arr,
                     aircraft_id, time_dep, time_arr, cnt_seats,
                     cnt_available_seats)
    VALUES (1, 1, 1, 2, 1, '2022-03-10 09:50:00+03', '2022-03-10 14:55:00', 550, 0),
           (2, 2, 2, 1, 2, '2022-03-11 09:50:00+03', '2022-03-11 14:45:00', 375, 0),
           (3, 3, 1, 3, 3, '2022-03-15 09:35:00+03', '2022-03-15 10:30:00', 103, 0),
           (4, 4, 1, 4, 4, '2022-04-01 11:05:00+03', '2022-04-01 14:30:00', 180, 21),
           (5, 5, 1, 5, 5, '2022-05-01 10:40:00+03', '2017-05-01 11:35:00', 189, 189);


INSERT INTO  tickets (id, flight_id, status, price, user_id)
    VALUES (1, 1, 'paid', 600, 2),
           (2, 1, 'paid', 100, 2),
           (3, 2, 'paid', 300, 3),
           (4, 3, 'paid', 500, 4),
           (5, 4, 'paid', 300, 5);

INSERT INTO bonuses (bonus_id, user_id, airline_id, cnt_km, cnt_used)
    VALUES (1, 1, 1, 123, 123),
           (2, 2, 2, 1234, 0),
           (3, 3, 3, 2345, 1000),
           (4, 4, 4, 10000, 0),
           (5, 5, 5, 0, 0);
