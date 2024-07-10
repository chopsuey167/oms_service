/* CUSTOMER */

INSERT INTO customers (id, first_name, last_name, email, address)
VALUES (1, 'Ricardo', 'Gutierrez', 'ricardog.astocondor@gmail.com', 'Gran via de les corts catalanes') ON CONFLICT (id) DO NOTHING;
COMMIT;


/* PRODUCT */

INSERT INTO products (id, description)
VALUES (1, 'PS5') ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, description)
VALUES (2, 'XBOX') ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, description)
VALUES (3, 'NINTENDO SWITCH') ON CONFLICT (id) DO NOTHING;
COMMIT;