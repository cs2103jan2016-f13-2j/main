
package main.parser;

//@@author A0133926A
public class Shortcuts {

	public static String shortcuts(String command) {
		switch (command) {
		// add task commands
		case "add":
		case "a":
		case "create":
		case "c":
			return "add";
		// delete task commands
		case "delete":
		case "del":
		case "d":
			return "delete";
		// edit task commands
		case "edit":
		case "u":
		case "update":
		case "e":
			return "edit";
		// display task commands
		case "display":
		case "view":
		case "v":
			return "display";
		// search command
		case "search":
		case "s":
		case "find":
		case "f":
			return "search";
		// recurring task
		case "recurring":
		case "recur":
		case "r":
			return "recurring";
		//undo command
		case "undo":
			return "undo";
		//redo command
		case "redo":
			return "redo";
		//set command
		case "set":
			return "set";
		//complete command
		case "complete":
		case "mark":
		case "done":
			return "complete";
		//uncomplete command
		case "incomplete":
		case "uncomplete":
			return "uncomplete";
		default:
			return command;
		}
	}
	
	public static String diffDateFormat(String dateInfo){
		switch(dateInfo.toLowerCase()){
		case "tmrw":
		case "tmr":
		case "tomorrow":
			return "tomorrow";
		case "mon":
		case "monday":
			return "monday";
		case "tues":
		case "tuesday":
			return "tuesday";
		case "wed":
		case "weds":
		case "wednesday":
			return "wednesday";
		case "thur":
		case "thurs":
		case "thursday":
			return "thursday";
		case "fri":
		case "firday":
			return "friday";
		case "sat":
		case "saturday":
			return "saturday";
		case "sun":
		case "sunday":
			return "sunday";
		case "january":
		case "jan":
			return "January";
		case "february":
		case "feb":
			return "February";
		case "march":
		case "mar":
			return "March";
		case "april":
		case "apr":
			return "April";
		case "may":
			return "May";
		case "june":
		case "jun":
			return "June";
		case "july":
		case "jul":
			return "July";
		case "august":
		case "aug":
			return "August";
		case "september":
		case "sep":
			return "September";
		case "october":
		case "oct":
			return "October";
		case "november":
		case "nov":
			return "November";
		case "december":
		case "dec":
			return "December";
		default:
			return dateInfo;
		}
	}
}
