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

            try {

                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                } else if (command.startsWith("mark")) {
                    int taskIndex = parseTaskIndex(command, "mark", taskCount);
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                } else if (command.startsWith("unmark")) {
                    int taskIndex = parseTaskIndex(command, "unmark", taskCount);
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                } else if (command.startsWith("delete")) {
                    int taskIndex = parseTaskIndex(command, "delete", taskCount);
                    Task removedTask = tasks[taskIndex];
                    for (int i = taskIndex; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                    tasks[taskCount - 1] = null;
                    taskCount--;
                    System.out.println("I have removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else if (command.startsWith("todo")) {
                    checkSpaceIsFull(taskCount);
                    String description = command.substring(4).trim();
                    if (description.isEmpty()) {
                        throw new yapBotException("The description of a todo cannot be empty. "
                                + "Usage: todo <description>");
                    }
                    Task task = new Todo(description);
                    tasks[taskCount] = task;
                    taskCount++;
                    printAddTaskResponse(task, taskCount);
                } else if (command.startsWith("deadline")) {
                    checkSpaceIsFull(taskCount);
                    String details = command.length() > 8 ? command.substring(8).trim() : "";
                    if (details.isEmpty()) {
                        throw new yapBotException("The description of a deadline cannot be empty. "
                                + "Usage: deadline <description> /by <date>");
                    }
                    String[] parts = details.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new yapBotException("A deadline needs both a description and a '/by' date. "
                                + "Usage: deadline <description> /by <date>");
                    }
                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks[taskCount] = task;
                    taskCount++;
                    printAddTaskResponse(task, taskCount);
                } else if (command.startsWith("event")) {
                    checkSpaceIsFull(taskCount);
                    String details = command.length() > 5 ? command.substring(5).trim() : "";
                    if (details.isEmpty()) {
                        throw new yapBotException("The description of an event cannot be empty. "
                                + "Usage: event <description> /from <start> /to <end>");
                    }
                    String[] parts = details.split(" /from | /to ");
                    if (parts.length < 3 || parts[0].trim().isEmpty()
                            || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                        throw new yapBotException("An event needs a description, a '/from' time and a '/to' time. "
                                + "Usage: event <description> /from <start> /to <end>");

                    }
                    Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    tasks[taskCount] = task;
                    taskCount++;
                    printAddTaskResponse(task, taskCount);
                } else if (command.isBlank()) {
                    throw new yapBotException("You didn't type anything. Try 'todo', 'deadline', "
                            + "'event', 'list', 'mark', 'unmark', 'delete' or 'bye'.");
                } else {
                    throw new yapBotException("I'm sorry, but I don't know what that means. "
                            + "Try 'todo', 'deadline', 'event', 'list', 'mark', 'unmark', 'delete' or 'bye'.");
                }
            } catch (yapBotException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                // Safety net for unanticipated bad input
                System.out.println("Oops, something went wrong: " + e.getMessage());
            }
        }
    }

// Parses the task number out of a "mark"/"unmark" command and validates it against the current task list.
    private static int parseTaskIndex(String command, String keyword, int taskCount) throws yapBotException {
        String argument = command.length() > keyword.length()
                ? command.substring(keyword.length()).trim()
                : "";
        if (argument.isEmpty()) {
            throw new yapBotException("Please specify a task number, e.g. '" + keyword + " 2'.");
        }

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e) {
            throw new yapBotException("'" + argument + "' is not a valid task number.");
        }

        if (taskCount == 0) {
            throw new yapBotException("Your task list is empty, so there's nothing to " + keyword + ".");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new yapBotException("Task number " + (taskIndex + 1) + " doesn't exist. "
                    + "You have " + taskCount + " task(s).");
        }
        return taskIndex;
    }

    //checks if space before adding into task list
    private static void checkSpaceIsFull(int taskCount) throws yapBotException {
        if (taskCount >= 100) {
            throw new yapBotException("Sorry, your task list is full (max 100 tasks).");
        }
    }

    // Helper method to print the response when a new task is added
    private static void printAddTaskResponse(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

}
