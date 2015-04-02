package vs.db;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.sql.Connection;
import java.sql.SQLException;

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

    private DbConnectionFactory connectionFactory;

    private DriverLoader driverLoader;

    private DbReader dbReader;

    private DbWriter dbWriter;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (driverLoader == null) {
            driverLoader = new DefaultDriverLoader();
        }
        if (connectionFactory == null) {
            connectionFactory = new DefaultDbConnectionFactory();
        }

        // Loading drivers
        try {
            driverLoader.load(sourceDriver);
            driverLoader.load(targetDriver);
        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException("Unable to load driver", e);
        }

        // Establishing database connections
        Connection sourceConnection = null;
        Connection targetConnection = null;
        try {
            sourceConnection = connectionFactory.connect(sourceUrl, sourceUsername, sourcePassword);
            targetConnection = connectionFactory.connect(targetUrl, targetUsername, targetPassword);
        } catch (SQLException e) {
            throw new MojoExecutionException("Database connection failed", e);
        }

        if (dbReader == null) {
            dbReader = new DefaultDbReader(sourceConnection);
        }
        if (dbWriter == null) {
            dbWriter = new DefaultDbWriter(targetConnection);
        }
        for (String table : tables) {
            dbReader.setTable(table);
            dbWriter.setTable(table);
            do {
                try {
                    dbWriter.write(dbReader.read());
                } catch (SQLException e) {
                    throw new MojoExecutionException("Error occurred", e);
                }
            } while (dbReader.hasNext());
        }

        try {
            sourceConnection.close();
            targetConnection.close();
        } catch (SQLException e) {
        }

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

    public void setConnectionFactory(DbConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDriverLoader(DriverLoader driverLoader) {
        this.driverLoader = driverLoader;
    }

    public void setDbReader(DbReader dbReader) {
        this.dbReader = dbReader;
    }

    public void setDbWriter(DbWriter dbWriter) {
        this.dbWriter = dbWriter;
    }
}
