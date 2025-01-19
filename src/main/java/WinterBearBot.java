import java.util.Scanner;

public class WinterBearBot {

    private static String HORIZONTAL_LINE = "\t____________________________________________________________\n";

    /**
     * Level-0: Displays welcome message
     */
    public static void displayWelcomeMessage() {
        String welcomeMessage = HORIZONTAL_LINE;
        welcomeMessage += "\tHello! I'm WinterBearBot\n";
        welcomeMessage += "\tWhat can I do for you?\n";
        welcomeMessage += HORIZONTAL_LINE;
        System.out.println(welcomeMessage);
    }

    /**
     * Helper method for Level-1 to print all items in the list
     * @param list
     */
    public static void printList(String[] list) {
        System.out.print(HORIZONTAL_LINE);
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null)
                break;
            System.out.println("\t" + i + ". " + list[i]);
        }
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Level-1: Echo user commands (inputs)
     */
    public static void storeListInput() {
        // Initialise variables
        int idx = 0;
        String[] list = new String[100];
        String input = "";
        Scanner sc = new Scanner(System.in);

        while (true) {
            input = sc.nextLine();

            if (input.equalsIgnoreCase("list")) {
                printList(list);
                continue;
            }
            else if (input.equalsIgnoreCase("bye"))
                break;

            list[idx] = input;
            idx++;
            System.out.println(HORIZONTAL_LINE + "\tadded: " + input + "\n" + HORIZONTAL_LINE);
        }
    }

    /**
     * Display message before the end of the program
     */
    public static void displayFarewellMessage() {
        String farewellMessage = HORIZONTAL_LINE;
        farewellMessage += "\tBye. Hope to see you again soon!\n";
        farewellMessage += HORIZONTAL_LINE;
        System.out.println(farewellMessage);
    }

    public static void main(String[] args) {
        displayWelcomeMessage();
        storeListInput();
        displayFarewellMessage();
    }
}
