package main.gui.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import main.gui.model.Task;
import main.storage.Storage;

public class Trekker {

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

	//MESSAGES
	private static final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";
	private static final String CMD_ADD = "add";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_EDIT = "edit";
	private static final String CMD_DISPLAY = "display";
	private static final String CMD_EXIT = "exit";


	//-----Constructor-----

	public Trekker(String command) {
		//Initialize variables
		storage = new Storage();
		taskList = storage.getTaskList();
		inputCommand = command.split(";");

	}

	//-----Basic Functions-----;

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

<<<<<<< HEAD
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
			addTask();
			break;
		}
		case "delete": {
			deleteTask();
			break;
		}
		case "edit": {
			editTask();
			break;
		}

		case "display": {
			//Nothing done here
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
	}
}
