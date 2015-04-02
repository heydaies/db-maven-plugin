package vs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlQueryBuilder {

    public static enum SelectionType {
        EVERYTHING("*"),
        COUNT("COUNT(*)");

        private String str;

        private SelectionType(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    private StringBuilder stringBuilder = new StringBuilder();

    public SqlQueryBuilder select(SelectionType type) {
        stringBuilder.append("SELECT ").append(type.toString());
        return this;
    }

    public SqlQueryBuilder from(String table) {
        stringBuilder.append(" FROM ").append(table);
        return this;
    }

    public SqlQueryBuilder limit(long limit) {
        stringBuilder.append(" LIMIT ").append(limit);
        return this;
    }

    public SqlQueryBuilder offset(long offset) {
        stringBuilder.append(" OFFSET ").append(offset);
        return this;
    }

    public PreparedStatement build(Connection connection) throws SQLException {
        try {
            return connection.prepareStatement(stringBuilder.toString());
        } finally {
            stringBuilder = new StringBuilder();
        }
    }

}
