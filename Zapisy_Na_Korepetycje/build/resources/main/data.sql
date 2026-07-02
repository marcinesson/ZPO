INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'tutor','{noop}tutor','TUTOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='tutor');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}tutor', role='TUTOR' WHERE login='tutor';
INSERT INTO tutor_slot (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Matematyka matura','MATH',3,4,3,80.00,true WHERE NOT EXISTS (SELECT 1 FROM tutor_slot WHERE name='Matematyka matura');
INSERT INTO tutor_slot (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Angielski rozmowy','ENGLISH',4,3,4,70.00,true WHERE NOT EXISTS (SELECT 1 FROM tutor_slot WHERE name='Angielski rozmowy');
INSERT INTO tutor_slot (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Programowanie Java','PROGRAMMING',2,5,2,120.00,true WHERE NOT EXISTS (SELECT 1 FROM tutor_slot WHERE name='Programowanie Java');
