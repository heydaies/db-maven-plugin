package vs.db.util.impl;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CsvWriterTest extends TestCase {

    private CsvWriter unit;

    private File testFile;

    public void setUp() throws Exception {
        super.setUp();

        testFile = File.createTempFile("testoutput", "csv");
        unit = new CsvWriter(testFile);
    }

    private ResultSet prepareMocks() throws SQLException {
        ResultSetMetaData resultSetMetaData = mock(ResultSetMetaData.class);
        when(resultSetMetaData.getColumnCount()).thenReturn(3);
        when(resultSetMetaData.getColumnLabel(1)).thenReturn("col1");
        when(resultSetMetaData.getColumnLabel(2)).thenReturn("col2");
        when(resultSetMetaData.getColumnLabel(3)).thenReturn("col3");
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getObject(1)).thenReturn(1, 2, 3);
        when(resultSet.getObject(2)).thenReturn("str1", null, "str2");
        when(resultSet.getObject(3)).thenReturn(Timestamp.valueOf("2015-04-06 18:30:25"), Timestamp.valueOf("2015-04-06 18:35:50"), null);

        return resultSet;
    }

    private static void assertFiles(File expected, File result) throws IOException {
        String str1 = new String(Files.readAllBytes(expected.toPath()));
        String str2 = new String(Files.readAllBytes(result.toPath()));
        str1 = str1.trim().replace("\r\n", "\n");
        str2 = str2.trim().replace("\r\n", "\n");

        assertEquals(str1, str2);
    }

    public void testWrite() throws Exception {
        unit.setTable("table1");
        unit.write(prepareMocks());
        unit.write(prepareMocks());

        assertFiles(new File("src/test/resources/csvWriterTestExpectedResult.csv"), testFile);
    }
}