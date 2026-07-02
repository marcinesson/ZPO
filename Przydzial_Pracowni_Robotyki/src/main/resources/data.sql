INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'mentor','{noop}mentor','MENTOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='mentor');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}mentor', role='MENTOR' WHERE login='mentor';
INSERT INTO robotics_lab (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Lego Lab','LEGO',12,2,6,90.00,true WHERE NOT EXISTS (SELECT 1 FROM robotics_lab WHERE name='Lego Lab');
INSERT INTO robotics_lab (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Arduino Lab','ARDUINO',10,4,4,130.00,true WHERE NOT EXISTS (SELECT 1 FROM robotics_lab WHERE name='Arduino Lab');
INSERT INTO robotics_lab (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Industrial Cell','INDUSTRIAL',6,5,2,220.00,true WHERE NOT EXISTS (SELECT 1 FROM robotics_lab WHERE name='Industrial Cell');
