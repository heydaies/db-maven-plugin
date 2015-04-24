package vs.db.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlQueryBuilder {

    protected StringBuilder stringBuilder = new StringBuilder();

    public SelectQueryBuilder select(SelectQueryBuilder.SelectionType type, String table) {
        return new SelectQueryBuilder(type, table);
    }

    public InsertQueryBuilder insert(String table) {
        return new InsertQueryBuilder(table);
    }

    public PreparedStatement build(Connection connection) throws SQLException {
        try {
            return connection.prepareStatement(stringBuilder.toString());
        } finally {
            stringBuilder = new StringBuilder();
        }
    }

}
