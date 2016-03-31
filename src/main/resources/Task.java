package main.resources;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//define and set a task class, ie a class entry
	   private String taskNumber;
	   private String taskName;
	   private String taskDetails;
	   private Date taskStartDate;
	   private Time taskStartTime;
	   private Date taskEndDate;
	   private Time taskEndTime;
	   private String taskLocation;
	   private int priority;
	   private int taskType;	//1-Event, 2-Floating, 3-Recurring 4-Deadline
	   private int recurTime;
	   private int recurFrequency; //1-daily 2-weekly 3-monthly 4-yearly
	   private boolean complete;
	   
	   public Task() {
	        this(null, null, 3);
	   }
	   
	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Task(String taskName, String taskDetails, int type) {
	        this.taskName = taskName;
	        this.taskDetails = taskDetails;
	        taskStartDate = null;
	        taskStartTime = null;
	        taskEndDate = null;
	        taskEndTime = null;
	        taskLocation = null;
	        priority = 3;
	        taskType = type;
	        taskNumber = null;
	        recurTime = -1;
	        recurFrequency = -1;
	        complete = false;
	    }
	    
	    public int getRecurTime(){
	    	return recurTime;
	    }
	    
	    public int getRecurFrequency(){
	    	return recurFrequency;
	    }
	    
	    public String getTaskName(){
	    	return this.taskName;
	    }
	    
	    public String getTaskNumber(){
	    	return this.taskNumber;
	    }
	    
	    public String getTaskDetails(){
	    	return this.taskDetails;
	    }
	    
	    public Date getTaskStartDate() {
	    	return taskStartDate;
	    }
	    
	    public Time getTaskStartTime() {
	    	return taskStartTime;
	    }
	    
	    public Date getTaskEndDate() {
	    	return taskEndDate;
	    }
	    
	    public Time getTaskEndTime() {
	    	return taskEndTime;
	    }
	    
	    
	    public String getTaskLocation() {
	    	return taskLocation;
	    }
	    
	    public int getPriority() {
	    	return priority;
	    }
	    
	    public int getTaskType() {
	    	return taskType;
	    }
	    
	    public boolean getComplete(){
	    	return complete;
	    }
	    
	    
	     public void setTaskName(String taskName){
	    	this.taskName = taskName;
	    }
	    
	     public void setTaskNumber(String taskNumber){
		    	this.taskNumber = taskNumber;
		    }
	     
	    public void setTaskDetails(String taskDetails){
	    	this.taskDetails = taskDetails;
	    }
	    
	    public void setTaskStartDate(Date date) {
	    	taskStartDate = date;
	    }
	    
	    public void setTaskEndDate(Date date) {
	    	taskEndDate = date;
	    }
	    
	    public void setTaskEndTime(Time time) {
	    	taskEndTime = time;
	    }
	    
	    public void setTaskStartDate(int day, int month, int year) {
	    	this.setTaskStartDate(new Date(day, month, year));
	    }
	    
	    public void setTaskStartTime(Time time) {
	    	taskStartTime = time;
	    }
	    
	    public void setTaskStartTime(int hour, int minute) {
	    	this.setTaskStartTime(new Time(hour, minute));
	    }
	    
	    public void setTaskLocation(String newLocation) {
	    	taskLocation = newLocation;
	    }
	    
	    public void setPriority(int priority) {
	    	this.priority = priority;
	    }
	    
	    public void setTaskType(int type) {
	    	taskType = type;
	    }
	    
	    public void setRecurTime(int recurTime){
	    	this.recurTime = recurTime;
	    }
	    
	    public void setRecurFrequency(int recurFrequency){
	    	this.recurFrequency = recurFrequency;
	    }
	    
	    public void setComplete(boolean b){
	    	this.complete = b;
	    }
	    
	    public StringProperty taskNameProperty() {
	        return convertType(taskName);
	    }
	    
	    public StringProperty taskNumberProperty() {
	        return convertType(taskNumber);
	    }
	    
	    public StringProperty taskPNumberProperty() {
	        return convertType(Integer.toString(priority));
	    }
	    
	    public StringProperty taskDetailsProperty() {
	        return convertType(taskDetails);
	    }
	    
	    public StringProperty taskStartTimeProperty() {
	    	if (taskStartTime == null) {
	    		return convertType("-");
	    	} else {
	    		if(taskStartTime.getHour()<10 && taskStartTime.getMinute()<10){
	    			return convertType("0"+taskStartTime.getHour() + ":0" + taskStartTime.getMinute());
	    		} else if (taskStartTime.getHour()<10 && taskStartTime.getMinute() > 9) {
	    			return convertType("0"+taskStartTime.getHour() + ":" + taskStartTime.getMinute());
	    		} else if (taskStartTime.getHour()>9 && taskStartTime.getMinute() > 9) {
	    			return convertType(taskStartTime.getTimeString());
	    		} else {
	    			return convertType(taskStartTime.getHour() + ":0" + taskStartTime.getMinute());
	    		}
	    	}   
	    } 
	    public StringProperty taskEndTimeProperty() {
	    	if (taskEndTime == null) {
	    		return convertType("-");
	    	} else {
	    		if(taskEndTime.getHour()<10 && taskEndTime.getMinute()<10){
	    			return convertType("0"+taskEndTime.getHour() + ":0" + taskEndTime.getMinute());
	    		} else if (taskEndTime.getHour()<10 && taskEndTime.getMinute() > 9) {
	    			return convertType("0"+taskEndTime.getHour() + ":" + taskEndTime.getMinute());
	    		} else if (taskEndTime.getHour()>9 && taskEndTime.getMinute() > 9) {
	    			return convertType(taskEndTime.getTimeString());
	    		} else {
	    			return convertType(taskEndTime.getHour() + ":0" + taskEndTime.getMinute());
	    		}
	    	}   
	    }
	    
	    public StringProperty taskStartDateProperty() {	    	
	    	if (taskStartDate == null) {
	    		return convertType("-");
	    	} else {
	    		return convertType(taskStartDate.getDateString());
	    	}	
	    }
	    
	    public StringProperty taskEndDateProperty() {	    	
	    	if (taskEndDate == null) {
	    		return convertType("-");
	    	} else {
	    		return convertType(taskEndDate.getDateString());
	    	}	
	    }
	    
	    public StringProperty taskLocationProperty() {
	    	if (taskLocation == null) {
	    		return convertType("-");
	    	} else {
	    		return convertType(taskLocation);
	    	}	
	    }
	    
	    public StringProperty convertType(String text) {
	    	return new SimpleStringProperty(text);
	    }
}
