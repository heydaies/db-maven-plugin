package vs.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

    public SqlQueryBuilder insert(String table) {
        stringBuilder.append("INSERT INTO ").append(table);
        return this;
    }

    public SqlQueryBuilder values(List<Object[]> values) {
        String rowSeparator = " VALUES ";
        for (Object[] row : values) {
            stringBuilder.append(rowSeparator);

            stringBuilder.append("(");
            String valueSeparator = "";
            for (Object value : row) {
                stringBuilder.append(valueSeparator);
                if (value instanceof String || value instanceof Date) {
                    stringBuilder.append("'").append(value.toString()).append("'");
                } else {
                    stringBuilder.append(String.valueOf(value));
                }
                valueSeparator = ",";
            }
            stringBuilder.append(")");

            rowSeparator = ",";
        }
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
