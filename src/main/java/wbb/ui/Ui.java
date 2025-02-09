package wbb.ui;
import java.util.ArrayList;
import java.util.Scanner;

import wbb.task.Task;

/**
 * Handles user interface operations such as displaying messages, reading user input,
 * and printing task lists. This class is responsible for interacting with the user
 * and presenting the relevant information.
 */
public class Ui {
    protected static final String HORIZONTAL_LINE = "\t____________________________________________________________\n";
    private final Scanner sc = new Scanner(System.in);
    private final StringBuilder outputBuffer = new StringBuilder();

    /**
     * Print horizontal line with tabs, along with the given msg.
     * @param msg The message to be prettyPrinted.
     */
    public void prettyPrint(String msg) {
        System.out.print(HORIZONTAL_LINE);
        System.out.println(msg);
        System.out.println(HORIZONTAL_LINE);
        outputBuffer.append(msg.trim());
    }

    /**
     * To display welcome message to user.
     */
    public void displayWelcomeMessage() {
        prettyPrint("\tHello! I'm WinterBearBot\n\tWhat can I do for you?"
                + "Enter 'help' for a list of commands.");
    }

    /**
     * Provides welcome message to GUI.
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm WinterBearBot.\nWhat can I do for you?";
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
            System.out.printf("\t%d. %s%n", (i + 1), item.toString());
        }
        System.out.println(HORIZONTAL_LINE);

        outputBuffer.append("Here are the tasks in your list:\n");
        for (int i = 0; i < list.size(); i++) {
            Task item = list.get(i);
            outputBuffer.append(i + 1).append(". ").append(item.toString()).append("\n");
        }
    }

    /**
     * Pretty-prints message indicating that task addition was successful.
     * @param taskListSize The size of the taskList.
     * @param taskName The taskName.
     */
    public void printAdditionSuccessfulMsg(int taskListSize, Task taskName) {
        String addTaskSuccessfulMsg = "Got it. I've added this task:\n\t";
        String totalTaskMsg = "\nNow you have " + taskListSize + " tasks in the list.";
        prettyPrint(addTaskSuccessfulMsg + taskName + totalTaskMsg);
    }

    /**
     * Pretty-prints message indicating that task deletion was successful.
     * @param taskListSize The size of the taskList.
     * @param taskName The taskName.
     */
    public void printDeleteSuccessfulMsg(int taskListSize, Task taskName) {
        String deleteTaskSuccessfulMsg = "Noted. I've removed this task:\n\t";
        String totalTaskMsg = "\nNow you have " + taskListSize + " tasks in the list.";
        prettyPrint(deleteTaskSuccessfulMsg + taskName + totalTaskMsg);
    }

    /**
     * Prints all tasks that are due today, if any.
     * @param tasksDueToday The tasks that are due today.
     */
    public void printTasksDueToday(ArrayList<Task> tasksDueToday) {
        if (tasksDueToday.isEmpty()) {
            System.out.println(HORIZONTAL_LINE + "\tNo tasks are due today.\n" + HORIZONTAL_LINE);
        } else {
            System.out.print(HORIZONTAL_LINE);
            System.out.println("\tTasks due today:");
            for (Task task : tasksDueToday) {
                System.out.println("\t" + task);
            }
            System.out.println(HORIZONTAL_LINE);
        }

        if (tasksDueToday.isEmpty()) {
            prettyPrint("\tNo tasks are due today.");
        } else {
            outputBuffer.append("Tasks due today:\n");
            for (Task task : tasksDueToday) {
                outputBuffer.append(task).append("\n");
            }
        }
    }

    /**
     * Prints all tasks that are matched by the "find" command, if any.
     * @param matchingTasks The tasks that are due today.
     */
    public void printMatchingTasks(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println(HORIZONTAL_LINE + "\tNo matching tasks.\n" + HORIZONTAL_LINE);
        } else {
            System.out.println("\tHere are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }

        if (matchingTasks.isEmpty()) {
            prettyPrint("\tNo matching tasks.");
        } else {
            outputBuffer.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                outputBuffer.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
            }
        }
    }

    /**
     * Store and provides output for GUI.
     * @return The output.
     */
    public String getLastOutput() {
        String output = outputBuffer.toString();
        outputBuffer.setLength(0);
        return output;
    }
}
