package main.logic;

import java.util.ArrayList;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Exit implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Exit(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		storage.saveFile();
	}

	@Override
	public void undo() {
		// Not needed
		
	}

	@Override
	public void redo() {
		// Not needed
		
	}

}
