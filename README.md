# db-maven-plugin

Database plugin for maven.  
Under Apache License 2.0.

##Goals  
###Copy  
Designed to copy set of tables from one database to another. Uses jbdc drivers. Tables should have identical names and identical or compatible column types.  
**Usage example:**
```
...
<plugin>
    <groupId>vs.db</groupId>
    <artifactId>db-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.35</version>
        </dependency>
    </dependencies>
    <configuration>
        <sourceDriver>com.mysql.jdbc.Driver</sourceDriver>
        <targetDriver>com.mysql.jdbc.Driver</targetDriver>
        <sourceUrl>jdbc:mysql://localhost/myDb</sourceUrl>
        <targetUrl>jdbc:mysql://localhost/backupDb</targetUrl>
        <sourceUsername>dev</sourceUsername>
        <sourcePassword>dev</sourcePassword>
        <targetUsername>admin</targetUsername>
        <targetPassword>test</targetPassword>
        <tables>
            <table>users</table>
            <table>user_roles</table>
            <table>devices</table>
            <table>events</table>
        </tables>
    </configuration>
    <executions>
        <execution>
            <phase>integration-test</phase>
            <goals>
                <goal>copy</goal>
            </goals>
        </execution>
    </executions>
</plugin>
...
```
**Parameter description:**  
sourceDriver - JDBC driver for source database  
targetDriver - JDBC driver for taraget database  
sourceUrl - Url to source database  
targetUrl - Url to target database  
sourceUsername/sourcePassword - Source database credentials  
targetUsername/targetPassword - Target database credentials  
tables - List of tables to copy 
###Export
Can be used for exporting table data to files.  
Supported formats:  
> CSV  
> SQL

**Usage example:**  
```
...
<plugin>
    <groupId>vs.db</groupId>
    <artifactId>db-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.35</version>
        </dependency>
    </dependencies>
    <configuration>
        <outputFile>output.csv</outputFile>
        <driver>com.mysql.jdbc.Driver</driver>
        <url>jdbc:mysql://localhost/myDb</url>
        <username>dev</username>
        <password>dev</password>
        <format>CSV</format>
        <tables>
            <table>users</table>
            <table>user_roles</table>
            <table>devices</table>
            <table>events</table>
        </tables>
    </configuration>
    <executions>
        <execution>
            <phase>integration-test</phase>
            <goals>
                <goal>export</goal>
            </goals>
        </execution>
    </executions>
</plugin>
...
```
**Prameter description:**  
outputFile - Ouput file path  
driver - JDBC driver name  
url - Database url  
username/password - Databse credentials
format - Ouput format  
tables - List of tables to export  
