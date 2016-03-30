
package main.parser;

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
		case "undo":
			return "undo";
		case "redo":
			return "redo";
		default:
			return command;
		}
	}
}
