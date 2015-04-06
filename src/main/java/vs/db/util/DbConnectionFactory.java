package vs.db.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnectionFactory {
    Connection connect(String url, String name, String password) throws SQLException;
}
