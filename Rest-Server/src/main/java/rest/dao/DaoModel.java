package rest.dao;

import rest.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model for any Dao class (with common elements)
 */
public abstract class DaoModel {
    private Properties properties;
    private InputStream input;

    private String dbUrl = "jdbc:postgresql://localhost:5432/aiws_db";
    private String dbUser = "admin_rest";
    private String dbPwd = "admin_rest";

    protected static Connection conn;
    protected Statement stmt;

    public DaoModel() {
        if(conn == null) init();
    }

    /**
     * Initialise DB connection
     */
    private void init() {
        // TODO: Read values from file
//        getPropertiesFile();
//
//        dbUrl = properties.getProperty("DB.URL");
//        dbUser = properties.getProperty("DB.USER");
//        dbPwd = properties.getProperty("DB.PWD");

        try {
            // Setting up database connection
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            stmt = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(DaoModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Load the db.properties file
     */
    private void getPropertiesFile() {
        properties = new Properties();
        try {
            System.out.println(System.getProperty("user.dir"));
            ClassLoader classLoader = getClass().getClassLoader();
            input = classLoader.getResourceAsStream(Constants.DB_PROPERTIES);
            //input = new FileInputStream(DB_PROPERTIES);
            properties.load(input);
        } catch (IOException e) {
            Logger.getLogger(DaoModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void close() {
        try {
            if(conn != null) conn.close();
        } catch (SQLException e) {
            Logger.getLogger(DaoModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
