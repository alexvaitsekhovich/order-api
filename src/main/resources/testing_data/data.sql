INSERT INTO orders (id, number, amount, discount_amount, payment_id, status_id) VALUES
    (1, 'AX1', 200, 0, 1, 0),
    (2, 'AX2', 340, 0, 2, 0),
    (3, 'AX3', 100, 0, 0, 0),
    (4, 'AX4', 100, 0, 0, 0),
    (5, 'AX5', 100, 0, 0, 0);


INSERT INTO order_address (id, city, zip, street, nr, order_id) VALUES
    (1, 'Berlin', '10115', 'Chausseestr.', '1', 1),
    (2, 'Leipzig', '04129', 'Wittenberger Str.', '26', 2);

INSERT INTO order_parts (id, count, item_id, item_name, price, order_id) VALUES
    (1, 1, 34, 'Pizza Nutella', 1200, 1),
    (2, 2, 15, 'Fanta', 150, 1);

