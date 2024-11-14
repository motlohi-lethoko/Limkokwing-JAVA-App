package motlohi.javagroupassignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/limkokwing_university";
    private static final String USER = "root";
    private static final String PASSWORD = "james";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
