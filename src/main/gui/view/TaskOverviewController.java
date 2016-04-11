//@@author A0124487Y
package main.gui.view;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
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


public class TaskOverviewController {
	
	private static final String CMD_DISPLAY = "display";
	private static final int MAIN_LOGIC_DEADLINE = 2;
	private static final int MAIN_LOGIC_EVENT = 0;
	private static final int MAIN_LOGIC_FLOATING = 1;
	
	private static final int TOTAL_TASK_LIST_DEADLINE = 0;
	private static final int TOTAL_TASK_LIST_EVENT = 1;
	private static final int TOTAL_TASK_LIST_FLOATING = 2;

	private static final int  USER_INPUT_ALL_TASK_TAB= 1;
	
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
	
	@FXML 
	private Label instantFeedback;
	@FXML 
	private Label overdueCounter;
	@FXML 
	private Label todayDate;
	@FXML 
	private Rectangle overdueRectangle;
	
	@FXML
	private TextField commandText;

	private ObservableList<Task> list = FXCollections.observableArrayList();
	private ObservableList<Task> eventList = FXCollections.observableArrayList();
	private ObservableList<Task> floatingList = FXCollections.observableArrayList();
	
	private ArrayList<ObservableList<Task>> totalTaskList = new ArrayList<ObservableList<Task>>();
	private ArrayList<TableView<Task>> allTables = new ArrayList<TableView<Task>>();
	
	private Feedback feedback;

	// Reference to the main application.
	private MainApp mainApp;

