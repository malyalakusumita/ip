# UI test plan

The expected output in each case is exact; the runner normalizes only line endings.

## Test case: Add tasks and list them
- Aim: Verify that todo adds tasks and that list shows them as incomplete.

### Input
~~~text
todo read book
todo return book
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
Got it. I've added this task:
  [T][ ] return book
Now you have 2 tasks in the list.
Here are the tasks in your list:
1.[T][ ] read book
2.[T][ ] return book
Bye. Hope to see you again soon!
~~~

## Test case: Unrecognized command does not corrupt the task list
- Aim: Verify that a command matching no known keyword is rejected with an error, and that the task list is unaffected.

### Input
~~~text
todo read book
read book
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
I'm sorry, but I don't know what that means. Try 'todo', 'deadline', 'event', 'list', 'mark', 'unmark', 'delete' or 'bye'.
Here are the tasks in your list:
1.[T][ ] read book
Bye. Hope to see you again soon!
~~~

## Test case: Mark and unmark a task
- Aim: Verify that mark marks a task done, unmark reverses it, and list shows the final status.

### Input
~~~text
todo read book
mark 1
unmark 1
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
Nice! I've marked this task as done:
  [T][X] read book
I've marked this task as not done yet:
  [T][ ] read book
Here are the tasks in your list:
1.[T][ ] read book
Bye. Hope to see you again soon!
~~~

## Test case: mark/unmark with invalid input
- Aim: Verify bad mark/unmark input (empty list, non-numeric, out-of-range, zero) is rejected without crashing, and a later valid mark still works.

### Input
~~~text
mark 1
unmark 1
todo read book
mark abc
mark 99
mark 0
unmark abc
mark 1
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Your task list is empty, so there's nothing to mark.
Your task list is empty, so there's nothing to unmark.
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
'abc' is not a valid task number.
Task number 99 doesn't exist. You have 1 task(s).
Task number 0 doesn't exist. You have 1 task(s).
'abc' is not a valid task number.
Nice! I've marked this task as done:
  [T][X] read book
Here are the tasks in your list:
1.[T][X] read book
Bye. Hope to see you again soon!
~~~

## Test case: Todo, Deadline, and Event tasks
- Aim: Verify that task types (todo, deadline, event) are correctly created, formatted, and listed.

### Input
~~~text
todo borrow book
deadline return book /by 2019-10-15
event project meeting /from Mon 2pm /to 4pm
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019)
Now you have 2 tasks in the list.
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Oct 15 2019)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
Bye. Hope to see you again soon!
~~~

## Test case: Empty descriptions for todo/deadline/event
- Aim: Verify each task type rejects an empty description, both for the bare keyword and the keyword with only trailing whitespace after it.

### Input
~~~text
//input not the comment command TODO 
todo

//with trailing whitespace 
todo     

deadline

//with trailing whitespace
deadline   

event

//with trailing whitespace 
event  
 
list

bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
The description of a todo cannot be empty. Usage: todo <description>
The description of a todo cannot be empty. Usage: todo <description>
The description of a deadline cannot be empty. Usage: deadline <description> /by <date>
The description of a deadline cannot be empty. Usage: deadline <description> /by <date>
The description of an event cannot be empty. Usage: event <description> /from <start> /to <end>
The description of an event cannot be empty. Usage: event <description> /from <start> /to <end>
Here are the tasks in your list:
Bye. Hope to see you again soon!
~~~

## Test case: Deadline/event missing required parts
- Aim: Verify a deadline without /by, and an event missing /from, missing /to, or missing both, are all rejected without adding a task.

### Input
~~~text
deadline return book
deadline return book /by
event meeting /from Mon
event meeting /to 4pm
event meeting
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
A deadline needs both a description and a '/by' date. Usage: deadline <description> /by <date>
A deadline needs both a description and a '/by' date. Usage: deadline <description> /by <date>
An event needs a description, a '/from' time and a '/to' time. Usage: event <description> /from <start> /to <end>
An event needs a description, a '/from' time and a '/to' time. Usage: event <description> /from <start> /to <end>
An event needs a description, a '/from' time and a '/to' time. Usage: event <description> /from <start> /to <end>
Here are the tasks in your list:
Bye. Hope to see you again soon!
~~~

