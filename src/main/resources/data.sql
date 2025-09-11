-- Mais dados de exemplo
-- data.sql
INSERT INTO restaurant (id, name, category, address, phone, delivery_fee, delivery_time, work_hours, zip, evaluation, active) VALUES
(1, 'Pizza Palace', 'Italiano', 'Pizza Street, 123', '(11) 1234-5678', 3.5, 7, '6:00-22:00', '12345-6789', 2, true),
(2, 'Burger King', 'Fast Food', 'Burger Avenue, 456', '(11) 8765-4321', 3.5, 7, '6:00-22:00', '12345-6789', 2, true);

INSERT INTO users (email, password, name, role, active, creation_date, restaurant_id) VALUES
('admin@delivery.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'System Admin', 'ADMIN', true, NOW(), null),
('john@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'John Client', 'CLIENT', true, NOW(), null),
('mary@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'Mary Client', 'CLIENT', true, NOW(), null),
('pizza@palace.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'Pizza Palace', 'RESTAURANT', true, NOW(), 1),
('burger@king.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'Burger King', 'RESTAURANT', true, NOW(), 2),
('charles@delivery.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXulpZR8J4OY6Nd4EMCFyZw4ufC', 'Charles Delivery', 'DELIVERY_PERSON', true, NOW(), null);

INSERT INTO product (name, description, price, category, available, restaurant_id) VALUES
('Margherita Pizza', 'Pizza with tomato sauce, mozzarella and basil', 35.90, 'Pizza', true, 1),
('Pepperoni Pizza', 'Pizza with tomato sauce, mozzarella and pepperoni', 42.90, 'Pizza', true, 1),
('Whopper', 'Grilled beef burger with lettuce, tomato, onion', 28.90, 'Burger', true, 2),
('Big King', 'Two burgers, lettuce, cheese, special sauce', 32.90, 'Burger', true, 2);