# Chocolate UI test plan

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
Oops! A todo needs a description. Example: todo borrow book
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
