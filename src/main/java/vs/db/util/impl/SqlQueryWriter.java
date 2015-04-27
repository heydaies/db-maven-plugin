package vs.db.util.impl;

import vs.db.util.DbWriter;
import vs.db.util.sql.SqlQueryBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlQueryWriter implements DbWriter {

    private FileWriter fileWriter;

    private String table;

    public SqlQueryWriter(File outputFile) throws IOException {
        fileWriter = new FileWriter(outputFile);
    }

    @Override
    public void write(ResultSet resultSet) throws Exception {
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
            String query = SqlQueryBuilder
                    .insert(table)
                    .values(results)
                    .build();

            fileWriter.write(query + "\r\n");
            fileWriter.flush();
        }
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }
}
