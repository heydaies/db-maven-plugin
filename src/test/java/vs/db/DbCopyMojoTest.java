package vs.db;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public class DbCopyMojoTest extends AbstractMojoTestCase {

    private DbCopyMojo unit;

    public void setUp() throws Exception {
        super.setUp();

        File pom = getTestFile("src/test/resources/dbCopyMojoTestPom.xml");
        unit = (DbCopyMojo) lookupMojo("copy", pom);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testExecute() throws Exception {
        unit.execute();
    }
}