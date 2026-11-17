package edu.cwi.swdv110.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Every SQL statement for the books table lives in this class.
 * RULES: every method opens its own Connection in try-with-resources,
 * and every value from outside goes in through a ? parameter – never with +.
 */
public class BookDAO {

    private final String url;

    public BookDAO(String url) {
        this.url = url;
    }

    /** Helper: builds a Book from the CURRENT row of a ResultSet. is_read is stored as 0/1. */
    private Book toBook(ResultSet rs) throws SQLException {
        // TODO Part 2
        
        return new Book(
            rs.getInt("id"), 
            rs.getString("isbn"), 
            rs.getString("title"), 
            rs.getString("author"), 
            rs.getInt("year"), 
            rs.getInt("rating"), 
            rs.getBoolean("is_read")
        );
    }

    // ---------------- Part 2: reading ----------------

    /** All books ordered by title. */
    public List<Book> getAll() throws SQLException {
        ArrayList<Book> booksByTitle = new ArrayList<>();
        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books ORDER BY title ASC");
            ResultSet result = statement.executeQuery()){

            while(result.next()){
                booksByTitle.add(toBook(result));
            }
        }

        return booksByTitle;
    }

    /** The book with this exact ISBN, or Optional.empty(). */
    public Optional<Book> findByIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM books WHERE isbn = ?";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, isbn);

            try(ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    return Optional.of(toBook(result));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Books whose title OR author contains term (SQLite's LIKE ignores case for A–Z),
     * ordered by title. Hint: the % wildcards go in the PARAMETER, not the SQL:
     *   ps.setString(1, "%" + term + "%");
     */
    public List<Book> search(String term) throws SQLException {
        ArrayList<Book> booksContainTerm = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? ORDER BY title";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
                String pattern = "%" + term + "%";
                statement.setString(1, pattern);
                statement.setString(2, pattern);

                try(ResultSet result = statement.executeQuery()){
                    while(result.next()){
                        booksContainTerm.add(toBook(result));
                    }
                }
        }

        return booksContainTerm;
    }

    /** Books not yet read, ordered by title. */
    public List<Book> unread() throws SQLException {
        ArrayList<Book> unreadBooks = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE is_read = 0 ORDER BY title";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
                try(ResultSet result = statement.executeQuery()){
                    while(result.next()){
                        unreadBooks.add(toBook(result));
                    }
                }
            }
        return unreadBooks;
    }

    // ---------------- Part 3: writing ----------------

    /**
     * Inserts the book (ignore b.id() – the database assigns it) and returns the new id.
     * Hint: prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), then getGeneratedKeys().
     * A duplicate ISBN makes the database throw SQLException – let it propagate.
     */
    public int add(Book b) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author, year, rating, is_read) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, b.isbn());
                statement.setString(2, b.title());
                statement.setString(3, b.author());
                statement.setInt(4, b.year());
                statement.setInt(5, b.rating());
                statement.setInt(6, b.read() ? 1 : 0);

                statement.executeUpdate();

                try(ResultSet result = statement.getGeneratedKeys()){
                    if(result.next()){
                        return result.getInt(1);
                    }
                }
            }

        throw new SQLException();
    }

    /** Updates every column of the row whose id = b.id(). True if a row changed. */
    public boolean update(Book b) throws SQLException {
        String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, year = ?, "
                + "rating = ?, is_read = ? WHERE id = ?";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setString(1, b.isbn());
                statement.setString(2, b.title());
                statement.setString(3, b.author());
                statement.setInt(4, b.year());
                statement.setInt(5, b.rating());
                statement.setInt(6, b.read() ? 1 : 0);
                statement.setInt(7, b.id());

                return statement.executeUpdate() > 0;
        }
    }

    /**
     * Marks a book read and sets its rating. Rating outside 1–5 ->
     * IllegalArgumentException BEFORE touching the database. True if a row changed.
     */
    public boolean markRead(String isbn, int rating) throws SQLException {
        // TODO
        if(rating < 1 || rating > 5){
            throw new IllegalArgumentException();
        }

        String sql = "UPDATE books SET rating = ?, is_read = ? WHERE isbn = ?";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, rating);
                statement.setInt(2, 1);
                statement.setString(3, isbn);

                return statement.executeUpdate() > 0;

        }
    }

    /** Deletes by ISBN. True if a row was deleted. */
    public boolean delete(String isbn) throws SQLException {
        // TODO
        String sql = "DELETE FROM books WHERE isbn = ?";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setString(1, isbn);

                return statement.executeUpdate() > 0;
            }
    }

    // ---------------- Part 4: reports ----------------

    /**
     * author -> number of books, most books first, ties by author A–Z.
     * Let SQL do the work: GROUP BY, COUNT(*), ORDER BY. Use a LinkedHashMap to keep that order.
     */
    public Map<String, Integer> countByAuthor() throws SQLException {
        // TODO
        LinkedHashMap<String, Integer> countedByAuthor = new LinkedHashMap<>();
    
        // SELECT author, COUNT(*) AS totalBooks
        // FROM books 
        // GROUP BY author;

        String sql = "SELECT author, COUNT(*) AS totalBooks FROM books GROUP BY author ORDER BY totalBooks DESC, author ASC";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){
                
                try(ResultSet result = statement.executeQuery()){
                    while(result.next()){
                        countedByAuthor.put(result.getString(1), result.getInt(2));
                    }
                }
            }

        return countedByAuthor;
    }

    /** Average rating of RATED books (rating > 0); 0.0 if none are rated. Use SQL's AVG. */
    public double averageRating() throws SQLException {
        // TODO
        // SELECT AVG(rating) AS averageRating
        // FROM books
        // WHERE rating > 0;
        String sql = "SELECT AVG(rating) FROM books WHERE rating > 0";

        try(Connection connection = Database.connect(url);
            PreparedStatement statement = connection.prepareStatement(sql)){

                try(ResultSet result = statement.executeQuery()){
                    return result.getDouble(1);
                }
            }
    }
}
