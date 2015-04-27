package vs.db.export;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import vs.db.util.DbConnectionFactory;
import vs.db.util.DbReader;
import vs.db.util.DbWriter;
import vs.db.util.DriverLoader;
import vs.db.util.impl.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@Mojo(name = "export")
public class DbExportMojo extends AbstractMojo {

    public enum Format {
        CSV,
        SQL
    }

    @Parameter
    private File outputFile;

    @Parameter
    private Format format;

    @Parameter
    private String driver;

    @Parameter
    private String url;

    @Parameter
    private String username;

    @Parameter
    private String password;

    @Parameter
    private String[] tables;

    private DriverLoader driverLoader;

    private DbConnectionFactory connectionFactory;

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

        try {
            driverLoader.load(driver);
        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException("Unable to load driver", e);
        }

        Connection connection = null;
        try {
            connection = connectionFactory.connect(url, username, password);
        } catch (SQLException e) {
            throw new MojoExecutionException("Database connection failed", e);
        }

        if (dbReader == null) {
            dbReader = new DefaultDbReader(connection);
        }
        if (dbWriter == null) {
            try {
                dbWriter = selectWriter();
            } catch (IOException e) {
                throw new MojoExecutionException("Can not open file", e);
            }
        }
        for (String table : tables) {
            dbReader.setTable(table);
            dbWriter.setTable(table);
            do {
                try {
                    dbWriter.write(dbReader.read());
                } catch (Exception e) {
                    try {
                        connection.close();
                    } catch (SQLException e1) {
                    }
                    throw new MojoExecutionException("Error occurred", e);
                }
            } while (dbReader.hasNext());
        }

        try {
            connection.close();
        } catch (SQLException e) {}

    }

    public DbWriter selectWriter() throws IOException {
        switch (format) {
            case CSV: return new CsvWriter(outputFile);
            case SQL: return new SqlQueryWriter(outputFile);
            default: return null;
        }
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setDriverLoader(DriverLoader driverLoader) {
        this.driverLoader = driverLoader;
    }

    public void setConnectionFactory(DbConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDbReader(DbReader dbReader) {
        this.dbReader = dbReader;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDbWriter(DbWriter dbWriter) {
        this.dbWriter = dbWriter;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}
