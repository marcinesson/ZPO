INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'operator','{noop}operator','OPERATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='operator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}operator', role='OPERATOR' WHERE login='operator';
INSERT INTO inspection_drone (name,category,capacity,price,available) SELECT 'Falcon Photo','PHOTO',2,120.00,true WHERE NOT EXISTS (SELECT 1 FROM inspection_drone WHERE name='Falcon Photo');
INSERT INTO inspection_drone (name,category,capacity,price,available) SELECT 'Thermo X','THERMAL',4,220.00,true WHERE NOT EXISTS (SELECT 1 FROM inspection_drone WHERE name='Thermo X');
INSERT INTO inspection_drone (name,category,capacity,price,available) SELECT 'Mapper Pro','MAPPING',6,280.00,true WHERE NOT EXISTS (SELECT 1 FROM inspection_drone WHERE name='Mapper Pro');
