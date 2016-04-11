
package main.parser;

//@@author A0133926A
public class Shortcuts {

	/**
	 * Parses the flexible command inputs
	 * @param command: the raw input string
	 * @return the standard format string.
	 */
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
		case "finished":
		case "finish":
			return "complete";
		//uncomplete command
		case "incomplete":
		case "uncomplete":
			return "uncomplete";
		default:
			return command;
		}
	}
	
	
	/**
	 * parses flexible date information 
	 * @param dateInfo:date information
	 * @return the standard date format string.
	 */
	public static String diffDateFormat(String dateInfo){
		switch(dateInfo.toLowerCase()){
		//set date as today
		case "today":
			return "today";
			
		//set date as tomorrow
		case "tmrw":
		case "tmr":
		case "tomorrow":
			return "tomorrow";
			
		//set date as monday
		case "mon":
		case "monday":
			return "monday";
			
		//set date as tuesday
		case "tues":
		case "tuesday":
			return "tuesday";
			
		//set date as wednesday
		case "wed":
		case "weds":
		case "wednesday":
			return "wednesday";
			
		//set date as thursday
		case "thur":
		case "thurs":
		case "thursday":
			return "thursday";
			
		//set date as friday
		case "fri":
		case "firday":
			return "friday";
			
		//set date as saturday
		case "sat":
		case "saturday":
			return "saturday";
			
		//set date as sunday
		case "sun":
		case "sunday":
			return "sunday";
			
		//set date as January
		case "january":
		case "jan":
			return "January";
			
		//set date as February
		case "february":
		case "feb":
			return "February";
			
		//set date as March
		case "march":
		case "mar":
			return "March";
			
		//set date as April
		case "april":
		case "apr":
			return "April";
			
		//set date as May
		case "may":
			return "May";
			
		//set date as June
		case "june":
		case "jun":
			return "June";
			
		//set date as July
		case "july":
		case "jul":
			return "July";
			
		//set date as August
		case "august":
		case "aug":
			return "August";
			
		//set date as September
		case "september":
		case "sep":
			return "September";
			
		//set date as October
		case "october":
		case "oct":
			return "October";
			
		//set date as November
		case "november":
		case "nov":
			return "November";
			
		//set date as December
		case "december":
		case "dec":
			return "December";
			
		default:
			return dateInfo;
		}
	}
}
