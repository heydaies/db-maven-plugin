package vs.db;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;
import java.sql.Connection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DbCopyMojoTest extends AbstractMojoTestCase {

    private DbCopyMojo unit;

    private DbConnectionFactory connectionFactory;


    public void setUp() throws Exception {
        super.setUp();

        File pom = getTestFile("src/test/resources/dbCopyMojoTestPom.xml");
        unit = (DbCopyMojo) lookupMojo("copy", pom);

        connectionFactory = mock(DbConnectionFactory.class);
        unit.setConnectionFactory(connectionFactory);

        DriverLoader driverLoader = mock(DriverLoader.class);
        unit.setDriverLoader(driverLoader);

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

        unit.execute();
    }
}