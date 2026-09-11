# yapBot User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Setting a task's priority

Tag a task with how urgent it is: `high`, `medium`, or `low`. A task has
no priority until you set one.

**Set a priority when you add a task**, by adding `/priority <level>` as
the last part of the command:

Example: `todo read book /priority high`

```
Got it. I've added this task:
  [T][ ][HIGH]read book
Now you have 1 tasks in the list.
```

This works the same way for `deadline` and `event`, with `/priority`
always coming after their own `/by`/`/from`/`/to` parts, e.g.
`deadline return book /by 2019-10-15 /priority medium`.

**Change an existing task's priority** (or set one for the first time)
with `priority <task number> <level>`:

Example: `priority 1 high`

```
Nice! I've updated this task's priority:
  [T][ ][HIGH]read book
```

`<level>` must be `high`, `medium`, or `low` (any case). Leaving out
`/priority` when adding a task, or never running `priority` on it,
leaves that task with no priority — its listing looks exactly as it
did before this feature existed.


## Feature XYZ

// Feature details
