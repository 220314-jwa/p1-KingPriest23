package Utilities;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    // a factory is responsible for generating an object and returning it
    // singleton is a design pattern where we only create one instance of a class,
    private static ConnectionFactory connectionFactory = null;
    // properties object contains key and value pair both as a string. Java.util.properties
    // class is the subclass of Hashtable. Used to get property value based on the property key.
    // The Properties class provides methods to get data from the properties file and store
    // data into the properties file.
        private static Properties properties;
    // return our connection to the database:
    public Connection getConnection() {
        Connection connection = null;
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        // try connecting to the database:
        try {
            // get connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // if something goes wrong, view the stack trace
            e.printStackTrace();
        }
        return connection;
    }

    private ConnectionFactory() {
            // InputStream class is the superclass of all the io classes representing an input stream
            // of bytes. Applications that are defining subclass of InputStream must provide method,
            // returning the next byte of input.
            InputStream stream = ConnectionFactory.class.getClassLoader().getResourceAsStream("dbConfig.properties");
            try{
               properties = new Properties();
                properties.load(stream);
                //IOException is a case where the user inputs improper data into the program
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static ConnectionFactory getConnectionFactory() {
            if (connectionFactory == null) connectionFactory = new ConnectionFactory();
            return connectionFactory;
        }


}