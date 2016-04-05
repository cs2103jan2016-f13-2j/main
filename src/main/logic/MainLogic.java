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
import main.resources.UserInput;



public class MainLogic {

	//-----Variables-----

	//Global Variables
	private static Storage storage;
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
	public MainLogic() {
		//Initialize variables
		storage = Storage.getInstance();
		updateTaskList();
		setDisplayList(taskList);
		sortType = 2;
		commandList = new Stack<Command>();
		undoedCommandList = new Stack<Command>();
		currentDate = null;
		currentTime = null;
	}
	
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
		} catch (Exception e) {
			logger.log(Level.WARNING, "MainLogic ERROR");
			success = false;
			e.printStackTrace();
		}
		logger.log(Level.INFO, "MainLogic END");
	}
	
	//-----Private methods-----
	private static void createCommandObject() {
		switch (getCommand()) {
		case "add": {
			command = new Add(userInput);
			commandList.push(command);
			clearUndoStack();
			displayList = storage.getTaskList();		
			break;
		}
		case "delete": {
			userInput.setTaskToDelete(findDeleteTask());
			command = new Delete(userInput);
			commandList.push(command);
			clearUndoStack();
			break;
		}
		case "edit": {
			userInput.setTaskToEdit(findEditTask());
			command = new Edit(userInput);
			commandList.push(command);
			clearUndoStack();
			break;
		}

		case "display": {
			userInput.setSortType(sortType);
			command = new Display(userInput);
			break;
		}
		
		case "sort": {
			command = new Sort(userInput);
			sortType = userInput.getSortType();
			break;
		}
		
		case "search": {
			command = new Search(userInput);
			break;
		}
		
		case "recurring": {
			command = new Add(userInput);
			commandList.push(command);
			clearUndoStack();
			displayList = storage.getTaskList();
			break;
		}
		
		case "home": {
			displayList = copyList();
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
			storage.importFile(userInput.getPath());
			displayList = storage.getTaskList();
			break;
		}
		
		case "export": {
			storage.exportFile(userInput.getPath());
			break;
		}
		
		case "complete": {
			completeTask(true);
			break;
		}
		
		case "uncomplete": {
			completeTask(false);
			break;
		}
		
		default: {
			userInput.setSearchTerm(userInput.getCommand());
			command = new Search(userInput);
			break;
		}
		}
	}
	
	private static void initializeMainLogic() {
		if (mainLogic == null) {
			MainLogic.createMainLogic();
		}
	}
	
	private static void setUserInput(UserInput input) {
		userInput = input;
	}
	
	private static void runParser() {
		userInput = Parser.resetUserInput(userInput);
	}
	
	private static void executeCommand() {
		command.execute();
	}

	private static void updateTaskList() {
		taskList = storage.getTaskList();
	}
	
	private static String getCommand() {
		return Shortcuts.shortcuts(userInput.getCommand().toLowerCase());
	}

	private static void undoCommand() {
		if (!commandList.empty()) {
			Command command = commandList.pop();
			undoedCommandList.push(command);
			command.undo();
		}
	}
	
	private static void clearUndoStack() {
		undoedCommandList = new Stack<Command>();
	}
	
	private static void redoCommand() {
		if (!undoedCommandList.empty()) {
			Command command = undoedCommandList.pop();
			commandList.push(command);
			command.redo();
		}
	}
	
	private static Task findEditTask() {
		int count = 0;
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
	
	private static void completeTask(boolean complete) {
		int count;
		int taskType = 0;
		int taskNumber = 0;
		//ArrayList<Task> list = null;
		
		for (int i=0; i<userInput.getDeleteNumber().size(); i++) {
			count = 0;
			//list = new ArrayList<Task>();
			taskType = userInput.getDeleteNumber().get(i)[0];
			taskNumber = userInput.getDeleteNumber().get(i)[1];
			
			for (int j=0; j<getList().size(); j++) {
				Task task = getList().get(j);
				if (task.getTaskType() == taskType) {
					count++;
					 if(count == taskNumber) {
						//list.add(task);
						task.setComplete(complete);
					 }
				}
			}
		}
	}
	
	private static ArrayList<Task> copyList() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0; i<storage.getTaskList().size(); i++) {
			list.add(storage.getTaskList().get(i));
		}
		
		return list;
	}
	
	private static void setCurrentTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		currentTime = new Time(hour, minute);   
	}
	
	private static void setCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		currentDate = new Date(day, month, year);
	}

	private static void createMainLogic() {
		mainLogic = new MainLogic();
	}
	
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
	public static Time getCurrentTime() {
		setCurrentTime();
		return currentTime;
	}
	
	public static Date getCurrentDate() {
		setCurrentDate();
		return currentDate;
	}
	
	public static String getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", 
				"Friday", "Saturday"};
		return days[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	public static boolean isSuccessful() {
		return success;
	}
	
	public static void setDisplayList(ArrayList<Task> newList) {
		displayList = newList;
	}
	
	public static ArrayList<Task> getDisplayList() {
		return displayList;
	}
	
	private static ArrayList<ArrayList<Task>> getFilteredList(ArrayList<Task> list) {
		ArrayList<ArrayList<Task>> newList = new ArrayList<ArrayList<Task>>();
		ArrayList<Task> eventList = new ArrayList<Task>();
		ArrayList<Task> floatList = new ArrayList<Task>();
		ArrayList<Task> deadlineList = new ArrayList<Task>();
		
		for (int i=0; i<list.size(); i++) {
			Task task = list.get(i);
			setCurrentTime();
			setCurrentDate();
			Calendar cal = Calendar.getInstance();
			if (task.getTaskStartDate() != null && task.getTaskStartDate().compareTo(currentDate) < 0) {
				if (task.isRecurring() && task.getRecurTime() > 0) {
					task.setRecurTime(task.getRecurTime() - 1);
					Date date = task.getTaskStartDate();
					cal.set(date.getYear(), date.getMonth(), date.getDay());
					cal.add(Calendar.DAY_OF_MONTH, 1);
					switch (task.getRecurFrequency()) {
					case 1: {	//daily
						cal.add(Calendar.DAY_OF_MONTH, 1);
						task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
															cal.get(Calendar.MONTH), 
																cal.get(Calendar.YEAR)));
						break;
					}
					case 2: {	//weekly
						cal.add(Calendar.DAY_OF_MONTH, 7);
						task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
															cal.get(Calendar.MONTH), 
																cal.get(Calendar.YEAR)));
						break;
					}
					case 3: {	//monthly
						cal.add(Calendar.MONTH, 1);
						task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
															cal.get(Calendar.MONTH), 
																cal.get(Calendar.YEAR)));
						break;
					}
					case 4: {	//yearly
						cal.add(Calendar.YEAR, 1);
						task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
															cal.get(Calendar.MONTH), 
																cal.get(Calendar.YEAR)));
						break;
					}
					}
				}
				
				else {
					task.setExpired(true);
				}
			}
			
			else if (task.getTaskStartDate() != null &&
						task.getTaskStartDate().compareTo(currentDate) == 0 && 
							task.getTaskStartTime() != null && 
								task.getTaskStartTime().compareTo(currentTime) < 0) {
				if (task.isRecurring()) {
					if (task.isRecurring() && task.getRecurTime() > 0) {
						task.setRecurTime(task.getRecurTime() - 1);
						Date date = task.getTaskStartDate();
						cal.set(date.getYear(), date.getMonth(), date.getDay());
						cal.add(Calendar.DAY_OF_MONTH, 1);
						switch (task.getRecurFrequency()) {
						case 1: {	//daily
							cal.add(Calendar.DAY_OF_MONTH, 1);
							task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
																cal.get(Calendar.MONTH), 
																	cal.get(Calendar.YEAR)));
							break;
						}
						case 2: {	//weekly
							cal.add(Calendar.DAY_OF_MONTH, 7);
							task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
																cal.get(Calendar.MONTH), 
																	cal.get(Calendar.YEAR)));
							break;
						}
						case 3: {	//monthly
							cal.add(Calendar.MONTH, 1);
							task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
																cal.get(Calendar.MONTH), 
																	cal.get(Calendar.YEAR)));
							break;
						}
						case 4: {	//yearly
							cal.add(Calendar.YEAR, 1);
							task.setTaskStartDate(new Date(cal.get(Calendar.DAY_OF_MONTH), 
																cal.get(Calendar.MONTH), 
																	cal.get(Calendar.YEAR)));
							break;
						}
						}
					}
				}
				
				else {
					task.setExpired(true);
				}
			}
			
			switch (task.getTaskType()) {
			case 1: {	//event
				eventList.add(task);
				break;
			}
			case 2: {	//floating
				floatList.add(task);
				break;
			}
			case 4: {	//deadline
				deadlineList.add(task);
				break;
			}
			}
		}
		
		newList.add(eventList);
		newList.add(floatList);
		newList.add(deadlineList);
		storage.saveFile();
		return newList;
	}
	
	//Lists
	public static ArrayList<ArrayList<Task>> getTaskList() {
		return getFilteredList(getTaskListUnfiltered());
	}
	
	public static ArrayList<ArrayList<Task>> getCompletedTasks() {
		return getFilteredList(getCompletedTasksUnfiltered());
	}

	public static ArrayList<ArrayList<Task>> getTodayTasks() {
		return getFilteredList(getTodayTasksUnfiltered());
	}
	
	public static ArrayList<ArrayList<Task>> getExpiredTasks() {
		return getFilteredList(getExpiredTasksUnfiltered());
	}
	
	public static ArrayList<ArrayList<Task>> getWeekTasks() {
		return getFilteredList(getWeekTasksUnfiltered());
	}
	
	public static ArrayList<Task> getTaskListUnfiltered() {
		return displayList;
	}
	
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
	
	public static ArrayList<Task> getTodayTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if (task.getTaskStartDate() != null && 
					task.getTaskStartDate().compareTo(getCurrentDate()) == 0 ||
						task.getTaskType() == 2) {
				list.add(task);
			}
		}
		
		return list;
	}
	
	public static ArrayList<Task> getExpiredTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if (task.isExpired() || task.getTaskType() == 2) {
				list.add(task);
			}
		}
		
		return list;
	}
	
	public static ArrayList<Task> getWeekTasksUnfiltered() {
		ArrayList<Task> list = new ArrayList<Task>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		Date date = new Date(cal.get(Calendar.DAY_OF_MONTH),
								cal.get(Calendar.MONTH), 
									cal.get(Calendar.YEAR));
		for (int i=0 ;i<displayList.size(); i++) {
			Task task = displayList.get(i);
			if (task.getTaskStartDate() != null && (task.getTaskStartDate().compareTo(getCurrentDate()) >= 0 ||
					task.getTaskStartDate().compareTo(date) < 0)  || task.getTaskType() == 2) {
				list.add(task);
			}
		}
		
		return list;
	}
}
