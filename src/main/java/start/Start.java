package start;

import presentation.Controller;
import presentation.View;
import utils.CommandText;

import java.util.List;

/**
 * Main class, containing only the main() method
 */

public class Start {

    /**
     * an instance of the View class, for accessing the inputs
     */
    private static View view = new View();
    /**
     * an instance of the Logic class, for processing the inputs
     */
    private static Logic logic = new Logic();

    /**
     * main method, executed when running the program
     * resets the database by emptying all the tables if the "reset" command is specified, retrieves the data from the input file and calls a method from the Logic class to process the data and execute the commands
     * @param args String of command line arguments
     */
    public static void main(String[] args){

        if(args.length == 2){
            if(args[1].compareToIgnoreCase("reset") == 0){
                logic.resetDB();
            }
        }
        List<CommandText> commands = view.readFile(args[0]);
        logic.executeCommand(commands);
        logic.generateBill();
    }
}
