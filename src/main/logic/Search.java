package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L

public class Search implements Command {
	
	private static final String MSG_SUCCESS_SEARCH = "Searching for text containing \"%1$s\".";
	private static final String MSG_FAIL_NO_SEARCH_TERM = "Error: No search term entered.";
	
	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private static Logger logger = Logger.getLogger("Search");

	/**
	 * Constructs a Search command
	 * @param userInput: userInput instance from MainLogic
	 */
	public Search(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		new ArrayList<Task>();
	}

	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		logger.log(Level.INFO, "Command SEARCH");
		if (userInput.getSearchTerm().length() == 0) {
			userInput.setTaskList(storage.getTaskList());
			feedback.setMessage(MSG_FAIL_NO_SEARCH_TERM);
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
			feedback.setMessage(String.format(MSG_SUCCESS_SEARCH, userInput.getSearchTerm()));
		}
	}

	@Override
	public void undo() {
		
	}

	@Override
	public void redo() {
		
	}

}
