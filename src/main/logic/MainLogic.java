package main.logic;

import java.util.*;

import main.storage.Storage;
import main.parser.Parser;
import main.resources.Task;
import main.resources.UserInput;
import main.parser.FlexiCommands;


public class MainLogic {

	//-----Variables-----

	//Running Program Variables
	Storage storage;
	ArrayList<Task> taskList;

	//Global Variables
	ArrayList<String> inputCommand;
	Command command;
	UserInput userInput;

	//-----Constructor-----
	public MainLogic(UserInput input) {
		//Initialize variables
		storage = new Storage();
		updateTaskList();
		userInput = input;
	}
	
	public void run() {
		runParser();
		createCommandObject();
		executeCommand();
		updateTaskList();
	}
	
	//-----Private methods-----
	private void createCommandObject() {
		switch (getCommand()) {
		case "add": {
			command = new Add(userInput);
			break;
		}
		case "delete": {
			command = new Delete(userInput);
			break;
		}
		case "edit": {
			command = new Edit(userInput);
			break;
		}

		case "display": {
			command = new Display(userInput);
			break;
		}
		
		case "sort": {
			command = new Sort(userInput);
			break;
		}
		
		case "search": {
			command = new Search(userInput);
			break;
		}
		
		case "exit": {
			command = new Exit(userInput);
			break;
		}
		default: {
			//return error
			break;
		}
		}
	}
	
	private void runParser() {
		userInput = Parser.resetUserInput(userInput);
	}
	
	private void executeCommand() {
		command.execute();
	}

	private void updateTaskList() {
		taskList = storage.getTaskList();
	}
	
	private String getCommand() {
		return FlexiCommands.flexiCommands(userInput.getCommand().toLowerCase());
	}
	

	//-----Public Methods-----
	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	/*
	private void sortTask() {
		Collections.sort(taskList, taskComparator);
		saveFile();
	}

	public static void searchTasks(String searchWords) {
		ArrayList<String> searchResults = new ArrayList<String>();
		for (Task t : taskList) {
			if ((t.getTaskDetails()).contains(searchWords)) {
				searchResults.add(t.getTaskDetails());
			}
		}
		displayTasks(searchResults);
	}

	public static void displayTasks(ArrayList<String> toDisplay) {
		if (toDisplay.size() == 0) {
			showToUser(MESSAGE_NO_FINDING_RESULTS);
		} else {
			for (int i = 1; i <= toDisplay.size(); i++) {
				showToUser(String.format(MESSAGE_DISPLAY_TASK, i, toDisplay.get(i - 1)));
			}
		}
	}

	public static final Comparator<Task> taskComparator = new Comparator<Task>() {

		@Override
		public int compare(Task t1, Task t2) {
			return (t1.getTaskDetails()).compareToIgnoreCase(t2.getTaskDetails());
		}

	};
	 */
}
