package vs.db.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlQueryBuilder {

    protected StringBuilder stringBuilder = new StringBuilder();

    protected SqlQueryBuilder() {}

    public static SelectQueryBuilder select(SelectQueryBuilder.SelectionType type, String table) {
        return new SelectQueryBuilder(type, table);
    }

    public static InsertQueryBuilder insert(String table) {
        return new InsertQueryBuilder(table);
    }

    public PreparedStatement build(Connection connection) throws SQLException {
        return connection.prepareStatement(build());
    }

    public String build() {
        String result = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        return result;
    }

}
