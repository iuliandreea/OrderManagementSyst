package bll;

import dao.OrderDAO;
import model.Order;
import model.OrderItem;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic class for the Order table, implementing methods used to further enhance the usage of the SQL queries
 */

public class OrderBLL {

    /**
     * instance of the OrderDAO class
     */
    private OrderDAO oDAO = new OrderDAO();

    /**
     * method to retrieve the largest id present in the Order table, to avoid overwriting data
     * @return the largest id in the Order table
     */
    private int getCurrentId(){
        if(oDAO.findAll() == null){
            return 1;
        }
        return oDAO.findAll().size()+1;
    }

    /**
     * method used to find a certain order with a specified id
     * @param id int representing the id of the order to be searched
     * @return an Order object representing the corresponding order or null, if the order was not found
     */
    public Order findOrderById(int id){
        Order order = oDAO.findById(id);
        if(order == null){
        }
        return order;
    }

    /**
     * method used to retrieve all the orders from the table
     * @return a list of Order objects or null, in case the table is empty
     */
    public List<Order> findAllOrders(){
        List<Order> orders = oDAO.findAll();
        if(orders == null){
            return null;
        }
        return orders;
    }

    /**
     * method used to find a certain order with a specified client id
     * @param id int representing the id of the client whose order is searched
     * @return an Order object representing the corresponding order or null, if the order was not found
     */
    public Order findOrderByIdClient(int id){
        Order order = oDAO.findByIdClient(id);
        if(order == null){
            return null;
        }
        return order;
    }

    /**
     * method used to insert an order in the database
     * if the order exists, it will update the existing order
     * @param name String representing the name of the order to be inserted
     * @param product String representing the address of the order to be inserted
     * @param quantity int representing the quantity of the product in the order
     * @return -1 if the client already exists, 1 if it does not
     */
    public int insertOrder(String name, String product, int quantity){
        ProductBLL pBLL = new ProductBLL();
        ClientBLL cBLL = new ClientBLL();
        int pId = pBLL.findProductByName(product);
        int cId = cBLL.findClientByName(name);
        if(pId == -1 || cId == -1){
            throw new NoSuchElementException("Client / Product with name " + name + " / " + product + " not found");
        }
        if(pBLL.findProductById(pId).getQuantity() < quantity){
            return -1;
        }
        Product p = pBLL.findProductById(pId);
        Order o = oDAO.findByIdClient(cId);
        OrderItemBLL oiBLL = new OrderItemBLL();
        if(o != null){
            o.setTotal(o.getTotal() + (p.getPrice() * quantity));
            oDAO.update(o);
            oiBLL.insertOrderItem(new OrderItem(o.getIdOrder(), pId, quantity, 0));
        }
        else{
            int id = getCurrentId();
            oDAO.insert(new Order(id, cId, p.getPrice() * quantity, 0));
            oiBLL.insertOrderItem(new OrderItem(id, pId, quantity, 0));
        }
        p.setQuantity(p.getQuantity() - quantity);
        pBLL.updateProduct(p);
        return 0;
    }

    /**
     * method to update an existing order from the database
     * @param order Order to be updated
     */
    public void updateOrder(Order order){
        if(oDAO.findById(order.getIdOrder()) == null){
            return;
        }
        oDAO.update(order);
    }

    /**
     * method used to delete an order from the database
     * @param order Order to be deleted from the database
     */
    public void deleteOrder(Order order){
        if(oDAO.findById(order.getIdOrder()) == null){
            return;
        }
        oDAO.delete(order);
        OrderItemBLL oiBLL = new OrderItemBLL();
        if(oiBLL.findAllOrdersByOrderId(order.getIdOrder()) != null){
            for(OrderItem oi : oiBLL.findAllOrdersByOrderId(order.getIdOrder())){
                oiBLL.deleteOrderItemByOrder(oi);
            }
        }

    }

    /**
     * method to apparently delete an order from the database
     * does not truly delete the order, but it changes the "deleted" flag from 0 to 1
     * @param order the Order to be deleted from the database
     */
    public void deleteOrderByFlag(Order order){
        if(oDAO.findById(order.getIdOrder()) == null){
            return;
        }
        order.setDeleted(1);
        oDAO.update(order);
        OrderItemBLL oiBLL = new OrderItemBLL();
        if(oiBLL.findAllOrdersByOrderId(order.getIdOrder()) != null){
            for(OrderItem oi : oiBLL.findAllOrdersByOrderId(order.getIdOrder())){
                oi.setDeleted(1);
                oiBLL.updateOrderItem(oi);
            }
        }
    }
}
