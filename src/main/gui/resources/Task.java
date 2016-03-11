package main.gui.resources;

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
	   private String taskName;
	   private String taskDetails;
	   private Date taskDate;
	   private Time taskTime;
	
	    public Task() {
	        this(null, null);
	    }
	   
	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Task(String taskName, String taskDetails) {
	        this.taskName = taskName;
	        this.taskDetails = taskDetails;
	        taskDate = null;
	        taskTime = null;
	    }
	    
	    public String getTaskName(){
	    	return this.taskName;
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
	    
	     public void setTaskName(String taskName){
	    	this.taskName = taskName;
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
	    
	    public StringProperty taskNameProperty() {
	        return convertType(taskName);
	    }
	    
	    public StringProperty taskDetailsProperty() {
	        return convertType(taskDetails);
	    }
	    
	    public StringProperty convertType(String text) {
	    	return new SimpleStringProperty(text);
	    }
}
