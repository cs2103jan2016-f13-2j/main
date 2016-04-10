package main.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import main.resources.Task;
import main.resources.UserInput;
import main.storage.Storage;

//@@author A0125255L
public class EditTest {

	UserInput userInput;
	Command command;
	ArrayList<Task> taskList;
	ArrayList<Integer> editList;
	Storage storage;
	Task task;
	
	private static File file;
	private static final String FILE_NAME = "testFile.dat";
	
	@Test
	public void test() {
		taskList = new ArrayList<Task>();
		editList = new ArrayList<Integer>();
		file = new File(FILE_NAME);
		
		if (file.exists()) {
			file.delete();
		}
		
		Storage.setFileName(FILE_NAME);
		storage = Storage.getInstance();	
		
		//Adding deadline task "test1" to the taskList
		task = new Task("deadline task", "test1", 2);
		userInput = new UserInput("add test2");
		userInput.setTask(task);
		command = new Add(userInput);
		command.execute();
		taskList.add(task);
		
		//Edit location of "test1" from the taskList
		userInput = new UserInput("edit d1");
		userInput.setLocation("location1");
		editList.add(1);	//task number
		editList.add(6); 	//location tag
		System.out.println("editlist "+editList.size());
		userInput.setEdit(editList);
		userInput.setTaskToEdit(task);
		MainLogic.setDisplayList(new ArrayList<Task>());
		command = new Edit(userInput);
		command.execute();
		taskList = new ArrayList<Task>();
		taskList.add(userInput.getTask());
		assertEquals(taskList, storage.getTaskList());
			
		if (file.exists()) {
			file.delete();
		}
	}
}
