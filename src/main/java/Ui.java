import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    Scanner sc = new Scanner(System.in);
    private static final String HORIZONTAL_LINE = "\t____________________________________________________________\n";

    /**
     * Print horizontal line with tabs, along with the given msg.
     * @param msg The message to be prettyPrinted.
     */
    public void prettyPrint(String msg) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(msg);
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * To display welcome message.
     */
    public void displayWelcomeMessage() {
        prettyPrint("\tHello! I'm WinterBearBot\n\tWhat can I do for you?");
    }

    /**
     * Display message before the end of the program.
     */
    public void displayFarewellMessage() {
        prettyPrint("\tBye. Hope to see you again soon!");
    }

    /**
     * Reads user input command.
     * @return The user input command.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Pretty-prints error message.
     * @param errorMsg The error message.
     */
    public void printErrorMsg(String errorMsg) {
        prettyPrint(errorMsg);
    }

    /**
     * To print all items in the taskList.
     * @param list The taskList.
     */
    public void printList(ArrayList<Task> list) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            Task item = list.get(i);
            System.out.printf("\t%d. %s%n", (i+1), item.toString());
        }
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Pretty-prints message indicating that task addition was successful.
     * @param taskListSize The size of the taskList.
     * @param taskName The taskName.
     */
    public void printAdditionSuccessfulMsg(int taskListSize, Task taskName) {
        String addTaskSuccessfulMsg = "\tGot it. I've added this task:\n\t\t";
        String totalTaskMsg = "\n\tNow you have " + taskListSize + " tasks in the list.";
        prettyPrint( addTaskSuccessfulMsg + taskName + totalTaskMsg);
    }

    /**
     * Pretty-prints message indicating that task deletion was successful.
     * @param taskListSize The size of the taskList.
     * @param taskName The taskName.
     */
    public void printDeleteSuccessfulMsg(int taskListSize, Task taskName) {
        String deleteTaskSuccessfulMsg = "\tNoted. I've removed this task:\n\t\t";
        String totalTaskMsg = "\n\tNow you have " + taskListSize + " tasks in the list.";
        prettyPrint( deleteTaskSuccessfulMsg + taskName + totalTaskMsg);
    }

    /**
     * Prints all tasks that are due today, if any.
     * @param tasksDueToday The tasks that are due today.
     */
    public void printTasksDueToday(ArrayList<Task> tasksDueToday) {
        if (tasksDueToday.isEmpty())
            System.out.println("No tasks are due today.");
        else {
            System.out.println("Tasks due today:");
            for (Task task : tasksDueToday) {
                System.out.println(task);
            }
        }
    }
}
