package vs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DefaultDbConnectionFactory implements DbConnectionFactory {
    @Override
    public Connection connect(String url, String name, String password) throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }
}
