INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO printer_station (name,category,capacity,price,available) SELECT 'Mini PLA','PLA',2,30.00,true WHERE NOT EXISTS (SELECT 1 FROM printer_station WHERE name='Mini PLA');
INSERT INTO printer_station (name,category,capacity,price,available) SELECT 'PETG Workbench','PETG',5,50.00,true WHERE NOT EXISTS (SELECT 1 FROM printer_station WHERE name='PETG Workbench');
INSERT INTO printer_station (name,category,capacity,price,available) SELECT 'Resin Detail','RESIN',3,65.00,true WHERE NOT EXISTS (SELECT 1 FROM printer_station WHERE name='Resin Detail');
