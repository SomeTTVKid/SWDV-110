1. In your own words, what is **SQL injection**? Show the SQL that `search` would have built from `' OR '1'='1` if you had used `+` instead of `?`. 

1a. SQL Injection is inputting code within a query to get access to data you normally shouldn't. If I built my sql query with '+' instead of '?', it would have looked like this: 

```
SELECT * FROM books
WHERE title LIKE '%' OR '1'='1%'
OR author LIKE '%' OR '1'='1%'
ORDER BY title
```

1a. In this example, `'1'='1%'` is false, but `title LIKE '%'` matches every title, so it would still return every book. 

2. Why does every DAO method open and close its own `Connection` with try-with-resources? What could go wrong if you forgot to close them? 

2a. Closing with try-with-resources means that the connection is only open for the operation you are trying to do with it. If you forgot to close it, connections could accumulate and have performance issues, or lock users out of future transactions. 

3. `App` contains no SQL at all. Name two benefits of keeping SQL inside the DAO. (Hint: think about next week.)

3a. One benefit is that App can focus on the menus and prompts instead of mixing in database code. Another is that if I need to change a query later, I can do it in the DAO without changing the menus or prompts. 

4. Paste your `queries.sql` results summary: each query and its row count. 

4a.
    Books ordered newest first: 10 rows
    Andy Weir books, oldest first: 3 rows
    Unread books: 3 rows
    Titles containing “the”: 3 rows
    Author book counts: 8 rows
    Average rating: 1 row
    Update Sapiens as read with a rating of 4: 1 row affected
    Delete check for unrated, unread books published before 2000: 1 row returned
    Delete unrated, unread books published before 2000: 1 row deleted
  