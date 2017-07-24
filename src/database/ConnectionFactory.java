package database;

/**
 * Created by gadalaya on 7/23/2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/vehicle_db";
    public static final String USER = "root";
    public static final String PASS = "CDKcdk11";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException|ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to the database.", e);
        }
    }
}