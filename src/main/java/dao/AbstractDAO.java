package dao;

import connection.ConnectionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access class, implementing the operations available on the database: SELECT, INSERT, UPDATE, DELETE through reflection
 * @param <T> generic parameter, which allows the class to be used with an object of any type
 */

public class AbstractDAO<T> {

    /**
     * used to generate the log message in case of a warning
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    /**
     * the type of the object which will be manipulated
     */
    private final Class<T> type;

    /**
     * basic constructor, assigning a new value to the type field
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * method to create the SELECT query for the database, based on a specified field
     * @param field String representing the field based on which the query will be performed
     * @return a String representing the SELECT query
     */
    protected String createSelectQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append("`" + type.getSimpleName() + "`");
        if(field != null) {
            sb.append(" WHERE " + type.getSimpleName() + "." + field + "=?");
        }
        return sb.toString();
    }

    /**
     * method to create the INSERT query for the database, based on a specified object
     * @param t the object which will be inserted into the database
     * @return a String representing the INSERT query
     */
   protected String createInsertQuery(T t) {
       StringBuilder sb = new StringBuilder();
       sb.append("INSERT INTO ");
       sb.append("`" + type.getSimpleName() + "`");
       sb.append(" VALUES (");
       try{
           for(Field field : type.getDeclaredFields()){
               field.setAccessible(true);
               sb.append("'" + field.get(t) + "'" + ",");
           }
           sb.setCharAt(sb.length()-1, ')');
       }
       catch(IllegalAccessException e){
           LOGGER.log(Level.WARNING, type.getName() + "DAO:InsertQuery " + e.getMessage());
       }
       return sb.toString();
   }

    /**
     * method to create the UPDATE query for the database, based on a specified object
     * @param t the object which will be updated in the database
     * @return a String representing the UPDATE query
     */
   protected String createUpdateQuery(T t){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("`" + type.getSimpleName() + "`");
        sb.append(" SET " );
        try{
            for(Field field : type.getDeclaredFields()){
                field.setAccessible(true);
                sb.append(field.getName() + "='" + field.get(t) + "',");
            }
            sb.setCharAt(sb.length()-1, ' ');
            Field id = type.getDeclaredFields()[0];
            id.setAccessible(true);
            sb.append("WHERE " + id.getName() + "=" + id.get(t));
        }catch(IllegalAccessException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:UpdateQuery " + e.getMessage());
        }
        return sb.toString();
   }

    /**
     * method to create the DELETE query for the database, based on a specified object
     * @param t the object which will be deleted from the database
     * @return a String representing the DELETE query
     */
    protected String createDeleteQuery(T t){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append("`" + type.getSimpleName() + "`");
        try{
            Field id = type.getDeclaredFields()[0];
            id.setAccessible(true);
            sb.append(" WHERE " + id.getName() + "=" + id.get(t));
        }catch(IllegalAccessException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:DeleteQuery " + e.getMessage());
        }
        return sb.toString();
    }

    /**
     * method to retrieve all the data from a certain table in the database
     * @return a list of objects T, representing all the data available in the database
     */
    public List<T> findAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(null);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return null;
            }
            return createObjects(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        }finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * method to retrieve an object of type T, with a specified id
     * @param idField String representing the name of the id field in the table
     * @param id int representing the id to be searched for
     * @return an object of type T corresponding to the given id or null, in case it wasn't found
     */
    public T findById(String idField, int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(idField);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return null;
            }
            return createObjects(resultSet).get(0);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * method to insert a new object T in the database
     * @param t object of type T which is to be inserted in the corresponding table
     */
    public void insert(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(t);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "DAO:Insert " + e.getMessage());
        }
        finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * method to update an already existing object of type T in the database
     * @param t object of type T which is searched and updated in the corresponding table
     */
    public void update(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(t);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "DAO:Update " + e.getMessage());
        }
        finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * method to delete an object of type T from the database
     * @param t object of type T which is searched and deleted from the corresponding table
     */
    public void delete(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        Field idField = type.getDeclaredFields()[0];
        idField.setAccessible(true);
        String query = createDeleteQuery(t);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }
        finally{
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    /**
     * method which transforms a given ResultSet into the corresponding list of objects
     * @param resultSet ResultSet type variable representing the result obtained from executing a SELECT query
     * @return a list of objets T corresponding to the given ResultSet and to the table which was accessed
     */
    protected List<T> createObjects(ResultSet resultSet){
        List<T> list = new ArrayList<T>();
        try{
            while(resultSet.next()){
                T instance = type.getDeclaredConstructor().newInstance();
                for(Field field : type.getDeclaredFields()){
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }catch(Exception e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
