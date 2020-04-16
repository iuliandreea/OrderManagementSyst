package bll;

import model.Client;
import dao.ClientDAO;
import model.Order;

import java.util.List;

/**
 * Business Logic class for the Client table, implementing methods used to further enhance the usage of the SQL queries
 */

public class ClientBLL {

    /**
     * instance of the ClientDAO class
     */
    private ClientDAO cDAO = new ClientDAO();

    /**
     * method to retrieve the largest id present in the Client table, to avoid overwriting data
     * @return the largest id in the Client table
     */
    private int getCurrentId(){
        if(cDAO.findAll() == null){
            return 1;
        }
        return cDAO.findAll().size()+1;
    }

    /**
     * method used to find a certain client with a specified name
     * @param name String representing the name of the client to be searched
     * @return an int representing the id of the client with the given name or -1 in case the client is not found
     */
    public int findClientByName(String name){
        if(cDAO.findAll() != null){
            for(Client c : cDAO.findAll()){
                if(name.equalsIgnoreCase(c.getName())){
                    return c.getIdClient();
                }
            }
        }
        return -1;
    }

    /**
     * method used to find a certain client with a specified id
     * @param id int representing the id of the client to be searched
     * @return a Client object representing the corresponding client or null, if the client was not found
     */
    public Client findClientById(int id){
        Client client = cDAO.findById(id);
        if(client == null){
            return null;
        }
        return client;
    }

    /**
     * method used to retrieve all the clients from the table
     * @return a list of Client objects or null, in case the table is empty
     */
    public List<Client> findAllClients(){
        List<Client> clients = cDAO.findAll();
        if(clients == null){
            return null;
        }
        return clients;
    }

    /**
     * method used to insert a client in the database
     * if the client exists, it will update the existing client
     * @param name String representing the name of the client to be inserted
     * @param address String representing the address of the client to be inserted
     * @return -1 if the client already exists, 1 if it does not
     */
    public int insertClient(String name, String address){
        boolean exists = false;
        if(cDAO.findAll() != null){
            for(Client c : cDAO.findAll()){
                if(name.equalsIgnoreCase(c.getName())){
                    exists = true;
                    c.setAddress(address);
                    c.setDeleted(0);
                    cDAO.update(c);
                    return -1;
                }
            }
        }
        if(exists == false){
            int id = getCurrentId();
            cDAO.insert(new Client(id, name, address, 0));
        }
        return 0;
    }

    /**
     * method to update an existing client from the database
     * @param client Client to be updated
     */
    public void updateClient(Client client){
        if(cDAO.findById(client.getIdClient()) == null){
            return;
        }
        cDAO.update(client);
    }

    /**
     * method used to delete a client from the database
     * @param client Client to be deleted from the database
     */
    public void deleteClient(Client client){
        if(cDAO.findById(client.getIdClient()) == null){
            return;
        }
        cDAO.delete(client);
        OrderBLL oBLL = new OrderBLL();
        if(oBLL.findOrderByIdClient(client.getIdClient()) != null){
            oBLL.deleteOrder(oBLL.findOrderByIdClient(client.getIdClient()));
        }
    }

    /**
     * method to apparently delete a client from the database
     * does not truly delete the client, but it changes the "deleted" flag from 0 to 1
     * @param name the name of the client to be deleted from the database
     */
    public void deleteClientByFlag(String name){
        int id = findClientByName(name);
        if(id == -1){
            return;
        }
        Client client = cDAO.findById(id);
        client.setDeleted(1);
        cDAO.update(client);
        OrderBLL oBLL = new OrderBLL();
        Order o = oBLL.findOrderByIdClient(client.getIdClient());
        if(o != null){
            o.setDeleted(1);
            oBLL.updateOrder(o);
        }
    }
}
