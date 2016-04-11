package main.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.TaskComparator;
import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L

public class Sort implements Command {

	private static final String MSG_SUCCESS_SORT = "Sorted tasks by %1$s.";
	private static final String MSG_FAIL_INVALID_SORT_TYPE = "Error: \"%1$s\" is an invalid sort category.";

	private static final String TYPE_DETAIL = "details";
	private static final String TYPE_START_DATE = "date";
	private static final String TYPE_START_TIME = "time";
	private static final String TYPE_LOCATION = "location";
	private static final String TYPE_PRIORITY = "priority";
	
	private static final int SORT_TYPE_DETAILS = 1;
	private static final int SORT_TYPE_DATE = 2;
	private static final int SORT_TYPE_TIME = 3;
	private static final int SORT_TYPE_LOCATION = 6;
	private static final int SORT_TYPE_PRIORITY = 7;

	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	private static Logger logger = Logger.getLogger("Sort");

	/**
	 * Constructs a Sort command
	 * @param userInput: userInput instance from MainLogic
	 */
	public Sort(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}

	/**
	 * Executes the command
	 */
	@Override
	public void execute() {
		logger.log(Level.INFO, "Command SORT");
		taskList = storage.getTaskList();
		ArrayList<Task> displayList = MainLogic.getDisplayList();
		switch (userInput.getSortType()) {
		case SORT_TYPE_DETAILS:	//task details
			Collections.sort(taskList, new TaskComparator(SORT_TYPE_DETAILS));
			Collections.sort(displayList, new TaskComparator(SORT_TYPE_DETAILS));
			break;

		case SORT_TYPE_DATE:	//task date
			Collections.sort(taskList, new TaskComparator(SORT_TYPE_DETAILS));
			Collections.sort(displayList, new TaskComparator(SORT_TYPE_DETAILS));
			break;

		case SORT_TYPE_TIME:	//task time
			Collections.sort(taskList, new TaskComparator(SORT_TYPE_TIME));
			Collections.sort(displayList, new TaskComparator(SORT_TYPE_TIME));
			break;

		case SORT_TYPE_LOCATION:	//task location
			Collections.sort(taskList, new TaskComparator(SORT_TYPE_LOCATION));
			Collections.sort(displayList, new TaskComparator(SORT_TYPE_LOCATION));
			break;

		case SORT_TYPE_PRIORITY:	//task priority
			Collections.sort(taskList, new TaskComparator(SORT_TYPE_PRIORITY));
			Collections.sort(displayList, new TaskComparator(SORT_TYPE_PRIORITY));
			break;

		default:
			feedback.setMessage(String.format(MSG_FAIL_INVALID_SORT_TYPE, feedback.getSortString()));
			return;
		}
		feedback.setMessage(String.format(MSG_SUCCESS_SORT, getSortCategoryString()));

		storage.saveFile();
		MainLogic.setDisplayList(displayList);

		userInput.setTaskList(taskList);
	}

	//@@author A0124711U
		/**
		 * Returns the type of task as a string description.
		 * @return The string description of the task type.
		 */
	public String getSortCategoryString() {
		String type = null;

		switch (userInput.getSortType()) {
		case 1://detail
			type = TYPE_DETAIL;
			break;

		case 2://start date
			type = TYPE_START_DATE;
			break;

		case 3://start time
			type = TYPE_START_TIME;
			break;

		case 6://location
			type = TYPE_LOCATION;
			break;

		case 7://priority
			type = TYPE_PRIORITY;
			break;

		default:
		}

		return type;
	}

	//@@author A0125255L
	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

	}

}
