INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'manager','{noop}manager','MANAGER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='manager');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}manager', role='MANAGER' WHERE login='manager';
INSERT INTO tennis_court (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Kort ziemny 1','CLAY',4,3,1,70.00,true WHERE NOT EXISTS (SELECT 1 FROM tennis_court WHERE name='Kort ziemny 1');
INSERT INTO tennis_court (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Kort twardy premium','HARD',4,5,1,120.00,true WHERE NOT EXISTS (SELECT 1 FROM tennis_court WHERE name='Kort twardy premium');
INSERT INTO tennis_court (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Kort trawiasty','GRASS',2,4,1,100.00,true WHERE NOT EXISTS (SELECT 1 FROM tennis_court WHERE name='Kort trawiasty');
