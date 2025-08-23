-- Inserir clientes
INSERT INTO client (name, email, phone, address, date_register, active) 
VALUES 
('João Silva', 'joao@email.com', '(11) 99999-1111', 'Rua A, 123 - São Paulo/SP', NOW(), true),
('Maria Santos', 'maria@email.com', '(11) 99999-2222', 'Rua B, 456 - São Paulo/SP', NOW(), true),
('Pedro Oliveira', 'pedro@email.com', '(11) 99999-3333', 'Rua C, 789 - São Paulo/SP', NOW(), true);

-- Inserir restaurantes
INSERT INTO restaurant (name, category, address, phone, delivery_fee, evaluation, active) 
VALUES
('Pizzaria Bella', 'Italiana', 'Av. Paulista, 1000 - São Paulo/SP', '(11) 3333-1111', 5, 4.5, true),
('Burguer House', 'Hamburgueria', 'R. Augusta, 500 - São Paulo/SP', '(11) 3333-2222', 3.5, 4.2, true),
('Sushi Master', 'Japonesa', 'R. Liberdade, 200 - São Paulo/SP', '(11) 3333-3333', 8, 4.8, true);

--  Inserir produtos
INSERT INTO product (name, description, price, category, available, restaurant_id)
VALUES
-- Pizzaria Bella
('Margherita', 'Molho de tomate, muçarela e manjericão', 35.9, 'Pizza', true, 1),
('Calabresa', 'Molho de tomate, muçarela e calabresa', 38.9, 'Pizza', true, 1),
('Margherita', 'Tradicional c/ molho bolonhesa', 28.9, 'Massa', true, 1),

-- Burger House
('X-Burger', 'Queijo, alface e tomate', 18.9, 'Hambúrguer', true, 2),
('X-Bacon', 'Queijo, bacon, alface e tomate', 22.9, 'Hambúrguer', true, 2),
('Batata frita', 'Porção crocante', 12.9, 'Acompanhamento', true, 2),

-- Sushi Master
('Combo Sashimi', '15 peças variados', 45.9, 'Sashimi', true, 3),
('Hot Roll Salmão', '8 peças de salmão', 32.9, 'Hot Roll', true, 3),
('Temaki Atum', 'Temaki de atum c/ cream cheese', 15.9, 'Temaki', true, 3);

INSERT INTO request (date_request, note, subtotal, total_value, status_request, client_id, restaurant_id)
VALUES
(NOW(), 'Sem cebola na pizza', 54.8, 54.8, 'PENDENTE',  1, 1),
(NOW(), '', 41.8, 41.8, 'CONFIRMADO', 2, 2),
(NOW(), 'Sem cebola na pizza', 54.8, 54.8, 'PENDENTE', 3, 3);

-- Inserir pedidos
INSERT INTO item_request (quantity, unitary_price, subtotal, request_id, product_id)
VALUES
-- Pedido 1 (João - Pizzaria Bella)
(1, 35.9, 35.9, 1, 1), -- Pizza Margherita
(1, 28.9, 28.9, 1, 3), -- Lasanha

-- Pedido 2 (MAria - Burguer House)
(1, 22.9, 22.9, 2, 5), -- X-Bacon
(1, 18.9, 18.9, 2, 4), -- X-Burguer

-- Pedido 3 (Pedro - Sushi Master)
(1, 45.9, 45.9, 3, 7), -- Combo Sashimi
(1, 32.9, 32.9, 3, 8); -- Hot Roll