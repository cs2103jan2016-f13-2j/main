package main.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.UserInput;
import main.storage.Storage;

public class Display implements Command {
	
	UserInput userInput;
	Storage storage;
	static Logger logger = Logger.getLogger("Display");

	public Display(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
	}


	@Override
	public void execute() {
		logger.log(Level.INFO, "Command DISPLAY");
		userInput.setSortType(10);
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
