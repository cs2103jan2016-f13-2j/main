package main.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.resources.Task;

public class Storage {
	private static Storage storage;
	Logger logger = Logger.getLogger("Storage");
	
	//Key must be exactly 16 bytes long.
	private final String SECURITY_KEY = "maryhadalittlela";

	//String constants
	private static String FILE_NAME = "task.txt";
	private static String DEFAULT = "default";
	
	private File taskFile;
	private ArrayList<Task> taskList;

	
	/**
	 * Initialises the File, and also the taskList with the contents of the File.
	 */

	private Storage() {
		taskList = new ArrayList<Task>();
		
		retreiveFile(FILE_NAME);
		//decryptTaskFile();
		readFileToTaskArrayList(taskFile, taskList);
	}
	
	//Public Methods
	
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
		//encryptTaskFile();
		logger.log(Level.INFO, "taskFile saved successfully.");
		
		return result;
	}
	
	/**
	 * Allows access to the taskList.
	 * @return the taskList object reference.
	 */

	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	public void setDirectory(String directory) {
		String filePath;
		
		if (directory.equals(DEFAULT)) {
			filePath = ""; 
		}
		else {
			filePath = directory + "\\";	
		}
		
		try {
			File file = new File(filePath);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//Private methods
	
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

	private void encryptTaskFile() {
		FileSecurity.encrypt(taskFile, SECURITY_KEY);
	}

	private void decryptTaskFile() {
		FileSecurity.decrypt(taskFile, SECURITY_KEY);
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
		catch (IOException e) {
			logger.log(Level.WARNING, "Unable to write to taskFile.");
			//e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
}
