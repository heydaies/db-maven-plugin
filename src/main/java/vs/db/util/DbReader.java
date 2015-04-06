package vs.db.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbReader {
    ResultSet read() throws SQLException;

    boolean hasNext();

    void setTable(String table);
}
