# Chocolate UI test plan

Run each independent test from a clean temporary working directory so saved data from one test
does not affect another test.

## Test: Save tasks and restore them in a later session

Aim: Verify task types and completion status are saved automatically and loaded at startup.

### First-session input
```text
todo read book
deadline return book /by Sunday
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
2.[D][X] return book (by: Sunday)
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
deadline return book /by Sunday
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
  [D][ ] return book (by: Sunday)
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
2.[D][ ] return book (by: Sunday)
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
deadline return book /by Sunday
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
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
**************************************
**************************************
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
**************************************
**************************************
Got it. I have removed the task:
  [D][ ] return book (by: Sunday)
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
