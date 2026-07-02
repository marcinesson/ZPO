INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'wardrobe','{noop}wardrobe','WARDROBE' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='wardrobe');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}wardrobe', role='WARDROBE' WHERE login='wardrobe';
INSERT INTO costume (name,category,capacity,price,available) SELECT 'Frak klasyczny','FORMAL',48,35.00,true WHERE NOT EXISTS (SELECT 1 FROM costume WHERE name='Frak klasyczny');
INSERT INTO costume (name,category,capacity,price,available) SELECT 'Zbroja sceniczna','HISTORICAL',52,70.00,true WHERE NOT EXISTS (SELECT 1 FROM costume WHERE name='Zbroja sceniczna');
INSERT INTO costume (name,category,capacity,price,available) SELECT 'Plaszcz fantasy','FANTASY',56,60.00,true WHERE NOT EXISTS (SELECT 1 FROM costume WHERE name='Plaszcz fantasy');
