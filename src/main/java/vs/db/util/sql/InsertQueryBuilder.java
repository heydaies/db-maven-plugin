package vs.db.util.sql;

import java.util.Date;
import java.util.List;

public class InsertQueryBuilder extends SqlQueryBuilder {


    public InsertQueryBuilder(String table) {
        stringBuilder.append("INSERT INTO ").append(table);
    }

    public InsertQueryBuilder values(List<Object[]> values) {
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

}
