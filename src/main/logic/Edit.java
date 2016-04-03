package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Edit implements Command {

	private static final String MSG_SUCCESS = "Editted task successfully.";
	private static final String MSG_INVALID_EDIT_TYPE = "Error: Invalid edit task.";
	private static final String MSG_FAIL_NO_START_DATE = "Error: Cannot add end date to floating task without start date.";
	private static final String MSG_FAIL_NO_START_TIME = "Error: Cannot add end time to floating task without start time.";
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
		boolean success = false;
		boolean changedTaskType = false;
		logger.log(Level.INFO, "Command EDIT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		Task taskToEdit = userInput.getTaskToEdit();
		userInput.setTaskToEdit(taskToEdit);
		Task newTask = Task.duplicateTask(taskToEdit);
		
		for (int i=1; i<userInput.getEditNumber().size(); i++) {
			switch (userInput.getEditNumber().get(i)) {
			case 1:	{	//task detail
				newTask.setTaskDetails(userInput.getDetails());
				success = true;
				break;
			}
			case 2: {	//task start date
				if (newTask.getTaskType() == 2) {	//floating
					if (!changedTaskType) {
						changedTaskType = true;
					}
					newTask.setTaskType(4);	//deadline
				}
				newTask.setTaskStartDate(userInput.getStartDate());
				success = true;
				break;
			}
			case 3:	{	//task start time
				if (newTask.getTaskType() == 2) {	//floating
					if (!changedTaskType) {
						changedTaskType = true;
					}
					newTask.setTaskType(4);	//deadline
				}
				newTask.setTaskStartTime(userInput.getStartTime());
				success = true;
				break;
			}
			case 4:	{	//task end date
				if (newTask.getTaskType() == 2) { //floating
					feedback.setMessage(MSG_FAIL_NO_START_DATE);
					break;
				}
				if (newTask.getTaskType() == 4) {	//deadline
					if (!changedTaskType) {
						changedTaskType = true;
					}
					newTask.setTaskType(1);	//event
				}
				newTask.setTaskEndDate(userInput.getEndDate());
				success = true;
				break;
			}
			case 5:	{	//task end time
				if (newTask.getTaskType() == 2) { //floating
					feedback.setMessage(MSG_FAIL_NO_START_TIME);
					break;
				}
				if (newTask.getTaskType() == 4) {	//deadline
					if (!changedTaskType) {
						changedTaskType = true;
					}
					newTask.setTaskType(1);	//event
				}
				newTask.setTaskEndTime(userInput.getEndTime());
				success = true;
				break;
			}
			case 6: {	//task location
				newTask.setTaskLocation(userInput.getLocation());
				success = true;
				break;
			}
			case 7:	{	//task priority
				newTask.setPriority(userInput.getPriority());
				success = true;
				break;
			}
			case 8: {	//is complete
				newTask.setComplete(userInput.getComplete());
				success = true;
				break;
			}
			default:
				feedback.setMessage(MSG_INVALID_EDIT_TYPE);
				return;
			}


			if (!storage.saveFile()) {
				feedback.setMessage(MSG_FAIL_FILE_SAVE);
			}
			else if (success) {
				feedback.setMessage(String.format(MSG_SUCCESS));
			}
		}
		
		taskList.remove(taskToEdit);
		taskList.add(newTask);
		displayList.remove(taskToEdit);
		displayList.add(newTask);
		userInput.setTask(newTask);
	}

	@Override
	public void undo() {
		logger.log(Level.INFO, "Command UNDO EDIT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		taskList.remove(userInput.getTask());
		taskList.add(userInput.getTaskToEdit());
		displayList.remove(userInput.getTask());
		displayList.add(userInput.getTaskToEdit());
	}

	@Override
	public void redo() {
		logger.log(Level.INFO, "Command REDO EDIT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		taskList.remove(userInput.getTaskToEdit());
		taskList.add(userInput.getTask());
		displayList.remove(userInput.getTaskToEdit());
		displayList.add(userInput.getTask());
	}
}
