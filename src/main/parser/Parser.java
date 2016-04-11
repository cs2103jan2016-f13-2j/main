	

package main.parser;

import java.util.ArrayList;

import main.resources.Date;
import main.resources.Feedback;
import main.resources.Task;
import main.resources.Time;
import main.resources.UserInput;

//@@author A0133926A

public class Parser {
	
	private static final String WHITESPACE = " ";
	private static final String FROM = "from";
	private static final String BY = "by";
	private static final String TO = "to";
	private static final String RECURRING = "recurring";
	private static final String COMPLETE  = "complete";
	private static final String UNCOMPLETE  = "uncomplete";
	
	//Feedback strings
	private static final String MSG_FAIL_NUM_FORMAT_EXCEPTION = "Error: \"%1$s\" is an invalid task number.";
	
	private static Feedback feedback;
	
	/**
	 * store the command into an array list
	 * @param inputFromLogic : the string user input.
	 * @return arraylist
	 */
	public final static ArrayList<String> retrieveCommand(String inputFromLogic){
		
		ArrayList<String> contentListForLogic = new ArrayList<String>();
		inputFromLogic = formatInputForValidParsing(inputFromLogic);
		updateList(inputFromLogic, contentListForLogic);		
		return contentListForLogic;
	}
	

	/**
	 * assign the value to some of the attributes in UserInput
	 * @param userInput : the userInput object.
	 * @return userInput
	 */
	public final static UserInput resetUserInput (UserInput userInput){
		feedback = Feedback.getInstance();
		
		ArrayList<String> inputCommand = retrieveCommand(userInput.getRawInput());
		switch (Shortcuts.shortcuts(inputCommand.get(0).toLowerCase())) {
		case "recurring":
			setUserInputForRecuring(userInput, inputCommand);
			break;
		
		case "add":
			setUserInputForAdd(userInput, inputCommand);
			break;

		
		case "delete":
			setUserInputForDelete(userInput, inputCommand);
			break;
			
		case "edit": 
			setUserInputForEdit(userInput, inputCommand);	
			break;	
			
		case "search":
			setUserInputForSearch(userInput, inputCommand);
			break;
		
		case "sort":
			setUserInputForSort(userInput, inputCommand);
			break;
		
		case "home":
			setUserInputForHome(userInput);
			break;
				
		case "undo":
			setUserInputForUndo(userInput);
			break;
			
		case "redo":
			setUserInputForRedo(userInput);
			break;
			
		case "complete":
			setUserInputForComplete(userInput, inputCommand);
			break;
		
		case "uncomplete":
			setUserInputForUncomplete(userInput, inputCommand);
			break;
			
		case "import":
			setUserInputForImport(userInput, inputCommand);
			break;
			
		case "export":
			setUserInputForExport(userInput, inputCommand);
			break;
			
		default:
			userInput.setCommand(userInput.getRawInput());
			break;
		}
		return userInput;

	}


	/**
	 * deal with redo command,assign the "redo" to command
	 * @param userInput : the userInput object.
	 */
	private static void setUserInputForRedo(UserInput userInput) {
		userInput.setCommand("redo");
	}


	/**
	 * deal with undo command, assign the "undo" to command
	 * @param userInput : the userInput object.
	 */
	private static void setUserInputForUndo(UserInput userInput) {
		userInput.setCommand("undo");
	}




	/**
	 * deal with home command, assign the "home" to command
	 * @param userInput : the userInput object.
	 */
	private static void setUserInputForHome(UserInput userInput) {
		userInput.setCommand("home");
	}


	/**
	 * deal with the export command, assign the "exprot" to command and assign the path
	 * @param userInput : the userInput object.
	 * @param inputCommand : the ArrayList containing the command.
	 */
	private static void setUserInputForExport(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("export");
		userInput.setPath(inputCommand.get(1));
	}


	/**
	 * deal with the import command, assign the "import" to command and assign the path
	 * @param userInput : the userInput object.
	 * @param inputCommand : the ArrayList containing the command.
	 */
	private static void setUserInputForImport(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("import");
		userInput.setPath(inputCommand.get(1));
	}


