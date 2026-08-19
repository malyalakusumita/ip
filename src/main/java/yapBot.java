import java.util.Scanner;

public class yapBot {
    public static void main(String[] args) {
        String banner = """
                __   __  ___   ____  ____   ___ _____
                \\ \\ / / / _ \\ |  _ \\| __ ) / _ \\_   _|
                 \\ V / | |_| || |_) |  _ \\| |_| || |
                  |_|   \\___/ |____/|___/ \\___/ |_|
                """;
        System.out.print(banner);
        System.out.println("Hello! I'm yapBot.");
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (command.startsWith("todo ")) {
                String description = command.substring(5).trim();
                Task task = new Todo(description);
                tasks[taskCount] = task;
                taskCount++;
                printAddTaskResponse(task, taskCount);
            } else if (command.startsWith("deadline ")) {
                String details = command.substring(9);
                String[] parts = details.split(" /by ");
                Task task = new Deadline(parts[0].trim(), parts[1].trim());
                tasks[taskCount] = task;
                taskCount++;
                printAddTaskResponse(task, taskCount);
            } else if (command.startsWith("event ")) {
                String details = command.substring(6);
                String[] parts = details.split(" /from | /to ");
                Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                tasks[taskCount] = task;
                taskCount++;
                printAddTaskResponse(task, taskCount);
            } else {
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("added: " + command);
            }
        }
    }

    // Helper method to print the response when a new task is added
    private static void printAddTaskResponse(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }
}
