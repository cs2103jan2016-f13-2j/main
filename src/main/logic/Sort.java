package main.logic;

import java.util.ArrayList;

import main.gui.resources.Task;
import main.gui.resources.UserInput;
import main.storage.Storage;

public class Sort implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Sort(UserInput userInput) {
		this.userInput = userInput;
		storage = new Storage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
	
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
