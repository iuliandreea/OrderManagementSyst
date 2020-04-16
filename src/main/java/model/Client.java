package model;

/**
 * Model class for the Client table in the database, used to store information about a client
 */

public class Client {

    /**
     * ID of the client; acts as primary key
     */
    private int idClient;
    /**
     * name of the client; also unique
     */
    private String name;
    /**
     * address of the client
     */
    private String address;
    /**
     * deleted flag, used to check if the client is considered to be deleted in the database
     * 0 if false, 1 if true
     */
    private int deleted;

    /**
     * basic constructor
     */
    public Client(){
    }

    /**
     * constructor containing all the necessary fields
     * @param idClient id of the client
     * @param name name of the client
     * @param address address of the client
     * @param deleted deleted flag
     */
    public Client(int idClient, String name, String address, int deleted){
        super();
        this.idClient = idClient;
        this.name = name;
        this.address = address;
        this.deleted = deleted;
    }

    /**
     * constructor containing the all the fields, except for the id, in case the id field is set on auto-increment
     * @param name name of the client
     * @param address address of the client
     * @param deleted deleted flag
     */
    public Client(String name, String address, int deleted){
        super();
        this.name = name;
        this.address = address;
        this.deleted = deleted;
    }

    /**
     * method to set / change the id of the client
     * @param idClient int representing the id of the client
     */
    public void setIdClient(int idClient){
        this.idClient = idClient;
    }

    /**
     * method to set / change the name of the client
     * @param name String representing the name of the client
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * method to set / change the address of the client
     * @param address String representing the address of the client
     */
    public void setAddress(String address){
        this.address = address;
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
            System.out.println("Invalid flag value in Client class: " + deleted);
        }
    }

    /**
     * method to retrieve the id of the client from the object
     * @return an int representing the id of the client
     */
    public int getIdClient(){
        return idClient;
    }

    /**
     * method to retrieve the name of the client from the object
     * @return a String representing the name of the client
     */
    public String getName(){
        return name;
    }

    /**
     * method to retrieve the address of the client from the object
     * @return a String representing the address of the client
     */
    public String getAddress(){
        return address;
    }

    /**
     * method to retrieve the value of the deleted flag from the object
     * @return an int representing the value of the deleted flag, either 0 or 1
     */
    public int getDeleted(){
        return deleted;
    }

}
