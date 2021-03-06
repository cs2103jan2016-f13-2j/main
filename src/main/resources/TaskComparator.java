package main.resources;

import java.util.Comparator;

//@@author A0125255L

public class TaskComparator implements Comparator<Task> {
	
	private static final int SORT_TYPE_DETAILS = 1;
	private static final int SORT_TYPE_DATE = 2;
	private static final int SORT_TYPE_TIME = 3;
	private static final int SORT_TYPE_LOCATION = 6;
	private static final int SORT_TYPE_PRIORITY = 7;
	
	int compareType;
	
	/**
	 * Constructs a TaskComparator
	 * @param type: sortType from MainLogic
	 */
	public TaskComparator(int type) {
		compareType = type;
	}

	/**
	 * Compare method for Comparator
	 * @param task0, task1: tasks to be compared
	 * @return int: Integer value of compare result
	 */
	@Override
	public int compare(Task task0, Task task1) {
		switch (compareType) {
		case SORT_TYPE_DETAILS: {	//task details
			if (task0.getTaskDetails() == null) {
				return 1;
			}
			
			else if (task1.getTaskDetails() == null) {
				return -1;
			}
			
			else if (!task0.getTaskDetails().equals(task1.getTaskDetails())) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else {
				return task0.getPriority() - task1.getPriority();
			}
		}
		case SORT_TYPE_DATE: {	//task date
			if (task0.getTaskStartDate() == null && 
					task1.getTaskStartDate() == null) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else if (task0.getTaskStartDate() == null) {
				return 1;
			}
			
			else if (task1.getTaskStartDate() == null) {
				return -1;
			}
			
			else if (!task0.getTaskStartDate().equals(task1.getTaskStartDate())) {
				return task0.getTaskStartDate().compareTo(task1.getTaskStartDate());
			}
			
			else if (task0.getTaskStartTime() == null &&
						task1.getTaskStartTime() == null) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else if (task0.getTaskStartTime() == null) {
				return 1;
			}
			
			else if (task1.getTaskStartTime() == null) {
				return -1;
			}
			
			else {
				return task0.getTaskStartTime().compareTo(task1.getTaskStartTime());
			}
		}
		case SORT_TYPE_TIME: {	//task time
			if (task0.getTaskStartTime() == null && 
					task1.getTaskStartTime() == null) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else if (task0.getTaskStartTime() == null) {
				return 1;
			}
			
			else if (task1.getTaskStartTime() == null) {
				return -1;
			}
			
			else if (!task0.getTaskStartTime().equals(task1.getTaskStartTime())) {
				return task0.getTaskStartTime().compareTo(task1.getTaskStartTime());
			}
			
			
			
			else {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
		}
		case SORT_TYPE_LOCATION: {	//task location
			if (task0.getTaskLocation() == null && 
					task1.getTaskLocation() == null) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else if (task0.getTaskLocation()== null) {
				return 1;
			}
			
			else if (task1.getTaskLocation() == null) {
				return -1;
			}
			
			else if (!task0.getTaskLocation().equals(task1.getTaskLocation())) {
				return task0.getTaskLocation().compareTo(task1.getTaskLocation());
			}
			
			else {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
		}
		case SORT_TYPE_PRIORITY: { 	//task priority
			if (task0.getPriority() != task1.getPriority()) {
				return task0.getPriority() - task1.getPriority();	
			}
			
			else if (task0.getTaskStartDate() == null && 
					task1.getTaskStartDate() == null) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else if (task0.getTaskStartDate() == null) {
				return 1;
			}
			
			else if (task1.getTaskStartDate() == null) {
				return -1;
			}
			
			else if (!task0.getTaskStartDate().equals(task1.getTaskStartDate())) {
				return task0.getTaskStartDate().compareTo(task1.getTaskStartDate());
			}
			
			else if (task0.getTaskStartTime() == null && task1.getTaskStartTime() == null) {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
			
			else if (task0.getTaskStartTime() == null) {
				return 1;
			}
			
			else if (task1.getTaskStartTime() == null) {
				return -1;
			}
			
			else if (!task0.getTaskStartTime().equals(task1.getTaskStartTime())) {
				return task0.getTaskStartTime().compareTo(task1.getTaskStartTime());
			}
			
			else {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
		}
		default: {
			
		}
		}
		
		return 0;
	}

}
