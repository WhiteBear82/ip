import java.util.Scanner;
import java.util.ArrayList;

public class WinterBearBot {

    private static String HORIZONTAL_LINE = "\t____________________________________________________________\n";

    /**
     * Helper method to print horizontal line with tabs, along with the given msg
     * @param msg
     */
    public static void prettyPrint(String msg) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(msg);
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Level-0: Displays welcome message
     */
    public static void displayWelcomeMessage() {
        prettyPrint("\tHello! I'm WinterBearBot\n\tWhat can I do for you?");
    }

    /**
     * Helper method to print all items in the taskList
     * @param list
     */
    public static void printList(ArrayList<Task> list) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            Task item = list.get(i);
            System.out.printf("\t%d. %s %s%n", (i+1), item.getStatusIcon(), item.getDescription());
        }
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Change item status to either "Done" or "Undone"
     * @param item
     * @param command
     */
    public static void changeItemStatus(Task item, String command) {
        String msg = "";
        if (command.startsWith("mark")) {
            item.setDone();
            msg = "\tNice! I've marked this task as done:";
        }
        else {
            item.setUndone();
            msg = "\tOK, I've marked this task as not done yet:";
        }
        prettyPrint(msg + "\n\t\t" + item.getStatusIcon() + " " + item.getDescription());
    }

    /**
     * Level-1: Echo user commands (inputs)
     * Level-2: Add inputs to list
     * Level-3: Mark as done
     */
    public static void manageTaskList() {
        // Initialise variables
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();
        String command = "";

        while (true) {
            // Get user command
            command = sc.nextLine();

            // Interpret Commands
            if (command.equalsIgnoreCase("list")) {
                printList(taskList);
                continue;
            }

            else if (command.equalsIgnoreCase("bye"))
                break;

            else if (command.startsWith("mark") || command.startsWith("unmark")) {
                int itemIdx = Integer.parseInt(command.split(" ")[1]) - 1; // -1 for array indexing
                Task item = taskList.get(itemIdx);
                changeItemStatus(item, command);
                continue;
            }

            taskList.add(new Task(command));
            prettyPrint("\tadded: " + command);
        }
    }

    /**
     * Display message before the end of the program
     */
    public static void displayFarewellMessage() {
        prettyPrint("\tBye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        displayWelcomeMessage();
        manageTaskList();
        displayFarewellMessage();
    }
}
