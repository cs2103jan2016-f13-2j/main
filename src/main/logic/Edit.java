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

//@@author A0125255L

public class Edit implements Command {

	private static final String MSG_SUCCESS_EDIT = "Editted task successfully.";
	private static final String MSG_SUCCESS_UNDO = "Undid previous command.";
	private static final String MSG_SUCCESS_REDO = "Redid previous command.";
	private static final String MSG_INVALID_EDIT_TYPE = "Error: Invalid edit category.";
	private static final String MSG_FAIL_NO_START_DATE = "Error: Cannot add end date to floating task without start date.";
	private static final String MSG_FAIL_NO_START_TIME = "Error: Cannot add end time to floating task without start time.";
	private static final String MSG_FAIL_FILE_SAVE = "Error: File could not be saved after edit command.";
	private static final String MSG_FAIL_INVALID_DATE = "Error: Invalid date/time entered.";
	private static final String MSG_FAIL_START_DATE_LATER_THAN_END_DATE = "Error: End date/time is earlier than start date/time";

	private static final int EDIT_TASK_DETAIL = 1;
	private static final int EDIT_TASK_START_DATE = 2;
	private static final int EDIT_TASK_START_TIME = 3;
	private static final int EDIT_TASK_END_DATE = 4;
	private static final int EDIT_TASK_END_TIME = 5;
	private static final int EDIT_TASK_LOCATION = 6;
	private static final int EDIT_TASK_PRIORITY = 7;
	private static final int EDIT_TASK_COMPLETE = 8;

	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	private ArrayList<Task> recurList;
	private static Logger logger = Logger.getLogger("Edit");

	/**
	 * Constructs an Edit command
	 * @param userInput: UserInput instance from MainLogic
	 */
	public Edit(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
		recurList = new ArrayList<Task>();
	}	

	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		boolean success = false;
		boolean changedTaskType = false;
		logger.log(Level.INFO, "Command EDIT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		Task taskToEdit = userInput.getTaskToEdit();
		userInput.setTaskToEdit(taskToEdit);
		Task newTask = null;


		if (taskToEdit.isRecurring() && userInput.getIsAll()) {
			Task t = taskToEdit.getHead();
			userInput.setRecurList(t.getRecurList());
			for (int i=0; i<t.getRecurList().size(); i++) {
				newTask = Task.duplicateTask(t.getRecurList().get(i));
				editTask(success, changedTaskType, displayList, t.getRecurList().get(i), newTask);
			}
			userInput.setTask(taskToEdit);
			t.setRecurList(recurList);
		}

		else if (taskToEdit.isRecurring()) {
			newTask = Task.duplicateTask(taskToEdit);
			editTask(success, changedTaskType, displayList, taskToEdit, newTask);
			userInput.setTask(newTask);
			taskToEdit.getHead().getRecurList().remove(taskToEdit);
			taskToEdit.getHead().getRecurList().add(newTask);
		}

		else {
			newTask = Task.duplicateTask(taskToEdit);
			editTask(success, changedTaskType, displayList, taskToEdit, newTask);
			userInput.setTask(newTask);
		}

		Date startDate = newTask.getTaskStartDate();
		Time startTime = newTask.getTaskStartTime();
		Date endDate = newTask.getTaskEndDate();
		Time endTime = newTask.getTaskEndTime();

		if (userInput.getStartDate().isValid()) {
			startDate = userInput.getStartDate();
		}
		if (userInput.getStartTime().isValid()) {
			startTime = userInput.getStartTime();
		}
		if (userInput.getEndDate() .isValid()) {
			endDate = userInput.getEndDate();
		}
		if (userInput.getEndTime().isValid()) {
			endTime = userInput.getEndTime();
		}

		if (!isValidDateAndTime(startDate, startTime, endDate, endTime)) {
			feedback.setMessage(MSG_FAIL_INVALID_DATE);
			return;
		}
		if (isEndDateEarlierThanStartDate(startDate, startTime, endDate, endTime)) {
			feedback.setMessage(MSG_FAIL_START_DATE_LATER_THAN_END_DATE);
			return;
		}
	}

