package main.logic;

import java.util.ArrayList;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Search implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Search(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		if (userInput.getSearchTerm().length() == 0) {
			userInput.setTaskList(storage.getTaskList());
		}
		
		else {
			ArrayList<Task> searchResults = new ArrayList<Task>();
			for (Task t : storage.getTaskList()) {
				if ((t.getTaskName()).contains(userInput.getSearchTerm()) ||
						t.getTaskDetails().contains(userInput.getSearchTerm())) {
					searchResults.add(t);
				}
			}
			MainLogic.setDisplayList(searchResults);
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
