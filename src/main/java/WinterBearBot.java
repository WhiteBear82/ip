import java.util.Scanner;
import java.util.ArrayList;

public class WinterBearBot {

    /*
     * Core methods:
     * 1. displayWelcomeMessage - the first message printed when the program starts, for Level-0 task.
     * 2. manageTaskList - to manage the taskList for Levels 1-4 tasks.
     */

    /*
     * Helper methods:
     * 1. prettyPrint - to prettyPrint any given message.
     * 2. displayFarewellMessage - to print the final message before the program ends.

     * 3. printList - to print the tasks in the taskList.
     * 4. changeItemStatus - either set a task to "Done" or "Undone".
     * 5. addTask - to add an item into taskList.
     *
     * Other helper methods, e.g. 4a, 5b, 5b-1, are specific to 4, 5, and 5b respectively, mostly used for validation.
     */

    /*
     * Legend: C1 = Core Method 1, H1 = Helper Method 1
     * Flow:
     * The main() method calls:
     * - displayWelcomeMessage() [C1]
     *   -> prettyPrint() [H1]
     * - manageTaskList() [C2]
     *   -> printList() [H3]
     *   -> changeItemStatus() [H4]
     *      --> validateAndGetItemIdx() [H4a]
     *      --> validateItemIdxForTaskList() [H4b]
     *          ---> prettyPrint() [H1]
     *      --> prettyPrint() [H1]
     *   -> addTask() [H5]
     *      --> validateAndGetTaskName() [H5a]
     *      --> newDeadlineTask() [H5b]
     *          ---> validateTaskNameBy() [H5b-1]
     *          ---> validateAndGetTaskNameBy() [H5b-2]
     *      --> newEventTask() [H5c]
     *          ---> validateFromTo() [H5c-1]
     *          ---> validateAndGetTaskNameFromTo() [H5c-2]
     *          ---> validateAndGetFromTo() [H5c-3]
     *      --> prettyPrint [H1]
     * - displayFarewellMessage() [H2]
     *   -> prettyPrint() [H1]
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
    public static void changeItemStatus(ArrayList<Task> taskList, String command) throws WBBException {
        // Validate itemIdx and taskList before continuing
        int itemIdx = validateAndGetItemIdx(command);
        validateItemIdxForTaskList(taskList, itemIdx);

        // Validation passed, continue running main program logic (i.e. to change the item status)
        Task taskName = taskList.get(itemIdx);
        if (command.startsWith("mark")) {
            taskName.setDone();
            prettyPrint("\tNice! I've marked this task as done:\n\t\t" + taskName);
        }
        else {
            taskName.setUndone();
            prettyPrint("\tOK, I've marked this task as not done yet:\n\t\t" + taskName);
        }
    }

    /**
     * Helper method 4a to retrieve, validate and return the item index.
     * @param command The user command.
     * @return The item index.
     * @throws WBBException if missing index, non-positive index, or non-integer index.
     */
    private static int validateAndGetItemIdx(String command) throws WBBException {
        String[] commandItemIdx = command.split(" ");
        if (commandItemIdx.length <= 1)
            throw new WBBException("\tERROR: Missing item index.\n\tPlease provide a valid index (e.g. mark 2 / unmark 2)");

        int itemIdx;
        try {
            itemIdx = Integer.parseInt(commandItemIdx[1]) - 1; // -1 for array indexing
        } catch (NumberFormatException e) {
            throw new WBBException("\tERROR: Index must be integers only (e.g. 1, 2, ...)");
        }

        if (itemIdx <= -1)  // Potential error input: "mark 0"
            throw new WBBException("\tERROR: Index must be greater than or equal to 1");
        return itemIdx;
    }

