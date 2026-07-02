INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'staff','{noop}staff','STAFF' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='staff');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}staff', role='STAFF' WHERE login='staff';
INSERT INTO tool_set (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Wiertarki','DRILL',3,3,2,45.00,true WHERE NOT EXISTS (SELECT 1 FROM tool_set WHERE name='Wiertarki');
INSERT INTO tool_set (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ogrod zestaw','GARDEN',2,4,1,70.00,true WHERE NOT EXISTS (SELECT 1 FROM tool_set WHERE name='Ogrod zestaw');
INSERT INTO tool_set (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Remont premium','RENOVATION',2,5,1,120.00,true WHERE NOT EXISTS (SELECT 1 FROM tool_set WHERE name='Remont premium');
