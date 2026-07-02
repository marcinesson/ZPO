INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'librarian','{noop}librarian','LIBRARIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='librarian');
INSERT INTO book (name,capacity,price,available) SELECT 'Basic',2,50.00,true WHERE NOT EXISTS (SELECT 1 FROM book WHERE name='Basic');
INSERT INTO book (name,capacity,price,available) SELECT 'Premium',5,100.00,true WHERE NOT EXISTS (SELECT 1 FROM book WHERE name='Premium');
