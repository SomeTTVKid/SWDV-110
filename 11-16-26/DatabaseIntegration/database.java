
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {

    public static void main(String[] args) {
        String productsQuery = "SELECT code, description, price " + "FROM products ORDER BY code";
        String dbUrl = "jdbc:sqlite:products.db";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(productsQuery)) {
            while (rs.next()) {
                String code = rs.getString("code");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");
            }
            
        } catch (SQLException e){
            
        }
    }
}