    /**
     * Helper method 4b to validate if item index supplied is out of the task list range, or verify if the task list is empty.
     * @param taskList The taskList.
     * @param itemIdx The itemIdx.
     * @throws WBBException if index is out of bounds, or task list is empty.
     */
    private static void validateItemIdxForTaskList(ArrayList<Task> taskList, int itemIdx) throws WBBException {
        if (taskList.isEmpty())
            throw new WBBException("\tERROR: Task list is empty. Nothing to mark or unmark.");
        if (itemIdx >= taskList.size()) {  // Potential error input: "mark 100" when list only has 1 element
            printList(taskList);
            throw new WBBException("\tERROR: Invalid item index.\n\tItem index out of bounds. Please select a valid index in the list above");
        }
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
    public static void addTask(ArrayList<Task> list, String command) throws WBBException {
        String typeOfTask = command.split(" ")[0];  // "todo", "deadline", "event"
        TaskType taskType = TaskType.fromString(typeOfTask);  // enum of typeOfTask
        String taskName = validateAndGetTaskName(command, typeOfTask, taskType);  // name of task
        Task task;

        if (typeOfTask.equalsIgnoreCase("todo"))
            task = new Todo(taskName);
        else if (typeOfTask.equalsIgnoreCase("deadline"))
            task = newDeadlineTask(taskName, taskType);  // Validate input and create new deadline task
        else
            task = newEventTask(taskName, taskType);  // Validate input and create new event task

        // After validation success, continue with main program logic (i.e. add new Task to taskList)
        list.add(task);
        String addTaskSuccessfulMsg = "\tGot it. I've added this task:\n\t\t";
        String totalTaskMsg = "\n\tNow you have " + list.size() + " tasks in the list.";
        prettyPrint( addTaskSuccessfulMsg + task + totalTaskMsg);
    }

    /**
     * Helper method 5a to validate input and return task name from the user command.
     * @param command The user command.
     * @param typeOfTask The type of task (e.g. todo, deadline, or event).
     * @param taskType The type of task (Enum).
     * @return The taskName, if valid.
     * @throws WBBException if taskName is empty.
     */
    private static String validateAndGetTaskName(String command, String typeOfTask, TaskType taskType) throws WBBException {
        String taskName = command.substring(typeOfTask.length()).trim();
        if (taskName.isEmpty())
            throw new WBBException("\tERROR: Missing task name\n\t" + taskType.getFormatExample());
        return taskName;
    }

    /**
     * Helper method 5b to validate input and return new deadline task.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return A new deadline task, if valid.
     * @throws WBBException if missing "/by", deadline or taskName.
     */
    private static Task newDeadlineTask(String taskName, TaskType taskType) throws WBBException {
        validateTaskNameBy(taskName, taskType);
        String[] taskNameBy = validateAndGetTaskNameBy(taskName, taskType);
        return new Deadline(taskNameBy[0].trim(), taskNameBy[1].trim());
    }

    /**
     * Helper method 5b-1 to validate taskName.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if taskName does not contain "/by".
     */
    private static void validateTaskNameBy(String taskName, TaskType taskType) throws WBBException {
        if (!taskName.contains("/by")) // Potential error input: "deadline borrow book"
            throw new WBBException("\tERROR: Missing '/by'\n\t" + taskType.getFormatExample());
    }

    /**
     * Helper method 5b-2 to validate taskName and deadline and return both if valid.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return taskNameBy, which contains taskName and deadline, if valid.
     * @throws WBBException if missing deadline or task name.
     */
    private static String[] validateAndGetTaskNameBy(String taskName, TaskType taskType) throws WBBException {
        String[] taskNameBy = taskName.split("/by");
        if (taskNameBy.length != 2)
            throw new WBBException("\tERROR: Missing deadline \n\t" + taskType.getFormatExample());
        if (taskNameBy[0].trim().isEmpty())  // Potential error input: "deadline /by 2"
            throw new WBBException(("\tERROR: Missing task name\n\t" + taskType.getFormatExample()));
        return taskNameBy;
    }

    /**
     * Helper method 5c to validate input and return new event task.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return A new event task, if valid.
     * @throws WBBException if missing "/from" or "/to", "/from" comes after "/to", start date, end date, or taskName
     */
    private static Task newEventTask(String taskName, TaskType taskType) throws WBBException {
        validateFromTo(taskName, taskType);
        String[] taskNameFromTo = validateAndGetTaskNameFromTo(taskName, taskType);
        String[] fromTo = validateAndGetFromTo(taskNameFromTo, taskType);
        return new Event(taskNameFromTo[0].trim(), fromTo[0].trim(), fromTo[1].trim());
    }

    /**
     * Helper method 5c-1 to validate "/from" and "/to" are present and in correct positions.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @throws WBBException if either "/from" or "/to" is missing, or "/from" comes after "/to".
     */
    private static void validateFromTo(String taskName, TaskType taskType) throws WBBException {
        if (!taskName.contains("/from") || !taskName.contains("/to"))
            throw new WBBException("\tERROR: Missing '/from' or '/to'\n\t" + taskType.getFormatExample());
        if (taskName.indexOf("/from") > taskName.indexOf("/to"))
            throw new WBBException("\tERROR: /from must come before /to\n\t" + taskType.getFormatExample());
    }

    /**
     * Helper method 5c-2 to further validate input and return taskName, start datetime, and end datetime.
     * @param taskName The taskName.
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return taskName, start datetime, and end datetime, together in a String "taskNameFromTo".
     * @throws WBBException if missing start date or taskName.
     */
    private static String[] validateAndGetTaskNameFromTo(String taskName, TaskType taskType) throws WBBException {
        String[] taskNameFromTo = taskName.split("/from");
        if (taskNameFromTo.length != 2)  // Potential error input: "event borrow /from"
            throw new WBBException("\tERROR: Missing start date \n\t" + taskType.getFormatExample());
        else if (taskNameFromTo[0].trim().isEmpty())  // Potential error input: "event /from /to 2pm"
            throw new WBBException("\tERROR: Missing task name \n\t" + taskType.getFormatExample());
        else
            if ((taskNameFromTo[1].trim().split("/to")).length == 0)  // Potential error input: "event borrow /from /to"
                throw new WBBException("\tERROR: Missing start date \n\t" + taskType.getFormatExample());
            else if (taskNameFromTo[1].trim().split("/to")[0].isEmpty())  // Potential error input: "event borrow /from /to 2pm"
                throw new WBBException("\tERROR: Missing start date \n\t" + taskType.getFormatExample());
        return taskNameFromTo;
    }

    /**
     * Helper method 5c-3 to further validate input and return start datetime and end datetime.
     * @param taskNameFromTo The taskName, start datetime and end datetime in one String "taskNameFromTo"
     * @param taskType The type of task (e.g. todo, deadline, or event).
     * @return start datetime and end datetime in one String "fromTo"
     * @throws WBBException if missing end date.
     */
    private static String[] validateAndGetFromTo(String[] taskNameFromTo, TaskType taskType) throws WBBException {
        String[] fromTo = taskNameFromTo[1].split("/to");
        if (fromTo.length != 2)
            throw new WBBException("\tERROR: Missing end date \n\t" + taskType.getFormatExample());  // Potential error input: "event borrow /from 2pm /to"
        return fromTo;
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
     * Level-5: Handle errors.
     */
    public static void manageTaskList() {
        // Initialise variables
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();
        String command;
        String commandPrefix;

        program:
        while (true) {
            // Get user command
            command = sc.nextLine();
            commandPrefix = command.split(" ")[0];

            // Interpret Commands
            try {
                switch (commandPrefix) {
                    case "list":
                        printList(taskList);
                        break;
                    case "bye":
                        break program;
                    case "mark":
                    case "unmark":
                        changeItemStatus(taskList, command);
                        break;
                    case "todo":
                    case "deadline":
                    case "event":
                        addTask(taskList, command);
                        break;
                    default:
                        throw new WBBException("\tERROR: Invalid command (valid commands are: list, todo, deadline, event, mark, unmark, bye)");
                }
            } catch (WBBException e) {
                prettyPrint(e.getMessage());
            }
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
