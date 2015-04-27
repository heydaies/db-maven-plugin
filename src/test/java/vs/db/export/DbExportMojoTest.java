package vs.db.export;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.SilentLog;
import vs.db.copy.DbCopyMojo;
import vs.db.util.DbConnectionFactory;
import vs.db.util.DbReader;
import vs.db.util.DbWriter;
import vs.db.util.DriverLoader;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DbExportMojoTest extends AbstractMojoTestCase {

    private DbExportMojo unit;

    private DbConnectionFactory connectionFactory;

    private DbWriter dbWriter;

    private DbReader dbReader;

    public void setUp() throws Exception {
        super.setUp();

        File pom = getTestFile("src/test/resources/dbExportMojoTestPom.xml");
        unit = (DbExportMojo) lookupMojo("export", pom);
        unit.setLog(new SilentLog());

        connectionFactory = mock(DbConnectionFactory.class);
        unit.setConnectionFactory(connectionFactory);

        DriverLoader driverLoader = mock(DriverLoader.class);
        unit.setDriverLoader(driverLoader);

        dbWriter = mock(DbWriter.class);
        unit.setDbWriter(dbWriter);

        dbReader = mock(DbReader.class);
        unit.setDbReader(dbReader);
    }

    public void testExecute() throws Exception {
        Connection connection = mock(Connection.class);

        when(connectionFactory.connect("jdbc:driver://host:port/database", "name", "password"))
                .thenReturn(connection);
        when(dbReader.hasNext()).thenReturn(true, true, false);

        unit.execute();

        verify(dbWriter).setTable("table1");
        verify(dbWriter).setTable("table2");
        verify(dbReader).setTable("table1");
        verify(dbReader).setTable("table2");
        verify(dbReader, times(4)).read();
        verify(dbWriter, times(4)).write(any(ResultSet.class));

        verify(connection).close();
    }
}