package dao;

import connection.ConnectionFactory;
import model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access class for the OrderItem table, implementing the operations available on the database: SELECT, INSERT, UPDATE, DELETE
 */

public class OrderItemDAO extends AbstractDAO<OrderItem> {

    /**
     * method to retrieve all the orderItems available in the database
     * @return a list of OrderItem objects
     */
    public List<OrderItem> findAll(){
        return super.findAll();
    }

    /**
     * method to insert a new orderItem in the database
     * @param orderItem OrderItem object which is to be inserted in the OrderItem table
     */
    public void insert(OrderItem orderItem){
        super.insert(orderItem);
    }

    /**
     * method to update an already existing orderItem in the database
     * @param orderItem OrderItem object which is searched and updated in the OrderItem table
     */
    public void update(OrderItem orderItem){
        super.update(orderItem);
    }

    /**
     * method to delete an orderItem from the database
     * @param orderItem OrderItem object which is searched and updated in the OrderItem table
     */
    public void delete(OrderItem orderItem){
        super.delete(orderItem);
    }

    /**
     * method to retrieve all the orderItems available in the database with a specified order id
     * @param id int representing the id of the order of the orderItem
     * @return a list of OrderItem objects, corresponding to the given order id or null, in case it wasn't found
     */
    public List<OrderItem> findAllByIdOrder(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = super.createSelectQuery("idOrder");
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return super.createObjects(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "OrderItemDAO:findAllByOrder " + e.getMessage());
        }finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * method to retrieve all the orderItems available in the database with a specified product id
     * @param id int representing the id of the product of the orderItem
     * @return a list of OrderItem objects, corresponding to the given order or null, in case it wasn't found
     */
    public List<OrderItem> findAllByIdProduct(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = super.createSelectQuery("idProduct");
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return super.createObjects(resultSet);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "OrderItemDAO:findAllByProduct " + e.getMessage());
        }finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
