import java.util.Scanner;
import java.util.ArrayList;

public class WinterBearBot {

    /*
     * Helper methods:
     * 1. prettyPrint - to prettyPrint any given message.
     * 2. displayFarewellMessage - to print the final message before the program ends.

     * 3. printList - to print the tasks in the taskList.
     * 4. changeItemStatus - either set a task to "Done" or "Undone".
     * 5. addTask - to add an item into taskList.
     */

    /*
     * Core methods:
     * 1. displayWelcomeMessage - the first message printed when the program starts, for Level-0 task.
     * 2. manageTaskList - to manage the taskList for Levels 1-4 tasks.
     */

    private static final String HORIZONTAL_LINE = "\t____________________________________________________________\n";


    /**
     * Helper method 1 to print horizontal line with tabs, along with the given msg.
     * @param msg The message to be prettyPrinted.
     */
    public static void prettyPrint(String msg) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(msg);
        System.out.println(HORIZONTAL_LINE);
    }


    /**
     * Helper method 2 to display message before the end of the program.
     */
    public static void displayFarewellMessage() {
        prettyPrint("\tBye. Hope to see you again soon!");
    }


    /**
     * Helper method 3 to print all items in the taskList.
     * @param list The taskList.
     */
    public static void printList(ArrayList<Task> list) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            Task item = list.get(i);
            System.out.printf("\t%d. %s%n", (i+1), item.toString());
        }
        System.out.println(HORIZONTAL_LINE);
    }


    /**
     * Helper method 4 to change item status to either "Done" or "Undone".
     * @param taskList The taskList.
     * @param command The input command by the user.
     */
    public static void changeItemStatus(ArrayList<Task> taskList, String command) {
        int itemIdx = Integer.parseInt(command.split(" ")[1]) - 1; // -1 for array indexing
        Task taskName = taskList.get(itemIdx);
        String msg;
        if (command.startsWith("mark")) {
            taskName.setDone();
            msg = "\tNice! I've marked this task as done:";
        }
        else {
            taskName.setUndone();
            msg = "\tOK, I've marked this task as not done yet:";
        }
        prettyPrint(msg + "\n\t\t" + taskName);
    }


    /**
     * Helper method 5 to add task in list.
     * Add item to list - Either "todo", "deadline" or "event".
     * Format "todo": todo <taskName>
     * Format "deadline": deadline <taskName> /by <when>
     * Format "event": event <taskName> /from <start> /to <end>
     * @param list The taskList.
     * @param command The input command by the user.
     */
    public static void addTask(ArrayList<Task> list, String command) {
        String typeOfTask = command.split(" ")[0];
        String taskName = command.substring(typeOfTask.length()).trim();
        Task task;

        if (typeOfTask.equalsIgnoreCase("todo"))
            task = new Todo(taskName);

        else if (typeOfTask.equalsIgnoreCase("deadline")) {
            String[] taskNameBy = taskName.split("/by");
            task = new Deadline(taskNameBy[0].trim(), taskNameBy[1].trim());
        }

        else {
            String[] taskNameFromTo = taskName.split("/from");
            taskName = taskNameFromTo[0].trim();
            String[] fromTo = taskNameFromTo[1].split("/to");
            task = new Event(taskName, fromTo[0].trim(), fromTo[1].trim());
        }

        list.add(task);
        prettyPrint("\tGot it. I've added this task:\n\t\t" + task
                + "\n\tNow you have " + list.size() + " tasks in the list.");
    }


    /**
     * Core method 1: to display welcome message for Level-0 task.
     * Level-0: Displays welcome message.
     */
    public static void displayWelcomeMessage() {
        prettyPrint("\tHello! I'm WinterBearBot\n\tWhat can I do for you?");
    }


    /**
     * Core method 2: to manage the taskList for Levels 1-4 tasks.
     * Level-1: Echo user commands (inputs).
     * Level-2: Add inputs to list.
     * Level-3: Mark as done.
     * Level-4: ToDos, Events, Deadlines.
     */
    public static void manageTaskList() {
        // Initialise variables
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();
        String command;

        while (true) {
            // Get user command
            command = sc.nextLine();

            // Interpret Commands
            if (command.equals("list"))
                printList(taskList);
            else if (command.equals("bye"))
                break;
            else if (command.startsWith("mark") || command.startsWith("unmark"))
                changeItemStatus(taskList, command);
            else if (command.startsWith("todo") || command.startsWith("deadline") || command.startsWith("event"))
                addTask(taskList, command);
        }
    }


    /**
     * The main method
     * @param args The arguments for the main method
     */
    public static void main(String[] args) {
        displayWelcomeMessage();
        manageTaskList();
        displayFarewellMessage();
    }
}
