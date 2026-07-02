INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'dispatcher','{noop}dispatcher','DISPATCHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='dispatcher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}dispatcher', role='DISPATCHER' WHERE login='dispatcher';
INSERT INTO courier_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Cargo Bike','CENTER',20,2,12,35.00,true WHERE NOT EXISTS (SELECT 1 FROM courier_vehicle WHERE name='Cargo Bike');
INSERT INTO courier_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Van City','CENTER',120,4,45,85.00,true WHERE NOT EXISTS (SELECT 1 FROM courier_vehicle WHERE name='Van City');
INSERT INTO courier_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Truck Industrial','INDUSTRIAL',600,5,55,160.00,true WHERE NOT EXISTS (SELECT 1 FROM courier_vehicle WHERE name='Truck Industrial');
