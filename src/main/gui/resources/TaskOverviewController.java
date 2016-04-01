package main.gui.resources;

import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.gui.MainApp;
import main.logic.MainLogic;
import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;

public class TaskOverviewController {
	
	private static final String CMD_DISPLAY = "display";
	
	public boolean x = true;
	
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private TableColumn<Task, String> taskNumberColumn;
	@FXML
	private TableColumn<Task, String> taskPNumberColumn;
	@FXML
	private TableColumn<Task, String> taskDetailsColumn;
	@FXML
	private TableColumn<Task, String> taskDateColumn;
	@FXML
	private TableColumn<Task, String> taskTimeColumn;
	@FXML
	private TableColumn<Task, String> taskLocationColumn;
	
	@FXML
	private TableView<Task> eventTable;
	@FXML
	private TableColumn<Task, String> eventNumberColumn;
	@FXML
	private TableColumn<Task, String> eventPNumberColumn;
	@FXML
	private TableColumn<Task, String> eventDetailsColumn;
	@FXML
	private TableColumn<Task, String> eventStartDateColumn;
	@FXML
	private TableColumn<Task, String> eventEndDateColumn;
	@FXML
	private TableColumn<Task, String> eventStartTimeColumn;
	@FXML
	private TableColumn<Task, String> eventEndTimeColumn;
	@FXML
	private TableColumn<Task, String> eventLocationColumn;
	
	@FXML
	private TableView<Task> floatingTable;
	@FXML
	private TableColumn<Task, String> floatingNumberColumn;
	@FXML
	private TableColumn<Task, String> floatingPNumberColumn;
	@FXML
	private TableColumn<Task, String> floatingDetailsColumn;
	
	@FXML private Label instantFeedback;
	
	@FXML private Label todayDate;

	//@FXML
	//private Label taskNameLabel;
	//@FXML
	//private Label taskDetailsLabel;

	@FXML
	private TextField commandText;

	private ObservableList<Task> list = FXCollections.observableArrayList();
	private ObservableList<Task> eventList = FXCollections.observableArrayList();
	private ObservableList<Task> floatingList = FXCollections.observableArrayList();
	
	private ArrayList<ObservableList<Task>> totalList = new ArrayList<ObservableList<Task>>();

	
	
	private ArrayList<TableView<Task>> allTables = new ArrayList<TableView<Task>>();
	
	Feedback feedback;

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
		taskPNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskPNumberProperty());
		taskDetailsColumn.setCellValueFactory(cellData -> cellData.getValue().taskDetailsProperty());
		taskDateColumn.setCellValueFactory(cellData -> cellData.getValue().taskStartDateProperty());
		taskTimeColumn.setCellValueFactory(cellData -> cellData.getValue().taskStartTimeProperty());
		taskLocationColumn.setCellValueFactory(cellData -> cellData.getValue().taskLocationProperty());
		
		eventNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskNumberProperty());
		eventPNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskPNumberProperty());
		eventDetailsColumn.setCellValueFactory(cellData -> cellData.getValue().taskDetailsProperty());
		eventStartDateColumn.setCellValueFactory(cellData -> cellData.getValue().taskStartDateProperty());
		eventEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().taskEndDateProperty());
		eventStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().taskStartTimeProperty());
		eventEndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().taskEndTimeProperty());
		eventLocationColumn.setCellValueFactory(cellData -> cellData.getValue().taskLocationProperty());
		
		floatingNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskNumberProperty());
		floatingPNumberColumn.setCellValueFactory(cellData -> cellData.getValue().taskPNumberProperty());
		floatingDetailsColumn.setCellValueFactory(cellData -> cellData.getValue().taskDetailsProperty());
	}


	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		totalList.add(list);
		totalList.add(eventList);
		totalList.add(floatingList);
		allTables.add(taskTable);
		allTables.add(eventTable);
		allTables.add(floatingTable);
		this.mainApp = mainApp;
		todayDate.setText(MainLogic.getCurrentDate().getDateString());
		feedback = Feedback.getInstance();
		instantFeedback.setText(feedback.getMessage());
		
		// Add observable list data to the table
		getTaskListFromFile();
		for (int i = 0; i < totalList.size(); i++) {
		allTables.get(i).setItems(totalList.get(i));
		}
		
	
		
		taskPNumberColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		                setText(item);
		                if (item.equals("1")) {
		                	setText("H");
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: red");
		                } else if (item.equals("2")) {
		                	setText("M");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: orange");
		                } else {
		                	setText("L");
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		            }
		        }
		    };
		});
		
		eventPNumberColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		                setText(item);
		                if (item.equals("1")) {
		                	setText("H");
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: red");
		                } else if (item.equals("2")) {
		                	setText("M");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: orange");
		                } else {
		                	setText("L");
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		            }
		        }
		    };
		});
		
		floatingPNumberColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		                setText(item);
		                if (item.equals("1")) {
		                	setText("H");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: red");
		                } else if (item.equals("2")) {
		                	setText("M");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: orange");
		                } else {
		                	setText("L");
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		            }
		        }
		    };
		});
	}
	
	
//convert arraylist to observable list
	private void getTaskListFromFile() {
		UserInput userInput = new UserInput(CMD_DISPLAY);
		MainLogic.run(userInput);
		ArrayList<ArrayList<Task>> temp = MainLogic.getTaskList();
		
		numberTaskArrayList(temp); 
		for (int k = 0; k< totalList.size(); k++){ 
			for (int j = 0; j < temp.size(); j++) {
				for (int i=0; i < temp.get(j).size(); i++) {
					if (k == 0 && j == 2) {
						totalList.get(0).add(temp.get(2).get(i));
					} else if (k == 1 && j == 0) {
						totalList.get(1).add(temp.get(0).get(i));
					} else if (k == 2 && j == 1) {
						totalList.get(2).add(temp.get(1).get(i));
					}
				}
			}
		}
	}
	
	private void numberTaskArrayList(ArrayList<ArrayList<Task>> list) {
		int taskNum = 1;
		
		for (ArrayList<Task> listT : list) {
				taskNum = 1;
				for (Task t : listT) {
					t.setTaskNumber(taskNum + "");
					taskNum++;
				}
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
		/**
		if(x) {
		instantFeedback.setText("please enter a valid command");
		x = false;
		} else {
			instantFeedback.setText("Error, : " +command+ " not entered");
			x = true;
		}
		/**/
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