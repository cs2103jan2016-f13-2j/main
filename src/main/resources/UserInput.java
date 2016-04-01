package main.resources;

import java.util.ArrayList;

public class UserInput {
	
	String rawInput;
	String command;
	
	//Add
	Task task;
	
	
	int taskType; //1-Event, 2-Floating, 3-Recurring 4-Deadline
	//Delete
	int deleteNumber;
	
	//Edit
	ArrayList<Integer> editNumber;
	Time startTime;
	Date startDate;
	Time endTime;
	Date endDate;
	String location;
	String details;
	boolean complete;
	int priority;
	
	//Search and sort
	String searchTerm;
	int sortType;
	ArrayList<Task> taskList;
	
	//set 
	String directory;
	
	
	
	//Constructors
	public UserInput(String raw) {
		rawInput = raw;
		startTime = new Time();
		startDate = new Date();
		endTime = new Time();
		endDate = new Date();
		details = null;
		command = null;
		task = null;
		deleteNumber = -1;
		editNumber = new ArrayList<Integer> ();
		location = null;
		sortType = -1;
		searchTerm = null;
		taskList = new ArrayList<Task>();
		priority = 0;
		taskType = -1;
		complete = false;
	}
	
	
	//Getters
	public boolean getComplete(){
		return complete;
	}
	
	public int getTaskType(){
		return taskType;
	}
	
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
	
	public ArrayList<Integer> getEditNumber() {
		return editNumber;
	}
	

	public String getLocation() {
		return location;
	}
	
	public Time getStartTime(){
		return startTime;
	}
	
	public Date getStartDate(){
		return startDate;
	}
	
	public Time getEndTime(){
		return endTime;
	}
	
	public Date getEndDate(){
		return endDate;
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
	
	public String getDirectory(){
		return directory;
	}
	
	
	//Setters
	public void setDirectory(String path){
		this.directory = path;
	}
	
	public void setComplete(boolean c){
		complete = c;
	}
	
	
	public void setRawInput(String input) {
		this.rawInput = input;
	}
	
	public void setTaskType(int deleteType){
		this.taskType = deleteType;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public void setDeleteNumber(int number) {
		deleteNumber = number;
	}
	
	public void setEdit(ArrayList<Integer> list) {
		editNumber = list;
	}
	
	public void setLocation(String location){
		this.location=location;
	}
	
	public void setStartDate(Date date){
		this.startDate=date;
	}
	
	public void setStartTime(Time time) {
		this.startTime = time;
	}
	
	public void setEndDate(Date date){
		this.endDate=date;
	}
	
	public void setEndTime(Time time) {
		this.endTime = time;
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
