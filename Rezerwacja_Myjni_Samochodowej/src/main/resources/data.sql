INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'manager','{noop}manager','MANAGER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='manager');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}manager', role='MANAGER' WHERE login='manager';
INSERT INTO wash_bay (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Stanowisko szybkie','BASIC',2,3,6,50.00,true WHERE NOT EXISTS (SELECT 1 FROM wash_bay WHERE name='Stanowisko szybkie');
INSERT INTO wash_bay (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Detailing premium','DETAILING',1,5,2,160.00,true WHERE NOT EXISTS (SELECT 1 FROM wash_bay WHERE name='Detailing premium');
INSERT INTO wash_bay (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Czyszczenie wnetrza','INTERIOR',2,4,3,100.00,true WHERE NOT EXISTS (SELECT 1 FROM wash_bay WHERE name='Czyszczenie wnetrza');
