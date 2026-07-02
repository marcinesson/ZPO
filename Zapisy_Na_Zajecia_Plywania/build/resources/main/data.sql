INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'instructor','{noop}instructor','INSTRUCTOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='instructor');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}instructor', role='INSTRUCTOR' WHERE login='instructor';
INSERT INTO swimming_group (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Poczatkujacy','BEGINNER',10,2,2,55.00,true WHERE NOT EXISTS (SELECT 1 FROM swimming_group WHERE name='Poczatkujacy');
INSERT INTO swimming_group (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Sredniozaawansowani','INTERMEDIATE',8,4,2,75.00,true WHERE NOT EXISTS (SELECT 1 FROM swimming_group WHERE name='Sredniozaawansowani');
INSERT INTO swimming_group (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Zaawansowani','ADVANCED',6,5,1,95.00,true WHERE NOT EXISTS (SELECT 1 FROM swimming_group WHERE name='Zaawansowani');
