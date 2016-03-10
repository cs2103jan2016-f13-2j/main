package main.logic;

import main.gui.model.UserInput;

import java.util.ArrayList;

import main.gui.model.Task;
import main.storage.Storage;

public class Add implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Add(UserInput userInput) {
		this.userInput = userInput;
		storage = new Storage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
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
