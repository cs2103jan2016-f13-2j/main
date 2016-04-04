package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Delete implements Command {
	
	
	private static final String MSG_SUCCESS = "Deleted %1$s task %2$d.";
	private static final String MSG_FAIL_INDEX_OOB = "Error: No such %1$s task with index %2$d.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after delete command.";
	
	private static final String TYPE_DEADLINE = "Deadline";
	private static final String TYPE_EVENT = "Event";
	private static final String TYPE_FLOATING = "Floating";
	private static final String TYPE_RECURRING = "Recurring";
	
	
	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	private static Logger logger = Logger.getLogger("Delete");

	public Delete(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		boolean success = false;
		
		logger.log(Level.INFO, "Command DELETE");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task task = userInput.getTasksToDelete().get(i);
			taskList.remove(task);
			displayList.remove(task);
		}
		
		success = true;
		
		if (!storage.saveFile()) {
			feedback.setMessage(MSG_FAIL_FILE_SAVE);
		}
		else if (success) {
			feedback.setMessage(String.format(MSG_SUCCESS, getTaskTypeString(), userInput.getDeleteNumber()));
		}
		else {
			feedback.setMessage(String.format(MSG_FAIL_INDEX_OOB, getTaskTypeString(), userInput.getDeleteNumber()));
		}
	}
	
	private String getTaskTypeString() {
		String type;
		
		switch(userInput.getTaskType()) {
		case 1:
			type = TYPE_EVENT;
			break;
			
		case 2:
			type = TYPE_FLOATING;
			break;
			
		case 3:
			type = TYPE_RECURRING;
			break;
			
		case 4:
			type = TYPE_DEADLINE;
			break;
			
		default:
			logger.log(Level.WARNING, "Invalid delete type.");
			type = null;
		}
		
		return type;
	}

	@Override
	public void undo() {
		logger.log(Level.INFO, "Command UNDO DELETE");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task task = userInput.getTasksToDelete().get(i);
			taskList.add(task);
			if (!displayList.equals(taskList)) {
				displayList.add(task);
			}
		}
		storage.saveFile();
	}

	@Override
	public void redo() {
		logger.log(Level.INFO, "Command REDO DELETE");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			Task task = userInput.getTasksToDelete().get(i);
			taskList.remove(task);
			displayList.remove(task);
		}
		storage.saveFile();
	}
}
