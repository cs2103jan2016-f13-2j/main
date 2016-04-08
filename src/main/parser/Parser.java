	

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
	
	
	public final static ArrayList<String> retrieveCommand(String inputFromLogic){
		
		ArrayList<String> contentListForLogic = new ArrayList<String>();
		inputFromLogic = formatInputForValidParsing(inputFromLogic);
		updateList(inputFromLogic, contentListForLogic);		
		return contentListForLogic;
	}
	

	
	public final static UserInput resetUserInput (UserInput userInput){
		feedback = Feedback.getInstance();
		
		ArrayList<String> inputCommand = retrieveCommand(userInput.getRawInput());
		switch (Shortcuts.shortcuts(inputCommand.get(0).toLowerCase())) {
		case "recurring":
			Task recurringTask = createTask.createRecurring(RECURRING, inputCommand);
			userInput.setTask(recurringTask);
			userInput.setCommand("recurring");
			break;
		
		case "add":
			Task addTask = createTaskForAdd(inputCommand);
			userInput.setTask(addTask);
			userInput.setCommand("add");
			break;

		
		case "delete":
			userInput.setCommand("delete");
			ArrayList<int[]> deleteList = new ArrayList<int[]>();
			if(inputCommand.contains("all")){
				userInput.setIsAll(true);
				int[] arr = new int[2];
				arr[0] = deleteType(inputCommand.get(2));
				arr[1] = deleteNumber(inputCommand.get(2));
				deleteList.add(arr);
			} else {
				passDeletePart(inputCommand,userInput,deleteList);
			}
			break;
			
		case "edit": 
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
			break;	
			
		case "search":
			String term = inputCommand.get(1);
			userInput.setCommand("search");
			userInput.setSearchTerm(term);
			break;
		
		case "sort":
			userInput.setCommand("sort");
			int sortType = getNumber(inputCommand.get(1));
			userInput.setSortType(sortType);
			break;
		
		case "home":
			userInput.setCommand("home");
			break;
		
		case "display":
			userInput.setCommand("display");
			break;

		
		case "undo":
			userInput.setCommand("undo");
			break;
			
		case "redo":
			userInput.setCommand("redo");
			break;
			
		case "complete":
			userInput.setCommand("complete");
			userInput.setComplete(true);
			ArrayList<int[]> completeList = new ArrayList<int[]>();
			passDeletePart(inputCommand,userInput,completeList);
			break;
		
		case "uncomplete":
			userInput.setCommand("uncomplete");
			userInput.setComplete(false);
			ArrayList<int[]> uncompleteList = new ArrayList<int[]>();
			passDeletePart(inputCommand,userInput,uncompleteList);
			break;
			
		case "import":
			userInput.setCommand("import");
			userInput.setPath(inputCommand.get(1));
			break;
			
		case "export":
			userInput.setCommand("export");
			userInput.setPath(inputCommand.get(1));
			break;
			
		default:
			userInput.setCommand(userInput.getRawInput());
			break;
		}
		return userInput;

	}

	

	public final static Task createTaskForAdd(ArrayList<String> listFromLogic) {
			
			Task task = new Task();
			String taskType = identifyTaskType(listFromLogic);
			
			switch(taskType) {
			case "deadline":
				task = createTask.createDeadline(taskType, listFromLogic);
				break;
			case "event":
				task = createTask.createEvent(taskType, listFromLogic);
				break;
			case "floating":
				task = createTask.createFloating(taskType, listFromLogic);
				break;
			default:
				break;
			}
			return task;
		}
	

	
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

	private static Integer getNumber(String command){
		int n = -1;
		switch(command){
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
			case "-c":
			case "c":
				n = 8;
				break;
			default:
				break;
		}
		feedback.setSortString(command);
		
		return n;
	} 
	
	public static int deleteType(String s){
		int type = -1;
		switch(s.substring(0,1)){
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
	
	private static int findNextCommand(ArrayList<String> commands, int n){//for edit
		int k = -1;
		for(int i=n+1; i<commands.size(); i++){
			if(commands.get(i).contains("-")){
				k = i;
				return k;
			} 
		}
		
		return k;
	}
	
	
	
	private static void passDeletePart(ArrayList<String> commands, UserInput userInput, ArrayList<int[]> list){
		for(int i=1; i<commands.size(); i++){
			int[] arr = new int[2];
			arr[0] = deleteType(commands.get(i));
			arr[1] = deleteNumber(commands.get(i));
			list.add(arr);
		}
		userInput.setDeleteNumber(list);
	}
	
	private static void passEditPart(ArrayList<String> commands, UserInput userInput, ArrayList<Integer> list,int start){
		//System.out.println("floating task?:"+userInput.getTaskType());
		int i = start;
		while(i<commands.size()){
			int n = getNumber(commands.get(i));
			list.add(n);
			//System.out.println("shown:"+n);
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
				String details = createTask.getDetail(commands, tempI+1, i);	
				userInput.setDetails(details);
				//System.out.println(userInput.getDetails());
				//System.out.println("show first task type:"+userInput.getEditNumber().get(1));
				break;
			case 2:
				Date startDate = createTask.getDate(commands.get(tempI+1));
				userInput.setStartDate(startDate);
				break;
			case 3:
				Time startTime = createTask.getTime(commands.get(tempI+1));
				userInput.setStartTime(startTime);
				break;
			case 4:
				Date endDate = createTask.getDate(commands.get(tempI+1));
				userInput.setEndDate(endDate);
				break;
			case 5:
				Time endTime = createTask.getTime(commands.get(tempI+1));
				userInput.setEndTime(endTime);
				break;
			case 6:
				String location = createTask.getLocation(commands, tempI+1, i);
				userInput.setLocation(location);
				break;
			case 7:
				int priority = Integer.parseInt(commands.get(tempI+1));
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

	//removes all unnecessary whitespaces to 1 whitespace
	private final static String formatInputForValidParsing (String input) {
		return input.replaceAll("\\s+", WHITESPACE).trim();
	}
	//check if a string input is only a word
	private final static boolean onlyOneWord(String input) {
		if (input.contains(WHITESPACE)) {
			return false;
		}
		else { 
			return true;
		}
	}

	
}
