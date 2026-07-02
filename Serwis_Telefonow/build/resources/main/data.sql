INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO phone_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Wymiana ekranow','SCREEN',3,4,4,85.00,true WHERE NOT EXISTS (SELECT 1 FROM phone_technician WHERE name='Wymiana ekranow');
INSERT INTO phone_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Baterie','BATTERY',4,3,5,60.00,true WHERE NOT EXISTS (SELECT 1 FROM phone_technician WHERE name='Baterie');
INSERT INTO phone_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Software','SOFTWARE',5,4,6,55.00,true WHERE NOT EXISTS (SELECT 1 FROM phone_technician WHERE name='Software');
