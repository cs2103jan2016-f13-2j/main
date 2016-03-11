package main.gui.resources;

public class UserInput {
	
	String rawInput;
	String command;
	int deleteNumber;
	int[] editNumber;
	String details;
	Task task;
	
	//Constructors
	public UserInput(String raw) {
		rawInput = raw;
		command = null;
		task = null;
		deleteNumber = -1;
		editNumber = new int[2];
		details = null;
	}
	
	
	//Getters
	public String getRawInput() {
		return rawInput;
	}
	
	public String getCommand() {
		return command;
	}
	
	public Task getTask() {
		return task;
	}
	
	public int getDeleteNumber() {
		return deleteNumber;
	}
	
	public int[] getEditNumber() {
		return editNumber;
	}
	

	public String getDetails() {
		return details;
	}
	
	
	//Setters
	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public void setDelete(int number) {
		deleteNumber = number;
	}
	
	public void setEdit(int number, int number2) {
		editNumber[0] = number;
		editNumber[1] = number2;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}

}
