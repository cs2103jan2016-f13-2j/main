package main.resources;

import java.util.ArrayList;

public class UserInput {
	
	String rawInput;
	String command;
	int tab;//1 for first tab, 2 for second tab etc.
	
	//Add
	Task task;
	
	
	int taskType; //1-Event, 2-Floating, 3-Recurring 4-Deadline
	//Delete
	ArrayList<int[]> deleteNumber;
	ArrayList<Task> taskToDelete;
	
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
	Task taskToEdit;
	
	//Search and sort
	String searchTerm;
	int sortType;
	boolean displaySort;
	ArrayList<Task> taskList;
	
	//set 
	String path;
	
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
		deleteNumber = new ArrayList<int[]>();
		editNumber = new ArrayList<Integer> ();
		location = null;
		sortType = -1;
		searchTerm = null;
		taskList = new ArrayList<Task>();
		priority = 0;
		taskType = -1;
		complete = false;
		displaySort = false;
	}
	
	public UserInput(String raw, int tab) {
		this.tab = tab;
		rawInput = raw;
		startTime = new Time();
		startDate = new Date();
		endTime = new Time();
		endDate = new Date();
		details = null;
		command = null;
		task = null;
		deleteNumber = new ArrayList<int[]>();
		editNumber = new ArrayList<Integer> ();
		location = null;
		sortType = -1;
		searchTerm = null;
		taskList = new ArrayList<Task>();
		priority = 0;
		taskType = -1;
		complete = false;
		displaySort = false;
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
	
	public ArrayList<int[]> getDeleteNumber() {
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
	
	public String getPath(){
		return path;
	}
	
	public ArrayList<Task> getTasksToDelete() {
		return taskToDelete;
	}
	
	public Task getTaskToEdit() {
		return taskToEdit;
	}
	
	public boolean getDisplaySort() {
		return displaySort;
	}
	
	
	//Setters
	public void setPath(String path){
		this.path = path;
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
	
	public void setDeleteNumber(ArrayList<int[]> list) {
		deleteNumber = list;
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
	
	public void setTaskToEdit(Task task) {
		taskToEdit = task;
	}

	public void setTaskToDelete(ArrayList<Task> list) {
		taskToDelete = list;
	}

	public void setDisplaySort(boolean displaySort) {
		this.displaySort = displaySort;
	}
}
