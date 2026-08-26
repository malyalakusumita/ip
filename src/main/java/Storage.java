import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles saving tasks to the hard disk.
 * Loading from disk is not yet implemented (happy-path write only).
 */
public class Storage {

    private static final String FILE_PATH = "./data/yapBot.txt";

    /**
     * Writes the given tasks to the save file, creating the parent directory
     * if it does not already exist. Any I/O failure is reported but does not
     * crash the program.
     *
     * @param tasks     the current task array
     * @param taskCount the number of active tasks in the array
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
}