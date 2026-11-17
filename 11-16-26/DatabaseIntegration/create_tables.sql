-- CREATE TABLE products (
--     code TEXT PRIMARY KEY,
--     description TEXT NOT NULL,
--     price REAL NOT NULL
-- );

-- INSERT INTO products (code, description, price)
-- VALUES
--     ('KB-1100', 'Ergonomic Keyboard', 69.95),
--     ('MS-300', 'Wireless Mouse', 24.95),
--     ('MN-220', '24-inch Monitor', 179.99),
--     ('HS-410', 'Over-Ear Headphones', 59.95),
--     ('SP-510', 'Bluetooth Speaker', 39.99),
--     ('WB-620', '1080p Webcam', 49.95),
--     ('MC-330', 'USB Microphone', 44.95),
--     ('HD-710', 'External Hard Drive', 89.99),
--     ('FL-840', 'USB Flash Drive', 14.95),
--     ('DP-150', 'DisplayPort Cable', 12.99),
--     ('UC-250', 'USB-C Hub', 34.95),
--     ('DS-460', 'Docking Station', 119.99),
--     ('LP-900', 'Laptop Stand', 29.95),
--     ('MP-310', 'Mouse Pad', 9.99),
--     ('CH-580', 'USB-C Charger', 22.95),
--     ('BT-720', 'Bluetooth Adapter', 16.95),
--     ('RJ-430', 'Ethernet Cable', 8.99),
--     ('PB-650', 'Portable Battery', 32.95),
--     ('PR-260', 'Wireless Presentation Remote', 27.95),
--     ('CA-370', 'USB-C Adapter', 18.95);

-- Sorted by description A to Z
SELECT * FROM products
ORDER BY description ASC;

-- Show products between 20 and 100
SELECT * FROM products
WHERE price < 100 AND price > 20;

-- Description contains cable
SELECT * FROM products
WHERE description LIKE '%cable';

-- Show the count, minimum, maximum, and average price
SELECT 
    COUNT(*) AS totalItems,
    MIN(price) AS minPrice,
    MAX(price) AS maxPrice,
    AVG(price) AS avgPrice
FROM products;