## Test case: Blank input line
- Aim: Verify pressing Enter with nothing typed is rejected rather than silently ignored or added as a task.

### Input
~~~text
todo read book

list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
You didn't type anything. Try 'todo', 'deadline', 'event', 'list', 'mark', 'unmark', 'delete' or 'bye'.
Here are the tasks in your list:
1.[T][ ] read book
Bye. Hope to see you again soon!
~~~

## Test case: Interleaved valid and invalid commands
- Aim: Verify a long sequence mixing valid adds, valid mark/unmark, and several kinds of invalid input never corrupts internal state.

### Input
~~~text
todo task A
blah
todo task B
mark 99
deadline
mark 1
unmark 3
todo
event x /from a /to b
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] task A
Now you have 1 tasks in the list.
I'm sorry, but I don't know what that means. Try 'todo', 'deadline', 'event', 'list', 'mark', 'unmark', 'delete' or 'bye'.
Got it. I've added this task:
  [T][ ] task B
Now you have 2 tasks in the list.
Task number 99 doesn't exist. You have 2 task(s).
The description of a deadline cannot be empty. Usage: deadline <description> /by <date>
Nice! I've marked this task as done:
  [T][X] task A
Task number 3 doesn't exist. You have 2 task(s).
The description of a todo cannot be empty. Usage: todo <description>
Got it. I've added this task:
  [E][ ] x (from: a to: b)
Now you have 3 tasks in the list.
Here are the tasks in your list:
1.[T][X] task A
2.[T][ ] task B
3.[E][ ] x (from: a to: b)
Bye. Hope to see you again soon!
~~~

## Test case: Delete a task
- Aim: Verify that delete removes the correct task, shifts the remaining tasks up, and updates the count.

### Input
~~~text
todo read book
mark 1
deadline return book /by 2019-06-06
mark 2
event project meeting /from Aug 6th 2pm /to 4pm
todo join sports club
mark 4
todo borrow book
list
delete 3
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
Nice! I've marked this task as done:
  [T][X] read book
Got it. I've added this task:
  [D][ ] return book (by: Jun 06 2019)
Now you have 2 tasks in the list.
Nice! I've marked this task as done:
  [D][X] return book (by: Jun 06 2019)
Got it. I've added this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 3 tasks in the list.
Got it. I've added this task:
  [T][ ] join sports club
Now you have 4 tasks in the list.
Nice! I've marked this task as done:
  [T][X] join sports club
Got it. I've added this task:
  [T][ ] borrow book
Now you have 5 tasks in the list.
Here are the tasks in your list:
1.[T][X] read book
2.[D][X] return book (by: Jun 06 2019)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
4.[T][X] join sports club
5.[T][ ] borrow book
I have removed this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 4 tasks in the list.
Here are the tasks in your list:
1.[T][X] read book
2.[D][X] return book (by: Jun 06 2019)
3.[T][X] join sports club
4.[T][ ] borrow book
Bye. Hope to see you again soon!
~~~

## Test case: Tasks are saved to disk after each change
- Aim: Verify that every task-list mutation (add, mark, unmark, delete) writes
  the current task list to `./data/yapBot.txt` in the pipe-delimited format,
  and that the console output is unaffected by this write.
- Note: this test case cannot be captured purely by the stdout diff that the
  other cases in this file use, since `Storage.save()` produces no output on
  success. It needs a filesystem check in addition to (or instead of) a
  stdout diff. If `runtest.sh`/`runtest.bat` only compares `ACTUAL.TXT`
  against `EXPECTED.TXT`, this case should be run and checked manually until
  the runner script is extended to also diff a saved-file fixture.
