package main.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.resources.Task;
import main.resources.TaskComparator;
import main.resources.UserInput;
import main.storage.Storage;

public class Sort implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;

	public Sort(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getStorage();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		taskList = storage.getTaskList();
		switch (userInput.getSortType()) {
		case 1: {	//task details
			Collections.sort(taskList, new TaskComparator(1));
			break;
		}
		case 6: {	//task date
			Collections.sort(taskList, new TaskComparator(6));
			break;
		}
		case 8: {	//task location
			Collections.sort(taskList, new TaskComparator(8));
			break;
		}
		case 9: {	//task priority
			Collections.sort(taskList, new TaskComparator(9));
			break;
		}
		default: {
			break;
		}
		}
		
		storage.saveFile();
		userInput.setTaskList(taskList);
		for (int i=0; i<taskList.size(); i++) {
			System.out.println(taskList.get(i).getTaskDetails());
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
