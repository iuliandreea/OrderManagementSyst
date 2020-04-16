package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.OrderItemBLL;
import bll.ProductBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Order;
import model.OrderItem;
import model.Product;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Presentation class used to generate the requested PDFs
 */

public class Controller {

    /**
     * instance of the ClientBLL class
     */
    private ClientBLL cBLL = new ClientBLL();
    /**
     * instance of the OrderBLL class
     */
    private OrderBLL oBLL = new OrderBLL();
    /**
     * inatance of the OrderItemBLL class
     */
    private OrderItemBLL oiBLL = new OrderItemBLL();
    /**
     * instance of the ProductBLL class
     */
    private ProductBLL pBLL = new ProductBLL();

    /**
     * method to retrieve the clients from the Client table and write them in the generated table from the PDF
     * @param table the table from the PDF file in which the data from the database will be written
     */
    private void writeClients(PdfPTable table){
        List<Client> clients = cBLL.findAllClients();
        if(clients != null){
            for(Client c : clients){
                if(c.getDeleted() == 0){
                    table.addCell(Integer.toString(c.getIdClient()));
                    table.addCell(c.getName());
                    table.addCell(c.getAddress());
                }
            }
        }
    }

    /**
     * method to generate the PDF containing the Client table from the database
     * @param id int representing the current time stamp
     */
    public void generateClientReport(int id){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Client Report Time" + id + ".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.addCell("ID");
            table.addCell("NAME");
            table.addCell("ADDRESS");
            writeClients(table);
            document.add(table);
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to retrieve the orders from the Order table and write them in the generated table from the PDF
     * @param table the table from the PDF file in which the data from the database will be written
     */
    private void writeOrders(PdfPTable table){
        List<OrderItem> orders = oiBLL.findAllOrderItems();
        if(orders != null){
            for(OrderItem oi : orders){
                Order o = oBLL.findOrderById(oi.getIdOrder());
                Client c = cBLL.findClientById(o.getIdClient());
                Product p = pBLL.findProductById(oi.getIdProduct());
                if(c.getDeleted() == 0 && p.getDeleted() == 0){
                    table.addCell(Integer.toString(oi.getIdOrder()));
                    table.addCell(c.getName());
                    table.addCell(p.getName());
                    table.addCell(Integer.toString(oi.getQuantity()));
                }
            }
        }
    }

    /**
     * method to generate the PDF containing the Order table from the database
     * @param id int representing the current timestamp
     */
    public void generateOrderReport(int id){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Order Report Time" + id + ".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(4);
            table.addCell("ID");
            table.addCell("CLIENT");
            table.addCell("PRODUCT");
            table.addCell("QUANTITY");
            writeOrders(table);
            document.add(table);
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to retrieve the products from the Product table and write them in the generated table from the PDF
     * @param table the table from the PDF file in which the data from the database will be written
     */
    private void writeProducts(PdfPTable table){
        List<Product> products = pBLL.findAllProducts();
        if(products != null){
            for(Product p : products){
                if(p.getDeleted() == 0){
                    table.addCell(Integer.toString(p.getIdProduct()));
                    table.addCell(p.getName());
                    table.addCell(Float.toString(p.getPrice()));
                    table.addCell(Integer.toString(p.getQuantity()));
                }
            }
        }
    }

    /**
     * method to generate the PDF containing the Product table from the database
     * @param id int representing the current time stamp
     */
    public void generateProductReport(int id){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Product Report Time" + id + ".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(4);
            table.addCell("ID");
            table.addCell("NAME");
            table.addCell("PRICE");
            table.addCell("QUANTITY");
            writeProducts(table);
            document.add(table);
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to write the notifications concerning all the failed orders
     * @param doc Document in which the notifications will be written
     * @param data String[] containing information about the failed order
     */
    private void writeFailed(Document doc, String[] data){
        try{
            Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
            Paragraph p = new Paragraph("Notifications", font);
            doc.add(p);
            font.setSize(11);
            p.setFont(font);Paragraph np = new Paragraph("Could not process order for " + data[2] + " " + data[1] + "(s). Not enough products in stock.", font);
            doc.add(np);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to write any type of notification about an order, i.e. whether it was successful or there were some errors
     * @param doc Document in which the notification will be written
     * @param notifs list of String[] representing all the failed orders
     * @param name String representing the name of the client whose bill is generated
     */
    private void writeNotifs(Document doc, List<String[]> notifs, String name){
        try{
            List<Integer> indexes = new ArrayList<>();
            boolean exists = false;
            for(String[] n : notifs){
                if(n[0].compareToIgnoreCase(name) == 0){
                    writeFailed(doc, n);
                    indexes.add(notifs.indexOf(n));
                    exists = true;
                }
            }
            for(Integer i : indexes){
                notifs.remove(i);
            }
            if(exists == false){
                Paragraph np = new Paragraph("All orders have been processed successfully.");
                doc.add(np);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to write the bills for all the orders which were processed successfully
     * @param doc Document in which the bill will be printed
     * @param o an Order object representing the order whose information will be printed
     */
    private void writeBill(Document doc, Order o){
        try{
            Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
            Paragraph p = new Paragraph("Bill", font);
            doc.add(p);
            font.setSize(14);
            Paragraph cp = new Paragraph(cBLL.findClientById(o.getIdClient()).getName(), font);
            doc.add(cp);
            font.setSize(11);
            for(OrderItem oi : oiBLL.findAllOrdersByOrderId(o.getIdOrder())){
                Product product = pBLL.findProductById(oi.getIdProduct());
                Paragraph oip = new Paragraph(product.getName() + "         " + oi.getQuantity() + "        " + product.getPrice());
                doc.add(oip);
            }
            font.setSize(12);
            Paragraph tp = new Paragraph("Total: " + o.getTotal());
            doc.add(tp);
            Paragraph newLine = new Paragraph("");
            newLine.setSpacingAfter(30);
            doc.add(newLine);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * method to generate the bills for all the clients / orders
     * @param notifs list of String[] notifications containing information about the orders which could not be processed
     * @param id an int representing the current time stamp
     */
    public void generateBill(List<String[]> notifs, int id){
        try {
            if(oBLL.findAllOrders() != null){
                for(Order o : oBLL.findAllOrders()){
                    Document doc1 = new Document();
                    PdfWriter.getInstance(doc1, new FileOutputStream("Client " + cBLL.findClientById(o.getIdClient()).getName() + " Bill Time" + id + ".pdf"));
                    doc1.open();
                    writeBill(doc1, o);
                    writeNotifs(doc1, notifs, cBLL.findClientById(o.getIdClient()).getName());
                    doc1.close();
                }
            }
            if(!notifs.isEmpty()){
                for(String[] n : notifs){
                    Document doc2 = new Document();
                    PdfWriter.getInstance(doc2, new FileOutputStream("Client " + n[0] + " Bill Time" + id + ".pdf"));
                    doc2.open();
                    writeFailed(doc2, n);
                    doc2.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
