package vs.db.util.impl;

import vs.db.util.DbWriter;
import vs.db.util.SqlQueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDbWriter implements DbWriter {
    private Connection connection;

    private String table;

    private SqlQueryBuilder queryBuilder = new SqlQueryBuilder();

    public DefaultDbWriter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void write(ResultSet resultSet) throws SQLException {
        List<Object[]> results = new ArrayList<Object[]>();
        int columnCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = resultSet.getObject(i + 1);
            }
            results.add(row);
        }

        if (!results.isEmpty()) {
            PreparedStatement statement = queryBuilder
                    .insert(table)
                    .values(results)
                    .build(connection);

            statement.executeUpdate();
        }
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }
}
