package main.logic;

import java.io.File;
import java.util.*;

import main.storage.Storage;
import main.parser.Parser;
import main.resources.Task;
import main.resources.UserInput;
import main.parser.FlexiCommands;


public class MainLogic {

	//-----Variables-----

	//File Variables
	File file;
	String fileName;

	//Running Program Variables
	Storage storage;
	ArrayList<Task> taskList;
	int numberOfTasks;
	Task task = null;

	//Global Variables
	ArrayList<String> inputCommand;
	Command command;
	UserInput userInput;

	//MESSAGES
	private static final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";


	//-----Constructor-----
	public MainLogic(UserInput userInput) {
		//Initialize variables
		storage = new Storage();
		taskList = storage.getTaskList();
		this.userInput = userInput;
		//-----------parser section

		userInput = Parser.resetUserInput(userInput);
		//-----------parser section
	}

	private void addTask() {
		try {
			task = userInput.getTask();
			taskList.add(task);
			numberOfTasks++;
			saveFile();
		} catch (NoSuchElementException e) {
		} catch (NumberFormatException e) {
		}
	}

	private void deleteTask() {
		try {
			taskList.remove(userInput.getDeleteNumber());
			numberOfTasks--;
			saveFile();
		} catch (IndexOutOfBoundsException e) {
		}
	}

	private void editTask() {
		try {
			int[] editNumber = userInput.getEditNumber();
			Task task = taskList.get(editNumber[0]);
			String whatToEdit = editNumber[1]+"";
			String newEdit = userInput.getDetails();
			task = updateTask(task, newEdit, whatToEdit);	
			saveFile();
		} catch (IndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
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


	//-----Start-up Program Functions-----

	private void runCommandOptions() {
		switch (FlexiCommands.flexiCommands(userInput.getCommand().toLowerCase())) {
		case "add": {
			//addTask();
			command = new Add(userInput);
			break;
		}
		case "delete": {
			//deleteTask();
			command = new Delete(userInput);
			break;
		}
		case "edit": {
			//editTask();
			command = new Edit(userInput);
			break;
		}

		case "display": {
			command = new Display(userInput);
			break;
		}
		case "exit": {
			exitProgram();
			break;
		}
		default: {
			showMessage(MSG_ERROR_COMMAND_NOT_FOUND);
			break;
		}
		}
		
		command.execute();
	}

	//-----Helper Functions-----
	private void showMessage(String message, Object... args) {
		System.out.printf(message, args).println();
	}

	private Task updateTask(Task task, String newEdit, String whatToEdit) {
		boolean notValid = true;

		while (notValid) {
			switch (whatToEdit.toLowerCase()) {
			case "taskname": {			//taskName
				task.setTaskName(newEdit);
				notValid = false;
				break;
			}
			case "taskdetails": {			//taskDetails
				task.setTaskDetails(newEdit);
				notValid = false;
				break;
			}
			default: {
				notValid = true;
			}
			}
		}

		return task;
	}

	private void exitProgram() {
		saveFile();
		storage.saveFile();
	}

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	//Saves the current testList to the text file
	private void saveFile() {
		//storage.setTaskList(taskList);
		storage.saveFile();
	}

	public void run() {
		//Runs command options
		runCommandOptions();
		//to be edited
		taskList = storage.getTaskList();
	}
}
