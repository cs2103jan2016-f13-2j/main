import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Trekker {
	
	//-----Variables-----
	
	//File Variables
	File file;
	String fileName;
	
	//Running Program Variables
	ArrayList<Task> taskList;
	int numberOfTasks;
	
	//Global Variables
	boolean run;
	Scanner sc;
	
	
	//MESSAGES
		public final String MSG_COMMAND = "Enter Command: ";
		public final String MSG_ADD = "Enter Task Details: <title> <startDate> <endDate> <notes>";
		public final String MSG_DELETE = "Enter Task to Delete: <task number>";
		public final String MSG_EDIT = "Enter Task to Edit: <task number>";
		public final String MSG_EDIT_CHOICE = "Choose number to edit: <1. title> <2. startDate> <3. endDate> <4. notes>";
		public final String MSG_EDIT_CHOICE_TO = "Edit to: ";
		public final String MSG_NO_TASK = "No tasks to display.";
		
	//MESSAGES_SUCCESS
		public final String MSG_ADD_SUCCESS = "Task added successfully.";
		public final String MSG_DELETE_SUCCESS = "Task deleted successfully.";
		public final String MSG_EDIT_SUCCESS = "Task updated successfully.";
		
	//MESSAGES_ERROR
		public final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";
		public final String MSG_ERROR_ADD_FAIL = "Failed to add task.";
		public final String MSG_ERROR_DELETE_FAIL = "Failed to delete task.";
		public final String MSG_ERROR_EDIT_FAIL = "Failed to edit task.";
	
	
	//-----Basic Functions-----;
	
	private void addTask() {
		try {
			showMessage(MSG_ADD);
			Task newTask = createTask();
			taskList.add(newTask);
			numberOfTasks+=1;
			saveFile();
			showMessage(MSG_ADD_SUCCESS);
		} catch (NoSuchElementException e) {
			showMessage(MSG_ERROR_ADD_FAIL);
		} catch (NumberFormatException e) {
			showMessage(MSG_ERROR_ADD_FAIL);
		}
	}
	
	private void deleteTask() {
		try {
			showMessage(MSG_DELETE);
			removeTask();
			showMessage(MSG_DELETE_SUCCESS);
		} catch (IndexOutOfBoundsException e) {
			showMessage(MSG_ERROR_DELETE_FAIL);
		}
	}
	
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
	
	private void displayTask() {
		if (numberOfTasks == 0) {
			showMessage(MSG_NO_TASK);
		}
		else {
			for (int i=0; i<numberOfTasks; i++) {
				showTask(taskList.get(i), i+1);
			}
		}
	}
	
	
	//-----Start-up Program Functions-----
	
	private void runCommandOptions() {
		showMessage(MSG_COMMAND);
		String command = sc.nextLine();
		
		switch (command.toUpperCase()) {
		case "ADD": {
			addTask();
			break;
		}
		case "DELETE": {
			deleteTask();
			break;
		}
		case "EDIT": {
			editTask();
			break;
		}
		case "DISPLAY": {
			displayTask();
			break;
		}
		case "EXIT": {
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
	private void showMessage(String message) {
		System.out.println(message);
	}
	
	private void showTask(Task task, int taskNumber) {
		showMessage("----- Task "+taskNumber+" -----");
		showMessage("Task name: "+task.getTitle());
		showMessage("Start Date: "+task.getStartDate());
		showMessage("End Date: "+task.getEndDate());
		showMessage("Notes: "+task.getNotes());
		showMessage("");
		showMessage("");
	}
	
	private void prepareFile() {
		if (file.exists()) {
			readFile();
		}
		
		else {
			createFile();
		}
	}
	
	private Task createTask() {
		String title = sc.nextLine();
		int startDate = Integer.parseInt(sc.nextLine());
		int endDate = Integer.parseInt(sc.nextLine());
		String notes = sc.nextLine();
		return new Task(title, startDate, endDate, notes);
	}
	
	private void removeTask() {
		int taskToRemove = Integer.parseInt(sc.nextLine());
		
		taskList.remove(taskToRemove-1);
		numberOfTasks--;
		saveFile();
	}
	
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
	
	private void exitProgram() {
		saveFile();
		run = false;
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
	
	
	
	//-----Constructor-----
	
	public Trekker() {
		//Initialize variables
		file = new File("task.txt");
		fileName = "task.txt";
		taskList = new ArrayList<Task>();
		
		prepareFile();
	}
	
	private void run() {
		//Show welcome message
		
		//Runs command options
		run = true;
		sc = new Scanner(System.in);
		
		while (run) {
			runCommandOptions();
		}
		
		sc.close();
	}
	
	public static void main(String[] args) {
		Trekker trekker = new Trekker();
		trekker.run();
	}
}
