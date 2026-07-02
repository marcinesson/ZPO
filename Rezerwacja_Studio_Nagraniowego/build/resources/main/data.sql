INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'engineer','{noop}engineer','SOUND_ENGINEER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='engineer');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}engineer', role='SOUND_ENGINEER' WHERE login='engineer';
INSERT INTO studio_room (name,category,capacity,price,available) SELECT 'Podcast Booth','PODCAST',2,80.00,true WHERE NOT EXISTS (SELECT 1 FROM studio_room WHERE name='Podcast Booth');
INSERT INTO studio_room (name,category,capacity,price,available) SELECT 'Vocal Room','VOCAL',3,110.00,true WHERE NOT EXISTS (SELECT 1 FROM studio_room WHERE name='Vocal Room');
INSERT INTO studio_room (name,category,capacity,price,available) SELECT 'Band Room','BAND',7,190.00,true WHERE NOT EXISTS (SELECT 1 FROM studio_room WHERE name='Band Room');
