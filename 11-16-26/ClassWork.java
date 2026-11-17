public class ClassWork {
    public static void main(String[] args) {
        // Comments look like 
        // -- Comment

        // ~~~~~~~~~~~~~~ CREATING ~~~~~~~~~~~~~

        // Every column, every row
        // SELECT * FROM products;

        // Choose columns, filter, sort
        // SELECT code, description, price
        // FROM products
        // WHERE pirce < 50
        // ORDER BY price DESC

        // Pattern matching and aggregates
        // SELECT COUNT(*), AVG(price)
        // FROM products
        // WHERE description LIKE '%USB%';

        // Creating a table
        // -- CREATE TABLE products (
        // --     code TEXT PRIMARY KEY,
        // --     description TEXT NOT NULL,
        // --     price REAL NOT NULL
        // -- );

        // ~~~~~~~~~~~~~~ UPDATING ~~~~~~~~~~~~~
        // Always include WHERE clause

        // Ads a row
        // INSERT INTO products (code, description, price)
        // VALUES ('WC-600', 'HD webcam', 59.95);

        // Changes existing rows
        // UPDATE products SET price = 79.99 WHERE code = 'KB-100';

        // Removes
        // DELETE FROM products WHERE code = 'CB-500';

        // ~~~~~~~~~~~~~~ RECAP ~~~~~~~~~~~~~    
        // ALWAYS use PreparedStatement for input
        // DAO keeps SQL out of the UI
        // JDBC: Connection -> Statement -> ResultSet    



    }
}