	public TaskOverviewController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		initializeLists();
		displayLabels();
		displayAllTablesData();
		showOverdueCounter();	
		overrideTableProperties();
	}

	/**
	 * Overrides all tables properties.
	 */
	private void overrideTableProperties() {
		wrapTableColumns();
		displayPriorityColour();
		displayDates();
	}

	/**
	 * Overrides table columns settings to wrap text. 
	 */
	private void wrapTableColumns() {
		taskDetailsWrap();
		taskLocationWrap();
		eventDetailsWrap();
		eventLocationWrap();
		floatingDetailsWrap();
	}

	/**
	 * Wraps text in floating details table column.
	 */
	private void floatingDetailsWrap() {
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
	 * Wraps text in event location table column.
	 */
	private void eventLocationWrap() {
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
	}

	/**
	 * Wraps text in event details table column.
	 */
	private void eventDetailsWrap() {
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
	}

	/**
	 * Wraps text in deadline location table column.
	 */
	private void taskLocationWrap() {
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
	}

	/**
	 * Wraps text in deadline details table column.
	 */
	private void taskDetailsWrap() {
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
	}

	/**
	 * Displays data from observableLists.
	 */
	private void displayAllTablesData() {
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
		setTablePlaceHolder();
	}

	/**
	 * Set text to put "No Tasks" if there are no contents in the table.
	 */
	private void setTablePlaceHolder() {
		taskTable.setPlaceholder(new Label("No tasks"));
		eventTable.setPlaceholder(new Label("No tasks"));
		floatingTable.setPlaceholder(new Label("No tasks"));
	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp Main Application for GUI
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Inputs the totalTaskList of tasks into the three tables.
	 */
	private void setTablesArrayList() {
		for (int i = 0; i < totalTaskList.size(); i++) {
		allTables.get(i).setItems(totalTaskList.get(i));
		}
	}

	/**
	 * Displays all the priority as L, M, H and sets corresponding colour as grey, orange, and red.
	 */
	private void displayPriorityColour() {
		displayPriority();
		displayEventPriority();
		displayFloatingPriority();
	}

	/**
	 * Displays all dates. Today's and tomorrow's dates will be labelled as today and tomorrow. 
	 * Passed dates will be highlighted grey.
	 */
	private void displayDates() {
		displayTaskDateColumn();
		displayEventStartDateColumn();
		displayEventEndDateColumn();
	}


	/**
	 * Displays deadline priority as L, M, H and sets corresponding colour as grey, orange, and red.
	 */
	private void displayPriority() {
		taskPNumberColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);
		            setPriorityStyle(item, empty);
		        }

				private void setPriorityStyle(String item, boolean empty) {
					if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		                setText(item);
		                int priority = Integer.parseInt(item);
		                assertTrue(priority <= 3 && priority >= 1);
		                if (priority == 1) {
		                	setText("H");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: red");
		                } else if (priority == 2) {
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
	

	/**
	 * Displays event priority as L, M, H and sets corresponding colour as grey, orange, and red.
	 */
	private void displayEventPriority() {
		eventPNumberColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);
		            setPriorityStyle(item, empty);
		        }

				private void setPriorityStyle(String item, boolean empty) {
					if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		                setText(item);
		                int priority = Integer.parseInt(item);
		                assertTrue(priority <= 3 && priority >= 1);
		                if (priority == 1) {
		                	setText("H");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: red");
		                } else if (priority == 2) {
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
	
	/**
	 * Displays floating priority as L, M, H and sets corresponding colour as grey, orange, and red.
	 */
	private void displayFloatingPriority() {
		floatingPNumberColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);
		            setPriorityStyle(item, empty);
		        }

				private void setPriorityStyle(String item, boolean empty) {
					if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		                setText(item);
		                int priority = Integer.parseInt(item);
		                assertTrue(priority <= 3 && priority >= 1);
		                if (priority == 1) {
		                	setText("H");
		                	setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: red");
		                } else if (priority == 2) {
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

	/**
	 * Displays deadline dates. Today's and tomorrow's dates will be labeled as today and tomorrow. 
	 * Passed dates will be highlighted grey.
	 */
	private void displayTaskDateColumn() {
		taskDateColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		            	boolean isExpired = false;
		            	boolean isTomorrow = false;
		            	boolean isToday = false;
		            	String[] date = item.split("-");
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
		            	assertTrue(todayDate.isValid());
		            	assertTrue(tmrwDate.isValid());
		            	assertTrue(taskDate.isValid());
		            	if(taskDate.compareTo(todayDate) < 0)
		            		isExpired = true;
		            	if(taskDate.equals(tmrwDate))
		            		isTomorrow = true;
		            	if(taskDate.equals(todayDate))
		            		isToday = true;
		                setText(item);
		                if (isExpired) {
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: transparent, derive(#808080,20%);");
						} else {
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		                if (isTomorrow)
		                	setText("Tomorrow");
		                if (isToday)
		                	setText("Today");
		            }
		        }
		    };
		});
	}

	/**
	 * Displays event start dates. Today's and tomorrow's dates will be labeled as today and tomorrow. 
	 * Passed dates will be highlighted grey.
	 */
	private void displayEventStartDateColumn() {
		eventStartDateColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		            	boolean isExpired = false;
		            	boolean isTomorrow = false;
		            	boolean isToday = false;
		            	String[] date = item.split("-");
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
		            	assertTrue(todayDate.isValid());
		            	assertTrue(tmrwDate.isValid());
		            	assertTrue(taskDate.isValid());
		            	if(taskDate.compareTo(todayDate) < 0)
		            		isExpired = true;
		            	if(taskDate.equals(tmrwDate))
		            		isTomorrow = true;
		            	if(taskDate.equals(todayDate))
		            		isToday = true;
		                setText(item);
		                if (isExpired) {
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: transparent, derive(#808080,20%);");
						} else {
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		                if (isTomorrow)
		                	setText("Tomorrow");
		                if (isToday)
		                	setText("Today");
		            }
		        }
		    };
		});
	}
	
	/**
	 * Displays event end dates. Today's and tomorrow's dates will be labeled as today and tomorrow. 
	 * Passed dates will be highlighted grey.
	 */
	private void displayEventEndDateColumn() {
		eventEndDateColumn.setCellFactory(column -> {
		    return new TableCell<Task, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		            	 setText("");
		                 setStyle("");
		            } else {
		            	boolean isExpired = false;
		            	boolean isTomorrow = false;
		            	boolean isToday = false;
		            	String[] date = item.split("-");
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
		            	assertTrue(todayDate.isValid());
		            	assertTrue(tmrwDate.isValid());
		            	assertTrue(taskDate.isValid());
		            	if(taskDate.compareTo(todayDate) < 0)
		            		isExpired = true;
		            	if(taskDate.equals(tmrwDate))
		            		isTomorrow = true;
		            	if(taskDate.equals(todayDate))
		            		isToday = true;
		                setText(item);
		                if (isExpired) {
		                    setTextFill(Color.WHITE);
		                    setStyle("-fx-background-color: transparent, derive(#808080,20%);");
						} else {
		                	setTextFill(Color.BLACK);
		                    setStyle("");
		                }
		                if (isTomorrow)
		                	setText("Tomorrow");
		                if (isToday)
		                	setText("Today");
		            }
		        }
		    };
		});
	}



	/**
	 * Displays today's date and instant feedback labels.
	 */
	private void displayLabels() {
		todayDate.setText(MainLogic.getCurrentDate().getDateString());
		feedback = Feedback.getInstance();
		instantFeedback.setText(feedback.getMessage());
	}

	/**
	 * Initializes lists from MainLogic.
	 */
	private void initializeLists() {
		totalTaskList.add(list);
		totalTaskList.add(eventList);
		totalTaskList.add(floatingList);
		allTables.add(taskTable);
		allTables.add(eventTable);
		allTables.add(floatingTable);
		getTaskListFromFile();
	}

	/**
	 * Displays the number of overdue tasks, and displays nothing when there are no overdue tasks.
	 */
	private void showOverdueCounter() {
		if (getNoOfDAndETasks(MainLogic.getExpiredTasks()) > 0) {
		overdueCounter.setText(""+getNoOfDAndETasks(MainLogic.getExpiredTasks()));
		} else {
			overdueRectangle.setOpacity(0);
		}
	}
	
	/**
	 * Converts a list of list of tasks from MainLogic to a list of ObservableList of Task. 
	 */
	private void getTaskListFromFile() {
		UserInput userInput = new UserInput(CMD_DISPLAY);
		MainLogic.run(userInput);
		ArrayList<ArrayList<Task>> temp = MainLogic.getTaskList();
		numberTaskArrayList(temp); 
		setTaskTotalList(temp);
		setTablesArrayList();
	}

	/**
	 * adds in arrayList from the MainLogic to totalTaskList
	 * 
	 * @param mainLogicList List of list of tasks from MainLogic
	 */
	private void setTaskTotalList(ArrayList<ArrayList<Task>> mainLogicList) {
		for (int k = 0; k< totalTaskList.size(); k++){ 
			for (int j = 0; j < mainLogicList.size(); j++) {
				for (int i=0; i < mainLogicList.get(j).size(); i++) {
					if (k == TOTAL_TASK_LIST_DEADLINE && j == MAIN_LOGIC_DEADLINE) {
						totalTaskList.get(TOTAL_TASK_LIST_DEADLINE).add(mainLogicList.get(MAIN_LOGIC_DEADLINE).get(i));
					} else if (k == TOTAL_TASK_LIST_EVENT && j == MAIN_LOGIC_EVENT) {
						totalTaskList.get(TOTAL_TASK_LIST_EVENT).add(mainLogicList.get(MAIN_LOGIC_EVENT).get(i));
					} else if (k == TOTAL_TASK_LIST_FLOATING && j == MAIN_LOGIC_FLOATING) {
						totalTaskList.get(TOTAL_TASK_LIST_FLOATING).add(mainLogicList.get(MAIN_LOGIC_FLOATING).get(i));
					}
				}
			}
		}
	}
	
	/**
	 * Assigns a unique number to each task in a list of list of tasks starting from 1 within each list of task
	 * @param list A list of list of tasks
	 */
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
	 * get total number of deadline and event tasks in an list of list of tasks from MainLogic.
	 * @param TotalList a list of list of tasks
	 * @return The number of deadline and event tasks
	 */
	private int getNoOfDAndETasks(ArrayList<ArrayList<Task>> list) {
		int counter=0;
		for (int i = 0; i < list.size(); i++) {
			if(i != MAIN_LOGIC_FLOATING)
			counter += list.get(i).size();
			}
		return counter;
	}

	/**
	 * Called when the user presses enter. Creates a UserInput object and passes it to MainLogic.
	 */
	public void onEnter(){
		feedback.setMessage(null);
		String command = commandText.getText(); //string received from user.
		commandText.setText("");
		UserInput userInput = new UserInput(command, USER_INPUT_ALL_TASK_TAB);
		MainLogic.run(userInput);	
		mainApp.showTaskOverview();
	}    
	
	
	/**
	 * Listener for TextField. If User enters a keyboard shortcut, it will be executed.
	 * @param keyEvent An event which indicates that a keystroke occurred in a component.
	 */
	@FXML
	void onKeyPressed(KeyEvent keyEvent) {
	  processKeyEventPressed(keyEvent);
	}

	/**
	 * Processes the key event pressed.
	 * @param keyEvent An event which indicates that a keystroke occurred in a component.
	 */
	private void processKeyEventPressed(KeyEvent keyEvent) {
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

	/**
	 * Listener for TextField. If User releases a keyboard shortcut, it will be executed.
	 * @param keyEvent An event which indicates that a keystroke occurred in a component.
	 * 
	 */
	@FXML
	void onKeyReleased(KeyEvent keyEvent) {
	  processKeyEventReleased(keyEvent);
	}

	/**
	 * Processes the key event pressed.
	 * @param keyEvent An event which indicates that a keystroke occurred in a component.
	 * 
	 */
	private void processKeyEventReleased(KeyEvent keyEvent) {
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
	
	/**
	 * When overdue tab is clicked, show overdue overview.
	 */
	@FXML 
	void onClickedOverdue() {
		mainApp.showOverdueOverview();
	}
	
	/**
	 * When help tab is clicked, show help overview.
	 */
	@FXML
	void onClickedHelp(){
		mainApp.showHelpOverview();
	}
	
	/** 
	 * When all tasks tab is clicked, show task overview.
	 */
	@FXML
	void onClickedAllTask(){
		mainApp.showTaskOverview();
	}
	
	/**
	 * When today tab is clicked, show today's overview.
	 */
	@FXML
	void onClickedToday(){
		mainApp.showTodayOverview();
	}
	
	/**
	 * When upcoming tab is clicked, show upcoming overview.
	 */
	@FXML
	void onClickedUpcoming(){
		mainApp.showUpcomingOverview();
	}
	
	/**
	 * When complete tab is clicked, show complete overview.
	 */
	@FXML
	void onClickedComplete(){
		mainApp.showCompleteOverview();
	}
}
