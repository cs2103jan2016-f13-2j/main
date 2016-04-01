package main.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;
import main.resources.TaskComparator;
import main.resources.UserInput;
import main.storage.Storage;

public class Sort implements Command {

	private static final String MSG_SUCCESS = "Sorted tasks by %1$s.";
	private static final String MSG_FAIL_INVALID_SORT_TYPE = "Error: \"%1$s\" is an invalid sort category.";

	private static final String TYPE_DETAIL = "details";
	private static final String TYPE_START_DATE = "date";
	private static final String TYPE_START_TIME = "time";
	private static final String TYPE_LOCATION = "location";
	private static final String TYPE_PRIORITY = "priority";

	private UserInput userInput;
	private static Storage storage;
	private static Feedback feedback;
	private ArrayList<Task> taskList;
	private static Logger logger = Logger.getLogger("Sort");

	public Sort(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command SORT");
		taskList = storage.getTaskList();
		switch (userInput.getSortType()) {
		case 1:	//task details
			Collections.sort(taskList, new TaskComparator(1));
			break;

		case 2:	//task date
			Collections.sort(taskList, new TaskComparator(2));
			break;

		case 3:	//task time
			Collections.sort(taskList, new TaskComparator(3));
			break;

		case 6:	//task location
			Collections.sort(taskList, new TaskComparator(6));
			break;

		case 7:	//task priority
			Collections.sort(taskList, new TaskComparator(7));
			break;

		default:
			feedback.setMessage(String.format(MSG_FAIL_INVALID_SORT_TYPE, feedback.getSortString()));
			return;
		}
		
		feedback.setMessage(String.format(MSG_SUCCESS, getSortCategoryString()));
		
		storage.saveFile();

		userInput.setTaskList(taskList);
	}

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

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

	}

}
