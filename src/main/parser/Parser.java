	

package main.parser;

import java.util.ArrayList;

import main.resources.Date;
import main.resources.Task;
import main.resources.Time;
import main.resources.UserInput;


public class Parser {
	
	private static final String WHITESPACE = " ";
	private static final String FROM = "from";
	private static final String BY = "by";
	private static final String TO = "to";
	private static final String RECURRING = "recurring";
	
	
	public final static ArrayList<String> retrieveCommand(String inputFromLogic){
		
		ArrayList<String> contentListForLogic = new ArrayList<String>();
		inputFromLogic = formatInputForValidParsing(inputFromLogic);
		updateList(inputFromLogic, contentListForLogic);		
		return contentListForLogic;
	}
	

	
	public final static UserInput resetUserInput (UserInput userInput){
		ArrayList<String> inputCommand = retrieveCommand(userInput.getRawInput());
		switch (Shortcuts.shortcuts(inputCommand.get(0).toLowerCase())) {
		case "recurring": {
			Task newTask = createTask.createRecurring(RECURRING, inputCommand);
			userInput.setTask(newTask);
			userInput.setCommand("recurring");
			break;
		}
		case "add": {
			Task newTask = createTaskForAdd(inputCommand);
			userInput.setTask(newTask);
			userInput.setCommand("add");
			break;
		}
		case "delete": {
			userInput.setCommand("delete");
			String details = inputCommand.get(1);
			userInput.setDeleteNumber(deleteNumber(details));
			userInput.setTaskType(deleteType(details));
			break;
		}
		case "edit": {
			String type_Num = inputCommand.get(1);
			userInput.setEdit(deleteNumber(type_Num));
			userInput.setTaskType(deleteType(type_Num));
			passEditPart(inputCommand,userInput);
			break;
		}
		case "search": {
			String term = inputCommand.get(1);
			userInput.setCommand("search");
			userInput.setSearchTerm(term);
			break;
		}
		case "sort": {
			userInput.setCommand("sort");
			int sortType = getNumber(inputCommand.get(1));
			userInput.setSortType(sortType);
			break;
		}
		case "home": {
			userInput.setCommand("home");
			break;
		}
		case "display": {
			userInput.setCommand("display");
			break;

		}
		case "undo": {
			userInput.setCommand("undo");
			break;
		}
		case "redo": {
			userInput.setCommand("redo");
			break;
		}
		default: {
			userInput.setCommand(userInput.getRawInput());
			break;
		}
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
			default:
				break;
		}
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
		return Integer.parseInt(s.substring(1,2));
	}
	
	private static int findNextCommand(ArrayList<String> commands, int n){
		int k = -1;
		for(int i=n; i<commands.size(); i++){
			if(commands.get(i).contains("-")){
				k = i;
				return k;
			} 
		}
		
		return k;
	}
	
	private static void passEditPart(ArrayList<String> commands, UserInput userInput){
		int i = 2;
		while(i<commands.size()){
			int n = getNumber(commands.get(i));
			userInput.setEdit(n);
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
				String details = createTask.getDetail(commands, tempI, i);
				userInput.setDetails(details);
				break;
			case 2:
				Date startDate = createTask.getDate(commands.get(i));
				userInput.setStartDate(startDate);
				break;
			case 3:
				Time startTime = createTask.getTime(commands.get(i));
				userInput.setStartTime(startTime);
				break;
			case 4:
				Date endDate = createTask.getDate(commands.get(i));
				userInput.setEndDate(endDate);
				break;
			case 5:
				Time endTime = createTask.getTime(commands.get(i));
				userInput.setEndTime(endTime);
				break;
			case 6:
				String location = createTask.getLocation(commands, tempI, i);
				userInput.setLocation(location);
				break;
			case 7:
				int priority = Integer.parseInt(commands.get(i));
				userInput.setPriority(priority);
				break;
			default:
				break;
			}
		}
		
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