- Also note: every *other* test case in this file (below and above) assumes
  a clean start, i.e. no pre-existing `./data/yapBot.txt`. Since loading is
  now implemented, any leftover save file from a previous manual run will
  change what these tests print. Delete `./data/yapBot.txt` before running
  any of the other cases, or run them in a working directory where it does
  not yet exist.

### Input
~~~text
todo read book
deadline return book /by 2019-06-06
event project meeting /from Aug 6th 2pm /to 4pm
mark 1
delete 2
bye
~~~

### Expected console output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
Got it. I've added this task:
  [D][ ] return book (by: Jun 06 2019)
Now you have 2 tasks in the list.
Got it. I've added this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 3 tasks in the list.
Nice! I've marked this task as done:
  [T][X] read book
I have removed this task:
  [D][ ] return book (by: Jun 06 2019)
Now you have 2 tasks in the list.
Bye. Hope to see you again soon!
~~~

### Expected contents of `./data/yapBot.txt` after the run
~~~text
T | 1 | read book
E | 0 | project meeting | Aug 6th 2pm | 4pm
~~~

### Manual verification steps
1. Delete any existing `./data/yapBot.txt` before running, so the test starts clean.
2. Run the program with the input above (e.g. pipe it in, or type it interactively).
3. Confirm the console output matches the block above.
4. Open `./data/yapBot.txt` and confirm its contents match the block above exactly
   (two lines, in this order, reflecting the mark and the delete).

## Test case: Load previously saved tasks on startup
- Aim: Verify that when `./data/yapBot.txt` already contains saved tasks,
  the chatbot loads them into the list on startup — including each task's
  type, description, extra fields (by / from / to), and done status.
- Setup: before running the input below, create `./data/yapBot.txt` with
  exactly the following contents (this simulates a save file left over
  from a previous session):
~~~text
T | 1 | read book
D | 0 | return book | 2019-06-06
E | 0 | project meeting | Aug 6th 2pm | 4pm
~~~

### Input
~~~text
list
bye
~~~

### Expected console output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: Jun 06 2019)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Bye. Hope to see you again soon!
~~~

### Manual verification steps
1. Manually create `./data/yapBot.txt` with the three lines shown above
   (do not run the program to generate it, so this test is independent of
   the save test case above).
2. Run the program with `list` then `bye` as input.
3. Confirm the printed task list shows task 1 as done (`[X]`) and tasks 2–3
   as not done (`[ ]`), with the correct type icons and extra fields.
4. This test case also cannot be captured purely by the stdout diff runner
   without a setup step, since it depends on a pre-existing file on disk
   rather than on program input alone.

## Test case: Starting fresh with no save file
- Aim: Verify that when `./data/yapBot.txt` does not exist (e.g. first run
  ever, or a fresh checkout), the chatbot starts with an empty task list
  instead of crashing or printing an error.

### Setup
- Ensure `./data/yapBot.txt` does not exist before running (delete it if present).

### Input
~~~text
list
bye
~~~

### Expected console output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Here are the tasks in your list:
Bye. Hope to see you again soon!
~~~

## Test case: Task fields cannot contain '|'
- Aim: Verify that a task field containing the '|' character is rejected at
  input time, since it is the save-file delimiter, instead of being silently
  accepted and corrupting the save file on the next write.

### Input
~~~text
todo read | book
deadline return book /by June | 6th
event x /from a | b /to c
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
The description of a todo cannot contain the '|' character, as it is reserved for the save file format.
The '/by' date of a deadline cannot contain the '|' character, as it is reserved for the save file format.
The '/from' time of an event cannot contain the '|' character, as it is reserved for the save file format.
Here are the tasks in your list:
Bye. Hope to see you again soon!
~~~

## Test case: Corrupted save-file lines are skipped, not crashed on
- Aim: Verify that a save file containing bad lines (wrong field count,
  invalid status, unknown type, blank required field) loads whatever valid
  tasks it can, skips the rest with a warning, and does not crash the
  program on startup.
