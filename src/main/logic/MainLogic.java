package main.logic;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.storage.Storage;
import main.parser.Parser;
import main.parser.Shortcuts;
import main.resources.Task;
import main.resources.UserInput;



public class MainLogic {

	//-----Variables-----

	//Global Variables
	private static Storage storage;
	private static ArrayList<Task> taskList;
	private static ArrayList<Task> displayList;
	private static ArrayList<String> inputCommand;
	private static Command command;
	private static UserInput userInput;
	private static MainLogic mainLogic;
	private static int sortType;
	private static int numTasks;
	
	static Logger logger = Logger.getLogger("MainLogic");

	//-----Constructor-----
	public MainLogic() {
		//Initialize variables
		storage = Storage.getInstance();
		updateTaskList();
		setDisplayList(taskList);
		sortType = 6;
		numTasks = 0;
	}
	
	public static void run(UserInput input) {
		logger.log(Level.INFO, "MainLogic START");
		initializeMainLogic();
		setUserInput(input);
		runParser();
		createCommandObject();
		executeCommand();
		updateTaskList();
		logger.log(Level.INFO, "MainLogic END");
	}
	
	//-----Private methods-----
	private static void createCommandObject() {
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
			userInput.setSortType(sortType);
			command = new Display(userInput);
			break;
		}
		
		case "sort": {
			command = new Sort(userInput);
			sortType = userInput.getSortType();
			break;
		}
		
		case "search": {
			command = new Search(userInput);
			break;
		}
		
		case "home": {
			displayList = storage.getTaskList();
		}
		
		default: {
			logger.log(Level.WARNING, "Command not found");
			break;
		}
		}
	}
	
	private static void initializeMainLogic() {
		if (mainLogic == null) {
			MainLogic.createMainLogic();
		}
	}
	
	private static void setUserInput(UserInput input) {
		userInput = input;
	}
	
	private static void runParser() {
		userInput = Parser.resetUserInput(userInput);
	}
	
	private static void executeCommand() {
		command.execute();
	}

	private static void updateTaskList() {
		taskList = storage.getTaskList();	
	}
	
	private static String getCommand() {
		return Shortcuts.shortcuts(userInput.getCommand().toLowerCase());
	}

	private static void createMainLogic() {
		mainLogic = new MainLogic();
	}
	

	//-----Public Methods-----
	public static ArrayList<Task> getTaskList() {
		numTasks = displayList.size();
		return displayList;
	}
	
	public static void setDisplayList(ArrayList<Task> newList) {
		displayList = newList;
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
