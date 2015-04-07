package vs.db.util.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import vs.db.util.DbWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CsvWriter implements DbWriter {

    private FileWriter fileWriter;

    private String table;

    private boolean first = true;

    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withNullString("NULL").withQuoteMode(QuoteMode.NON_NUMERIC);

    public CsvWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    @Override
    public void write(ResultSet resultSet) throws Exception {
        if (first) {
            CSVFormat.DEFAULT.print(fileWriter).printRecord(table);
            CSVFormat.DEFAULT.withHeader(resultSet).print(fileWriter);
            first = false;
        }
        FORMAT.print(fileWriter).printRecords(resultSet);
        fileWriter.flush();
    }

    @Override
    public void setTable(String table) {
        this.table = table;
        first = true;
    }
}
