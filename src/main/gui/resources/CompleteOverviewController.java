package main.gui.resources;

import java.util.ArrayList;
import java.util.Calendar;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.shape.Rectangle;
import main.gui.MainApp;
import main.logic.MainLogic;
import main.resources.Date;
import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;

public class CompleteOverviewController {
	
	private static final String CMD_DISPLAY = "display";
	
	private Boolean controlPressed = false;
    private Boolean zPressed = false;
    private Boolean yPressed = false;
    private Boolean qPressed = false;

	
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

	@FXML private Label overdueCounter;
	
	@FXML private Rectangle overdueRectangle;

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
	public CompleteOverviewController() {
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
		
		taskTable.setPlaceholder(new Label("No tasks"));
		eventTable.setPlaceholder(new Label("No tasks"));
		floatingTable.setPlaceholder(new Label("No tasks"));
		
		taskDetailsColumn.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {

	        @Override
	        public TableCell<Task, String> call(
	                TableColumn<Task, String> param) {
	            TableCell<Task, String> cell = new TableCell<>();
	            Text text = new Text();
	            cell.setGraphic(text);
	            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
	            text.wrappingWidthProperty().bind(taskDetailsColumn.widthProperty());
	            text.textProperty().bind(cell.itemProperty());
	            return cell ;
	        }
	    });
		
		taskLocationColumn.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {

	        @Override
	        public TableCell<Task, String> call(
	                TableColumn<Task, String> param) {
	            TableCell<Task, String> cell = new TableCell<>();
	            Text text = new Text();
	            cell.setGraphic(text);
	            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
	            text.wrappingWidthProperty().bind(taskLocationColumn.widthProperty());
	            text.textProperty().bind(cell.itemProperty());
	            return cell ;
	        }
	    });
		
		eventDetailsColumn.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {

	        @Override
	        public TableCell<Task, String> call(
	                TableColumn<Task, String> param) {
	            TableCell<Task, String> cell = new TableCell<>();
	            Text text = new Text();
	            cell.setGraphic(text);
	            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
	            text.wrappingWidthProperty().bind(eventDetailsColumn.widthProperty());
	            text.textProperty().bind(cell.itemProperty());
	            return cell ;
	        }
	    });
		
		eventLocationColumn.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {

	        @Override
	        public TableCell<Task, String> call(
	                TableColumn<Task, String> param) {
	            TableCell<Task, String> cell = new TableCell<>();
	            Text text = new Text();
	            cell.setGraphic(text);
	            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
	            text.wrappingWidthProperty().bind(eventLocationColumn.widthProperty());
	            text.textProperty().bind(cell.itemProperty());
	            return cell ;
	        }
	    });
		
		floatingDetailsColumn.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {

	        @Override
	        public TableCell<Task, String> call(
	                TableColumn<Task, String> param) {
	            TableCell<Task, String> cell = new TableCell<>();
	            Text text = new Text();
	            cell.setGraphic(text);
	            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
	            text.wrappingWidthProperty().bind(floatingDetailsColumn.widthProperty());
	            text.textProperty().bind(cell.itemProperty());
	            return cell ;
	        }
	    });
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
		if (getNoOfTasks(MainLogic.getExpiredTasks()) > 0) {
		overdueCounter.setText(""+getNoOfTasks(MainLogic.getExpiredTasks()));
		} else {
			overdueRectangle.setOpacity(0);
		}
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
		
		taskDateColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		            	boolean expired = false;
		            	boolean tomorrow = false;
		            	String[] date = item.split("-");
		            	//String[] currDate = MainLogic.getCurrentDate().getDateString().split("-");
		            	Calendar cal = Calendar.getInstance();
		            	cal.add(Calendar.DAY_OF_MONTH, 1);
		            	Date todayDate = MainLogic.getCurrentDate();
		            	Date tmrwDate = new Date(cal.get(Calendar.DAY_OF_MONTH),
									cal.get(Calendar.MONTH) + 1, 
									cal.get(Calendar.YEAR));
		            	Date taskDate = new Date(
		            			Integer.parseInt(date[0]), 
		            			Integer.parseInt(date[1]), 
		            			Integer.parseInt(date[2]));
		            	if(taskDate.compareTo(todayDate) < 0)
		            		expired = true;
		            	if(taskDate.equals(tmrwDate)){
		            		tomorrow = true;
		            	}
		                setText(item);
		                if (expired) {
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: transparent, derive(#808080,20%);");
						} else {
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		                if (tomorrow) {
		                	setText("Tomorrow");
		                }
		            }
		        }
		    };
		});
		
		eventStartDateColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		            	boolean expired = false;
		            	boolean tomorrow = false;
		            	String[] date = item.split("-");
		            	//String[] currDate = MainLogic.getCurrentDate().getDateString().split("-");
		            	Calendar cal = Calendar.getInstance();
		            	cal.add(Calendar.DAY_OF_MONTH, 1);
		            	Date todayDate = MainLogic.getCurrentDate();
		            	Date tmrwDate = new Date(cal.get(Calendar.DAY_OF_MONTH),
									cal.get(Calendar.MONTH) + 1, 
									cal.get(Calendar.YEAR));
		            	Date taskDate = new Date(
		            			Integer.parseInt(date[0]), 
		            			Integer.parseInt(date[1]), 
		            			Integer.parseInt(date[2]));
		            	if(taskDate.compareTo(todayDate) < 0)
		            		expired = true;
		            	if(taskDate.equals(tmrwDate)){
		            		tomorrow = true;
		            	}
		                setText(item);
		                if (expired) {
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: transparent, derive(#808080,20%);");
						} else {
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		                if (tomorrow) {
		                	setText("Tomorrow");
		                }
		            }
		        }
		    };
		});
		
		eventEndDateColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		            	boolean expired = false;
		            	boolean tomorrow = false;
		            	String[] date = item.split("-");
		            	//String[] currDate = MainLogic.getCurrentDate().getDateString().split("-");
		            	Calendar cal = Calendar.getInstance();
		            	cal.add(Calendar.DAY_OF_MONTH, 1);
		            	Date todayDate = MainLogic.getCurrentDate();
		            	Date tmrwDate = new Date(cal.get(Calendar.DAY_OF_MONTH),
									cal.get(Calendar.MONTH) + 1, 
									cal.get(Calendar.YEAR));
		            	Date taskDate = new Date(
		            			Integer.parseInt(date[0]), 
		            			Integer.parseInt(date[1]), 
		            			Integer.parseInt(date[2]));
		            	if(taskDate.compareTo(todayDate) < 0)
		            		expired = true;
		            	if(taskDate.equals(tmrwDate)){
		            		tomorrow = true;
		            	}
		                setText(item);
		                if (expired) {
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: transparent, derive(#808080,20%);");
						} else {
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		                if (tomorrow) {
		                	setText("Tomorrow");
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
		//Eeshuan, this part reads value as things are typed in
		commandText.textProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("TextField Text Changed (newValue: " + newValue + ")");
		});
	//	commandText.setOnAction((event) -> {
	//		onEnter();
	//	});
	}
	
	
