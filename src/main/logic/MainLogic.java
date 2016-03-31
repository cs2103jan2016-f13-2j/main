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
	private static Command command;
	private static UserInput userInput;
	private static MainLogic mainLogic;
	private static int sortType;
	private static int numTasks;
	private static Stack<Command> commandList;
	private static Stack<Command> undoedCommandList;
	
	static Logger logger = Logger.getLogger("MainLogic");

	//-----Constructor-----
	public MainLogic() {
		//Initialize variables
		storage = Storage.getInstance();
		updateTaskList();
		setDisplayList(taskList);
		sortType = 6;
		numTasks = 0;
		commandList = new Stack<Command>();
		undoedCommandList = new Stack<Command>();
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
			commandList.push(command);
			clearUndoStack();
			break;
		}
		case "delete": {
			command = new Delete(userInput);
			commandList.push(command);
			clearUndoStack();
			break;
		}
		case "edit": {
			command = new Edit(userInput);
			commandList.push(command);
			clearUndoStack();
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
		
		case "recurring": {
			command = new Add(userInput);
			commandList.push(command);
			clearUndoStack();
			break;
		}
		
		case "home": {
			displayList = storage.getTaskList();
			break;
		}
		
		case "undo": {
			undoCommand();
			break;
		}
		
		case "redo": {
			redoCommand();
			break;
		}
		
		default: {
			userInput.setSearchTerm(userInput.getCommand());
			command = new Search(userInput);
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

	private static void undoCommand() {
		if (!commandList.empty()) {
			Command command = commandList.pop();
			undoedCommandList.push(command);
			command.undo();
		}
	}
	
	private static void clearUndoStack() {
		undoedCommandList = new Stack<Command>();
	}
	
	private static void redoCommand() {
		if (!undoedCommandList.empty()) {
			Command command = undoedCommandList.pop();
			commandList.push(command);
			command.redo();
		}
	}

	private static void createMainLogic() {
		mainLogic = new MainLogic();
	}
	

	//-----Public Methods-----
	public static ArrayList<ArrayList<Task>> getTaskList() {
		numTasks = displayList.size();
		ArrayList<ArrayList<Task>> newList = new ArrayList<ArrayList<Task>>();
		ArrayList<Task> eventList = new ArrayList<Task>();
		ArrayList<Task> floatList = new ArrayList<Task>();
		ArrayList<Task> recurringList = new ArrayList<Task>();
		ArrayList<Task> deadlineList = new ArrayList<Task>();
		
		for (int i=0; i<numTasks; i++) {
			Task task = displayList.get(i);
			switch (task.getTaskType()) {
			case 1: {	//event
				eventList.add(task);
				break;
			}
			case 2: {	//floating
				floatList.add(task);
				break;
			}
			case 3: {	//recurring
				if (task.getTaskEndDate()== null && 
						task.getTaskStartDate()== null) {	//floating
					task.setTaskType(2);
					floatList.add(task);
				}
				
				else if (task.getTaskStartDate() == null) {	//deadline
					task.setTaskType(4);
					deadlineList.add(task);
				}
				
				else {	//event
					task.setTaskType(1);
					eventList.add(task);
				}
				break;
			}
			case 4: {	//deadline
				deadlineList.add(task);
				break;
			}
			}
		}
		newList.add(eventList);
		newList.add(floatList);
		newList.add(recurringList);
		newList.add(deadlineList);
		
		return newList;
	}
	
	public static void setDisplayList(ArrayList<Task> newList) {
		displayList = newList;
	}
}