	/**
	 * Executes the task edit
	 * @param success: returns true if editTask runs without error
	 * @param changedTaskType: returns true if taskType is changed previously
	 * @param displayList: displayList from MainLogic
	 * @param taskToEdit: Task to be edited
	 * @param newTask: Task to change to
	 */
	private void editTask(boolean success, boolean changedTaskType, ArrayList<Task> displayList, Task taskToEdit,
			Task newTask) {
		for (int i=1; i<userInput.getEditNumber().size(); i++) {
			switch (userInput.getEditNumber().get(i)) {
			case EDIT_TASK_DETAIL:	{	//task detail
				newTask.setTaskDetails(userInput.getDetails());
				success = true;
				break;
			}

			case EDIT_TASK_START_DATE: {	//task start date
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

			case EDIT_TASK_START_TIME:	{	//task start time
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
			case EDIT_TASK_END_DATE:	{	//task end date		
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
			case EDIT_TASK_END_TIME:	{	//task end time
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
			case EDIT_TASK_LOCATION: {	//task location
				newTask.setTaskLocation(userInput.getLocation());
				success = true;
				break;
			}
			case EDIT_TASK_PRIORITY:	{	//task priority
				newTask.setPriority(userInput.getPriority());
				success = true;
				break;
			}
			case EDIT_TASK_COMPLETE: {	//is complete			
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
				feedback.setMessage(String.format(MSG_SUCCESS_EDIT));
			}
		}

		recurList.add(newTask);
		taskList.remove(taskToEdit);
		taskList.add(newTask);
		if (!displayList.equals(taskList)) {
			displayList.remove(taskToEdit);
			displayList.add(newTask);
		}
	}

	//@@author A0124711U
	/**
	 * Checks if the dates in the tasks are valid and the end date is not earlier than the start date.
	 * @param startDate : the start date object.
	 * @param startTime : the start time object.
	 * @param endDate : the end date object.
	 * @param endTime : the end time object.
	 * @return true if dates are valid, false otherwise.
	 */
	private static boolean isValidDateAndTime(Date startDate, Time startTime, Date endDate, Time endTime) {
		if (startDate != null && !startDate.isValid() || endDate != null && !endDate.isValid()) {
			return false;
		}

		if (startTime != null && !startTime.isValid() || endTime != null && !endTime.isValid()) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the end date/time is earlier than the start date/time.
	 * @param startDate : the start date object.
	 * @param startTime : the start time object.
	 * @param endDate : the end date object.
	 * @param endTime : the end time object.
	 * @return true if the end date/time is earlier than start date/time, false otherwise.
	 */
	private static boolean isEndDateEarlierThanStartDate(Date startDate, Time startTime, Date endDate, Time endTime) {
		assert(isValidDateAndTime(startDate, startTime, endDate, endTime));

		if (startDate != null && endDate != null) {
			if (startDate.compareTo(endDate) > 0) {
				return true;
			}
			else if (startTime != null && endTime != null && startTime.compareTo(endTime) > 0) {
				return true;
			}
		}

		return false;
	}

	//@@author A0125255L
	/**
	 * Undo the command
	 */
	@Override
	public void undo() {
		logger.log(Level.INFO, "Command UNDO EDIT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();

		if (userInput.getTask().isRecurring() && userInput.getIsAll()) {
			Task t = userInput.getTask().getHead();
			for (int i=0; i<t.getRecurList().size(); i++) {
				taskList.remove(t.getRecurList().get(i));
				taskList.add(userInput.getRecurList().get(i));
				if (!displayList.equals(taskList)) {
					displayList.remove(t.getRecurList().get(i));
					displayList.add(userInput.getRecurList().get(i));
				}
			}

			ArrayList<Task> swapList = userInput.getRecurList();
			userInput.setRecurList(t.getRecurList());
			t.setRecurList(swapList);
		}
		else {
			taskList.remove(userInput.getTask());
			taskList.add(userInput.getTaskToEdit());
			if (!displayList.equals(taskList)) {
				displayList.remove(userInput.getTask());
				displayList.add(userInput.getTaskToEdit());
			}		
		}
		storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_UNDO);
	}

	/**
	 * Redo the command
	 */
	@Override
	public void redo() {
		logger.log(Level.INFO, "Command REDO EDIT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();

		if (userInput.getTask().isRecurring() && userInput.getIsAll()) {
			Task t = userInput.getTask().getHead();
			for (int i=0; i<t.getRecurList().size(); i++) {
				taskList.remove(t.getRecurList().get(i));
				taskList.add(userInput.getRecurList().get(i));
				if (!displayList.equals(taskList)) {
					displayList.remove(t.getRecurList().get(i));
					displayList.add(userInput.getRecurList().get(i));
				}
			}

			ArrayList<Task> swapList = userInput.getRecurList();
			userInput.setRecurList(t.getRecurList());
			t.setRecurList(swapList);
		}
		else {
			taskList.remove(userInput.getTaskToEdit());
			taskList.add(userInput.getTask());
			if (!displayList.equals(taskList)) {
				displayList.remove(userInput.getTaskToEdit());
				displayList.add(userInput.getTask());
			}

			
		}
		storage.saveFile();
		
		feedback.setMessage(MSG_SUCCESS_REDO);
	}
}
