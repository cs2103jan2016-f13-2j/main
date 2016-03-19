package main.logic;

import java.util.ArrayList;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Edit implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Edit(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		taskList = storage.getTaskList();
		Task taskToEdit = taskList.get(userInput.getEditNumber()[0]-1);
		switch (userInput.getEditNumber()[1]) {
		case 1: {	//task detail
			taskToEdit.setTaskDetails(userInput.getDetails());
			break;
		}
		case 6: {	//task date
			taskToEdit.setTaskDate(userInput.getDate());
			break;
		}
		case 7: {	//task time
			taskToEdit.setTaskTime(userInput.getTime());
			break;
		}
		case 8: {	//task location
			taskToEdit.setTaskLocation(userInput.getLocation());
			break;
		}
		case 9: {
			taskToEdit.setPriority(userInput.getPriority());
			break;
		}
		default: {
			//Do something
		}
		}
		
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
