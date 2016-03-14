package main.logic;

import main.resources.UserInput;
import main.storage.Storage;

public class Display implements Command {
	
	UserInput userInput;
	Storage storage;

	public Display(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
	}


	@Override
	public void execute() {
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
