# Chocolate UI test plan

Run each independent test from a clean temporary working directory so saved data from one test
does not affect another test.
Run the packaged application using the `chocolate.Chocolate` main class.

## Test: Find tasks by description keyword

Aim: Verify find displays matching tasks only and matches keywords without case sensitivity.

### Input
```text
todo Read Book
deadline return book /by 2019-10-15
event project meeting /from Mon 2pm /to 4pm
find BOOK
find missing
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Got it. I've added this task:
  [T][ ] Read Book
Now you have 1 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019)
Now you have 2 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
**************************************
**************************************
Here are the matching tasks in your list:
1.[T][ ] Read Book
2.[D][ ] return book (by: Oct 15 2019)
**************************************
**************************************
Here are the matching tasks in your list:
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Archive all active tasks and list archived tasks

Aim: Verify archive moves every active task out of the list, preserves task details, and rejects unsupported archive arguments.

### Input
```text
archive
todo buy chocolate
deadline submit report /by 2019-10-15
mark 2
archive
list
archive list
archive all
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
There are no tasks to archive.
**************************************
**************************************
Got it. I've added this task:
  [T][ ] buy chocolate
Now you have 1 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [D][ ] submit report (by: Oct 15 2019)
Now you have 2 tasks in the list.
**************************************
**************************************
Well Done! I have marked this task as done:
  [X] submit report
**************************************
**************************************
Archived 2 task(s). Your active task list is now empty.
**************************************
**************************************
Here are the tasks in your list:
**************************************
**************************************
Here are your archived tasks:
1.[T][ ] buy chocolate
2.[D][X] submit report (by: Oct 15 2019)
**************************************
**************************************
Oops! Please use: archive or archive list.
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Reject malformed commands without terminating

Aim: Verify Parser reports missing command details and Chocolate continues accepting commands.

### Input
```text
mark
delete abc
deadline return book
event meeting /from Monday
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Oops! Please provide a task number!
**************************************
**************************************
Oops! Please provide a whole number for the task number!
**************************************
**************************************
Oops! Please use: deadline DESCRIPTION /by yyyy-MM-dd.
**************************************
**************************************
Oops! Please use: event DESCRIPTION /from START /to END.
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Reject invalid deadline dates

Aim: Verify malformed and impossible dates show an error and do not add tasks.

### Input
```text
deadline return book /by 15-10-2019
deadline submit report /by 2019-02-30
list
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Oops! Please use the date format yyyy-MM-dd.
**************************************
**************************************
Oops! Please use the date format yyyy-MM-dd.
**************************************
**************************************
Here are the tasks in your list:
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Save tasks and restore them in a later session

Aim: Verify task types and completion status are saved automatically and loaded at startup.

### First-session input
```text
todo read book
deadline return book /by 2019-10-15
mark 2
bye
```

### Second-session input
```text
list
bye
```

### Expected second-session output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Here are the tasks in your list:
1.[T][ ] read book
2.[D][X] return book (by: Oct 15 2019)
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Add, list, mark, and unmark each task type

Aim: Verify Todo, Deadline, and Event commands preserve their type and done status.

### Input
```text
todo borrow book
deadline return book /by 2019-10-15
event project meeting /from Mon 2pm /to 4pm
list
mark 2
unmark 2
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019)
Now you have 2 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
**************************************
**************************************
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Oct 15 2019)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
**************************************
**************************************
Well Done! I have marked this task as done:
  [X] return book
**************************************
**************************************
Alright, I have marked this task as not done yet:
  [ ] return book
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Reject an empty todo without changing the task list

Aim: Verify empty todo commands show an error and do not add a task.

### Input
```text
todo
list
todo read book
list
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Oops! Please provide a valid description after the command you used!
**************************************
**************************************
Here are the tasks in your list:
**************************************
**************************************
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
**************************************
**************************************
Here are the tasks in your list:
1.[T][ ] read book
**************************************
**************************************
Thank you and see you again
**************************************
```

## Test: Delete a task without changing the list after an invalid deletion

Aim: Verify deletion removes the chosen task and invalid deletion leaves the list unchanged.

### Input
```text
todo read book
deadline return book /by 2019-10-15
event project meeting /from Mon 2pm /to 4pm
delete 2
list
delete 3
list
bye
```

### Expected output
```text
**************************************
Chocolate
Hi, my name is Chocolate!
How may I help you today?
**************************************
**************************************
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019)
Now you have 2 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
**************************************
**************************************
Got it. I have removed the task:
  [D][ ] return book (by: Oct 15 2019)
You now have 2 tasks left in your list!
**************************************
**************************************
Here are the tasks in your list:
1.[T][ ] read book
2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
**************************************
**************************************
Oops! That task number does not exist in your list!
**************************************
**************************************
Here are the tasks in your list:
1.[T][ ] read book
2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
**************************************
**************************************
Thank you and see you again
**************************************
```
