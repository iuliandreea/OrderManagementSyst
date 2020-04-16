package bll;

import dao.OrderItemDAO;
import model.OrderItem;

import java.util.List;

/**
 * Business Logic class for the OrderItem table, implementing methods used to further enhance the usage of the SQL queries
 */

public class OrderItemBLL {

    /**
     * instance of the OrderItemDAO class
     */
    private OrderItemDAO oiDAO = new OrderItemDAO();

    /**
     * method used to retrieve all the orderIteams from the table
     * @return a list of OrdetItem objects or null, in case the table is empty
     */
    public List<OrderItem> findAllOrderItems(){
        List<OrderItem> orders = oiDAO.findAll();
        if(orders == null){
            return null;
        }
        return orders;
    }

    /**
     * method to retrieve all the orderItems from the table based on a specified order id
     * @param id int representing the id of the order containing the orderItem
     * @return a list of OrderItems with the specified order id
     */
    public List<OrderItem> findAllOrdersByOrderId(int id){
        List<OrderItem> orderItems = oiDAO.findAllByIdOrder(id);
        if(orderItems == null){
            return null;
        }
        return orderItems;
    }

    /**
     * method to retrieve all the orderItems from the table based on a specified product id
     * @param id int representing the id of the product containing the orderItem
     * @return a list of OrderItems with the specified order id
     */
    public List<OrderItem> findAllOrdersByProductId(int id){
        List<OrderItem> orderItems = oiDAO.findAllByIdProduct(id);
        if(orderItems == null){
            return null;
        }
        return orderItems;
    }

    /**
     * method used to insert an orderItem in tha database
     * @param orderItem OrderItem object to be inserted
     */
    public void insertOrderItem(OrderItem orderItem){
            oiDAO.insert(orderItem);
    }

    /**
     * method to update an existing OrderItem from the database
     * @param orderItem OrderItem to be updated
     */
    public void updateOrderItem(OrderItem orderItem){
        if(oiDAO.findAllByIdOrder(orderItem.getIdOrder()) == null || oiDAO.findAllByIdProduct(orderItem.getIdProduct()) == null){
            return;
        }
        oiDAO.update(orderItem);
    }

    /**
     * method to delete all the OrderItems based on the id of the order
     * @param orderItem the OrderItems to be deleted
     */
    public void deleteOrderItemByOrder(OrderItem orderItem){
        if(oiDAO.findAllByIdOrder(orderItem.getIdOrder()) == null){
            return;
        }
        if(oiDAO.findAllByIdOrder(orderItem.getIdOrder()) != null){
            for(OrderItem oi : oiDAO.findAllByIdOrder(orderItem.getIdOrder())){
                oiDAO.delete(oi);
            }
        }
    }
}