- Setup: before running, create `./data/yapBot.txt` with exactly the
  following contents:
~~~text
T | 1 | read book
X | 0 | unknown type
T | 2 | bad status value
D | 0 | missing by field
T | 0 |
E | 0 | ok event | Aug 6th 2pm | 4pm
~~~

### Input
~~~text
list
bye
~~~

### Expected console output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Here are the tasks in your list:
1.[T][X] read book
2.[E][ ] ok event (from: Aug 6th 2pm to: 4pm)
Bye. Hope to see you again soon!
~~~
- Note: the exact wording of the "Warning: skipping..." lines that also get
  printed during load is not asserted here, since it is diagnostic text
  aimed at the developer rather than a stable interface. What matters is
  that only the two well-formed lines are loaded (in order, with the
  correct type/description/status/extra fields), and the program does not
  crash. Diff the ACTUAL output against EXPECTED with this in mind.

## Test case: Save file with more tasks than the array can hold
- Aim: Verify that a save file with more entries than the task array's
  capacity (100) loads only the first 100 and warns about the rest, rather
  than crashing with an out-of-bounds error.
- Setup: generate a `./data/yapBot.txt` with 105 valid `T | 0 | task N`
  lines (e.g. via a small script), then run `list` followed by `bye`.
- Expected: exactly 100 tasks are listed (numbered 1 to 100, matching the
  first 100 lines of the file in order); the program does not crash; a
  warning about the remaining 5 tasks is printed to the console (exact
  wording not asserted, per the note above).

## Test case: delete with invalid input
- Aim: Verify delete on an empty list, with no number, and with a non-numeric or out-of-range number, is rejected without crashing, and a later valid delete still works.

### Input
~~~text
delete
delete abc
delete 99
todo x
delete 1
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Please specify a task number, e.g. 'delete 2'.
'abc' is not a valid task number.
Your task list is empty, so there's nothing to delete.
Got it. I've added this task:
  [T][ ] x
Now you have 1 tasks in the list.
I have removed this task:
  [T][ ] x
Now you have 0 tasks in the list.
Here are the tasks in your list:
Bye. Hope to see you again soon!
~~~

## Test case: Deadline date is parsed and reformatted for display
- Aim: Verify that a deadline's '/by' date, entered in yyyy-mm-dd format,
  is stored as a real date and displayed in "MMM dd yyyy" format,
  including a case where the day has no leading zero in the input.

### Input
~~~text
deadline pay bills /by 2019-01-05
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Got it. I've added this task:
  [D][ ] pay bills (by: Jan 05 2019)
Now you have 1 tasks in the list.
Here are the tasks in your list:
1.[D][ ] pay bills (by: Jan 05 2019)
Bye. Hope to see you again soon!
~~~

## Test case: Invalid deadline date format is rejected
- Aim: Verify that a deadline date not in yyyy-mm-dd format is rejected
  with a clear error, and no task is added.

### Input
~~~text
deadline pay bills /by June 6th
deadline pay bills /by 6/6/2019
deadline pay bills /by 2019-13-01
list
bye
~~~

### Expected output
~~~text
__   __  ___   ____  ____   ___ _____
\ \ / / / _ \ |  _ \| __ ) / _ \_   _|
 \ V / | |_| || |_) |  _ \| |_| || |
  |_|   \___/ |____/|___/ \___/ |_|
Hello! I'm YAPBOT.
What can I do for you?
Please enter the deadline date in yyyy-mm-dd format, e.g. 2019-10-15.
Please enter the deadline date in yyyy-mm-dd format, e.g. 2019-10-15.
Please enter the deadline date in yyyy-mm-dd format, e.g. 2019-10-15.
Here are the tasks in your list:
Bye. Hope to see you again soon!
~~~
- Note: 2019-13-01 has a valid yyyy-mm-dd shape but an invalid month (13),
  so LocalDate.parse() rejects it the same way as a malformed string.
