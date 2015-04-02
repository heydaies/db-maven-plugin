package vs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultDbReader implements DbReader {

    public static final long BATCH_SIZE = 25;

    private Connection connection;

    private String table;

    private SqlQueryBuilder queryBuilder = new SqlQueryBuilder();

    private long currentCount = 0;

    private boolean next = true;

    public DefaultDbReader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ResultSet read() throws SQLException {
        PreparedStatement statement = queryBuilder
                .select(SqlQueryBuilder.SelectionType.EVERYTHING)
                .from(table)
                .limit(BATCH_SIZE)
                .offset(currentCount)
                .build(connection);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            currentCount += BATCH_SIZE;
            resultSet.beforeFirst();
            next = true;
        } else {
            next = false;
        }

        return resultSet;
    }

    @Override
    public boolean hasNext() {
        return next;
    }

    @Override
    public void setTable(String table) {
        currentCount = 0;
        this.table = table;
    }
}
