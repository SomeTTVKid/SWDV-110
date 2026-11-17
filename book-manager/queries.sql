-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
SELECT * FROM books
ORDER BY year DESC;

-- Result: 10 rows returned in 9ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT * FROM books
WHERE author LIKE 'Andy Weir'
ORDER BY year ASC;

-- Result: 3 rows returned in 6ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT * FROM books
WHERE is_read == 0
ORDER BY title;

-- Result: 3 rows returned in 6ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT * FROM books
WHERE title LIKE '%the%';

-- Result: 3 rows returned in 8ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT author, COUNT(*) AS totalBooks
FROM books 
GROUP BY author 
ORDER BY totalBooks DESC;

-- Result: 8 rows returned in 5ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT AVG(rating) AS averageRating
FROM books
WHERE rating > 0;

-- Result: 1 rows returned in 5ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

UPDATE books 
SET rating = 4, is_read = 1
WHERE title = 'Sapiens';

-- Result: query executed successfully. Took 0ms, 1 rows affected

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SELECT * FROM books
WHERE rating <= 0 
AND is_read = 0
AND year < 2000;

-- Result: 1 rows returned in 5ms

-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

DELETE FROM books 
WHERE rating <= 0 
AND is_read = 0 
AND year < 2000;

-- Result: query executed successfully. Took 0ms, 1 rows affected