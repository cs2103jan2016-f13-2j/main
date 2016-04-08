package main.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L

public class Delete implements Command {


	private static final String MSG_SUCCESS_DELETE = "Deleted %1$s task %2$d.";
	private static final String MSG_SUCCESS_DELETE_MULTIPLE = "Deleted multiple tasks.";
	private static final String MSG_SUCCESS_UNDO = "Undid previous command.";
	private static final String MSG_SUCCESS_REDO = "Redid previous command.";
	private static final String MSG_FAIL_INDEX_OOB = "Error: No such %1$s task with index %2$d.";
	private static final String MSG_FAIL_INDEX_OOB_MULTIPLE = "Error: At least one invalid index entered.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after delete command.";

	private static final String TYPE_DEADLINE = "deadline";
	private static final String TYPE_EVENT = "event";
	private static final String TYPE_FLOATING = "floating";


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

		Task task = null;
		
		for (int i=0; i<userInput.getTasksToDelete().size(); i++) {
			task = userInput.getTasksToDelete().get(i);
			taskList.remove(task);
			displayList.remove(task);
		}

		success = true;

		if (!storage.saveFile()) {
			feedback.setMessage(MSG_FAIL_FILE_SAVE);
		}
		else if (success) {
			if (userInput.getDeleteNumber().size() > 1) {
				feedback.setMessage(MSG_SUCCESS_DELETE_MULTIPLE);
			}
			else {
				feedback.setMessage(String.format(MSG_SUCCESS_DELETE, getTaskTypeString(task), userInput.getDeleteNumber().get(0)[1]));
			}
		}
		else {
			if (userInput.getDeleteNumber().size() > 1) {
				feedback.setMessage(MSG_FAIL_INDEX_OOB_MULTIPLE);;
			}
			else {
				feedback.setMessage(String.format(MSG_FAIL_INDEX_OOB, getTaskTypeString(task), userInput.getDeleteNumber().get(0)[1]));
			}
		}
	}
	
	//@@author A0124711U
	/**
	 * Returns the type of task as a string description.
	 * @param task : The task type to be checked.
	 * @return The string description of the task type.
	 */
	private String getTaskTypeString(Task task) {
		String type;
		int taskType = task.getTaskType();
		System.out.println(taskType);

		switch(taskType) {
		case 1:
			type = TYPE_EVENT;
			break;

		case 2:
			type = TYPE_FLOATING;
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

	//@@author A0125255L
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

		feedback.setMessage(MSG_SUCCESS_UNDO);
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

		feedback.setMessage(MSG_SUCCESS_REDO);
	}
}
