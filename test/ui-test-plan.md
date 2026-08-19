# UI test plan

The expected output in each case is exact; the runner normalizes only line endings.

## Test case: Add tasks and list them
- Aim: Verify that ordinary input adds tasks and that list shows them as incomplete.

### Input
~~~text
read book
return book
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
added: read book
added: return book
Here are the tasks in your list:
1.[ ] read book
2.[ ] return book
Bye. Hope to see you again soon!
~~~

## Test case: Mark and unmark a task
- Aim: Verify that mark marks a task done, unmark reverses it, and list shows the final status.

### Input
~~~text
read book
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
added: read book
Nice! I've marked this task as done:
  [X] read book
OK, I've marked this task as not done yet:
  [ ] read book
Here are the tasks in your list:
1.[ ] read book
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