INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'manager','{noop}manager','MANAGER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='manager');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}manager', role='MANAGER' WHERE login='manager';
INSERT INTO bowling_lane (name,category,capacity,price,available) SELECT 'Tor rodzinny','CHILDREN',4,55.00,true WHERE NOT EXISTS (SELECT 1 FROM bowling_lane WHERE name='Tor rodzinny');
INSERT INTO bowling_lane (name,category,capacity,price,available) SELECT 'Tor standardowy','STANDARD',6,75.00,true WHERE NOT EXISTS (SELECT 1 FROM bowling_lane WHERE name='Tor standardowy');
INSERT INTO bowling_lane (name,category,capacity,price,available) SELECT 'Tor VIP','VIP',8,120.00,true WHERE NOT EXISTS (SELECT 1 FROM bowling_lane WHERE name='Tor VIP');
