package main.gui.resources;

import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.gui.MainApp;
import main.logic.MainLogic;
import main.resources.Task;
import main.resources.UserInput;

public class TaskOverviewController {
	
	private static final String CMD_DISPLAY = "display";
	
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private TableColumn<Task, String> taskNumberColumn;
	@FXML
	private TableColumn<Task, String> taskDetailsColumn;
	@FXML
	private TableColumn<Task, String> taskDateColumn;
	@FXML
	private TableColumn<Task, String> taskTimeColumn;
	@FXML
	private TableColumn<Task, String> taskLocationColumn;
	
	@FXML private Label instantFeedback;

	//@FXML
	//private Label taskNameLabel;
	//@FXML
	//private Label taskDetailsLabel;

	@FXML
	private TextField commandText;

	private ObservableList<Task> list = FXCollections.observableArrayList();

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public TaskOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the task table with the two columns.
		taskNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskNumberProperty());
		taskDetailsColumn.setCellValueFactory(cellData -> cellData.getValue().taskDetailsProperty());
		taskDateColumn.setCellValueFactory(cellData -> cellData.getValue().taskDateProperty());
		taskTimeColumn.setCellValueFactory(cellData -> cellData.getValue().taskTimeProperty());
		taskLocationColumn.setCellValueFactory(cellData -> cellData.getValue().taskLocationProperty());
		
	}


	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		instantFeedback.setText("Please enter a command");
		// Add observable list data to the table
		getTaskListFromFile();
		taskTable.setItems(list);


	}
//convert arralist to observable list
	private void getTaskListFromFile() {
		UserInput userInput = new UserInput(CMD_DISPLAY);
		MainLogic.run(userInput);
		ArrayList<Task> temp = MainLogic.getTaskList();
		
		numberTaskArrayList(temp);
		
		for (int i=0; i<temp.size(); i++) {
			list.add(temp.get(i));
		}
	}
	
	private void numberTaskArrayList(ArrayList<Task> list) {
		int taskNum = 1;
		
		for (Task t : list) {
			t.setTaskNumber(taskNum + "");
			taskNum++;
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteTask() {
		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			taskTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a task in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user presses enter.
	 */
	public void onEnter(){
		String command = commandText.getText(); //string received from user.
		instantFeedback.setText("please enter a valid command");
		commandText.setText("");
		//System.out.println(command);
		UserInput userInput = new UserInput(command);
		MainLogic.run(userInput);
		mainApp.showTaskOverview(); 
	}    

	/**
	 * Fills all text fields to show details about the task.
	 * If the specified task is null, all text fields are cleared.
	 * 
	 * @param Task the task or null
	 * 
	 * Unused at the moment.
	 */
	/**
	private void showTaskDetails(Task task) {
		if (task != null) {
			// Fill the labels with info from the task object.
			taskNameLabel.setText(task.getTaskName());
			taskDetailsLabel.setText(task.getTaskDetails());


		} else {
			// task is null, remove all the text.
			taskNameLabel.setText("");
			taskDetailsLabel.setText("");

		}
	}
	/**/
}