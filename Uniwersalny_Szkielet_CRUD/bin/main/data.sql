INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'admin','{noop}admin','ADMIN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='admin');
INSERT INTO main_item (name,capacity,price,available) SELECT 'Basic',2,50.00,true WHERE NOT EXISTS (SELECT 1 FROM main_item WHERE name='Basic');
INSERT INTO main_item (name,capacity,price,available) SELECT 'Premium',5,100.00,true WHERE NOT EXISTS (SELECT 1 FROM main_item WHERE name='Premium');
