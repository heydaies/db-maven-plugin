package vs.db;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.mockito.Mockito;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DbCopyMojoTest extends AbstractMojoTestCase {

    private DbCopyMojo unit;

    private DbConnectionFactory connectionFactory;

    private DbWriter dbWriter;

    private DbReader dbReader;

    public void setUp() throws Exception {
        super.setUp();

        File pom = getTestFile("src/test/resources/dbCopyMojoTestPom.xml");
        unit = (DbCopyMojo) lookupMojo("copy", pom);

        connectionFactory = mock(DbConnectionFactory.class);
        unit.setConnectionFactory(connectionFactory);

        DriverLoader driverLoader = mock(DriverLoader.class);
        unit.setDriverLoader(driverLoader);

        dbWriter = mock(DbWriter.class);
        unit.setDbWriter(dbWriter);

        dbReader = mock(DbReader.class);
        unit.setDbReader(dbReader);

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testExecute() throws Exception {
        Connection sourceConnection = mock(Connection.class);
        Connection targetConnection = mock(Connection.class);

        when(connectionFactory.connect("jdbc:driver://host:port/source", "name", "password"))
                .thenReturn(sourceConnection);
        when(connectionFactory.connect("jdbc:driver://host:port/target", null, null))
                .thenReturn(targetConnection);
        when(dbReader.hasNext()).thenReturn(true, true, false);

        unit.execute();

        verify(dbWriter).setTable("table1");
        verify(dbWriter).setTable("table2");
        verify(dbReader).setTable("table1");
        verify(dbReader).setTable("table2");
        verify(dbReader, times(4)).read();
        verify(dbWriter, times(4)).write(any(ResultSet.class));

        verify(targetConnection).close();
        verify(sourceConnection).close();
    }
}