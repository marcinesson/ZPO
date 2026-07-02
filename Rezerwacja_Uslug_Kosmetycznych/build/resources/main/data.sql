INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'beautician','{noop}beautician','BEAUTICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='beautician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}beautician', role='BEAUTICIAN' WHERE login='beautician';
INSERT INTO beauty_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Paznokcie','NAILS',2,3,4,75.00,true WHERE NOT EXISTS (SELECT 1 FROM beauty_station WHERE name='Paznokcie');
INSERT INTO beauty_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Zabieg twarzy','FACIAL',1,5,2,140.00,true WHERE NOT EXISTS (SELECT 1 FROM beauty_station WHERE name='Zabieg twarzy');
INSERT INTO beauty_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Makijaz','MAKEUP',2,4,3,100.00,true WHERE NOT EXISTS (SELECT 1 FROM beauty_station WHERE name='Makijaz');
