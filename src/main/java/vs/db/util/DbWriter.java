package vs.db.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbWriter {
    void write(ResultSet resultSet) throws SQLException;

    void setTable(String table);
}
