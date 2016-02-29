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
	public final String MSG_EDIT = "Enter Task to Edit: <task number>";
	public final String MSG_EDIT_CHOICE = "Choose number to edit: <1. title> <2. startDate> <3. endDate> <4. notes>";
	public final String MSG_EDIT_CHOICE_TO = "Edit to: ";

	//MESSAGES_SUCCESS
	public final String MSG_EDIT_SUCCESS = "Task updated successfully.";

	//MESSAGES_ERROR
	public final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";
	public final String MSG_ERROR_EDIT_FAIL = "Failed to edit task.";


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
/*
	private void editTask() {
		try {
			showMessage(MSG_EDIT);
			int taskToEdit = Integer.parseInt(sc.nextLine());
			Task task = taskList.get(taskToEdit-1);
			showTask(task, taskToEdit);
			showMessage(MSG_EDIT_CHOICE);
			int lineToEdit = Integer.parseInt(sc.nextLine());
			showMessage(MSG_EDIT_CHOICE_TO);
			String newEdit = sc.nextLine();
			task = updateTask(task, newEdit, lineToEdit);	
			saveFile();
			showMessage(MSG_EDIT_SUCCESS);
		} catch (IndexOutOfBoundsException e) {
			showMessage(MSG_ERROR_EDIT_FAIL);
		} catch (NumberFormatException e) {
			showMessage(MSG_ERROR_EDIT_FAIL);
		}
	}
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
			//editTask();
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

/*
	private Task updateTask(Task task, String newEdit, int lineToEdit) {
		boolean notValid = true;

		while (notValid) {
			switch (lineToEdit) {
			case 1: {			//title
				task.updateTitle(newEdit);
				notValid = false;
				break;
			}
			case 2: {			//startDate
				task.updateStartDate(Integer.parseInt(newEdit));
				notValid = false;
				break;
			}
			case 3: {			//endDate
				task.updateEndDate(Integer.parseInt(newEdit));
				notValid = false;
				break;
			}
			case 4: {			//notes
				task.updateNotes(newEdit);
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
*/
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
