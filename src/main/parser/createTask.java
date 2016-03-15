

package main.parser;

import main.resources.Task;



public class createTask {
	

		
	public final static Task createDeadline(String taskType, String taskContent) {
		return null;
	}
	
	public final static Task createEvent(String taskType, String taskContent, int type) {
		String taskName = taskType+" task";
		Task task = new Task(taskName, taskContent, type);
		return task;
	}
	
	public final static Task createFloating(String taskType, String taskContent) {
		return null;
	}
	
}
