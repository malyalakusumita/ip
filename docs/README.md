# YapBot User Guide

![YapBot user interface](Ui.png)

YapBot is a personal task-management chatbot for recording todos, deadlines,
events, priorities, and completed work. It is available through a JavaFX
graphical interface and a console interface.

## Getting started

YapBot requires **JDK 25**. In IntelliJ IDEA, open the project and run
`yapbot.gui.Launcher` for the graphical interface or `yapbot.YapBot` for the
console interface. In the GUI, enter commands in the text box. In the console,
type a command and press Enter.

To build and run the packaged application on Windows:

```text
gradlew.bat clean shadowJar
java -jar build/libs/yapBot.jar
```

## Command summary

| Command | Purpose |
| --- | --- |
| `todo <description>` | Add a simple task |
| `deadline <description> /by <date>` | Add a task with a due date |
| `event <description> /from <start> /to <end>` | Add an event |
| `list` | Show all tasks |
| `find <keyword>` | Search task descriptions |
| `mark <number>` | Mark a task as done |
| `unmark <number>` | Mark a task as not done |
| `priority <number> <level>` | Change a task's priority |
| `delete <number>` | Delete a task |
| `bye` | Exit YapBot |

Task numbers are the numbers shown by `list` and start at 1.

## Adding tasks

Add a normal todo:

```text
todo read chapter 3
```

Add a deadline. Dates must use `yyyy-mm-dd` format:

```text
deadline submit assignment /by 2026-10-15
```

Add an event with a start and end time:

```text
event project meeting /from Monday 2pm /to Monday 3pm
```

Event times may be natural text. If both values are dates in `yyyy-mm-dd`
format, YapBot checks that the start is earlier than the end.

## Priorities

YapBot supports `high`, `medium`, and `low`. Set a priority while adding a
task by placing `/priority <level>` at the end of the command:

```text
todo revise lecture notes /priority high
deadline pay bill /by 2026-09-30 /priority medium
event dentist appointment /from Friday 10am /to Friday 11am /priority low
```

For deadlines and events, `/priority` must come after `/by`, `/from`, and
`/to`. To change an existing task's priority, use its number:

```text
priority 1 high
```

## Viewing and searching

Display every task with:

```text
list
```

The list shows each task's number, type, completion status, description,
deadline or event details, and priority when set.

Search descriptions without changing the task list:

```text
find assignment
```

## Managing tasks

Mark task 2 as completed:

```text
mark 2
```

Return task 2 to an incomplete state:

```text
unmark 2
```

Remove task 3:

```text
delete 3
```

Use `list` before changing or deleting a task if you are unsure of its number.

## Saving data

YapBot automatically saves tasks to `data/yapBot.txt` and loads them when it
starts. Todos, deadlines, events, priorities, and completion statuses persist
between sessions. When using the JAR, the `data` folder is created relative to
the folder from which the JAR is run.

## Input rules and troubleshooting

- Enter one complete command per line.
- Todos, deadlines, and events require descriptions.
- A deadline requires exactly one `/by` date.
- An event requires exactly one `/from` value and one `/to` value.
- Priority must be `high`, `medium`, or `low`.
- Do not use `|` in task details because it is reserved by the save format.
- If a command is incomplete or unknown, YapBot displays an error and a usage
  hint.
- If a task number is invalid, run `list` and use a current number.
- If tasks do not reappear after restarting, check that `data/yapBot.txt` is
  being read from the expected working folder.

## Exiting

End the session with:

```text
bye
```

YapBot waits a short duration and then closes the session, 
while saved tasks remain in `data/yapBot.txt`.
