package main.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

public class AddTest {
	
	UserInput userInput;
	Command command;
	ArrayList<Task> taskList;
	Storage storage;
	Task task;
	
	private static File file;
	private static final String FILE_NAME = "testFile.dat";
	
	@Test
	public void test() {
		taskList = new ArrayList<Task>();
		file = new File(FILE_NAME);
		
		if (file.exists()) {
			file.delete();
		}
		
		Storage.setFileName(FILE_NAME);
		storage = Storage.getInstance();	
		
		
		//Adding floating task "test1" to the taskList
		task = new Task("floating task", "test1", 1);
		userInput = new UserInput("add test1");
		userInput.setTask(task);
		command = new Add(userInput);
		command.execute();
		taskList.add(task);
		assertEquals(taskList, storage.getTaskList());
		
		//Adding deadline task "test2" to the taskList
		task = new Task("deadline task", "test2", 2);
		userInput = new UserInput("add test2");
		userInput.setTask(task);
		command = new Add(userInput);
		command.execute();
		taskList.add(task);
		assertEquals(taskList, storage.getTaskList());
		
		//Adding event task "test3" to the taskList
		task = new Task("event task", "test3", 3);
		userInput = new UserInput("add test3");
		userInput.setTask(task);
		command = new Add(userInput);
		command.execute();
		taskList.add(task);
		assertEquals(taskList, storage.getTaskList());
		
		if (file.exists()) {
			file.delete();
		}
	}

}
