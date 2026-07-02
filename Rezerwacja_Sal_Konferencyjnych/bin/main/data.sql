INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'admin','{noop}admin','ADMIN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='admin');
INSERT INTO conference_room (name,capacity,price_per_hour) SELECT 'Sala A',10,80.00 WHERE NOT EXISTS (SELECT 1 FROM conference_room WHERE name='Sala A');
INSERT INTO conference_room (name,capacity,price_per_hour) SELECT 'Sala B',25,150.00 WHERE NOT EXISTS (SELECT 1 FROM conference_room WHERE name='Sala B');
