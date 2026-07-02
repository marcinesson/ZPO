INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO technician_slot (name,capacity,price,available) SELECT 'Basic',2,50.00,true WHERE NOT EXISTS (SELECT 1 FROM technician_slot WHERE name='Basic');
INSERT INTO technician_slot (name,capacity,price,available) SELECT 'Premium',5,100.00,true WHERE NOT EXISTS (SELECT 1 FROM technician_slot WHERE name='Premium');
