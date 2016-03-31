package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Delete implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;
	static Logger logger = Logger.getLogger("Delete");

	public Delete(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command DELETE");
		int count = 0;
		taskList = storage.getTaskList();
		for (int i=0; i<taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getTaskType() == userInput.getDeleteType()) {
				count++;
				 if(count == userInput.getDeleteNumber()) {
					 userInput.setTask(taskList.get(i));
					 taskList.remove(i);
				 }
			}
		}
		storage.saveFile();
	}

	@Override
	public void undo() {
		taskList = storage.getTaskList();
		taskList.add(userInput.getTask());
		storage.saveFile();
	}

	@Override
	public void redo() {
		taskList = storage.getTaskList();
		taskList.remove(userInput.getTask());
		storage.saveFile();
	}
}
