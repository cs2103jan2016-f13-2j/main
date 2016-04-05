package main.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Feedback;
import main.resources.Task;

public class Storage {
	private static Storage storage;
	private static Feedback feedback;
	Logger logger = Logger.getLogger("Storage");

	//String constants
	private static String FILE_NAME = "task.dat";
	private static String DEFAULT = "default";
	
	//Message strings
	private static String MSG_SUCCESS_IMPORT = "Successfully imported file data from %1$s";
	private static String MSG_SUCCESS_EXPORT = "Successfully exported file data from %1$s";
	private static String MSG_FAIL_FILE_NOT_FOUND = "Error: The specified file does not exist.";
	private static String MSG_FAIL_FILE_EXIST = "Error: A file with the name \"%1$s\" already exists.";
	private static String MSG_FAIL_DIRECTORY_NOT_FOUND = "Error: Directory does not exist.";
	private static String MSG_FAIL_RELATIVE_FILE_PATH = "Error: File path must be absolute. Please specify a valid file path.";
	private static String MSG_FAIL_READ_FILE = "Error: The specified file could not be read.";
	private static String MSG_FAIL_WRITE_FILE = "Error: Unable to write to file.";
	
	private File taskFile;
	private ArrayList<Task> taskList;

	
	/**
	 * Initialises the File, and also the taskList with the contents of the File.
	 */

	private Storage() {
		feedback = Feedback.getInstance();
		taskList = new ArrayList<Task>();
		
		retreiveFile(FILE_NAME);
		readFileToTaskArrayList(taskFile, taskList);
	}
	
	//-----Public Methods-----
	
	/**
	 * Creates a Storage object for use if it doesn't exist.
	 * @return the Storage object.
	 */
	
	public static Storage getInstance() {
		if (storage == null) {
			storage = new Storage();
		}
		
		return storage;
	}
	
	/**
	 * Allows access to the taskList.
	 * @return the taskList object reference.
	 */

	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	/**
	 * For testing purposes.
	 * 
	 * Sets the FILE_NAME to the test file path.
	 * @param string : The file path of the test file.
	 */
	
	public static void setFileName(String string) {
		FILE_NAME = string;
	}
	
	/**
	 * Writes the existing taskList to file.
	 * @return true if successfully saved, false if exception thrown.
	 */
	
	public boolean saveFile() {
		boolean result = writeTaskArrayListToFile(taskList, taskFile);
		logger.log(Level.INFO, "taskFile saved successfully.");
		
		return result;
	}

	/**
	 * Imports a file from another location to the default location for use.
	 * @param filePath : The absolute file path of the target file.
	 * @return true if import was successful, false otherwise.
	 */
	public boolean importFile(String filePath) {
		File file = new File(filePath);
		ArrayList<Task> list = new ArrayList<Task>();
		
		if (!file.exists()) {
			feedback.setMessage(MSG_FAIL_FILE_NOT_FOUND);
			logger.log(Level.INFO, "Import file doesn't exist.");
			return false;
		}
		
		if (readFileToTaskArrayList(file, list) == false) {
			logger.log(Level.INFO, "Import read failed.");
			return false;
		}
		
		taskList = list;
		
		if (saveFile() == false) {
			feedback.setMessage(MSG_FAIL_WRITE_FILE);
			logger.log(Level.WARNING, "Import write failed.");
			return false;
		}
		
		feedback.setMessage(String.format(MSG_SUCCESS_IMPORT, filePath));
		logger.log(Level.INFO, "Import successful.");
		return true;
	}
	
	/**
	 * Exports the task file to the specified file path.
	 * @param filePath : The absolute path of the target file location
	 * @return true if export was successful, false otherwise.
	 */
	public boolean exportFile(String filePath) {
		File file = new File(filePath);
		
		if (!file.isAbsolute()) {
			feedback.setMessage(MSG_FAIL_RELATIVE_FILE_PATH);
			return false;
		}

		if (file.exists()) {
			feedback.setMessage(MSG_FAIL_FILE_EXIST);
			logger.log(Level.INFO, "Export file with same name exists.");
			return false;
		}
		
		if (writeTaskArrayListToFile(taskList, file) == false) {
			logger.log(Level.WARNING, "Export write failed.");
			return false;
		}

		feedback.setMessage(String.format(MSG_SUCCESS_EXPORT, filePath));
		logger.log(Level.INFO, "Export successful.");
		return true;
	}
	

	//-----Private methods-----
	
	/**
	 * Retrieves the file object and creates one if it doesn't exist.
	 * @param fileName : The file path String
	 */
	
	private void retreiveFile(String fileName) {
		taskFile = new File(fileName);

		if (!taskFile.exists()) {
			assert(!taskFile.exists());
			createNewFile(taskFile);
			assert(taskFile.exists());
			logger.log(Level.INFO, "New taskFile created.");
		}
	}
	
	/**
	 * Creates a new file in the current directory.
	 * @param file : The file to be created/
	 * @return true if successfully created, false if exception thrown.
	 */

	private boolean createNewFile(File file) {
		try {
			file.createNewFile();
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(new EofIndicator());
			
			out.close();
		}
		catch (IOException e) {
			logger.log(Level.WARNING, "Unable to create new file.");
			//e.printStackTrace();
			return false;
		}
		
		assert(file.exists());
		return true;
	}
	
	/**
	 * Reads content of file into list for manipulation by other classes.
	 * @param file : The file to be read.
	 * @param list : The list for contents to be put into.
	 * @return true if successfully read, false if exception thrown.
	 */

	private boolean readFileToTaskArrayList(File file, ArrayList<Task> list) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

			Object object = in.readObject();
			
			while (!(object instanceof EofIndicator)) {
				list.add((Task) object);
				object = in.readObject();
			}
			in.close();
		}
		catch (IOException | ClassNotFoundException e) {
			feedback.setMessage(MSG_FAIL_READ_FILE);
			logger.log(Level.WARNING, "Unable to read from taskFile.");
			//e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Writes the contents of existing task list to file.
	 * @param list : The list to be written to file.
	 * @param file : The destination file to be written to.
	 * @return true if successfully written, false if exception thrown.
	 */
	
	private boolean writeTaskArrayListToFile(ArrayList<Task> list, File file) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			
			for (int i = 0; i < list.size(); i++) {
				out.writeObject(list.get(i));
			}
			
			out.writeObject(new EofIndicator());
			out.close();
		}
		catch (FileNotFoundException e) {
			feedback.setMessage(MSG_FAIL_DIRECTORY_NOT_FOUND);
			
			return false;
		}
		catch (IOException e) {
			feedback.setMessage(MSG_FAIL_WRITE_FILE);
			logger.log(Level.WARNING, "Unable to write to taskFile.");
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
}
