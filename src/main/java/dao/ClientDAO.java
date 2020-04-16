package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access class for the Client table, implementing the operations available on the database: SELECT, INSERT, UPDATE, DELETE
 */

public class ClientDAO extends AbstractDAO<Client> {

    /**
     * method to retrieve all the clients available in the database
     * @return a list of Client objects
     */
    public List<Client> findAll(){
        return super.findAll();
    }

    /**
     * method to retrieve a client with a specified id
     * @param id int representing the id of the client to be found
     * @return a Client object corresponding to the given id or null, in case it wasn't found
     */
    public Client findById(int id){
        return super.findById("idclient", id);
    }

    /**
     * method to insert a new client in the database
     * @param client Client object which is to be inserted in the Client table
     */
    public void insert(Client client){
        super.insert(client);
    }

    /**
     * method to update an already existing client in the database
     * @param client Client object which is searched and updated in the Client table
     */
    public void update(Client client){
        super.update(client);
    }

    /**
     * method to delete a client from the database
     * @param client Client object which is searched and deleted from the Client table
     */
    public void delete(Client client){
        super.delete(client);
    }

}
