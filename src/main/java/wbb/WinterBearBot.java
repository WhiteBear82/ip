package wbb;
import java.util.ArrayList;

import wbb.command.Command;
import wbb.exception.WBBException;
import wbb.parser.Parser;
import wbb.storage.Storage;
import wbb.task.Task;
import wbb.ui.Ui;

/**
 * Winter Bear Bot - The task manager application.
 */
public class WinterBearBot {

    private Ui ui;
    private Storage storage;
    private Parser parser;
    private ArrayList<Task> taskList;
    private String commandType;

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
                if (c == null) {
                    continue;
                }
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
            if (c == null) {
                throw new WBBException("ERROR: Invalid command "
                        + "(valid commands are: list, todo, deadline, event, mark, unmark, delete, tasks, find, bye)");
            }
            c.execute(taskList, input, ui, storage);
            commandType = c.getClass().getSimpleName();
            if (commandType.equals("ExitCommand")) {
                System.exit(0);
            }
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
    public Ui getUi() {
        return ui;
    }
    public Storage getStorage() {
        return storage;
    }
    public Parser getParser() {
        return parser;
    }
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
    public void setUi(Ui ui) {
        this.ui = ui;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
    public void setParser(Parser parser) {
        this.parser = parser;
    }
    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * The main method.
     * @param args The arguments for the main method.
     */
    public static void main(String[] args) {
        new WinterBearBot().runProgram();
    }
}
