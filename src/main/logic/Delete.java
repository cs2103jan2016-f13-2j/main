package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Delete implements Command {
	
	
	private static final String MSG_SUCCESS = "Deleted: %1$s task %2$d.";
	private static final String MSG_FAIL_INDEX_OOB = "Error: No such %1$s task with index %2$d.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after delete.";
	
	private static final String TYPE_DEADLINE = "Deadline";
	private static final String TYPE_EVENT = "Event";
	private static final String TYPE_FLOATING = "Floating";
	private static final String TYPE_RECURRING = "Recurring";
	
	
	UserInput userInput;
	Storage storage;
	Feedback feedback;
	ArrayList<Task> taskList;
	static Logger logger = Logger.getLogger("Delete");

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
		int count = 0;
		taskList = storage.getTaskList();
		for (int i=0; i<taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.getTaskType() == userInput.getTaskType()) {
				count++;
				 if(count == userInput.getDeleteNumber()) {
					 userInput.setTask(taskList.get(i));
					 taskList.remove(i);
					 success = true;
				 }
			}
		}
		
		if (!storage.saveFile()) {
			feedback.setMessage(MSG_FAIL_FILE_SAVE);
		}
		
		if (success) {
			feedback.setMessage(String.format(MSG_SUCCESS, getTaskType(), userInput.getDeleteNumber()));
		}
		else {
			feedback.setMessage(String.format(MSG_FAIL_INDEX_OOB, getTaskType(), userInput.getDeleteNumber()));
		}
		
	}
	
	private String getTaskType() {
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
		taskList = storage.getTaskList();
		taskList.add(userInput.getTask());
		storage.saveFile();
	}

	@Override
	public void redo() {
		taskList = storage.getTaskList();
		taskList.remove(userInput.getTask());
		storage.saveFile();
	}
}
