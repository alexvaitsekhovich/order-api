INSERT INTO orders (id, number, amount, discount_amount, payment_id, status_id) VALUES
    (1, 'AX', 200, 0, 1, 0),
    (2, 'BY', 340, 0, 2, 0);


INSERT INTO order_address (id, city, zip, street, nr, order_id) VALUES
    (1, 'Berlin', '10115', 'Chausseestr.', '1', 1),
    (2, 'Leipzig', '04129', 'Wittenberger Str.', '26', 2);

INSERT INTO order_parts (id, count, item_id, item_name, price, order_id) VALUES
    (1, 1, 34, 'Pizza Nutella', 1200, 1),
    (2, 2, 15, 'Fanta', 150, 1);

