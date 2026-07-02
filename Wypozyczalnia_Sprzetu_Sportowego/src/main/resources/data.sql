INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'staff','{noop}staff','STAFF' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='staff');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}staff', role='STAFF' WHERE login='staff';
INSERT INTO sport_equipment (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Narty carvingowe','SKI',2,4,2,80.00,true WHERE NOT EXISTS (SELECT 1 FROM sport_equipment WHERE name='Narty carvingowe');
INSERT INTO sport_equipment (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Rower trekkingowy','BIKE',1,5,1,60.00,true WHERE NOT EXISTS (SELECT 1 FROM sport_equipment WHERE name='Rower trekkingowy');
INSERT INTO sport_equipment (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Zestaw tenisowy','TENNIS',2,3,3,40.00,true WHERE NOT EXISTS (SELECT 1 FROM sport_equipment WHERE name='Zestaw tenisowy');
