package model;

/**
 * Model class for the Product table in the database, used to store information about a product
 */

public class Product {

    /**
     * ID of the product; acts as primary key
     */
    private int idProduct;
    /**
     * name of the product; also unique
     */
    private String name;
    /**
     * price of the product
     */
    private float price;
    /**
     * quantity available in stock
     */
    private int quantity;
    /**
     * deleted flag, used to check if the client is considered to be deleted in the database
     * 0 if false, 1 if true
     */
    private int deleted;

    /**
     * basic constructor
     */
    public Product() {
    }

    /**
     * constructor containing all the fields
     * @param idProduct id or the product
     * @param name name of the product
     * @param price price of the product
     * @param quantity quantity available in stock
     * @param deleted deleted flag
     */
    public Product(int idProduct, String name, float price, int quantity, int deleted){
        super();
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    /**
     * constructor containing all the fields, except for the id, in case the id field is set on auto-increment
     * @param name name of the product
     * @param price price of the product
     * @param quantity quantity available in stock
     * @param deleted deleted flag
     */
    public Product(String name, float price, int quantity, int deleted){
        super();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    /**
     * method to set / change the id of the product
     * @param idProduct int representing the id of the product
     */
    public void setIdProduct(int idProduct){
        this.idProduct = idProduct;
    }

    /**
     * method to set / change the name of the product
     * @param name String representing the name of the product
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * method to set / change the price of the product
     * @param price float representing the price of the product
     */
    public void setPrice(float price){
        this.price = price;
    }

    /**
     * method to set / change the quantity available in stock
     * @param quantity int representing the quantity
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
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
            System.out.println("Invalid flag value in Product class: " + deleted);
        }
    }

    /**
     * method to retrieve the id of the product from the object
     * @return an int representing the id of the product
     */
    public int getIdProduct(){
        return idProduct;
    }

    /**
     * method to retrieve the name of the product from the object
     * @return a String representing the id of the product
     */
    public String getName(){
        return name;
    }

    /**
     * method to retrieve the price of the product from the object
     * @return a float representing the price of the product
     */
    public float getPrice(){
        return price;
    }

    /**
     * method to retrieve the quantity available in stock from the object
     * @return an int representing the quantity
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
