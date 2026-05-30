import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static final String URL =
            "jdbc:mysql://localhost:3306/college_portal";

    static final String USER = "root";

    static final String PASSWORD = "Vaani789@";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database Connected!");

            return con;

        } catch (Exception e) {

            System.out.println(e);

            return null;
        }
    }
}