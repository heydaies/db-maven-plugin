package vs.db.util.impl;

import junit.framework.TestCase;
import org.apache.maven.plugin.testing.SilentLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultDbReaderTest extends TestCase {

    private DefaultDbReader unit;

    private Connection connection;

    public void setUp() throws Exception {
        super.setUp();

        connection = mock(Connection.class);
        unit = new DefaultDbReader(connection, new SilentLog());
    }

    public void testRead() throws Exception {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement countStatement = mock(PreparedStatement.class);
        ResultSet countResultSet = mock(ResultSet.class);

        when(connection.prepareStatement("SELECT * FROM testTable LIMIT " + DefaultDbReader.BATCH_SIZE + " OFFSET 0"))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(connection.prepareStatement("SELECT COUNT(*) FROM testTable"))
                .thenReturn(countStatement);
        when(countStatement.executeQuery()).thenReturn(countResultSet);
        when(countResultSet.getLong(1)).thenReturn(10l);

        unit.setTable("testTable");
        ResultSet result = unit.read();
        assertEquals(result, resultSet);
        assertTrue(unit.hasNext());

        when(connection.prepareStatement("SELECT * FROM testTable LIMIT " + DefaultDbReader.BATCH_SIZE + " OFFSET " + DefaultDbReader.BATCH_SIZE))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        result = unit.read();
        assertEquals(result, resultSet);
        assertFalse(unit.hasNext());
    }

    public void testReadWithDifferentTable() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        unit.setTable("testTable");
        unit.read();

        unit.setTable("nextTable");
        unit.read();

        verify(connection).prepareStatement("SELECT * FROM testTable LIMIT " + DefaultDbReader.BATCH_SIZE + " OFFSET 0");
        verify(connection).prepareStatement("SELECT * FROM nextTable LIMIT " + DefaultDbReader.BATCH_SIZE + " OFFSET 0");
    }
}