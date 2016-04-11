package main.logic;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.storage.Storage;
import main.parser.Parser;
import main.parser.Shortcuts;
import main.resources.Task;
import main.resources.Time;
import main.resources.Date;
import main.resources.Feedback;
import main.resources.UserInput;

//@@author A0125255L

public class MainLogic {

	//-----Variables-----

	//Feedback Strings
	private static final String MSG_FAIL_INVALID_DATE = "Error: Invalid date/time entered.";
	private static final String MSG_FAIL_START_DATE_LATER_THAN_END_DATE = "Error: End date/time is earlier than start date/time";
	private static final int EVENT = 1;
	private static final int FLOATING = 2;
	private static final int DEADLINE = 4;
	private static final int SORT_TYPE_DATE = 2;
	
	//Global Variables
	private static Storage storage;
	private static Feedback feedback;
	private static ArrayList<Task> taskList;
	private static ArrayList<Task> displayList;
	private static Command command;
	private static UserInput userInput;
	private static MainLogic mainLogic;
	private static int sortType;
	private static Stack<Command> commandList;
	private static Stack<Command> undoedCommandList;
	private static Date currentDate;
	private static Time currentTime;
	private static boolean success;

	static Logger logger = Logger.getLogger("MainLogic");

	//-----Constructor-----
	/**
	 * Creates an instance of MainLogic and initializes variables
	 */
	public MainLogic() {
		//Initialize variables
		storage = Storage.getInstance();
		feedback = Feedback.getInstance();
		updateTaskList();
		setDisplayList(taskList);
		sortType = SORT_TYPE_DATE;
		commandList = new Stack<Command>();
		undoedCommandList = new Stack<Command>();
		currentDate = null;
		currentTime = null;
	}

	/**
	 * Runs the MainLogic
	 * throws exception if any component fails to complete
	 * @param input: Default UserInput object containing only raw data
	 */
	public static void run(UserInput input) {
		logger.log(Level.INFO, "MainLogic START");
		success = true;
		try {
			initializeMainLogic();
			setUserInput(input);
			runParser();
			createCommandObject();
			executeCommand();
			updateTaskList();
			logger.log(Level.INFO, "MainLogic END");
		} catch (Exception e) {
			logger.log(Level.WARNING, "MainLogic ERROR");
			success = false;
		}
	}

	//-----Private methods-----
	/**
	 * Creates a Command object with the respective command type
	 */
	private static void createCommandObject() throws Exception {
		if (!isValidDateAndTime()) {
			feedback.setMessage(MSG_FAIL_INVALID_DATE);
			throw new Exception("Invalid date format");
		}
		if (isEndDateEarlierThanStartDate()) {
			feedback.setMessage(MSG_FAIL_START_DATE_LATER_THAN_END_DATE);
			throw new Exception("End date/time is earlier than start date/time");
		}
		
		switch (getCommand()) {
		case "add": {
			addCommand();		
			break;
		}
		case "delete": {
			deleteCommand();
			break;
		}
		case "edit": {
			editCommand();
			break;
		}

		case "display": {
			displayCommand();
			break;
		}

		case "sort": {
			sortCommand();
			break;
		}

		case "search": {
			searchCommand();
			break;
		}

		case "recurring": {
			addCommand();
			break;
		}

		case "home": {
			homeCommand();
			break;
		}

		case "undo": {
			undoCommand();
			break;
		}

		case "redo": {
			redoCommand();
			break;
		}

		case "import": {
			importCommand();
			break;
		}

		case "export": {
			exportCommand();
			break;
		}

		case "complete": {
			completeCommand();
			break;
		}

		case "uncomplete": {
			completeCommand();
			break;
		}

		default: {
			defaultSearch();
			break;
		}
		}
	}
	
	/**
	 * Creates the "Add" command object
	 * Push the Object to the commandList
	 */
	private static void addCommand() {
		command = new Add(userInput);
		commandList.push(command);
		clearUndoStack();
		displayList = storage.getTaskList();
	}
	
