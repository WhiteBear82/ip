package wbb.command;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wbb.storage.Storage;
import wbb.task.Task;
import wbb.task.TaskType;
import wbb.ui.Ui;

/**
 * Handles the "help" command to display task formats and other command usages.
 */
public class HelpCommand extends Command {
    private static final Map<String, String> COMMAND_DESCRIPTIONS = Map.ofEntries(
            Map.entry("list", "Lists all tasks."),
            Map.entry("bye", "Exits the application."),
            Map.entry("mark", "Marks a task as completed. Usage: mark [task number]"),
            Map.entry("unmark", "Unmarks a completed task. Usage: unmark [task number]"),
            Map.entry("todo", "Adds a new todo task. Usage: todo [task description]"),
            Map.entry("deadline", "Adds a new deadline. Usage: deadline [description] /by [date]"),
            Map.entry("event", "Adds a new event. Usage: event [description] /from [start time] /to [end time]"),
            Map.entry("delete", "Deletes a task. Usage: delete [task number]"),
            Map.entry("tasks", "Displays all tasks with filters."),
            Map.entry("find", "Finds tasks that match a keyword. Usage: find [keyword]"),
            Map.entry("help", "Displays this help message.")
    );

    @Override
    public void execute(ArrayList<Task> taskList, String command, Ui ui, Storage storage) {
        StringBuilder helpMessage = new StringBuilder();

        // Add blue bold title for Available Commands
        helpMessage.append("<h2 style='color:blue;'>Available Commands:</h2>");
        helpMessage.append("<ul>");
        for (Map.Entry<String, String> entry : COMMAND_DESCRIPTIONS.entrySet()) {
            helpMessage.append("<li><b>").append(entry.getKey()).append("</b> - ")
                    .append(entry.getValue()).append("</li>");
        }
        helpMessage.append("</ul>");

        // Add blue bold title for Task Formats
        helpMessage.append("<h2 style='color:blue;'>Task Formats:</h2>");
        helpMessage.append("<ul>");
        for (TaskType type : TaskType.values()) {
            helpMessage.append("<li>").append(type.getFormatExample()).append("</li>");
        }
        helpMessage.append("</ul>");

        // Create a new stage for the help window
        Stage helpStage = new Stage();
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.setTitle("Help - Winter Bear Bot");

        WebView webView = new WebView();
        webView.getEngine()
                .loadContent("<html><body style='font-size:14px;'>" + helpMessage + "</body></html>");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> helpStage.close());

        VBox layout = new VBox(webView, closeButton);
        layout.setSpacing(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout, 500, 400);
        helpStage.setScene(scene);
        helpStage.showAndWait();
    }
}
