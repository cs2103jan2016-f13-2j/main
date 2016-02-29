package main.gui.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
//define and set a task class, ie a class entry
	   private final StringProperty taskName;
	   private final StringProperty taskDetails;
	
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
	        this.taskName = new SimpleStringProperty(taskName);
	        this.taskDetails = new SimpleStringProperty(taskDetails);
	    }
	    
	    public String getTaskName(){
	    	return this.taskName.get();
	    }
	    
	    public void setTaskName(String taskName){
	    	this.taskName.set(taskName);
	    }
	    
	    public StringProperty taskNameProperty() {
	        return taskName;
	    }
	    
	    public String getTaskDetails(){
	    	return this.taskDetails.get();
	    }
	    
	    public void setTaskDetails(String taskDetails){
	    	this.taskName.set(taskDetails);
	    }
	    
	    public StringProperty taskDetailsProperty() {
	        return taskDetails;
	    }
	    
	    }
