INSERT INTO app_user (login, password, role, age)
SELECT 'admin', '{noop}admin', 'ADMIN', 30
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'admin');

INSERT INTO app_user (login, password, role, age)
SELECT 'client', '{noop}client', 'CLIENT', 22
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'client');

INSERT INTO car (brand, car_model, price_for24h, available)
SELECT 'Toyota', 'Corolla', 150.00, true
WHERE NOT EXISTS (SELECT 1 FROM car WHERE brand = 'Toyota' AND car_model = 'Corolla');

INSERT INTO car (brand, car_model, price_for24h, available)
SELECT 'BMW', 'Series 3', 300.00, true
WHERE NOT EXISTS (SELECT 1 FROM car WHERE brand = 'BMW' AND car_model = 'Series 3');

INSERT INTO car (brand, car_model, price_for24h, available)
SELECT 'Skoda', 'Octavia', 180.00, true
WHERE NOT EXISTS (SELECT 1 FROM car WHERE brand = 'Skoda' AND car_model = 'Octavia');
