INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'coordinator','{noop}coordinator','COORDINATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='coordinator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}coordinator', role='COORDINATOR' WHERE login='coordinator';
INSERT INTO moving_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mini przeprowadzka','SMALL',30,3,15,100.00,true WHERE NOT EXISTS (SELECT 1 FROM moving_team WHERE name='Mini przeprowadzka');
INSERT INTO moving_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mieszkanie','FLAT',80,4,20,180.00,true WHERE NOT EXISTS (SELECT 1 FROM moving_team WHERE name='Mieszkanie');
INSERT INTO moving_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Dom','HOUSE',180,5,25,300.00,true WHERE NOT EXISTS (SELECT 1 FROM moving_team WHERE name='Dom');
