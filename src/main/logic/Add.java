package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Add implements Command {
	
	UserInput userInput;
	Storage storage;
	Feedback feedback;
	ArrayList<Task> taskList;
	static Logger logger = Logger.getLogger("Add");

	public Add(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command ADD");
		taskList = storage.getTaskList();
		taskList.add(userInput.getTask());
		storage.saveFile();
	}

	@Override
	public void undo() {
		taskList = storage.getTaskList();
		taskList.remove(userInput.getTask());
		storage.saveFile();
		
	}

	@Override
	public void redo() {
		taskList = storage.getTaskList();
		taskList.add(userInput.getTask());
		storage.saveFile();		
	}

}
