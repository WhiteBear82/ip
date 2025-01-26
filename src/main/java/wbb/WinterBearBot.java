package wbb;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import wbb.ui.Ui;
import wbb.storage.Storage;
import wbb.parser.Parser;
import wbb.task.Task;
import wbb.command.Command;
import wbb.exception.WBBException;

public class WinterBearBot {

    public Ui ui;
    public Storage storage;
    public Parser parser;
    public ArrayList<Task> taskList;
    public String commandType;

    /**
     * Constructor to initialise new Ui, Storage, TaskList, Parser, and load Tasks from Storage.
     */
    public WinterBearBot() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.parser = new Parser();
        this.taskList = storage.loadTasks();
    }

    /**
     * Program to manage user's taskList.
     */
    public void manageTaskList() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String command = ui.readCommand();
                Command c = parser.parseCommand(command);
                if (c == null)
                    continue;
                c.execute(taskList, command, ui, storage);
                isExit = c.isExit();
            } catch (WBBException e) {
                ui.printErrorMsg(e.getMessage());
            }
        }
    }

    /**
     * Run the WinterBearBot Program.
     */
    public void runProgram() {
        ui.displayWelcomeMessage();
        manageTaskList();
        ui.displayFarewellMessage();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {

        try {
            Command c = parser.parseCommand(input);
            if (c == null)
                throw new WBBException("ERROR: Invalid command (valid commands are: list, todo, deadline, event, mark, unmark, delete, tasks, find, bye)");
            c.execute(taskList, input, ui, storage);
            commandType = c.getClass().getSimpleName();
            if (commandType.equals("ExitCommand"))
                System.exit(0);
            System.out.println(commandType);
            return ui.getLastOutput();
        } catch (WBBException | NullPointerException e) {
            commandType = "InvalidCommand";
            return e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * The main method.
     * @param args The arguments for the main method.
     */
    public static void main(String[] args) {
        new WinterBearBot().runProgram();
    }
}
