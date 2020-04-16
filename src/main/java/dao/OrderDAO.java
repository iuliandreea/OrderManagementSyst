package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Data access class for the Order table, implementing the operations available on the database: SELECT, INSERT, UPDATE, DELETE
 */

public class OrderDAO extends AbstractDAO<Order> {

    /**
     * method to retrieve all the orders available in the database
     * @return a list of Order objects
     */
    public List<Order> findAll(){
        return super.findAll();
    }

    /**
     * method to retrieve an order with a specified id
     * @param id int representing the id of the order to be found
     * @return an Order object corresponding to the given id or null, in case it wasn't found
     */
    public Order findById(int id){
        return super.findById("idorder", id);
    }

    /**
     * method to insert a new order in the database
     * @param order Order object which is to be inserted in the Order table
     */
    public void insert(Order order){
        super.insert(order);
    }

    /**
     * method to update an already existing order in the database
     * @param order Order object which is searched and updated in the Order table
     */
    public void update(Order order){
        super.update(order);
    }

    /**
     * method to delete an order from the database
     * @param order Order object which is searched and deleted from the Order database
     */
    public void delete(Order order){
        super.delete(order);
    }

    /**
     * method to retrieve an order with a specified id of the client who placed the order
     * @param id int representing the id of the client who placed the order to be found
     * @return an Order object corresponding to the given id or null, in case it wasn't found
     */
    public Order findByIdClient(int id){
        return super.findById("idclient", id);
    }

}
