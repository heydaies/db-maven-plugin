package vs.db;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "copy")
public class DbCopyMojo extends AbstractMojo {

    @Parameter
    private String[] tables;

    @Parameter
    private String sourceUrl;

    @Parameter
    private String sourceUsername;

    @Parameter
    private String sourcePassword;

    @Parameter
    private String sourceDriver;

    @Parameter
    private String targetUrl;

    @Parameter
    private String targetUsername;

    @Parameter
    private String targetPassword;

    @Parameter
    private String targetDriver;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    public void setSourceDriver(String sourceDriver) {
        this.sourceDriver = sourceDriver;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }

    public void setTargetDriver(String targetDriver) {
        this.targetDriver = targetDriver;
    }
}
