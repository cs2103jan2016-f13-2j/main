package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Complete implements Command {

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
		logger.log(Level.INFO, "Command COMPLETE");
		taskList = storage.getTaskList();
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task taskToMark = userInput.getTasksToDelete().get(i);
			for (Task t: taskList) {
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
	}

}
