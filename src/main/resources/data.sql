-- Exemplo de script de inicialização do banco de dados para o H2.
-- Use o comando MERGE INTO para inserir dados e evitar erros de duplicidade.

-- ####################
-- TABELA FUEL (Combustíveis)
-- ####################

-- Insere os tipos de combustível se eles ainda não existirem.
MERGE INTO fuel (id, name, price) KEY(name) VALUES (1, 'Gasolina', 4.50);
MERGE INTO fuel (id, name, price) KEY(name) VALUES (2, 'Etanol', 3.80);
MERGE INTO fuel (id, name, price) KEY(name) VALUES (3, 'Diesel', 5.10);

-- ####################
-- TABELA FUEL_DISPENSERS (Bombas de Combustível)
-- ####################

-- Insere as bombas de combustível e as associa a um tipo de combustível (FUEL).
-- Assumimos que o campo 'id' da tabela 'fueldispensers' é auto-incrementável.
MERGE INTO fuel_dispensers (id, fuel_id, status) KEY(id) VALUES (101, 1, 'available'); -- Bomba 101, Gasolina
MERGE INTO fuel_dispensers (id, fuel_id, status) KEY(id) VALUES (102, 2, 'available'); -- Bomba 102, Etanol
MERGE INTO fuel_dispensers (id, fuel_id, status) KEY(id) VALUES (103, 2, 'available'); -- Bomba 103, Etanol

-- ####################
-- TABELA ORDERS (Pedidos)
-- ####################

-- Insere alguns pedidos de exemplo.
-- Relaciona cada pedido a uma bomba e a um combustível.
-- Assumimos que o campo 'id' da tabela 'orders' é auto-incrementável.
MERGE INTO orders (id, order_date, fuel_id, fuel_dispenser_id, total_amount) KEY(id) VALUES (1, '2025-08-09 10:00:00', 1, 101, 50.00);
MERGE INTO orders (id, order_date, fuel_id, fuel_dispenser_id, total_amount) KEY(id) VALUES (2, '2025-08-09 10:10:00', 2, 102, 35.50);