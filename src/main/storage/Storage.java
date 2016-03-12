package main.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import main.resources.Task;

public class Storage {
	//Key must be exactly 16 bytes long.
	private final String SECURITY_KEY = "maryhadalittlela";

	private static final String FILE_NAME = "task.txt";

	File taskFile;
	ArrayList<Task> taskList;

	public Storage() {
		taskList = new ArrayList<Task>();
		
		getFile(FILE_NAME);
		//decryptTaskFile();
		//initTaskArrayList(taskList);
		readFileIntoTaskArrayList(taskFile, taskList);
	}

	private void getFile(String fileName) {
		taskFile = new File(fileName);

		if (!taskFile.exists()) {
			createNewFile(taskFile);
		}
	}

	private void createNewFile(File file) {
		try {
			file.createNewFile();
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(new EofIndicator());
			
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void encryptTaskFile() {
		FileSecurity.encrypt(taskFile, SECURITY_KEY);
	}

	private void decryptTaskFile() {
		FileSecurity.decrypt(taskFile, SECURITY_KEY);
	}
	
	private void initTaskArrayList(ArrayList<Task> list) {
		list = new ArrayList<Task>();
	}

	private void readFileIntoTaskArrayList(File file, ArrayList<Task> list) {
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
			e.printStackTrace();
		}
	}
	
	private void writeTaskArrayListToFile(ArrayList<Task> list, File file) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			
			for (int i = 0; i < list.size(); i++) {
				out.writeObject(list.get(i));
			}
			
			out.writeObject(new EofIndicator());
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveFile() {
		writeTaskArrayListToFile(taskList, taskFile);
		//encryptTaskFile();
	}

	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	public void setTaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}
}
