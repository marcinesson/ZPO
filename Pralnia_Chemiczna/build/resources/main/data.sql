INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'worker','{noop}worker','LAUNDRY_WORKER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='worker');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}worker', role='LAUNDRY_WORKER' WHERE login='worker';
INSERT INTO laundry_line (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Koszule express','SHIRTS',30,3,20,45.00,true WHERE NOT EXISTS (SELECT 1 FROM laundry_line WHERE name='Koszule express');
INSERT INTO laundry_line (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Garnitury','SUITS',10,5,5,120.00,true WHERE NOT EXISTS (SELECT 1 FROM laundry_line WHERE name='Garnitury');
INSERT INTO laundry_line (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Dywany','CARPETS',4,4,2,150.00,true WHERE NOT EXISTS (SELECT 1 FROM laundry_line WHERE name='Dywany');
