import java.util.ArrayList;

public class WinterBearBot {

    private final Ui ui;
    private final Storage storage;
    private final Parser parser;
    private final ArrayList<Task> taskList;

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
                Command c = parser.parseCommand(command, ui);
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
     * The main method.
     * @param args The arguments for the main method.
     */
    public static void main(String[] args) {
        new WinterBearBot().runProgram();
    }
}
