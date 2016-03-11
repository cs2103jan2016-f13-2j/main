

package main.parser;

import main.gui.resources.Task;



public class createTask {
	

		
	public final static Task createDeadline(String taskType, String taskContent) {
		return null;
	}
	
	public final static Task createEvent(String taskType, String taskContent) {
		String taskName = taskType+" task";
		Task task = new Task(taskName, taskContent);
		return task;
	}
	
	public final static Task createFloating(String taskType, String taskContent) {
		return null;
	}
	
}
