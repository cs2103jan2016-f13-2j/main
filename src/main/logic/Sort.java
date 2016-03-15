package main.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Sort implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Sort(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		switch (userInput.getSortType()) {
		case 1: {	//Alphabetical
			taskList = storage.getTaskList();
			List<Task> list = new ArrayList<Task>();
			for (Task t : taskList) {
				list.add(t);
			}
			
			//Collections.sort(list);
			storage.saveFile();
			break;
		}
		case 2: {	//Date (Default)
			break;
		}
		case 3: {	//Priority
			break;
		}
		default: {
			break;
		}
		}
	
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
