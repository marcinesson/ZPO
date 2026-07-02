INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'stylist','{noop}stylist','STYLIST' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='stylist');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}stylist', role='STYLIST' WHERE login='stylist';
INSERT INTO hairdresser (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Fryzjer meski','CUT',2,3,4,70.00,true WHERE NOT EXISTS (SELECT 1 FROM hairdresser WHERE name='Fryzjer meski');
INSERT INTO hairdresser (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Koloryzacja premium','COLOR',1,5,2,130.00,true WHERE NOT EXISTS (SELECT 1 FROM hairdresser WHERE name='Koloryzacja premium');
INSERT INTO hairdresser (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Stylizacja okazjonalna','STYLING',2,4,3,95.00,true WHERE NOT EXISTS (SELECT 1 FROM hairdresser WHERE name='Stylizacja okazjonalna');
