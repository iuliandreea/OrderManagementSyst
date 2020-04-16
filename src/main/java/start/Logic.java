package start;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.OrderItemBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.OrderItem;
import model.Product;
import presentation.Controller;
import utils.CommandText;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to process the instructions read from the input file by calling the appropriate method from the BLL class and the respective query
 */

public class Logic {

    /**
     * instance of the ClientBLL class
     */
    private ClientBLL cBLL = new ClientBLL();
    /**
     * instance of the OrderBLL class
     */
    private OrderBLL oBLL = new OrderBLL();
    /**
     * instance of the OrderItemBLL class
     */
    private OrderItemBLL oiBLL = new OrderItemBLL();
    /**
     * instance of the ProductBLL class
     */
    private ProductBLL pBLL = new ProductBLL();

    /**
     * time stamp used to name the generated PDFs, to avoid overwriting
     */
    private int currentTime = 0;

    /**
     * list of orders which could not be processed
     */
    private List<String[]> notifs = new ArrayList<>();

    /**
     * method to reset the database, by emptying all its tables
     */
    public void resetDB(){
        if(oBLL.findAllOrders() != null){
            for(Order o : oBLL.findAllOrders()){
                oBLL.deleteOrder(o);
            }
        }
        if(cBLL.findAllClients() != null){
            for(Client c : cBLL.findAllClients()){
                cBLL.deleteClient(c);
            }
        }
        if(pBLL.findAllProducts() != null){
            for(Product p : pBLL.findAllProducts()){
                pBLL.deleteProduct(p);
            }
        }
    }

    /**
     * method to choose the corresponding command based on the Command field in CommandText
     * @param commands a list of CommandText objects, representing the commands to be executed
     */
    public void executeCommand(List<CommandText> commands){
        for(CommandText c : commands){
            switch(c.getCommand()){
                case INSERT:
                    prepareInsert(c.getTable(), c.getText());
                    break;
                case ORDER:
                    prepareInsert("order", c.getText());
                    break;
                case DELETE:
                    prepareDelete(c.getTable(), c.getText());
                    break;
                case REPORT:
                    prepareReport(c.getTable());
                    break;
            }
        }

    }

    /**
     * method to choose the corresponding table on which the insert command will pe executed and to pass the data to be manipulated
     * if an order cannot be processed, the notification is added to the notification list
     * @param table String representing the table on which the operation will be performed
     * @param obj String representing the data which will be manipulated
     */
    private void prepareInsert(String table, String obj){
        String[] aux = obj.split(", ");
        switch(table){
            case "client":
                cBLL.insertClient(aux[0], aux[1]);
                break;
            case "product":
                pBLL.insertProduct(aux[0], Integer.parseInt(aux[1]), Float.parseFloat(aux[2]));
                break;
            case "order":
                if(oBLL.insertOrder(aux[0], aux[1], Integer.parseInt(aux[2])) == -1) {
                    notifs.add(aux);
                }
                break;
        }
    }

    /**
     * method to choose the corresponding table on which the delete command will pe executed and to pass the data to be manipulated
     * @param table String representing the table on which the operation will be performed
     * @param obj String representing the data which will be manipulated
     */
    private void prepareDelete(String table, String obj){
        String[] aux = obj.split(", ");
        switch(table){
            case "client":
                cBLL.deleteClientByFlag(aux[0]);
                break;
            case "product":
                pBLL.deleteProductByFlag(aux[0]);
                break;
        }
    }

    /**
     * method to choose the corresponding table on which the report command will pe executed and to pass the data to be manipulated
     * @param table String representing the table on which the operation will be performed
     */
    private void prepareReport(String table){
        Controller controller = new Controller();
        switch (table) {
            case "client":
                controller.generateClientReport(currentTime);
                currentTime++;
                break;
            case "order":
                controller.generateOrderReport(currentTime);
                currentTime++;
                break;
            case "product":
                controller.generateProductReport(currentTime);
                currentTime++;
                break;
        }
    }

    /**
     * method to generate the bills for all the orders at the end of the execution
     */
    public void generateBill(){
        Controller controller = new Controller();
        controller.generateBill(notifs, currentTime);
        currentTime++;
    }
}
