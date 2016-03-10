package main.logic;

import java.io.File;
import java.util.*;

import main.gui.model.Task;
import main.gui.model.UserInput;
import main.storage.Storage;

public class MainLogic {

	//-----Variables-----

	//File Variables
	File file;
	String fileName;

	//Running Program Variables
	Storage storage;
	ArrayList<Task> taskList;
	int numberOfTasks;

	//Global Variables
	String[] inputCommand;
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
		inputCommand = userInput.getRawInput().split(";");
		userInput.setCommand(inputCommand[0]);
		//-----------parser section
	}

	private void addTask() {
		try {
			Task newTask = new Task(inputCommand[1], inputCommand[2]);
			taskList.add(newTask);
			numberOfTasks++;
			saveFile();
		} catch (NoSuchElementException e) {
		} catch (NumberFormatException e) {
		}
	}

	private void deleteTask() {
		try {
			taskList.remove(Integer.parseInt(inputCommand[1].trim())-1);
			numberOfTasks--;
			saveFile();
		} catch (IndexOutOfBoundsException e) {
		}
	}

	private void editTask() {
		try {
			int taskToEdit = Integer.parseInt(inputCommand[1].trim())-1;
			Task task = taskList.get(taskToEdit);
			String whatToEdit = inputCommand[2].trim();
			String newEdit = inputCommand[3].trim();
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
		switch (inputCommand[0].toLowerCase()) {
		case "add": {
			//addTask();
			command = new Add(userInput);
			Task newTask = new Task(inputCommand[1], inputCommand[2]);
			userInput.setTask(newTask);
			break;
		}
		case "delete": {
			//deleteTask();
			userInput.setDelete(Integer.parseInt(inputCommand[1]));
			command = new Delete(userInput);
			break;
		}
		case "edit": {
			//editTask();
			userInput.setEdit(Integer.parseInt(inputCommand[1]), Integer.parseInt(inputCommand[2]));
			userInput.setDetails(inputCommand[3]);
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
