package vs.db.util.impl;

import junit.framework.TestCase;
import vs.db.util.impl.DefaultDbWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultDbWriterTest extends TestCase {

    private DefaultDbWriter dbWriter;

    private Connection connection;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        connection = mock(Connection.class);
        dbWriter = new DefaultDbWriter(connection);
    }

    public void testWrite() throws Exception {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        ResultSet resultSet = mock(ResultSet.class);
        ResultSetMetaData setMetaData = mock(ResultSetMetaData.class);
        when(resultSet.getMetaData()).thenReturn(setMetaData);
        when(setMetaData.getColumnCount()).thenReturn(3);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject(1)).thenReturn(10);
        when(resultSet.getObject(2)).thenReturn("test");
        when(resultSet.getObject(3)).thenReturn(false);

        dbWriter.setTable("testTable");
        dbWriter.write(resultSet);

        verify(connection).prepareStatement("INSERT INTO testTable VALUES (10,'test',false)");
        verify(preparedStatement).executeUpdate();
    }
}