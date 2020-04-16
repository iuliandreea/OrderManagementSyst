package utils;

/**
 * Utility class used to store all the necessary information about a command received as input from the input file
 */

public class CommandText {

    /**
     * the command to be executed; one of the options from the Command enumeration
     */
    private Command command;
    /**
     * the table on which the operation will be performed
     */
    private String table;
    /**
     * the data which will be manipulated
     */
    private String text;

    /**
     * basic constructor
     */
    public CommandText(){
    }

    /**
     * constructor containing only the command and the table, in case the command doesn't require a text field
     * @param command command to be executed
     * @param table table on which the operation will be performed
     */
    public CommandText(Command command, String table){
        this.command = command;
        this.table = table;
    }

    /**
     * constructor containing all the fields
     * @param command command to be executed
     * @param table table on which the operation will be performed
     * @param text the data which will be manipulated
     */
    public CommandText(Command command, String table, String text){
        this.command = command;
        this.table = table;
        this.text = text;
    }

    /**
     * method to set / change the command
     * @param command Command object representing the command to be executed
     */
    public void setCommand(Command command){
        this.command = command;
    }

    /**
     * method to set / change the table
     * @param table String representing the desired table
     */
    public void setTable(String table){
        this.table = table;
    }

    /**
     * method to set / change the data to be manipulated
     * @param text String representing the desired data
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * method to retrieve the command from the object
     * @return a Command object
     */
    public Command getCommand(){
        return command;
    }

    /**
     * method to retrieve the name of the table from the object
     * @return a String representing the name of the table
     */
    public String getTable(){
        return table;
    }

    /**
     * method to retrieve the data from the object
     * @return a String representing the data which is to be manipulated
     */
    public String getText(){
        return text;
    }
}