	/**
	 * Creates the "Delete" command object
	 * Push the Object to the commandList
	 */
	private static void deleteCommand() {
		userInput.setTaskToDelete(findDeleteTask());
		command = new Delete(userInput);
		commandList.push(command);
		clearUndoStack();
	}

	/**
	 * Creates the "Edit" command object
	 * Push the Object to the commandList
	 */
	private static void editCommand() {
		userInput.setTaskToEdit(findEditTask());
		command = new Edit(userInput);
		commandList.push(command);
		clearUndoStack();
	}

	/**
	 * Creates the "Display" command object
	 * Sets the display sort type to be the current sort structure
	 */
	private static void displayCommand() {
		userInput.setSortType(sortType);
		command = new Display(userInput);
	}

	/**
	 * Creates the "Sort" command object
	 * Sets the sortType to the new sort structure
	 */
	private static void sortCommand() {
		command = new Sort(userInput);
		sortType = userInput.getSortType();
	}


	/**
	 * Creates the "Search" command object
	 */
	private static void searchCommand() {
		command = new Search(userInput);
	}

	/**
	 * Sets the displayList to the default storage taskList
	 * copyList(): new instance of storage taskList
	 */
	private static void homeCommand() {
		displayList = storage.getTaskList();
	}
	
	/**
	 * Undo the last executed Command object
	 * Removes the Object from the commandList and pushes the object to the
	 * undoedCommandList for redoing purposes
	 */
	private static void undoCommand() {
		if (!commandList.empty()) {
			Command command = commandList.pop();
			undoedCommandList.push(command);
			command.undo();
		}
	}

	/**
	 * Redo the last undone Command object
	 * Removes the Object from the undoedCommandList and pushes the object to 
	 * the commandList for undoing purposes
	 */
	private static void redoCommand() {
		if (!undoedCommandList.empty()) {
			Command command = undoedCommandList.pop();
			commandList.push(command);
			command.redo();
		}
	}

	/**
	 * Imports the file through the file path given
	 */
	private static void importCommand() {
		storage.importFile(userInput.getPath());
		displayList = storage.getTaskList();
	}

	/**
	 * Exports the file through the file path given
	 */
	private static void exportCommand() {
		storage.exportFile(userInput.getPath());
	}

	/**
	 * Creates the "Complete" command object
	 * Push the Object to the commandList
	 */
	private static void completeCommand() {
		userInput.setTaskToDelete(completeTask());
		command = new Complete(userInput);
		commandList.push(command);
		clearUndoStack();
		displayList = storage.getTaskList();
	}

	/**
	 * Sets the default command to be search term
	 * Creates the "Search" command object
	 */
	private static void defaultSearch() {
		userInput.setSearchTerm(userInput.getCommand());
		searchCommand();
	}

	//@@author A0124711U
	/**
	 * Checks if the dates in the tasks are valid and the end date is not earlier than the start date.
	 * @return true if dates are valid, false otherwise.
	 */
	private static boolean isValidDateAndTime() {
		if (userInput.getTask() == null) {
			return true;
		}
		
		Date startDate = userInput.getTask().getTaskStartDate();
		Date endDate = userInput.getTask().getTaskEndDate();

		if (startDate != null && !startDate.isValid() || endDate != null && !endDate.isValid()) {
			return false;
		}
		
		Time startTime = userInput.getTask().getTaskStartTime();
		Time endTime = userInput.getTask().getTaskEndTime();
		
		if (startTime != null && !startTime.isValid() || endTime != null && !endTime.isValid()) {
			return false;
		}

		return true;
	}
	