	/**
	 * deal with the uncomplete command, assign the "uncomplete" to command, and assign value to 
	 * the delete the deleteNumber
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForUncomplete(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("uncomplete");
		userInput.setComplete(false);
		ArrayList<int[]> uncompleteList = new ArrayList<int[]>();
		passDeletePart(inputCommand,userInput,uncompleteList);
	}


	/**
	 * deal with the complete command, assign the "complete" to command, and assign value to 
	 * the delete the deleteNumber
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForComplete(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("complete");
		userInput.setComplete(true);
		ArrayList<int[]> completeList = new ArrayList<int[]>();
		passDeletePart(inputCommand,userInput,completeList);
	}


	/**
	 * deal with the sort command, assign the "sort" to command, and assign value to 
	 * the searchTerm, sortType
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForSort(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("sort");
		int sortType = getNumber(inputCommand.get(1));
		userInput.setSortType(sortType);
	}


	/**
	 * deal with the search command, assign the "search" to command, and assign value to 
	 * the serachTerm and taskList
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForSearch(UserInput userInput, ArrayList<String> inputCommand) {
		String term = inputCommand.get(1);
		userInput.setCommand("search");
		userInput.setSearchTerm(term);
	}


	/**
	 * deal with the edit command, assign the "edit" to command, and assign value to 
	 * the editNumber, startTime, startDate, endTime, endDate, location, details priority and taskToEdit
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForEdit(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("edit");
		ArrayList<Integer> editList = new ArrayList<Integer>();
		if(inputCommand.contains("all")){
			userInput.setIsAll(true);
			String type_Num = inputCommand.get(2);
			editList.add(deleteNumber(type_Num));
			userInput.setTaskType(deleteType(type_Num));
			passEditPart(inputCommand,userInput,editList,3);
		} else {
			String type_Num = inputCommand.get(1);
			editList.add(deleteNumber(type_Num));
			userInput.setTaskType(deleteType(type_Num));
			passEditPart(inputCommand,userInput,editList,2);
		}
	}


	/**
	 * deal with the delete command, assign the "delete" command and assign value to the deleteNumber
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForDelete(UserInput userInput, ArrayList<String> inputCommand) {
		userInput.setCommand("delete");
		ArrayList<int[]> deleteList = new ArrayList<int[]>();
		if(inputCommand.contains("all")){
			userInput.setIsAll(true);
			int[] arr = new int[2];
			arr[0] = deleteType(inputCommand.get(2));
			arr[1] = deleteNumber(inputCommand.get(2));
			deleteList.add(arr);
			userInput.setDeleteNumber(deleteList);
		} else {
			passDeletePart(inputCommand,userInput,deleteList);
		}
	}


	/**
	 * deal with the add command, assign the "add" command and assign value to the task attribute
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForAdd(UserInput userInput, ArrayList<String> inputCommand) {
		Task addTask = createTaskForAdd(inputCommand);
		userInput.setTask(addTask);
		userInput.setCommand("add");
	}


	/**
	 * deal with the recurring command, assign the "recurring" command and assign value to the task attribute
	 * @param userInput
	 * @param inputCommand
	 */
	private static void setUserInputForRecuring(UserInput userInput, ArrayList<String> inputCommand) {
		Task recurringTask = CreateTask.createRecurring(RECURRING, inputCommand);
		userInput.setTask(recurringTask);
		userInput.setCommand("recurring");
	}

	
	/**
	 * create different task type for add command
	 * @param listFromLogic
	 * @return a task object
	 */
	public final static Task createTaskForAdd(ArrayList<String> listFromLogic) {
			
			Task task = new Task();
			String taskType = identifyTaskType(listFromLogic);
			
			switch(taskType) {
			case "deadline":
				task = CreateTask.createDeadline(taskType, listFromLogic);
				break;
			case "event":
				task = CreateTask.createEvent(taskType, listFromLogic);
				break;
			case "floating":
				task = CreateTask.createFloating(taskType, listFromLogic);
				break;
			default:
				break;
			}
			return task;
		}
	

	/**
	 * get the string the user typed in and store each words in a right format in 
	 * an array list.
	 * @param inputFromLogic
	 * @param contentListForLogic
	 */
	private static void updateList(String inputFromLogic, ArrayList<String> contentListForLogic) {
		
		//for commands: exit, help, undo etc
		if(onlyOneWord(inputFromLogic)) {
			contentListForLogic.add(Shortcuts.shortcuts(inputFromLogic));
		}
		
		else {  
			//splitting first input from logic into 2: (command) (content)
			String content[] = inputFromLogic.split(WHITESPACE);
			for(int i=0; i<content.length; i++){
				if(i==0){
					contentListForLogic.add(Shortcuts.shortcuts(content[0]));
				} else {
					contentListForLogic.add(content[i]);
				}
			}
		}
	}

	/**
	 * identify different task type
	 * @param listFromLogic
	 * @return the string which represent different task type
	 */
	public static String identifyTaskType(ArrayList<String> listFromLogic ) {
		
		if(listFromLogic.contains(RECURRING)) {
			return "recurring";
		}
		else if(listFromLogic.contains(FROM) && listFromLogic.contains(TO)) {
			return "event";
		} 		
		else if(listFromLogic.contains(BY)) {
			return "deadline";
		}
		else {
			return "floating";
		}
	}

	
	/**
	 * differentiate different command for editing
	 * @param command
	 * @return integer which represents the corresponding command
	 */
	private static Integer getNumber(String command){
		int n = -1;
		switch(command.toLowerCase()){
			case "-de":
			case "de"://detail
				n = 1;
				break;
			case "-sd":
			case "sd"://start date
				n = 2;
				break;
			case "-st":
			case "st"://start time
				n = 3;
				break;
			case "-ed":
			case "ed"://end date
				n = 4;
				break;
			case "-et":
			case "et"://end time
				n = 5;
				break;
			case "-l":
			case "l"://location
				n = 6;
				break;
			case "-p":
			case "p"://priority
				n = 7;
				break;
			case "-c"://complete
			case "c":
				n = 8;
				break;
			default:
				break;
		}
		feedback.setSortString(command);
		
		return n;
	} 
	
