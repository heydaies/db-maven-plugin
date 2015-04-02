package vs.db;

import junit.framework.TestCase;

import java.sql.Connection;

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
}