	/**
	 * Checks if the end date/time is earlier than the start date/time.
	 * @return true if the end date/time is earlier than start date/time, false otherwise.
	 */
	private static boolean isEndDateEarlierThanStartDate() {
		if (userInput.getTask() == null) {
			return false;
		}
		
		assert(isValidDateAndTime());
		
		Date startDate = userInput.getTask().getTaskStartDate();
		Date endDate = userInput.getTask().getTaskEndDate();
		Time startTime = userInput.getTask().getTaskStartTime();
		Time endTime = userInput.getTask().getTaskEndTime();
		
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
	 * Calls the constructor of MainLogic if it doesn't exist
	 */
	private static void initializeMainLogic() {
		if (mainLogic == null) {
			MainLogic.createMainLogic();
		}
	}
	
	/**
	 * Sets the local userInput variable to the current userInput
	 * @param input: the userInput variable from TaskOverviewController
	 */
	private static void setUserInput(UserInput input) {
		userInput = input;
	}

	/**
	 * Updates the new userInput variable with parameters from Parser
	 */
	private static void runParser() {
		userInput = Parser.resetUserInput(userInput);
	}

	/**
	 * Executes the command object instance if it is not null 
	 */
	private static void executeCommand() {
		command.execute();
	}

	/**
	 * Updates the local variable taskList after Command object execution 
	 */
	private static void updateTaskList() {
		taskList = storage.getTaskList();
	}

	/**
	 * Gets the command intended by the input string from TaskOverviewController
	 * @return String object of Command type
	 */
	private static String getCommand() {
		return Shortcuts.shortcuts(userInput.getCommand().toLowerCase());
	}
	
	/**
	 * Clears the undo Command stack
	 */
	private static void clearUndoStack() {
		undoedCommandList = new Stack<Command>();
	}
	
	/**
	 * Searches for the Task object the user intend to edit
	 * @return Task if task is present in list
	 */
	private static Task findEditTask() {
		int count = 0;
		//ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0; i<getList().size(); i++) {
			Task task = getList().get(i);
			if (task.getTaskType() == userInput.getTaskType()) {
				count++;
				if(count == userInput.getEditNumber().get(0)) {
					return task;
				}
			}
		}

		return null;
	}

	/**
	 * Searches all the Tasks the user intend to delete
	 * @return ArrayList of Tasks if Task is present in list
	 */
	private static ArrayList<Task> findDeleteTask() {
		int count;
		ArrayList<Task> list = new ArrayList<Task>();
		int taskType = 0;
		int taskNumber = 0;

		for (int i=0; i<userInput.getDeleteNumber().size(); i++) {
			count = 0;
			taskType = userInput.getDeleteNumber().get(i)[0];
			taskNumber = userInput.getDeleteNumber().get(i)[1];

			for (int j=0; j<getList().size(); j++) {
				Task task = getList().get(j);
				if (task.getTaskType() == taskType) {
					count++;
					if(count == taskNumber) {
						list.add(task);
					}
				}
			}
		}
		
		return list;
	}

	/**
	 * Searches the Tasks the user intend to complete/uncomplete
	 * @return ArrayList of Tasks if Tasks is present in list
	 */
	private static ArrayList<Task> completeTask() {
		int count;
		int taskType = 0;
		int taskNumber = 0;
		ArrayList<Task> list = new ArrayList<Task>();;
		for (int i=0; i<userInput.getDeleteNumber().size(); i++) {
			count = 0;
			taskType = userInput.getDeleteNumber().get(i)[0];
			taskNumber = userInput.getDeleteNumber().get(i)[1];

			for (int j=0; j<getList().size(); j++) {
				Task task = getList().get(j);
				if (task.getTaskType() == taskType) {
					count++;
					if(count == taskNumber) {
						list.add(task);
					}
				}
			}
		}

		return list;
	}

