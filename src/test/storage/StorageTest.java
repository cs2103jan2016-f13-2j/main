package test.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import main.resources.Task;
import main.resources.Time;
import main.storage.Storage;

public class StorageTest {

	private static File file;
	private static final String FILE_NAME = "testFile.dat";
	
	
	@BeforeClass
	public static void ensureNoFileExists() {
		file = new File(FILE_NAME);
		
		if (file.exists()) {
			file.delete();
		}
		
		Storage.setFileName(FILE_NAME);
	}
	
	@After
	public void deleteFileIfItExists() {
		if (file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void test1() {
		Storage storage = Storage.getStorage();
		
		assert(file.exists());
	}
	
	@Test
	public void test2() {
		Storage storage = Storage.getStorage();
		
		boolean result = storage.saveFile();
		
		assertEquals(true, result);
	}
	
	public void test3() {
		Storage storage = Storage.getStorage();
		Task task = new Task();
		
		task.setTaskDetails("This is a test task.");
		task.setTaskDate(1, 2, 3456);
		task.setTaskTime(new Time(12, 34));
		task.setTaskLocation("This is a test location.");
		task.setTaskType(0);
		
		boolean result = storage.saveFile();

		assertEquals(true, result);
	}

}
