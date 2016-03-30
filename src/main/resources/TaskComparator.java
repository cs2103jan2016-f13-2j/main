package main.resources;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
	
	int compareType;
	
	public TaskComparator(int type) {
		compareType = type;
	}

	@Override
	public int compare(Task task0, Task task1) {
		switch (compareType) {
		case 1: {	//task details
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
		case 6: {	//task date
			if (task0.getTaskDate() == null) {
				return 1;
			}
			
			else if (task1.getTaskDate() == null) {
				return -1;
			}
			
			else if (!task0.getTaskDate().equals(task1.getTaskDate())) {
				return task0.getTaskDate().compareTo(task1.getTaskDate());
			}
			
			else {
				return task0.getTaskTime().compareTo(task1.getTaskTime());
			}
		}
		case 7: {	//task time
			if (task0.getTaskTime() == null) {
				return 1;
			}
			
			else if (task1.getTaskTime() == null) {
				return -1;
			}
			
			else if (!task0.getTaskTime().equals(task1.getTaskTime())) {
				return task0.getTaskTime().compareTo(task1.getTaskTime());
			}
			
			else {
				return task0.getTaskDetails().compareTo(task1.getTaskDetails());
			}
		}
		case 8: {	//task location
			if (task0.getTaskLocation()== null) {
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
		case 9: { 	//task priority
			if (task0.getPriority() != task1.getPriority()) {
				return task0.getPriority() - task1.getPriority();	
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
