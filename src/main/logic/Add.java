package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Add implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;
	static Logger logger = Logger.getLogger("Add");

	public Add(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
