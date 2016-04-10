package main.logic;

//@@author A0125255L

public interface Command {

	/**
	 * Executes the command
	 */
	public void execute();
	
	/**
	 * Undo the command
	 */
	public void undo();
	
	/**
	 * Redo the command
	 */
	public void redo();
}
