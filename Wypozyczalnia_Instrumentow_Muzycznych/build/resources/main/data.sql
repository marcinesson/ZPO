INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'curator','{noop}curator','CURATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='curator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}curator', role='CURATOR' WHERE login='curator';
INSERT INTO instrument (name,category,capacity,price,available) SELECT 'Gitara koncertowa','GUITAR',2,45.00,true WHERE NOT EXISTS (SELECT 1 FROM instrument WHERE name='Gitara koncertowa');
INSERT INTO instrument (name,category,capacity,price,available) SELECT 'Keyboard sceniczny','KEYBOARD',4,90.00,true WHERE NOT EXISTS (SELECT 1 FROM instrument WHERE name='Keyboard sceniczny');
INSERT INTO instrument (name,category,capacity,price,available) SELECT 'Perkusja studyjna','DRUMS',5,130.00,true WHERE NOT EXISTS (SELECT 1 FROM instrument WHERE name='Perkusja studyjna');
