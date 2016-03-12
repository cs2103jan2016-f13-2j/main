

package main.parser;

import java.util.ArrayList;
import main.parser.FlexiCommands;
import main.resources.Task;
import main.resources.UserInput;


public class Parser {
	
	private static final String WHITESPACE = " ";
	
	
	public final static ArrayList<String> retrieveCommand(String inputFromLogic){
		
		ArrayList<String> contentListForLogic = new ArrayList<String>();
		inputFromLogic = formatInputForValidParsing(inputFromLogic);
		updateList(inputFromLogic, contentListForLogic);		
		return contentListForLogic;
	}
	

	
	public final static UserInput resetUserInput (UserInput userInput){
		ArrayList<String> inputCommand = retrieveCommand(userInput.getRawInput());
		switch (FlexiCommands.flexiCommands(inputCommand.get(0).toLowerCase())) {
		case "add": {
			//addTask();
			Task newTask = createTaskForAdd(inputCommand);
			userInput.setTask(newTask);
			userInput.setCommand("add");
			userInput.setDetails(inputCommand.get(1));
			break;

		}
		case "delete": {
			//deleteTask();
			userInput.setCommand("delete");
			userInput.setDelete(Integer.parseInt(inputCommand.get(1)));
			break;

		}
		case "edit": {
			//editTask();
			String[] details = inputCommand.get(1).split(WHITESPACE, 3);
			userInput.setCommand("edit");
			userInput.setEdit(Integer.parseInt(details[0]), Integer.parseInt(details[1]));
			userInput.setDetails(details[2]);
			break;
		}

		case "display": {
			userInput.setCommand("display");
			break;

		}
		case "exit": {
			userInput.setCommand("exit");
			break;
		}
		}
		return userInput;

	}

	

	public final static Task createTaskForAdd(ArrayList<String> listFromLogic) {
			
			Task task = new Task();
			String taskType = identifyTaskType(listFromLogic);
			String taskContent = listFromLogic.get(1);
			
			switch(taskType) {
			case "deadline":
				task = createTask.createDeadline(taskType, taskContent);
				break;
			case "event":
				task = createTask.createEvent(taskType, taskContent);
				break;
			case "floating":
				task = createTask.createFloating(taskType, taskContent);
				break;
			default:
				break;
			}
			return task;
		}
	

	
	private static void updateList(String inputFromLogic, ArrayList<String> contentListForLogic) {
		
		//for commands: exit, help, undo etc
		if(onlyOneWord(inputFromLogic)) {
			contentListForLogic.add(FlexiCommands.flexiCommands(inputFromLogic));
		}
		
		else {  
			//splitting first input from logic into 2: (command) (content)
			String content[] = inputFromLogic.split(WHITESPACE, 2);
			contentListForLogic.add(FlexiCommands.flexiCommands(content[0]));
			contentListForLogic.add(content[1]);
		}
	}

	
	public static String identifyTaskType(ArrayList<String> listFromLogic ) {
		String taskContent = listFromLogic.get(1);
		
		/*if(taskContent.contains(KEYWORD_BY)) {
			return "deadline";
		}
		else if(taskContent.contains(KEYWORD_FROM) && taskContent.contains(KEYWORD_TO)) {
			return "event";
		}
		else {
			return "floating";
		}*/
		return "event";
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
