package main.logic;

import java.util.ArrayList;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Delete implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Delete(UserInput userInput) {
		this.userInput = userInput;
		storage = new Storage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		taskList = storage.getTaskList();
		taskList.remove(userInput.getDeleteNumber()-1);
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
