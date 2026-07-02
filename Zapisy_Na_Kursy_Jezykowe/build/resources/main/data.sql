INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'teacher','{noop}teacher','TEACHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='teacher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}teacher', role='TEACHER' WHERE login='teacher';
INSERT INTO language_group (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Angielski A2','ENGLISH',12,2,2,50.00,true WHERE NOT EXISTS (SELECT 1 FROM language_group WHERE name='Angielski A2');
INSERT INTO language_group (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Niemiecki B1','GERMAN',10,3,2,60.00,true WHERE NOT EXISTS (SELECT 1 FROM language_group WHERE name='Niemiecki B1');
INSERT INTO language_group (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Hiszpanski B2','SPANISH',8,4,1,75.00,true WHERE NOT EXISTS (SELECT 1 FROM language_group WHERE name='Hiszpanski B2');
