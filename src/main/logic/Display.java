package main.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L

public class Display implements Command {
	
	UserInput userInput;
	Storage storage;
	static Logger logger = Logger.getLogger("Display");

	/**
	 * Constructs a Display command
	 * @param userInput: userInput instance from MainLogic
	 */
	public Display(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
	}


	/**
	 * Execute the command
	 */
	@Override
	public void execute() {
		logger.log(Level.INFO, "Command DISPLAY");
		Command command = new Sort(userInput);
		command.execute();
		storage.saveFile();		
	}

	@Override
	public void undo() {
		//do nothing
		
	}

	@Override
	public void redo() {
		//do nothing	
	}

}
