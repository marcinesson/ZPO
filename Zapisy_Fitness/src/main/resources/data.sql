INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'trainer','{noop}trainer','TRAINER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='trainer');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}trainer', role='TRAINER' WHERE login='trainer';
INSERT INTO fitness_class (name,capacity,price,available) SELECT 'Basic',2,50.00,true WHERE NOT EXISTS (SELECT 1 FROM fitness_class WHERE name='Basic');
INSERT INTO fitness_class (name,capacity,price,available) SELECT 'Premium',5,100.00,true WHERE NOT EXISTS (SELECT 1 FROM fitness_class WHERE name='Premium');
