package wbb.storage;
import java.io.*;
import java.util.ArrayList;
import wbb.task.Task;
import wbb.task.TaskType;

/**
 * Handle loading and saving tasks from and to a file.
 */
public class Storage {
    private static final String FILE_PATH = "./data/wbb.txt";

    /**
     * Create the directory "./data" and/or the file "./data/wbb.txt" if it doesn't exist.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void setupFile() {
        File file = new File(FILE_PATH);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * To save taskList into FILE_PATH.
     * @param taskList The taskList.
     */
    public void saveTasks(ArrayList<Task> taskList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : taskList) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\tERROR: Could not save tasks.");
        }
    }

    /**
     * To load contents of FILE_PATH into taskList.
     * @return The taskList.
     */
    public ArrayList<Task> loadTasks() {
        setupFile();
        ArrayList<Task> taskList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                try {
                    TaskType type = TaskType.fromString(parts[0].trim());
                    Task task = type.createTask(parts);
                    task.isDone = Boolean.parseBoolean(parts[1].trim());
                    taskList.add(task);
                } catch (IllegalArgumentException e) {
                    System.out.println("\tERROR: Skipping invalid task " + line);
                } catch (NullPointerException e) {
                    System.out.println("\tERROR: Skipping invalid task type (only todo/deadline/event): " + line);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("\tERROR: Skipping invalid task format (missing details): " + line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return taskList;
    }
}
