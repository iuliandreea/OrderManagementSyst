package presentation;

import utils.CommandText;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.Command;

/**
 * Presentation class used to efficiently read the commands from the input file
 */

public class View {

    /**
     * a list which holds all the commands retrieved from the input file
     */
    private List<CommandText> commands = new ArrayList<>();

    /**
     * method used to interpret all the commands given in the input file
     * @param fileName String representing the path of the input file
     * @return a list of CommandText objects represnting the list of commands to be processed
     */
    public List<CommandText> readFile(String fileName) {
        String[] line, aux;
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNext()) {
                line = sc.nextLine().split(": ");
                aux = line[0].toLowerCase().split(" ");
                switch(aux[0]){
                    case "insert":
                        commands.add(new CommandText(Command.INSERT, aux[1], line[1]));
                        break;
                    case "order":
                        commands.add(new CommandText(Command.ORDER, "order", line[1]));
                        break;
                    case "delete":
                        commands.add(new CommandText(Command.DELETE, aux[1], line[1]));
                        break;
                    case "report":
                        commands.add(new CommandText(Command.REPORT, aux[1]));
                        break;
                    default:
                        System.out.println("No such command " + aux[0]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commands;
    }
}
