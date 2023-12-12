import java.sql.*;

public class LoadDriver {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = " ";
        String password = " ";

        String query = "SELECT * FROM Products";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            // Executing query
            ResultSet rs = stmt.executeQuery(query);

//            while (rs.next()) {
//                String productId = rs.getString("ProductID");
//                String productName = rs.getString("ProductName");
//                String UnitPrice = rs.getString("UnitPrice");
//                String UnitsInStock = rs.getString("UnitsInStock");
//
//                System.out.println("Product Id: " + productId);
//                System.out.println("Name: " + productName);
//                System.out.println("Price: " + UnitPrice);
//                System.out.println("Stock: " + UnitsInStock);
//                System.out.println("------------------");
//            }
//                or

            System.out.printf("%-4s %-35s %-8s %-6s%n", "Id", "Name", "Price", "Stock");
            System.out.println("---  ---------------------------------   -------  ------");


            while (rs.next()) {
                String productId = rs.getString("ProductID");
                String productName = rs.getString("ProductName");
                double unitPrice = rs.getDouble("UnitPrice");
                int unitsInStock = rs.getInt("UnitsInStock");

                System.out.printf("%-4s %-35s %8.4f %6d%n", productId, productName, unitPrice, unitsInStock);
            }
            rs.close();
            stmt.close();
            conn.close();


        } catch (Exception ex) {
        }
    }
}