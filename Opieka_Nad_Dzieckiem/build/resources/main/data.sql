INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'babysitter','{noop}babysitter','BABYSITTER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='babysitter');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}babysitter', role='BABYSITTER' WHERE login='babysitter';
INSERT INTO babysitter (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Wieczorna opieka','EVENING',3,3,4,60.00,true WHERE NOT EXISTS (SELECT 1 FROM babysitter WHERE name='Wieczorna opieka');
INSERT INTO babysitter (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Weekend','WEEKEND',4,4,3,80.00,true WHERE NOT EXISTS (SELECT 1 FROM babysitter WHERE name='Weekend');
INSERT INTO babysitter (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Pomoc w lekcjach','HOMEWORK',2,5,2,90.00,true WHERE NOT EXISTS (SELECT 1 FROM babysitter WHERE name='Pomoc w lekcjach');
