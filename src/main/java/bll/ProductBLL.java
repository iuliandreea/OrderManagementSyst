package bll;

import dao.ProductDAO;
import model.OrderItem;
import model.Product;

import java.util.List;

/**
 * Business Logic class for the Product table, implementing methods used to further enhance the usage of the SQL queries
 */

public class ProductBLL {

    /**
     * instance of the ProductDAO class
     */
    private ProductDAO pDAO = new ProductDAO();

    /**
     * method to retrieve the largest id present in the Product table, to avoid overwriting data
     * @return the largest id in the Product table
     */
    private int getCurrentId(){
        if(pDAO.findAll() == null){
            return 1;
        }
        return pDAO.findAll().size()+1;
    }

    /**
     * method used to find a certain product with a specified name
     * @param name String representing teh name of the product to be searched
     * @return an int representing the id of the product with the given name or -1 in case the product is not found
     */
    public int findProductByName(String name){
        if(pDAO.findAll() != null)
        {
            for(Product p : pDAO.findAll()){
                if(name.equalsIgnoreCase(p.getName())){
                    return p.getIdProduct();
                }
            }
        }
        return -1;
    }

    /**
     * method used to find a certain product with a specified id
     * @param id int representing the id of the product to be searched
     * @return a Product object representing the corresponding product or null, if the product was not found
     */
    public Product findProductById(int id){
        Product product = pDAO.findById(id);
        if(product == null){
            return null;
        }
        return product;
    }

    /**
     * method used to retrieve all the products from the table
     * @return a list of Product objects or null, in case the table is empty
     */
    public List<Product> findAllProducts(){
        List<Product> products = pDAO.findAll();
        if(products == null){
            return null;
        }
        return products;
    }

    /**
     * method used to insert a product in the database
     * if the product exists, it will update the existing product
     * @param name String representing the name of the product to be inserted
     * @param quantity int representing the quantity of the product
     * @param price float representing the price of the product
     * @return -1 if the client already exists, 1 if it does not
     */
    public int insertProduct(String name, int quantity, float price){
        boolean exists = false;
        if(pDAO.findAll() != null){
            for(Product p : pDAO.findAll()){
                if(name.equalsIgnoreCase(p.getName())){
                    exists = true;
                    p.setPrice(price);
                    p.setQuantity(p.getQuantity() + quantity);
                    p.setDeleted(0);
                    pDAO.update(p);
                    return -1;
                }
            }
        }
        if(exists == false){
            int id = getCurrentId();
            pDAO.insert(new Product(id, name, price, quantity, 0));
        }
        return 0;
    }

    /**
     * method to update an existing product from the database
     * @param product Product to be updated
     */
    public void updateProduct(Product product){
        if(pDAO.findById(product.getIdProduct()) == null){
            return;
        }
        pDAO.update(product);
    }

    /**
     * method used to delete a product from the database
     * @param product Product to be selected from the database
     */
    public void deleteProduct(Product product){
        if(pDAO.findById(product.getIdProduct()) == null){
            return;
        }
        pDAO.delete(product);
        OrderBLL oBLL = new OrderBLL();
        OrderItemBLL oiBLL = new OrderItemBLL();
        if(oiBLL.findAllOrdersByProductId(product.getIdProduct()) != null){
            for(OrderItem oi : oiBLL.findAllOrdersByProductId(product.getIdProduct())){
                oBLL.deleteOrder(oBLL.findOrderById(oi.getIdOrder()));
            }
        }
    }

    /**
     * method to apparently delete a product from the database
     * does not truly delete the product, but it changes the "deleted" flag from 0 to 1
     * @param name the name of the product to be deleted from the database
     */
    public void deleteProductByFlag(String name){
        int id = findProductByName(name);
        if(id == -1){
            return;
        }
        Product product = pDAO.findById(id);
        product.setDeleted(1);
        pDAO.update(product);
        OrderItemBLL oiBLL = new OrderItemBLL();
        if(oiBLL.findAllOrdersByProductId(product.getIdProduct()) != null){
            for(OrderItem oi : oiBLL.findAllOrdersByProductId(product.getIdProduct())){
                oi.setDeleted(1);
                oiBLL.updateOrderItem(oi);
            }
        }
    }
}
