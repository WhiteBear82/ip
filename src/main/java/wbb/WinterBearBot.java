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
     * Continuously processes user commands to manage the task list
     * until an exit command is received.
     */
    public void manageTaskList() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String command = ui.readCommand();
                assert command != null : "Command input should not be null";

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
     * Starts the WinterBearBot application, displaying a welcome message,
     * managing tasks, and handling user interactions until termination.
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
            Command parsedCommand = parser.parseCommand(input);
            if (parsedCommand == null) {
                throw new WBBException("ERROR: Invalid command "
                        + "(valid commands are: list, todo, deadline, event, mark, unmark, delete, tasks, find, bye)");
            }
            parsedCommand.execute(taskList, input, ui, storage);
            commandType = parsedCommand.getClass().getSimpleName();
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
        assert ui != null : "Ui cannot be null";
        this.ui = ui;
    }
    public void setStorage(Storage storage) {
        assert storage != null : "Storage cannot be null";
        this.storage = storage;
    }
    public void setParser(Parser parser) {
        assert parser != null : "Parser cannot be null";
        this.parser = parser;
    }
    public void setTaskList(ArrayList<Task> taskList) {
        assert taskList != null : "TaskList cannot be null";
        this.taskList = taskList;
    }

    /**
     * Entry point of the WinterBearBot application.
     * Initializes and runs the bot to handle task management.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new WinterBearBot().runProgram();
    }
}
