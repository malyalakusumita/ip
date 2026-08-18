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
        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
            }
        }
    }
}
