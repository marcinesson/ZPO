INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'kitchen','{noop}kitchen','KITCHEN_MANAGER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='kitchen');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}kitchen', role='KITCHEN_MANAGER' WHERE login='kitchen';
INSERT INTO catering_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ekipa standard','STANDARD',80,3,40,120.00,true WHERE NOT EXISTS (SELECT 1 FROM catering_team WHERE name='Ekipa standard');
INSERT INTO catering_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ekipa vege','VEGE',60,4,30,150.00,true WHERE NOT EXISTS (SELECT 1 FROM catering_team WHERE name='Ekipa vege');
INSERT INTO catering_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ekipa bezglutenowa','GLUTEN_FREE',40,5,20,180.00,true WHERE NOT EXISTS (SELECT 1 FROM catering_team WHERE name='Ekipa bezglutenowa');
