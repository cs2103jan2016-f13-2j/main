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
	private static final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";
	private static final String CMD_ADD = "add";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_EDIT = "edit";
	private static final String CMD_DISPLAY = "display";
	private static final String CMD_EXIT = "exit";


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


	//-----Start-up Program Functions-----

	private void runCommandOptions() {
		switch (inputCommand[0].toLowerCase()) {
		case CMD_ADD: {
			addTask();
			break;
		}
		case CMD_DELETE: {
			deleteTask();
			break;
		}
		case CMD_EDIT: {
			editTask();
			break;
		}

		case CMD_DISPLAY: {
			//Nothing done here
			break;
		}
		case CMD_EXIT: {
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
