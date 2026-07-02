INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'coordinator','{noop}coordinator','COORDINATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='coordinator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}coordinator', role='COORDINATOR' WHERE login='coordinator';
INSERT INTO cleaning_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Office Team','OFFICE',200,3,80,90.00,true WHERE NOT EXISTS (SELECT 1 FROM cleaning_team WHERE name='Office Team');
INSERT INTO cleaning_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Renovation Crew','POST_RENOVATION',160,5,45,170.00,true WHERE NOT EXISTS (SELECT 1 FROM cleaning_team WHERE name='Renovation Crew');
INSERT INTO cleaning_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Event Rapid Team','EVENT',300,4,120,140.00,true WHERE NOT EXISTS (SELECT 1 FROM cleaning_team WHERE name='Event Rapid Team');
