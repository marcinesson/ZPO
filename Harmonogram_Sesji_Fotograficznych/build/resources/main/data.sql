INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'photographer','{noop}photographer','PHOTOGRAPHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='photographer');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}photographer', role='PHOTOGRAPHER' WHERE login='photographer';
INSERT INTO photo_studio_set (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Product Table','PRODUCT',20,3,60,80.00,true WHERE NOT EXISTS (SELECT 1 FROM photo_studio_set WHERE name='Product Table');
INSERT INTO photo_studio_set (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Portrait Softbox','PORTRAIT',4,4,35,110.00,true WHERE NOT EXISTS (SELECT 1 FROM photo_studio_set WHERE name='Portrait Softbox');
INSERT INTO photo_studio_set (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Fashion Cyclorama','FASHION',8,5,25,180.00,true WHERE NOT EXISTS (SELECT 1 FROM photo_studio_set WHERE name='Fashion Cyclorama');
