package rest.todo.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rest.todo.utils.Constants.DB_PROPERTIES;

/**
 * Model for any Dao class (with common elements)
 */
public abstract class DaoModel {
    protected Properties properties;
    protected FileInputStream input;

    protected String dbUrl;
    protected String dbUser;
    protected String dbPwd;

    private Connection conn;
    private Statement stmt;

    public DaoModel() {
        init();
    }

    /**
     * Initialise DB connection
     */
    private void init() {
        getPropertiesFile();

        dbUrl = properties.getProperty("DB.URL");
        dbUser = properties.getProperty("DB.USER");
        dbPwd = properties.getProperty("DB.PWD");

        try {
            // Setting up database connection
            Class.forName(properties.getProperty("DB.JDBC"));
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            stmt = conn.createStatement();
            // May have to be removed later
            // dataServices = new DataServices(dbUser, dbPwd, dbUrl);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DaoModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Load the db.properties file
     */
    private void getPropertiesFile() {
        properties = new Properties();
        try {
            input = new FileInputStream(DB_PROPERTIES);
            properties.load(input);
        } catch (IOException e) {
            Logger.getLogger(DaoModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
