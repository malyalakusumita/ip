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
Hello! I'm yapBot.
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
Hello! I'm yapBot.
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
Hello! I'm yapBot.
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
Hello! I'm yapBot.
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
deadline return book /by Sunday
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
Hello! I'm yapBot.
What can I do for you?
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
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
Hello! I'm yapBot.
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
Hello! I'm yapBot.
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
Hello! I'm yapBot.
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
Hello! I'm yapBot.
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
deadline return book /by June 6th
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
Hello! I'm yapBot.
What can I do for you?
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
Nice! I've marked this task as done:
  [T][X] read book
Got it. I've added this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
Nice! I've marked this task as done:
  [D][X] return book (by: June 6th)
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
2.[D][X] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
4.[T][X] join sports club
5.[T][ ] borrow book
I have removed this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 4 tasks in the list.
Here are the tasks in your list:
1.[T][X] read book
2.[D][X] return book (by: June 6th)
3.[T][X] join sports club
4.[T][ ] borrow book
Bye. Hope to see you again soon!
~~~

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
Hello! I'm yapBot.
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