	/**
	 * Gets and sets the local currentTime variable to the console time
	 */
	private static void setCurrentTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		currentTime = new Time(hour, minute);   
	}

	/**
	 * Gets and sets the local currentDate variable to the console date
	 */
	private static void setCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		currentDate = new Date(day, month, year);
	}

	/**
	 * Creates a mainLogic instance
	 */
	private static void createMainLogic() {
		mainLogic = new MainLogic();
	}

	/**
	 * Gets the current taskList user is viewing from
	 * @return ArrayList of Task user is currently viewing
	 */
	private static ArrayList<Task> getList() {
		switch (userInput.getTab()) {
		case 1: {	//All
			return getTaskListUnfiltered();
		}
		case 2: {	//Today
			return getTodayTasksUnfiltered();
		}
		case 3: {	//Upcoming
			return getWeekTasksUnfiltered();
		}
		case 4: {	//Complete
			return getCompletedTasksUnfiltered();
		}
		case 5: {	//Overdue/Expired
			return getExpiredTasksUnfiltered();
		}
		default: {
			return null;
		}
		}
	}


	//-----Public Methods-----
	/**
	 * Sets the current time and returns the Time object
	 * @return Time object of current time
	 */
	public static Time getCurrentTime() {
		setCurrentTime();
		return currentTime;
	}

	/**
	 * Sets the current date and returns the Date object
	 * @return return Date object of current date
	 */
	public static Date getCurrentDate() {
		setCurrentDate();
		return currentDate;
	}

	/**
	 * Gets the current day
	 * @return String representation of current day
	 */
	public static String getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", 
				"Friday", "Saturday"};
		return days[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * Checks if the MainLogic runs to the end without errors
	 * @return true if successful
	 */
	public static boolean isSuccessful() {
		return success;
	}

	/**
	 * Sets the displayList to a newList
	 * @param newList: list to change the displayList to
	 */
	public static void setDisplayList(ArrayList<Task> newList) {
		displayList = newList;
	}

	/**
	 * Gets the current displayList
	 * @return ArrayList of Task of current displayList
	 */
	public static ArrayList<Task> getDisplayList() {
		return displayList;
	}

	/**
	 * Filters the given list to 3 different components: Deadline, Floating, Event
	 * @param list: list to be filtered
	 * @return ArrayList of ArrayList of Task Object of filtered components
	 */
	private static ArrayList<ArrayList<Task>> getFilteredList(ArrayList<Task> list) {
		ArrayList<ArrayList<Task>> newList = new ArrayList<ArrayList<Task>>();
		ArrayList<Task> eventList = new ArrayList<Task>();
		ArrayList<Task> floatList = new ArrayList<Task>();
		ArrayList<Task> deadlineList = new ArrayList<Task>();
		setCurrentDate();
		setCurrentTime();
		
		for (int i=0; i<list.size(); i++) {
			Task task = checkExpired(list, i);
			filterTask(eventList, floatList, deadlineList, task);
		}

		createList(newList, eventList, floatList, deadlineList);
		return newList;
	}

	/**
	 * Creates the list containing: Deadline, Floating, Event
	 * @param newList: List to be created
	 * @param eventList: Event list
	 * @param floatList: Float list
	 * @param deadlineList: Deadline list
	 */
	private static void createList(ArrayList<ArrayList<Task>> newList, ArrayList<Task> eventList,
			ArrayList<Task> floatList, ArrayList<Task> deadlineList) {
		newList.add(eventList);
		newList.add(floatList);
		newList.add(deadlineList);
		storage.saveFile();
	}

	/**
	 * Filter the task into the respective Deadline, Floating, Event lists
	 * @param eventList: Event list
	 * @param floatList: Float list
	 * @param deadlineList: Deadline list
	 * @param task: Task to be added
	 */
	private static void filterTask(ArrayList<Task> eventList, ArrayList<Task> floatList, ArrayList<Task> deadlineList,
			Task task) {
		switch (task.getTaskType()) {
		case EVENT: {	//event
			eventList.add(task);
			break;
		}
		case FLOATING: {	//floating
			floatList.add(task);
			break;
		}
		case DEADLINE: {	//deadline
			deadlineList.add(task);
			break;
		}
		}
	}

	/**
	  * Check whether the task is expired
	 * @param list: taskList from Storage
	 * @param i: Task object position in list
	 * @return Task object after determining whether to toggle expired variable
	 */
	private static Task checkExpired(ArrayList<Task> list, int i) {
		Task task = list.get(i);
		if (task.getTaskStartDate() != null && task.getTaskStartDate().compareTo(currentDate) < 0) {
				task.setExpired(true);
		}

		else if (task.getTaskStartDate() != null &&
				task.getTaskStartDate().compareTo(currentDate) == 0 && 
				task.getTaskStartTime() != null && 
				task.getTaskStartTime().compareTo(currentTime) < 0) {
				task.setExpired(true);
		}
		
		else {
			task.setExpired(false);
		}
		return task;
	}

	//Lists
	/**
	 * Gets the filtered "All" taskList
	 * @return ArrayList of ArrayList of Tasks of filtered list
	 */
	public static ArrayList<ArrayList<Task>> getTaskList() {
		return getFilteredList(getTaskListUnfiltered());
	}

	/**
	 * Gets the filtered "Completed" taskList
	 * @return ArrayList of ArrayList of Tasks of filtered list
	 */
	public static ArrayList<ArrayList<Task>> getCompletedTasks() {
		return getFilteredList(getCompletedTasksUnfiltered());
	}

	/**
	 * Gets the filtered "Today" taskList
	 * @return ArrayList of ArrayList of Tasks of filtered list
	 */
	public static ArrayList<ArrayList<Task>> getTodayTasks() {
		return getFilteredList(getTodayTasksUnfiltered());
	}

	/**
	 * Gets the filtered "Expired" taskList
	 * @return ArrayList of ArrayList of Tasks of filtered list
	 */
	public static ArrayList<ArrayList<Task>> getExpiredTasks() {
		return getFilteredList(getExpiredTasksUnfiltered());
	}

	/**
	 * Gets the filtered "Upcoming" taskList
	 * @return ArrayList of ArrayList of Tasks of filtered list
	 */
	public static ArrayList<ArrayList<Task>> getWeekTasks() {
		return getFilteredList(getWeekTasksUnfiltered());
	}

	/**
	 * Gets the unfiltered "All" taskList
	 * @return ArrayList of Tasks of unfiltered list
	 */
	public static ArrayList<Task> getTaskListUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if (!task.isComplete()) {
				list.add(task);
			}
		}
		return list;
	}

	/**
	 * Gets the unfiltered "Completed" taskList
	 * @return ArrayList of Tasks of unfiltered list
	 */
	public static ArrayList<Task> getCompletedTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if (task.isComplete()) {
				list.add(task);
			}
		}
		return list;
	}
	
	/**
	 * Gets the unfiltered "Today" taskList
	 * @return ArrayList of Tasks of unfiltered list
	 */
	public static ArrayList<Task> getTodayTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if ((task.getTaskStartDate() != null && 
					(task.getTaskStartDate().compareTo(getCurrentDate()) == 0) &&
						!task.isComplete()) ||
							task.getTaskType() == FLOATING) {
				list.add(task);
			}
		}

		return list;
	}

	/**
	 * Gets the unfiltered "Expired" taskList
	 * @return ArrayList of Tasks of unfiltered list
	 */
	public static ArrayList<Task> getExpiredTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if ((task.isExpired() || task.getTaskType() == FLOATING) && !task.isComplete()) {
				list.add(task);
			}
		}

		return list;
	}

	/**
	 * Gets the unfiltered "Upcoming" taskList
	 * @return ArrayList of Tasks of unfiltered list
	 */
	public static ArrayList<Task> getWeekTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		Date date = new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if ((task.getTaskStartDate() != null &&
					((task.getTaskStartDate().compareTo(getCurrentDate()) >= 0) && 
							(task.getTaskStartDate().compareTo(date) < 0)) &&
								!task.isComplete()) ||
									task.getTaskType() == FLOATING) {				
				list.add(task);
			}
		}

		return list;
	}
}
