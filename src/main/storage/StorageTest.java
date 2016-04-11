package main.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.resources.Task;
import main.resources.Time;
import main.storage.Storage;
//@@author A0124711U
public class StorageTest {

	private static File file;
	private static ArrayList<Task> taskList;
	
	private static final String FILE_NAME = "testFile.txt";
	private static final String IMPORT_FILE_NAME = "testImport.txt";
	private static final String EXPORT_FILE_NAME = "testExport.txt";
	
	/**
	 * Sets the name for the test file and deletes any existing file with the same name.
	 */
	@Before
	public void setUpTestFile() {
		file = new File(FILE_NAME);
		
		if (file.exists()) {
			file.delete();
		}
		
		Storage.setFileName(FILE_NAME);
	}
	
	/**
	 * Deletes the test file at the end of testing.
	 */
	@After
	public void deleteTestFile() {
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * Tests the file creation.
	 */
	@Test
	public void test1() {
		Storage.getInstance();
		
		assert(file.exists());
	}
	
	/**
	 * Tests the writing and reading of empty task list.
	 */
	@Test
	public void test2() {
		Storage storage = Storage.getInstance();
		taskList = storage.getTaskList();
		taskList = new ArrayList<Task>();
		
		//Writing
		boolean result = storage.saveFile();
		assertTrue(result);
		
		//Reading
		storage.readFileToTaskArrayList(file, storage.getTaskList());	
		assertEquals(0, taskList.size());
	}
	
	/**
	 * Tests the writing and reading of a task list with one task.
	 */
	@Test
	public void test3() {
		Storage storage = Storage.getInstance();
		taskList = storage.getTaskList();
		taskList = new ArrayList<Task>();
		Task task = new Task();
		
		assertEquals(0, taskList.size());
		
		task.setTaskDetails("This is a test task.");
		task.setTaskStartDate(1, 2, 3456);
		task.setTaskStartTime(new Time(12, 34));
		task.setTaskLocation("This is a test location.");
		task.setTaskType(0);
		
		taskList.add(task);
		
		//Writing
		boolean result = storage.saveFile();
		assertTrue(result);
		
		//Reading
		storage.readFileToTaskArrayList(file, taskList);
		assertEquals(1, taskList.size());
		assertEquals(task, taskList.get(0));
	}
	
	/**
	 * Tests importing and exporting of a file.
	 * Pre-requisite: Sample file of tasks to be imported (IMPORT_FILE_NAME)
	 */
	@Test
	public void test4() {
		Storage storage = Storage.getInstance();
		taskList = new ArrayList<Task>();
		File importFile = new File(IMPORT_FILE_NAME);
		
		//Importing
		boolean result = storage.importFile(importFile.getAbsolutePath());
		assertTrue(result);
		
		try {
			FileInputStream importFis = new FileInputStream(importFile);
			FileInputStream taskFis = new FileInputStream(file);
			
			byte[] importArr = new byte[1024];
			byte[] taskArr = new byte[1024];
			
			int numImportByte = -2;
			int numTaskByte = -2;
			
			while (numImportByte != -1 && numTaskByte != -1) {
				assertTrue(Arrays.equals(importArr, taskArr));
				
				numImportByte = importFis.read(importArr);
				numTaskByte = taskFis.read(taskArr);
				}
			
			if (numImportByte != -1 || numTaskByte != -1) {
				fail("Import files are not equal in test4.");
			}
			
			importFis.close();
			taskFis.close();
		}
		catch (IOException e) {
			fail("IOException occurred during import test in test4.");
		}
		
		//Exporting
		File exportFile = new File(EXPORT_FILE_NAME);
		
		result = storage.exportFile(exportFile.getAbsolutePath());
		assertTrue(result);
		
		try {
			FileInputStream exportFis = new FileInputStream(exportFile);
			FileInputStream taskFis = new FileInputStream(file);
			
			byte[] exportArr = new byte[1024];
			byte[] taskArr = new byte[1024];
			
			int numExportByte = -2;
			int numTaskByte = -2;
			
			while (numExportByte != -1 && numTaskByte != -1) {
				assertTrue(Arrays.equals(exportArr, taskArr));
				
				numExportByte = exportFis.read(exportArr);
				numTaskByte = taskFis.read(taskArr);
				}
			
			if (numExportByte != -1 || numTaskByte != -1) {
				fail("Export files are not equal in test4.");
			}
			
			exportFis.close();
			taskFis.close();
		}
		catch (IOException e) {
			fail("IOException occurred during export test in test4.");
		}
	}

}
