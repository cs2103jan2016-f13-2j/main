package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Search implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;
	static Logger logger = Logger.getLogger("Search");

	public Search(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command SEARCH");
		if (userInput.getSearchTerm().length() == 0) {
			userInput.setTaskList(storage.getTaskList());
		}
		
		else {
			ArrayList<Task> searchResults = new ArrayList<Task>();
			String searchTerm = userInput.getSearchTerm().toLowerCase();
			for (Task t : storage.getTaskList()) {
				if (t.getTaskLocation() != null && t.getTaskLocation().toLowerCase().contains(searchTerm)) {
					searchResults.add(t);
				}
				
				else if (t.getTaskDetails() != null && t.getTaskDetails().toLowerCase().contains(searchTerm)) {
					searchResults.add(t);
				}
				
				else if (t.getTaskLocation() != null && t.getTaskLocation().toLowerCase().contains(searchTerm)) {
					searchResults.add(t);
				}
				
				else if (t.getTaskStartTime() != null && t.getTaskStartTime().getTimeString().contains(searchTerm)) {
					searchResults.add(t);
				}
				
				else if (t.getTaskEndTime() != null && t.getTaskEndTime().getTimeString().contains(searchTerm)) {
					searchResults.add(t);
				}
				
				else if (t.getTaskStartDate() != null && t.getTaskStartDate().getDateString().contains(searchTerm)) {
					searchResults.add(t);
				}
				
				else if (t.getTaskEndDate() != null && t.getTaskEndDate().getDateString().contains(searchTerm)) {
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
