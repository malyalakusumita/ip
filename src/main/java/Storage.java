import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Handles saving tasks to, and loading tasks from, the hard disk.
 */
public class Storage {

    private static final String FILE_PATH = "./data/yapBot.txt";

    /**
     * Writes the given tasks to the save file, creating the parent directory
     * if it does not already exist. Any I/O failure is reported but does not
     * crash the program.
     *
     * @param tasks     the current task array.
     * @param taskCount the number of active tasks in the array.
     */
    public static void save(Task[] tasks, int taskCount) {
        try {
            Path path = Paths.get(FILE_PATH);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (FileWriter writer = new FileWriter(path.toFile())) {
                for (int i = 0; i < taskCount; i++) {
                    writer.write(tasks[i].toFileFormat());
                    writer.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save tasks to disk: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the save file into the given array, starting at index 0.
     * If the save file does not exist yet (e.g. first run), no tasks are loaded
     * and 0 is returned. A line that cannot be parsed is skipped with a warning
     * rather than aborting the whole load.
     *
     * @param tasks the array to populate with loaded tasks.
     * @return the number of tasks successfully loaded.
     */
    public static int load(Task[] tasks) {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return 0;
        }

        int taskCount = 0;
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                try {
                    tasks[taskCount] = parseTask(line);
                    taskCount++;
                } catch (yapBotException e) {
                    System.out.println("Warning: skipping corrupted save-file line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not load tasks from disk: " + e.getMessage());
        }
        return taskCount;
    }

    /**
     * Parses a single save-file line into the matching Task subclass.
     *
     * @param line one line from the save file, e.g. {@code "T | 1 | read book"}.
     * @return the parsed task, with its done status applied.
     * @throws yapBotException if the line is not in a recognized format.
     */
    private static Task parseTask(String line) throws yapBotException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new yapBotException("Save-file line has too few fields: " + line);
        }

        String type = parts[0].trim();
        String statusValue = parts[1].trim();
        String description = parts[2].trim();
        Task task;

        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new yapBotException("Deadline line is missing the 'by' field: " + line);
                }
                task = new Deadline(description, parts[3].trim());
                break;
            case "E":
                if (parts.length < 5) {
                    throw new yapBotException("Event line is missing 'from'/'to' fields: " + line);
                }
                task = new Event(description, parts[3].trim(), parts[4].trim());
                break;
            default:
                throw new yapBotException("Unknown task type '" + type + "' in save file.");
        }

        if (statusValue.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}