package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class Add implements Command {
	
	private static final String MSG_SUCCESS_ADD = "Added new %1$s task \"%2$s\".";
	private static final String MSG_SUCCESS_UNDO = "Undid previous command.";
	private static final String MSG_SUCCESS_REDO = "Redid previous command.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after add command.";
	
	private static final String TYPE_DEADLINE = "deadline";
	private static final String TYPE_EVENT = "event";
	private static final String TYPE_FLOATING = "floating";
	private static final String TYPE_RECURRING = "recurring";
	
	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	
	private static Logger logger = Logger.getLogger("Add");

	public Add(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command ADD");
		taskList = storage.getTaskList();
		taskList.add(userInput.getTask());
		
		if (!storage.saveFile()) {
			feedback.setMessage(MSG_FAIL_FILE_SAVE);
		}
		else {
			feedback.setMessage(String.format(MSG_SUCCESS_ADD, getTaskTypeString(), userInput.getTask().getTaskDetails()));
		}
	}
	
	private String getTaskTypeString() {
		String type;
		
		switch(userInput.getTask().getTaskType()) {
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
		logger.log(Level.INFO, "Command UNDO ADD");
		taskList = storage.getTaskList();
		taskList.remove(userInput.getTask());
		storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_UNDO);
	}

	@Override
	public void redo() {
		logger.log(Level.INFO, "Command REDO ADD");
		taskList = storage.getTaskList();
		taskList.add(userInput.getTask());
		storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_REDO);
	}

}
