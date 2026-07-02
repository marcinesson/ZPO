INSERT INTO app_user (login, password, role)
SELECT 'vetAnita', '{noop}vet', 'VET'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'vetAnita');

INSERT INTO app_user (login, password, role)
SELECT 'vetKlaudia', '{noop}vet', 'VET'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'vetKlaudia');

INSERT INTO app_user (login, password, role)
SELECT 'client0', '{noop}client0', 'CLIENT'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'client0');

INSERT INTO app_user (login, password, role)
SELECT 'client1', '{noop}client1', 'CLIENT'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'client1');