//convert arraylist to observable list
	private void getTaskListFromFile() {
		UserInput userInput = new UserInput(CMD_DISPLAY);
		MainLogic.run(userInput);
		ArrayList<ArrayList<Task>> temp = MainLogic.getCompletedTasks();
		
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

	private int getNoOfTasks(ArrayList<ArrayList<Task>> array) {
		int counter=0;
		for (int i = 0; i < array.size(); i++) {
			counter += array.get(i).size();
			}
		return counter;
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
		commandText.setText("");
		//System.out.println(command);
		UserInput userInput = new UserInput(command, 4);
		MainLogic.run(userInput);	
		mainApp.showCompleteOverview(); 
	}    
	
	
	
	@FXML
	void onKeyPressed(KeyEvent keyEvent) {
	  if (keyEvent.getCode() == KeyCode.CONTROL) { 
	    System.out.print("ctrl pressed");
	    controlPressed = true;
      } else if (keyEvent.getCode() == KeyCode.Z) {
          zPressed = true;
      } else if (keyEvent.getCode() == KeyCode.Y) {
          yPressed = true;
      } else if (keyEvent.getCode() == KeyCode.Q) {
          qPressed = true;
      } else if (keyEvent.getCode() == KeyCode.F12) {
          mainApp.showHelpOverview();
      } else if (keyEvent.getCode() == KeyCode.F5) {
          mainApp.showOverdueOverview();
      } else if (keyEvent.getCode() == KeyCode.F4) {
          mainApp.showCompleteOverview();
      } else if (keyEvent.getCode() == KeyCode.F3) {
          mainApp.showUpcomingOverview();
      } else if (keyEvent.getCode() == KeyCode.F2) {
          mainApp.showTodayOverview();
      } else if (keyEvent.getCode() == KeyCode.F1) {
          mainApp.showTaskOverview();
      } else if (keyEvent.getCode() == KeyCode.F11) {
          mainApp.getPrimaryStage().toBack();
      } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
          commandText.setText("home");
          onEnter();
      }
	  if(controlPressed && zPressed){
		  commandText.setText("undo");
		  onEnter();
	  }
	  if(controlPressed && yPressed){
		  commandText.setText("redo");
		  onEnter();
	  }
	  if(controlPressed && qPressed){
		  System.exit(0);
	  }
	}

	@FXML
	void onKeyReleased(KeyEvent keyEvent) {
	  if (keyEvent.getCode() == KeyCode.CONTROL) { 
	    controlPressed = false;
      } else if (keyEvent.getCode() == KeyCode.Z) {
          zPressed = false;
      } else if (keyEvent.getCode() == KeyCode.Y) {
          yPressed = false;
      } else if (keyEvent.getCode() == KeyCode.Q) {
    	  qPressed = false;
      }
	}
	
	@FXML 
	void onClickedOverdue() {
		mainApp.showOverdueOverview();
	}
	
	@FXML
	void onClickedHelp(){
		mainApp.showHelpOverview();
	}
	
	@FXML
	void onClickedAllTask(){
		mainApp.showTaskOverview();
	}
	
	@FXML
	void onClickedToday(){
		mainApp.showTodayOverview();
	}
	
	@FXML
	void onClickedUpcoming(){
		mainApp.showUpcomingOverview();
	}
	
	@FXML
	void onClickedComplete(){
		mainApp.showCompleteOverview();
	}
}