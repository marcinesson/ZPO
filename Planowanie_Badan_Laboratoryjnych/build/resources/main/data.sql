INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'labtech','{noop}labtech','LAB_TECH' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='labtech');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}labtech', role='LAB_TECH' WHERE login='labtech';
INSERT INTO lab_device (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Blood Analyzer','BLOOD',40,3,20,100.00,true WHERE NOT EXISTS (SELECT 1 FROM lab_device WHERE name='Blood Analyzer');
INSERT INTO lab_device (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Genetic Sequencer','GENETIC',12,5,4,260.00,true WHERE NOT EXISTS (SELECT 1 FROM lab_device WHERE name='Genetic Sequencer');
INSERT INTO lab_device (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Chemical Bench','CHEMICAL',25,4,10,150.00,true WHERE NOT EXISTS (SELECT 1 FROM lab_device WHERE name='Chemical Bench');
