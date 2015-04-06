package vs.db.util.impl;

import junit.framework.TestCase;
import vs.db.util.SqlQueryBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SqlQueryBuilderTest extends TestCase{

    private SqlQueryBuilder unit;

    public void setUp() throws Exception {
        unit = new SqlQueryBuilder();
    }

    public void testBuildSelectCount() throws Exception {
        Connection connection = mock(Connection.class);

        unit.select(SqlQueryBuilder.SelectionType.COUNT)
                .from("testTable")
                .build(connection);

        verify(connection).prepareStatement("SELECT COUNT(*) FROM testTable");
    }

    public void testBuildSelectWithLimitAndOffset() throws Exception {
        Connection connection = mock(Connection.class);

        unit.select(SqlQueryBuilder.SelectionType.EVERYTHING)
                .from("testTable")
                .limit(100)
                .offset(200)
                .build(connection);

        verify(connection).prepareStatement("SELECT * FROM testTable LIMIT 100 OFFSET 200");
    }

    public void testBuildInsert() throws SQLException {
        Connection connection = mock(Connection.class);

        List<Object[]> values = Arrays.asList(
                new Object[] { 10, "TestStr", false },
                new Object[] { 11, "", true },
                new Object[] { 2, null, null }
        );

        unit.insert("testTable")
                .values(values)
                .build(connection);

        verify(connection).prepareStatement("INSERT INTO testTable VALUES (10,'TestStr',false),(11,'',true),(2,null,null)");
    }
}