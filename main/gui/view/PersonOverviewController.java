package main.gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.gui.MainApp;
import main.gui.model.Task;

public class PersonOverviewController {
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, String> taskDetailsColumn;

    @FXML
    private Label taskNameLabel;
    @FXML
    private Label taskDetailsLabel;
    
    @FXML
    private TextField commandText;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the task table with the two columns.
        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        taskDetailsColumn.setCellValueFactory(cellData -> cellData.getValue().taskDetailsProperty());
        
        // Clear person details.
        showTaskDetails(null);

        // Listen for selection changes and show the person details when changed.
        taskTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTaskDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        taskTable.setItems(mainApp.getTaskData());
    }
    
    /**
     * Fills all text fields to show details about the task.
     * If the specified task is null, all text fields are cleared.
     * 
     * @param Task the task or null
     */
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
    
    public void onEnter(){
    	String command = commandText.getText(); //string received from user.
 	   
 	   commandText.clear();
 	   
 	}
    
}