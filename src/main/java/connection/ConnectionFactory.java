package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection class, implementing the methods necessary to begin / end the connection with a given database
 */

public class ConnectionFactory {

    /**
     * used to generate the log message in case of a warning
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /**
     * the driver used to connect to the database
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * the url used to connect to the database
     */
    private static final String DBURL = "jdbc:mysql://localhost:3306/management";
    /**
     * the user used to connect to the database
     */
    private static final String USER = "root";
    /**
     * the password used to connect to the database
     */
    private static final String PASS = "root";

    /**
     * the single instance of the ConnectionFactory class
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * basic constructor
     * generates a ClassNotFoundException if the corresponding driver class is not found
     */
    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * private method used to establish the connection between the program and the database
     * can generate an SQLException
     * @return a Connection object representing the generated connection
     */
    private Connection createConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * public method used to retrieve the single instance of the connection generated in the createConnection() method
     * @return a Connection object representing the generated connection
     */
    public static Connection getConnection(){
        return singleInstance.createConnection();
    }

    /**
     * method used to close the connection between the program and the database
     * can generate an SQLException
     * @param connection the Connection to be closed
     */
    public static void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
            }catch(SQLException e){
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection");
            }
        }
    }

    /**
     * method used to close the statement when executing a query on the database
     * can generate an SQLException
     * @param statement the Statement to be closed
     */
    public static void close(Statement statement){
        if(statement != null){
            try{
                statement.close();
            }catch(SQLException e){
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement");
            }
        }
    }

    /**
     * method used to close the resultSet when executing a SELECT query
     * can generate an SQLException
     * @param resultSet the ResultSet to be closed
     */
    public static void close(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
            }catch(SQLException e){
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
                e.printStackTrace();
            }
        }
    }

}
