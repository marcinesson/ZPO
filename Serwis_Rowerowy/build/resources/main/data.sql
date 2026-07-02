INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'mechanic','{noop}mechanic','MECHANIC' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='mechanic');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}mechanic', role='MECHANIC' WHERE login='mechanic';
INSERT INTO bike_mechanic (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mechanik hamulcow','BRAKES',3,3,5,70.00,true WHERE NOT EXISTS (SELECT 1 FROM bike_mechanic WHERE name='Mechanik hamulcow');
INSERT INTO bike_mechanic (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mechanik napedu','GEARS',2,4,4,90.00,true WHERE NOT EXISTS (SELECT 1 FROM bike_mechanic WHERE name='Mechanik napedu');
INSERT INTO bike_mechanic (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mechanik e-bike','ELECTRIC',1,5,2,130.00,true WHERE NOT EXISTS (SELECT 1 FROM bike_mechanic WHERE name='Mechanik e-bike');
