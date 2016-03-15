package main.resources;

import java.util.ArrayList;

public class UserInput {
	
	String rawInput;
	String command;
	int deleteNumber;
	int[] editNumber;
	String details;
	String searchTerm;
	int sortType;
	ArrayList<Task> taskList;
	Task task;
	
	//Constructors
	public UserInput(String raw) {
		rawInput = raw;
		command = null;
		task = null;
		deleteNumber = -1;
		editNumber = new int[2];
		details = null;
		sortType = 1;
		searchTerm = null;
		taskList = new ArrayList<Task>();
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
	
	public String getSearchTerm() {
		return searchTerm;
	}
	
	public int getSortType() {
		return sortType;
	}
	
	public ArrayList<Task> getTaskList() {
		return taskList;
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
	
	public void setSearchTerm(String term) {
		searchTerm = term;
	}
	
	public void setSortType(int type) {
		sortType = type;
	}
	
	public void setTaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

}
