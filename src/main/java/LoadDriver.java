import java.sql.*;
import java.util.Scanner;

public class LoadDriver {
    public static Scanner scanner = new Scanner(System.in);
    public static String url = "jdbc:mysql://localhost:3306/northwind";
    public static String user = "root";
    public static String password = "randylopez";


    public static void main(String[] args) {

        String Input;
        while (true) {
            System.out.println("""
                    \nWhat do you want to do?
                     1) Display all products
                     2) Display all customers
                     3) Display all categories
                     0) Exit
                    Select an option:""");
            Input = scanner.nextLine().trim();

            switch (Input) {
                case "1":
                    executeQuery("SELECT * FROM Products",
                            "ProductID", "ProductName", "UnitPrice", "UnitsInStock");
                    break;


                case "2":
                    executeQuery("SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country",
                            "ContactName", "CompanyName", "City", "Country", "Phone");
                    break;


                case "3":
                    executeCategoryQuery();
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Choose a valid option.");
                    break;
            }
        }
    }

    private static void executeQuery(String query, String... columnNames) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                for (String columnName : columnNames) {
                    String columnValue = rs.getString(columnName);
                    System.out.println(columnName + ": " + columnValue);
                }
                System.out.println("------------------");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void executeCategoryQuery() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            // Display all categories
            executeQuery("SELECT * FROM categories Order By CategoryID",
                    "CategoryID", "CategoryName");

            // Prompt the user for the category they want to see
            System.out.println("Enter the CategoryID you want to see:");
            int categoryId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Execute the query with the selected category
            String productQuery = "SELECT * FROM Products WHERE CategoryID = ?";
            executeQueryWithIntParameter(conn, productQuery,
                    "ProductID", "ProductName", "UnitPrice", "UnitsInStock", String.valueOf(categoryId));

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeQueryWithIntParameter(Connection conn, String query, String... columnNamesAndInt) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            // Set the int parameter
            int categoryId = Integer.parseInt(columnNamesAndInt[columnNamesAndInt.length - 1]);
            preparedStatement.setInt(1, categoryId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < columnNamesAndInt.length - 1; i++) {
                    String columnName = columnNamesAndInt[i];
                    String columnValue = rs.getString(columnName);
                    System.out.println(columnName + ": " + columnValue);
                }
                System.out.println("------------------");
            }

            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
