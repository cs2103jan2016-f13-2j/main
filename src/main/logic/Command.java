package main.logic;

//@@author A0125255L

public interface Command {

	public void execute();
	
	public void undo();
	
	public void redo();
}
