package main.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Date;
import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L

public class Add implements Command {
	
	private static final String MSG_SUCCESS_ADD = "Added new %1$s task \"%2$s\".";
	private static final String MSG_SUCCESS_UNDO = "Undid previous command.";
	private static final String MSG_SUCCESS_REDO = "Redid previous command.";
	private static final String MSG_FAIL_INVALID_RECUR_FREQ = "Error: Invalid recurring frequency entered.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after add command.";
	
	private static final String TYPE_DEADLINE = "deadline";
	private static final String TYPE_EVENT = "event";
	private static final String TYPE_FLOATING = "floating";
	private static final String TYPE_RECURRING = "recurring";
	
	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	private ArrayList<Task> recurList;
	
	private static Logger logger = Logger.getLogger("Add");

	/**
	 * Constructs an Add command
	 * @param userInput: userInput instance from MainLogic
	 */
	public Add(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
		recurList = new ArrayList<Task>();
	}

	/**
	 * Executes the command
	 */
	@Override
	public void execute() {
		logger.log(Level.INFO, "Command ADD");
		taskList = storage.getTaskList();
		Task task = userInput.getTask();
		if (task.isRecurring()) {
			task.setHead(task);
			Task newTask = Task.duplicateTask(task);
			recurList.add(newTask);
			taskList.add(newTask);
			if (task.getRecurTime() <= 0) {
				feedback.setMessage(MSG_FAIL_INVALID_RECUR_FREQ);
				return;
			}
			task.setRecurTime(task.getRecurTime() - 1);
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			while (task.getRecurTime() > 0) {
				task.setRecurTime(task.getRecurTime() - 1);
				Date dateStart = task.getTaskStartDate();
				calStart.set(dateStart.getYear(), dateStart.getMonth(), dateStart.getDay());
				Date dateEnd;
				if (task.getTaskEndDate() != null) {
					dateEnd = task.getTaskEndDate();
					calEnd.set(dateEnd.getYear(), dateEnd.getMonth(), dateEnd.getDay());
				}
				switch (task.getRecurFrequency()) {
				case 1: {	//daily
					calStart.add(Calendar.DAY_OF_MONTH, 1);
					calEnd.add(Calendar.DAY_OF_MONTH, 1);
					task.setTaskStartDate(new Date(calStart.get(Calendar.DAY_OF_MONTH), 
														calStart.get(Calendar.MONTH), 
															calStart.get(Calendar.YEAR)));
					if (task.getTaskEndDate() != null) {
						task.setTaskEndDate(new Date(calEnd.get(Calendar.DAY_OF_MONTH), 
								calEnd.get(Calendar.MONTH), 
									calEnd.get(Calendar.YEAR)));
					}
					break;
				}
				case 2: {	//weekly
					calStart.add(Calendar.DAY_OF_MONTH, 7);
					calEnd.add(Calendar.DAY_OF_MONTH, 7);
					task.setTaskStartDate(new Date(calStart.get(Calendar.DAY_OF_MONTH), 
														calStart.get(Calendar.MONTH), 
															calStart.get(Calendar.YEAR)));
					if (task.getTaskEndDate() != null) {
						task.setTaskEndDate(new Date(calEnd.get(Calendar.DAY_OF_MONTH), 
								calEnd.get(Calendar.MONTH), 
									calEnd.get(Calendar.YEAR)));
					}
					break;
				}
				case 3: {	//monthly
					calStart.add(Calendar.MONTH, 1);
					calEnd.add(Calendar.MONTH, 1);
					task.setTaskStartDate(new Date(calStart.get(Calendar.DAY_OF_MONTH), 
														calStart.get(Calendar.MONTH), 
															calStart.get(Calendar.YEAR)));
					if (task.getTaskEndDate() != null) {
						task.setTaskEndDate(new Date(calEnd.get(Calendar.DAY_OF_MONTH), 
								calEnd.get(Calendar.MONTH), 
									calEnd.get(Calendar.YEAR)));
					}
					break;
				}
				case 4: {	//yearly
					calStart.add(Calendar.YEAR, 1);
					calEnd.add(Calendar.YEAR, 1);
					task.setTaskStartDate(new Date(calStart.get(Calendar.DAY_OF_MONTH), 
														calStart.get(Calendar.MONTH), 
															calStart.get(Calendar.YEAR)));
					if (task.getTaskEndDate() != null) {
						task.setTaskEndDate(new Date(calEnd.get(Calendar.DAY_OF_MONTH), 
								calEnd.get(Calendar.MONTH), 
									calEnd.get(Calendar.YEAR)));
					}
					break;
				}
				}
				newTask = Task.duplicateTask(task);
				recurList.add(newTask);
				taskList.add(newTask);
				System.out.println(newTask.getHead());
			}
			
			task.setRecurList(recurList);	
		}
		
		else {
			if (task.getTaskStartDate() != null && 
					(task.getTaskStartDate().compareTo(MainLogic.getCurrentDate()) < 0)) {
				task.setExpired(true);
			}
			
			taskList.add(task);
		}
		
		if (!storage.saveFile()) {
			feedback.setMessage(MSG_FAIL_FILE_SAVE);
		}
		else {
			feedback.setMessage(String.format(MSG_SUCCESS_ADD, getTaskTypeString(), userInput.getTask().getTaskDetails()));
		}
	}
	
	//@@author A0124711U
		/**
		 * Returns the type of task as a string description.
		 * @param task : The task type to be checked.
		 * @return The string description of the task type.
		 */
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

	//@@author A0125255L
	/**
	 * Undo the command
	 */
	@Override
	public void undo() {
		logger.log(Level.INFO, "Command UNDO ADD");
		taskList = storage.getTaskList();
		if (userInput.getTask().isRecurring()) {
			for (int i=0; i<userInput.getTask().getRecurList().size(); i++) {
				Task t = userInput.getTask().getRecurList().get(i);
				taskList.remove(t);
			}
		}
		
		else {
			taskList.remove(userInput.getTask());
		}
		storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_UNDO);
	}

	/**
	 * Redo the command
	 */
	@Override
	public void redo() {
		logger.log(Level.INFO, "Command REDO ADD");
		taskList = storage.getTaskList();
		if (userInput.getTask().isRecurring()) {
			for (int i=0; i<userInput.getTask().getRecurList().size(); i++) {
				Task t = userInput.getTask().getRecurList().get(i);
				taskList.add(t);
			}
		}
		
		else {
			taskList.add(userInput.getTask());
		}
		storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_REDO);
	}

}
