package model;

/**
 * Model class for the Order table in the database, used to store information about an order
 */

public class Order {

    /**
     * ID of the order; acts as primary key
     */
    private int idOrder;
    /**
     * ID of the client; also unique in this case; acts as foreign key
     */
    private int idClient;
    /**
     * total price from all the orders placed by the client idClient
     */
    private float total;
    /**
     * deleted flag, used to check if the client is considered to be deleted in the database
     * 0 if false, 1 if true
     */
    private int deleted;

    /**
     * basic constructor
     */
    public Order(){
    }

    /**
     * constructor containing all the necessary fields
     * @param idOrder id of the order
     * @param idClient id of the client
     * @param total total price for all the orders of idClient
     * @param deleted deleted flag
     */
    public Order(int idOrder, int idClient, float total, int deleted){
        super();
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.total = total;
        this.deleted = deleted;
    }

    /**
     * constructor containing all the fields, except for the id, in case the id field is set on auto-increment
     * @param clientId id of the client
     * @param total total price for all he orders of idClient
     * @param deleted deleted flag
     */
    public Order(int clientId, float total, int deleted){
        super();
        this.idClient = idClient;
        this.total = total;
        this.deleted = deleted;
    }

    /**
     * method to set / change the id of the order
     * @param idOrder int representing the id of the order
     */
    public void setIdOrder(int idOrder){
        this.idOrder = idOrder;
    }

    /**
     * method to set / change the id of the client
     * @param idClient int representing the id of the client
     */
    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    /**
     * method to set / change the total price of an order
     * @param total float representing the total price of the order
     */
    public void setTotal(float total){
        this.total = total;
    }

    /**
     * method to set / change the value of the deleted flag to 0 or 1
     * will print out an error message in case the flag is invalid
     * @param deleted int representing the deleted flag
     */
    public void setDeleted(int deleted){
        if(deleted == 0 || deleted == 1){
            this.deleted = deleted;
        }
        else{
            System.out.println("Invalid flag value in Order class: " + deleted);
        }
    }

    /**
     * method to retrieve the id of the order from the object
     * @return an int representing the id of the order
     */
    public int getIdOrder(){
        return idOrder;
    }

    /**
     * method to retrieve the id of the client from the object
     * @return an int representing the id of the client
     */
    public int getIdClient(){
        return idClient;
    }

    /**
     * method to retrieve the total price from all the orders of idClient from the object
     * @return an int representing the total price
     */
    public float getTotal(){
        return total;
    }

    /**
     * method to retrieve the value of the deleted flag from the object
     * @return an int representing the value of the deleted flag, either 0 or 1
     */
    public int getDeleted(){
        return deleted;
    }
}
