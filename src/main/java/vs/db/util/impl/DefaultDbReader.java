package vs.db.util.impl;

import org.apache.maven.plugin.logging.Log;
import vs.db.util.DbReader;
import vs.db.util.sql.SelectQueryBuilder;
import vs.db.util.sql.SqlQueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultDbReader implements DbReader {

    public static final long BATCH_SIZE = 25;

    private Connection connection;

    private String table;

    private long currentCount = 0;

    private boolean next = true;

    private Log logger;

    public DefaultDbReader(Connection connection, Log logger) {
        this.connection = connection;
        this.logger = logger;
    }

    @Override
    public ResultSet read() throws SQLException {
        PreparedStatement statement = SqlQueryBuilder
                .select(SelectQueryBuilder.SelectionType.EVERYTHING, table)
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

        try {
            PreparedStatement statement = SqlQueryBuilder
                    .select(SelectQueryBuilder.SelectionType.COUNT, table)
                    .build(connection);

            ResultSet result = statement.executeQuery();
            long count = result.getLong(1);
            logger.info("Found " + count + " records on " + table);

        } catch (SQLException e) { }
    }
}
