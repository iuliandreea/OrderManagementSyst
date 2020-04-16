package dao;

import model.Product;

import java.util.List;

/**
 * Data Access class for the Product table, implementing the operations available on the database: SELECT, INSERT, UPDATE, DELETE
 */

public class ProductDAO extends AbstractDAO<Product> {

    /**
     * method to retrieve all the products available in the database
     * @return a list of Product objects
     */
    public List<Product> findAll(){
        return super.findAll();
    }

    /**
     * method to retrieve a product with a specified id
     * @param id int representing the id of the product to be found
     * @return an Order object corresponding to the given id or null, in case it wasn't found
     */
    public Product findById(int id){
        return super.findById("idproduct", id);
    }

    /**
     * method to insert a new product in the database
     * @param product Product object which is to be inserted in the Product table
     */
    public void insert(Product product){
        super.insert(product);
    }

    /**
     * method to update an already existing product in the database
     * @param product Product object which is searched and updated in the Product table
     */
    public void update(Product product){
        super.update(product);
    }

    /**
     * method to delete a product from the database
     * @param product Product object which is searched and deleted from the Product table
     */
    public void delete(Product product){
        super.delete(product);
    }

}
