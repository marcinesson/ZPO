INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO service_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Spec od pralek','WASHER',3,3,5,90.00,true WHERE NOT EXISTS (SELECT 1 FROM service_technician WHERE name='Spec od pralek');
INSERT INTO service_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Spec od lodowek','FRIDGE',2,4,4,120.00,true WHERE NOT EXISTS (SELECT 1 FROM service_technician WHERE name='Spec od lodowek');
INSERT INTO service_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Spec od piekarnikow','OVEN',2,5,3,130.00,true WHERE NOT EXISTS (SELECT 1 FROM service_technician WHERE name='Spec od piekarnikow');
