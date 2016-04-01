package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Date;
import main.resources.Feedback;
import main.resources.Task;
import main.resources.Time;
import main.resources.UserInput;
import main.storage.Storage;

public class Edit implements Command {
	
	private static final String MSG_SUCCESS = "Editted task successfully.";
	private static final String MSG_FAIL_INDEX_OOB = "Error: Index out of bounds. Unable to edit.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after edit command.";
	
	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	private static Logger logger = Logger.getLogger("Edit");

	public Edit(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command EDIT");
		taskList = storage.getTaskList();
		Task taskToEdit = findTask();
		for (int i=1; i<userInput.getEditNumber().size(); i++) {
			switch (userInput.getEditNumber().get(i)) {
			case 1:	{	//task detail
				String originalData = taskToEdit.getTaskDetails();
				taskToEdit.setTaskDetails(userInput.getDetails());
				userInput.setDetails(originalData);
				break;
			}
			case 2: {	//task date
				Date originalData = taskToEdit.getTaskStartDate();
				taskToEdit.setTaskStartDate(userInput.getStartDate());
				if (taskToEdit.getTaskType() == 2) {	//floating
					taskToEdit.setTaskType(4);	//deadline
				}
				userInput.setStartDate(originalData);
				break;
			}
			case 3:	{	//task time
				Time originalData = taskToEdit.getTaskStartTime();
				taskToEdit.setTaskStartTime(userInput.getStartTime());
				if (taskToEdit.getTaskType() == 2) {	//floating
				taskToEdit.setTaskType(4);	//deadline
				}
				userInput.setStartTime(originalData);
				break;
			}
			case 6: {	//task location
				String originalData = taskToEdit.getTaskLocation();
				System.out.println(originalData);
				taskToEdit.setTaskLocation(userInput.getLocation());
				System.out.println(userInput.getLocation());
				userInput.setLocation(originalData);
				break;
			}
			case 7:	{	//task priority
				int originalData = taskToEdit.getPriority();
				taskToEdit.setPriority(userInput.getPriority());
				userInput.setPriority(originalData);
				break;
			}
			default:
				feedback.setMessage(MSG_FAIL_INDEX_OOB);
				return;
			}
			
			userInput.setTask(taskToEdit);
			
			if (!storage.saveFile()) {
				feedback.setMessage(MSG_FAIL_FILE_SAVE);
			}
			else {
				feedback.setMessage(String.format(MSG_SUCCESS));
			}
		}
	}

	@Override
	public void undo() {
		taskList = storage.getTaskList();
		for (Task t : taskList) {
			if (t.equals(userInput.getTask())) {
				for (int i=1; i<userInput.getEditNumber().size(); i++) {
					switch (userInput.getEditNumber().get(i)) {
					case 1: {	//task detail
						String originalData = t.getTaskDetails();					
						t.setTaskDetails(userInput.getDetails());
						userInput.setDetails(originalData);
						break;
					}
					case 2: {	//task date
						Date originalData = t.getTaskStartDate();
						t.setTaskStartDate(userInput.getStartDate());
						userInput.setStartDate(originalData);
						break;
					}
					case 3: {	//task time
						Time originalData = t.getTaskStartTime();
						t.setTaskStartTime(userInput.getStartTime());
						userInput.setStartTime(originalData);
						break;
					}
					case 6: {	//task location
						String originalData = t.getTaskLocation();
						t.setTaskLocation(userInput.getLocation());
						userInput.setLocation(originalData);
						break;
					}
					case 7: {	//task priority
						int originalData = t.getPriority();
						t.setPriority(userInput.getPriority());
						userInput.setPriority(originalData);
						break;
					}
					}	
				}
			}
		}
	}

	@Override
	public void redo() {
		taskList = storage.getTaskList();
		for (Task t : taskList) {
			if (t.equals(userInput.getTask())) {
				for (int i=1; i<userInput.getEditNumber().size(); i++) {
					switch (userInput.getEditNumber().get(i)) {
					case 1: {	//task detail
						String originalData = t.getTaskDetails();					
						t.setTaskDetails(userInput.getDetails());
						userInput.setDetails(originalData);
						break;
					}
					case 2: {	//task date
						Date originalData = t.getTaskStartDate();
						t.setTaskStartDate(userInput.getStartDate());
						userInput.setStartDate(originalData);
						break;
					}
					case 3: {	//task time
						Time originalData = t.getTaskStartTime();
						t.setTaskStartTime(userInput.getStartTime());
						userInput.setStartTime(originalData);
						break;
					}
					case 6: {	//task location
						String originalData = t.getTaskLocation();
						t.setTaskLocation(userInput.getLocation());
						userInput.setLocation(originalData);
						break;
					}
					case 7: {	//task priority
						int originalData = t.getPriority();
						t.setPriority(userInput.getPriority());
						userInput.setPriority(originalData);
						break;
					}
					}	
				}
			}
		}		
	}
	
	private Task findTask() {
		int count = 0;
		for (int i=0; i<taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getTaskType() == userInput.getTaskType()) {
				count++;
				 if(count == userInput.getEditNumber().get(0)) {
					return task;
				 }
			}
		}
		
		return null;
	}

}
