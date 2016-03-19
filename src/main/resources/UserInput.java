package main.resources;

import java.util.ArrayList;

public class UserInput {
	
	String rawInput;
	String command;
	
	//Add
	Task task;
	
	//Delete
	int deleteNumber;
	
	//Edit
	int[] editNumber;
	Time time;
	Date date;
	String location;
	String details;
	int priority;
	
	//Search and sort
	String searchTerm;
	int sortType;
	ArrayList<Task> taskList;
	
	
	
	//Constructors
	public UserInput(String raw) {
		rawInput = raw;
		time = new Time();
		date = new Date();
		details = null;
		command = null;
		task = null;
		deleteNumber = -1;
		editNumber = new int[2];
		location = null;
		sortType = -1;
		searchTerm = null;
		taskList = new ArrayList<Task>();
		priority = 0;
	}
	
	
	//Getters
	public String getRawInput() {
		return rawInput;
	}
	
	public String getDetails() {
		return details;
	}
	
	public int getPriority() {
		return priority ;
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
	

	public String getLocation() {
		return location;
	}
	
	public Time getTime(){
		return time;
	}
	
	public Date getDate(){
		return date;
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
	
	public void setDetails(String details) {
		this.details = details;
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
	
	public void setLocation(String location){
		this.location=location;
	}
	
	public void setDate(Date date){
		this.date=date;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public void setSearchTerm(String term) {
		searchTerm = term;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void setSortType(int type) {
		sortType = type;
	}
	
	public void setTaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

}
