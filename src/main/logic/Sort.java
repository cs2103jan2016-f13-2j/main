package main.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Task;
import main.resources.TaskComparator;
import main.resources.UserInput;
import main.storage.Storage;

public class Sort implements Command {
	
	UserInput userInput;
	Storage storage;
	ArrayList<Task> taskList;
	static Logger logger = Logger.getLogger("Sort");

	public Sort(UserInput userInput) {
		this.userInput = userInput;
		storage = Storage.getInstance();
		taskList = new ArrayList<Task>();
	}

	@Override
	public void execute() {
		logger.log(Level.INFO, "Command SORT");
		taskList = storage.getTaskList();
		switch (userInput.getSortType()) {
		case 1: {	//task details
			Collections.sort(taskList, new TaskComparator(1));
			break;
		}
		case 2: {	//task date
			Collections.sort(taskList, new TaskComparator(6));
			break;
		}
		case 3: {	//task time
			Collections.sort(taskList, new TaskComparator(7));
			break;
		}
		case 6: {	//task location
			Collections.sort(taskList, new TaskComparator(8));
			break;
		}
		case 7: {	//task priority
			Collections.sort(taskList, new TaskComparator(9));
			break;
		}
		default: {
			break;
		}
		}
		
		storage.saveFile();
		userInput.setTaskList(taskList);
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
