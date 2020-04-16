package model;

/**
 * Model class for the OrderItem table in the database, used to store information about the items contained in all the orders
 */

public class OrderItem {

    /**
     * ID of the order; acts as foreign key
     */
    private int idOrder;
    /**
     * ID of the product; acts as foreign key
     */
    private int idProduct;
    /**
     * ordered quantity of the product
     */
    private int quantity;
    /**
     * deleted flag, used to check if the orderItem is considered to be deleted in the database
     * 0 if false, 1 if true
     */
    private int deleted;

    /**
     * basic constructor
     */
    public OrderItem(){
    }

    /**
     * constructor containing all the necessary fields
     * @param idOrder id of the order
     * @param idProduct id of the product
     * @param quantity ordered quantity of the product
     * @param deleted deleted flag
     */
    public OrderItem(int idOrder, int idProduct, int quantity, int deleted){
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.quantity = quantity;
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
     * method to set / change the id of the product
     * @param idProduct int representing the id of the product
     */
    public void setIdProduct(int idProduct){
        this.idProduct = idProduct;
    }

    /**
     * method to set / change the quantity of the product within the order
     * @param quantity int representing the quantity of the product
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * method to set / change the value of the deleted flag to 0 or 1
     * will prind out an error message in case the flag is invalid
     * @param deleted int representing the deleted flag
     */
    public void setDeleted(int deleted){
        if(deleted == 0 || deleted == 1){
            this.deleted = deleted;
        }
        else{
            System.out.println("Invalid flag value in OrderItem class: " + deleted);
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
     * method to retrieve the id of the product from the object
     * @return an int representing the id of the product
     */
    public int getIdProduct(){
        return idProduct;
    }

    /**
     * method to retrieve the quantity of the product in the order from the object
     * @return an int representing the quantity of the product
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * method to retrieve the value of the deleted flag from the object
     * @return an int representing the value of the deleted flag, either 0 or 1
     */
    public int getDeleted(){
        return deleted;
    }
}
