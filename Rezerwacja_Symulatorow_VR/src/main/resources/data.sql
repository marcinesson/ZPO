INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'instructor','{noop}instructor','INSTRUCTOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='instructor');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}instructor', role='INSTRUCTOR' WHERE login='instructor';
INSERT INTO vr_simulator (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Racing Rig','RACING',2,3,4,70.00,true WHERE NOT EXISTS (SELECT 1 FROM vr_simulator WHERE name='Racing Rig');
INSERT INTO vr_simulator (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Flight Cabin','FLIGHT',3,5,3,120.00,true WHERE NOT EXISTS (SELECT 1 FROM vr_simulator WHERE name='Flight Cabin');
INSERT INTO vr_simulator (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Medical Station','MEDICAL',4,5,2,150.00,true WHERE NOT EXISTS (SELECT 1 FROM vr_simulator WHERE name='Medical Station');
