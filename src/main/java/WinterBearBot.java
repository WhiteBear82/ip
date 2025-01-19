import java.util.Scanner;

public class WinterBearBot {

    private static String HORIZONTAL_LINE = "\t____________________________________________________________\n";

    /**
     * Level-0: Displays welcome message
     */
    public static void DisplayWelcomeMessage() {
        String welcomeMessage = HORIZONTAL_LINE;
        welcomeMessage += "\tHello! I'm WinterBearBot\n";
        welcomeMessage += "\tWhat can I do for you?\n";
        welcomeMessage += HORIZONTAL_LINE;
        System.out.println(welcomeMessage);
    }

    /**
     * Level-1: Echo user commands (inputs)
     */
    public static void EchoInput() {
        String input = "";
        while (true) {
            Scanner sc = new Scanner(System.in);
            input = sc.nextLine();
            if (input.equalsIgnoreCase("bye"))
                break;
            System.out.println(HORIZONTAL_LINE + "\t" + input + "\n" + HORIZONTAL_LINE);
        }
    }

    /**
     * Display message before the end of the program
     */
    public static void DisplayFarewellMessage() {
        String farewellMessage = HORIZONTAL_LINE;
        farewellMessage += "\tBye. Hope to see you again soon!\n";
        farewellMessage += HORIZONTAL_LINE;
        System.out.println(farewellMessage);
    }

    public static void main(String[] args) {
        DisplayWelcomeMessage();
        EchoInput();
        DisplayFarewellMessage();
    }
}
