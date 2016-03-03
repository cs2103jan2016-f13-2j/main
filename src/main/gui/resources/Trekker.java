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

public class Trekker {

	//-----Variables-----

	//File Variables
	File file;
	String fileName;

	//Running Program Variables
	ArrayList<Task> taskList;
	int numberOfTasks;

	//Global Variables
	String[] inputCommand;

	//MESSAGES
	public final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";


	//-----Constructor-----

	public Trekker(String command) {
		//Initialize variables
		file = new File("task.txt");
		fileName = "task.txt";
		taskList = new ArrayList<Task>();
		inputCommand = command.split(";");
		prepareFile();
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

	private void prepareFile() {
		if (file.exists()) {
			readFile();
		}

		else {
			createFile();
		}
	}


	private Task updateTask(Task task, String newEdit, String whatToEdit) {
		boolean notValid = true;

		while (notValid) {
			switch (whatToEdit) {
			case "taskName": {			//taskName
				task.setTaskName(newEdit);
				notValid = false;
				break;
			}
			case "taskDetails": {			//taskDetails
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
}

public ArrayList<Task> getTaskList() {
	return taskList;
}


	//-----File Readers-----

	//Reads the file and inputs it into the ArrayList
private void readFile() {
	try {			
		ObjectInputStream objectInputStream = new ObjectInputStream(
			new FileInputStream("task.txt"));

			//Read number of Tasks
		numberOfTasks = (Integer) objectInputStream.readObject();


		Task task = null;
		for (int i=0; i<numberOfTasks; i++) {
			task = (Task) objectInputStream.readObject();
			taskList.add(task);
		}

		objectInputStream.close();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

}

	//Saves the current testList to the text file
private void saveFile() {
	try {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			new FileOutputStream("task.txt"));
		objectOutputStream.writeObject(numberOfTasks);
		for (int i=0; i<numberOfTasks; i++) {
			objectOutputStream.writeObject(taskList.get(i));
		}

		objectOutputStream.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	//Creates text file if it does not exist
private void createFile() {
	try {
		file.createNewFile();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			new FileOutputStream("task.txt"));
		objectOutputStream.writeObject(new Integer(0));

		objectOutputStream.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void run() {
		//Runs command options
	runCommandOptions();
}
}
