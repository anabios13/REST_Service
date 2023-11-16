package by.anabios13.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    static {
        try {
            Class.forName("org.postgresql.Driver"); // Загрузка драйвера PostgreSQL
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error connecting with database");
        }
    }

    private final static HikariConfig config = new HikariConfig("/db.properties");
    private final static HikariDataSource ds = new HikariDataSource(config);

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
