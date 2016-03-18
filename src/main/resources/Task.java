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
	   private Date taskDate;
	   private Time taskTime;
	   private String taskLocation;
	   private int priority;
	   private int taskType;	//1-Event, 2-Floating, 3-Recurring 4-Deadline
	
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
	        taskDate = null;
	        taskTime = null;
	        taskLocation = null;
	        priority = 3;
	        taskType = type;
	        taskNumber = null;
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
	    
	    public Date getTaskDate() {
	    	return taskDate;
	    }
	    
	    public Time getTaskTime() {
	    	return taskTime;
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
	    
	    
	     public void setTaskName(String taskName){
	    	this.taskName = taskName;
	    }
	    
	     public void setTaskNumber(String taskNumber){
		    	this.taskNumber = taskNumber;
		    }
	     
	    public void setTaskDetails(String taskDetails){
	    	this.taskDetails = taskDetails;
	    }
	    
	    public void setTaskDate(Date date) {
	    	taskDate = date;
	    }
	    
	    public void setTaskDate(int day, int month, int year) {
	    	this.setTaskDate(new Date(day, month, year));
	    }
	    
	    public void setTaskTime(Time time) {
	    	taskTime = time;
	    }
	    
	    public void setTaskTime(int hour, int minute) {
	    	this.setTaskTime(new Time(hour, minute));
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
	    
	    
	    public StringProperty taskNameProperty() {
	        return convertType(taskName);
	    }
	    
	    public StringProperty taskNumberProperty() {
	        return convertType(taskNumber);
	    }
	    
	    public StringProperty taskDetailsProperty() {
	        return convertType(taskDetails);
	    }
	    
	    public StringProperty taskTimeProperty() {
	    	if (taskTime == null) {
	    		return convertType("-");
	    	} else {
	    		if(taskTime.getHour()<10 && taskTime.getMinute()<10){
	    			return convertType("0"+taskTime.getHour() + ":0" + taskTime.getMinute());
	    		} else if (taskTime.getHour()<10 && taskTime.getMinute() > 9) {
	    			return convertType("0"+taskTime.getHour() + ":" + taskTime.getMinute());
	    		} else if (taskTime.getHour()>9 && taskTime.getMinute() > 9) {
	    			return convertType(taskTime.getTime());
	    		} else {
	    			return convertType(taskTime.getHour() + ":0" + taskTime.getMinute());
	    		}
	    	}   
	    }
	    
	    public StringProperty taskDateProperty() {	    	
	    	if (taskDate == null) {
	    		return convertType("-");
	    	} else {
	    		return convertType(taskDate.getDate());
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
