import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Trekker {
	
	//-----Variables-----
	File file;
	String fileName;
	ArrayList<Task> taskList;
	int numberOfTasks;
	boolean run;
	
	Scanner sc;
	
	//MESSAGES
		public final String MSG_COMMAND = "Enter Command: ";
		public final String MSG_ADD = "Enter Task Details: <title> <startDate> <endDate> <notes>";
		
	//MESSAGES_SUCCESS
		public final String MSG_ADD_SUCCESS = "Task added successfully.";
		
	//MESSAGES_ERROR
		public final String MSG_ERROR_COMMAND_NOT_FOUND = "Command not found.";
		public final String MSG_ERROR_ADD_FAIL = "Failed to add task.";
	
	
	//-----Basic Functions-----
	
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
		
	}
	
	private void editTask() {
		
	}
	
	private void displayTask() {
		for (int i=0; i<numberOfTasks; i++) {
			showTask(taskList.get(i), i);
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
		showMessage("----- Task "+(taskNumber+1)+" -----");
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