	/**
	 * find what kind of task you want to delete
	 * @param s
	 * @return the integer number which represent the corresponding type
	 */
	public static int deleteType(String s){
		int type = -1;
		switch(s.substring(0,1).toLowerCase()){
		case "d":
			type = 4;
			break;
		case "e":
			type = 1;
			break;
		case "f":
			type = 2;
			break;
		default:
			break;
		}
		return type;
	}
	
	/**
	 * find the task number user wants to delete
	 * @param s
	 * @return the task number user wants to delete
	 */
	public static int deleteNumber(String s){
		int num = -1;
		try {
			num = Integer.parseInt(s.substring(1,s.length()));
		}
		catch (NumberFormatException e) {
			feedback.setMessage(String.format(MSG_FAIL_NUM_FORMAT_EXCEPTION, s));
		}
		
		return num;
	}
	
	/**
	 * find the next commnad such like "-st", "-sd" etc.
	 * @param commands
	 * @param n
	 * @return the index number of the next command in the array list
	 */
	private static int findNextCommand(ArrayList<String> commands, int n){//for edit
		int k = -1;
		for(int i=n+1; i<commands.size(); i++){
			if(commands.get(i).toLowerCase().contains("-st")||commands.get(i).toLowerCase().contains("-sd")||commands.get(i).toLowerCase().contains("-et")||commands.get(i).toLowerCase().contains("-ed")||commands.get(i).toLowerCase().contains("-p")||commands.get(i).toLowerCase().contains("-l")
					||commands.get(i).toLowerCase().contains("-de")){
				k = i;
				return k;
			} 
		}
		return k;
	}
	
	
	/**
	 * If the command is delete, then is will assign value to the deleteNumber attribute
	 * @param commands
	 * @param userInput
	 * @param list
	 */
	private static void passDeletePart(ArrayList<String> commands, UserInput userInput, ArrayList<int[]> list){
		for(int i=1; i<commands.size(); i++){
			int[] arr = new int[2];
			arr[0] = deleteType(commands.get(i));
			arr[1] = deleteNumber(commands.get(i));
			list.add(arr);
		}
		userInput.setDeleteNumber(list);
	}
	
	/**
	 * If the command is edit, then this function will help to assign value to
	 * details, start date, start time, end date, end time, location, priority and 
	 * complete attributes
	 * @param commands
	 * @param userInput
	 * @param list
	 * @param start
	 */
	private static void passEditPart(ArrayList<String> commands, UserInput userInput, ArrayList<Integer> list,int start){
		int i = start;
		while(i<commands.size()){
			int n = getNumber(commands.get(i));
			list.add(n);
			int nextI = findNextCommand(commands,i);
			int tempI;
			if(nextI==-1){
				tempI = i;
				i = commands.size();
			} else {
				tempI = i;
				i = nextI;
			}
			switch(n){
			case 1:
				String details = CreateTask.getDetail(commands, tempI+1, i);	
				userInput.setDetails(details);
				break;
			case 2:
				Date startDate = CreateTask.getDate(commands.get(tempI+1));
				userInput.setStartDate(startDate);
				break;
			case 3:
				Time startTime = CreateTask.getTime(commands.get(tempI+1));
				userInput.setStartTime(startTime);
				break;
			case 4:
				Date endDate = CreateTask.getDate(commands.get(tempI+1));
				userInput.setEndDate(endDate);
				break;
			case 5:
				Time endTime = CreateTask.getTime(commands.get(tempI+1));
				userInput.setEndTime(endTime);
				break;
			case 6:
				String location = CreateTask.getLocation(commands, tempI+1, i);
				userInput.setLocation(location);
				break;
			case 7:
				int priority = CreateTask.getPriority(commands.get(tempI+1));
				userInput.setPriority(priority);
				break;
			case 8:
				if(commands.get(tempI+1).equals("COMPLETE")){
					userInput.setComplete(true);
				} else {
					userInput.setComplete(false);
				}		
				break;
			default:
				break;
			}
		}
		userInput.setEdit(list);
		
	}

	/**
	 * removes all unnecessary whitespaces to 1 whitespace
	 * @param input
	 * @return String
	 */
	private final static String formatInputForValidParsing (String input) {
		return input.replaceAll("\\s+", WHITESPACE).trim();
	}
	/**
	 * check if a string input is only a word
	 * @param input
	 * @return true if it contains only one word
	 */
	private final static boolean onlyOneWord(String input) {
		if (input.contains(WHITESPACE)) {
			return false;
		}
		else { 
			return true;
		}
	}

	
}
