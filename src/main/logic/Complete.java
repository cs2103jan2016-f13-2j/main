package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Complete implements Command {

	//Feedback Strings
	private static final String MSG_SUCCESS_COMPLETE = "Task completed.";
	private static final String MSG_SUCCESS_UNCOMPLETE = "Task uncompleted.";
	private static final String MSG_SUCCESS_UNDO = "Undid previous command.";
	private static final String MSG_SUCCESS_REDO = "Redid previous command.";
	private static final String MSG_FAIL_TASK_COMPLETED = "Error: Task is already completed.";
	private static final String MSG_FAIL_TASK_UNCOMPLETED = "Error: Task is not completed.";
	private static final String MSG_FAIL_INDEX_OOB = "Error: The specified task could not be found.;";

	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;

	private static Logger logger = Logger.getLogger("Complete");
	private static boolean COMPLETE = true;

	public Complete(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}
	@Override
	public void execute() {
		boolean done = false;

		logger.log(Level.INFO, "Command COMPLETE");
		taskList = storage.getTaskList();
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task taskToMark = userInput.getTasksToDelete().get(i);
			for (Task t: taskList) {
				System.out.println("Task not found.");
				if (taskToMark.equals(t)) {
					System.out.println("Task found!");
					if (userInput.getComplete() == COMPLETE) {
						if (t.isComplete() == false) {
							t.setComplete(true);
							taskToMark.setComplete(true);
							feedback.setMessage(MSG_SUCCESS_COMPLETE);
							done = true;
						}
						else {
							feedback.setMessage(MSG_FAIL_TASK_COMPLETED);
							done = true;
						}
					}
					else {
						if (t.isComplete() == true) {
							t.setComplete(false);
							taskToMark.setComplete(false);
							feedback.setMessage(MSG_SUCCESS_UNCOMPLETE);
							done = true;
						}
						else {
							feedback.setMessage(MSG_FAIL_TASK_UNCOMPLETED);
							done = true;
						}
					}
				}
			}
			
			if (!done) {
				feedback.setMessage(MSG_FAIL_INDEX_OOB);
			}
		}
	}

	@Override
	public void undo() {
		logger.log(Level.INFO, "Command COMPLETE UNDO");
		taskList = storage.getTaskList();
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task taskToMark = userInput.getTasksToDelete().get(i);
			for (Task t: taskList) {
				if (taskToMark.equals(t)) {
					if (userInput.getComplete() == COMPLETE) {
						t.setComplete(false);
						taskToMark.setComplete(false);
					}

					else {
						t.setComplete(true);
						taskToMark.setComplete(true);
					}
				}
			}
		}
		
		feedback.setMessage(MSG_SUCCESS_UNDO);

	}

	@Override
	public void redo() {
		logger.log(Level.INFO, "Command COMPLETE REDO");
		taskList = storage.getTaskList();
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task taskToMark = userInput.getTasksToDelete().get(i);				for (Task t: taskList) {
				if (taskToMark.equals(t)) {
					if (userInput.getComplete() == COMPLETE) {
						t.setComplete(true);
						taskToMark.setComplete(true);
					}

					else {
						t.setComplete(false);
						taskToMark.setComplete(false);
					}
				}
			}
		}
storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_REDO);
	}

}
