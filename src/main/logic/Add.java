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
import sun.util.resources.cldr.aa.CalendarData_aa_ER;

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
	
	private static final int RECUR_TYPE_DAILY = 1;
	private static final int RECUR_TYPE_WEEKLY = 2;
	private static final int RECUR_TYPE_MONTHLY = 3;
	private static final int RECUR_TYPE_YEARLY = 4;
	
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
			if (task.getRecurTime() <= 0) {
				feedback.setMessage(MSG_FAIL_INVALID_RECUR_FREQ);
				return;
			}
			task.setHead(task);
			Task newTask = Task.duplicateTask(task);
			recurList.add(newTask);
			taskList.add(newTask);
			task.setRecurTime(task.getRecurTime() - 1);
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			while (task.getRecurTime() > 0) {
				updateDate(task, calStart, calEnd);
				generateRecurTasks(task, calStart, calEnd);
				updateList(task);
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

	/**
	 * Updates the taskList and recurList to the list of all recurring tasks
	 * @param task: Task to be added to the list
	 */
	private void updateList(Task task) {
		Task newTask;
		newTask = Task.duplicateTask(task);
		recurList.add(newTask);
		taskList.add(newTask);
	}

	/**
	 * Updates the date for the recurring task
	 * @param task: Task to be updated
	 * @param calStart: Start date of task
	 * @param calEnd: End date of task
	 */
	private void updateDate(Task task, Calendar calStart, Calendar calEnd) {
		task.setRecurTime(task.getRecurTime() - 1);
		Date dateStart = task.getTaskStartDate();
		calStart.set(dateStart.getYear(), dateStart.getMonth(), dateStart.getDay());
		Date dateEnd;
		if (task.getTaskEndDate() != null) {
			dateEnd = task.getTaskEndDate();
			calEnd.set(dateEnd.getYear(), dateEnd.getMonth(), dateEnd.getDay());
		}
	}

	/**
	 * Creates an instance after updating the new recurring date
	 * @param task: Task to update the date to
	 * @param calStart: Start date of task
	 * @param calEnd: End date of task
	 */
	private void generateRecurTasks(Task task, Calendar calStart, Calendar calEnd) {
		switch (task.getRecurFrequency()) {
		case RECUR_TYPE_DAILY: {	//daily
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
		case RECUR_TYPE_WEEKLY: {	//weekly
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
		case RECUR_TYPE_MONTHLY: {	//monthly
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
		case RECUR_TYPE_YEARLY: {	//yearly
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
	}
	
	//@@author A0124711U
		/**
		 * Returns the type of task as a string description.
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
