INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'admin','{noop}admin','ADMIN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='admin');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}admin', role='ADMIN' WHERE login='admin';
INSERT INTO hotel_room (name,capacity,price,available) SELECT 'Basic',2,50.00,true WHERE NOT EXISTS (SELECT 1 FROM hotel_room WHERE name='Basic');
INSERT INTO hotel_room (name,capacity,price,available) SELECT 'Premium',5,100.00,true WHERE NOT EXISTS (SELECT 1 FROM hotel_room WHERE name='Premium');
