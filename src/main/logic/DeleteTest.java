package main.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L
public class DeleteTest {

	UserInput userInput;
	Command command;
	ArrayList<Task> taskList;
	ArrayList<Task> deleteList;
	Storage storage;
	Task task;
	
	private static File file;
	private static final String FILE_NAME = "testFile.dat";
	
	@Test
	public void test() {
		taskList = new ArrayList<Task>();
		deleteList = new ArrayList<Task>();
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
		
		//Adding deadline task "test2" to the taskList
		task = new Task("deadline task", "test2", 2);
		userInput = new UserInput("add test2");
		userInput.setTask(task);
		command = new Add(userInput);
		command.execute();
		taskList.add(task);
		
		//Adding event task "test3" to the taskList
		task = new Task("event task", "test3", 3);
		userInput = new UserInput("add test3");
		userInput.setTask(task);
		command = new Add(userInput);
		command.execute();
		taskList.add(task);
		
		//Delete "test1" from the taskList
		userInput = new UserInput("delete d1");
		deleteList.add(new Task("floating task", "test1", 1));
		userInput.setTaskToDelete(deleteList);
		MainLogic.run(userInput);
		command = new Delete(userInput);
		command.execute();
		taskList.remove(new Task("floating task", "test1", 1));
		assertEquals(taskList, storage.getTaskList());
		
		//Delete "test2" and "test3" from the taskList
		userInput = new UserInput("delete d1 d2");
		deleteList.add(new Task("deadline task", "test2", 2));
		deleteList.add(new Task("event task", "test3", 3));
		userInput.setTaskToDelete(deleteList);
		MainLogic.run(userInput);
		command = new Delete(userInput);
		command.execute();
		taskList.remove(new Task("deadline task", "test2", 2));
		taskList.remove(new Task("event task", "test3", 3));
		assertEquals(taskList, storage.getTaskList());
		
		if (file.exists()) {
			file.delete();
		}
	}
}
