//@@author A0124487Y
package main.gui.view;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import main.gui.MainApp;
import main.logic.MainLogic;
import main.resources.Feedback;
import main.resources.Task;
import main.resources.UserInput;


public class HelpOverviewController {
	

	private static final int  USER_INPUT_HELP_TAB = 1;
	private static final int MAIN_LOGIC_FLOATING = 1;
	
	private Boolean controlPressed = false;
    private Boolean zPressed = false;
    private Boolean yPressed = false;
    private Boolean qPressed = false;

	@FXML 
	private Label overdueCounter;
	@FXML 
	private Rectangle overdueRectangle;
	@FXML
	private TextField commandText;
	@FXML 
	private Label instantFeedback;

	private Feedback feedback;

	// Reference to the main application.
	private MainApp mainApp;

	public HelpOverviewController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		showOverdueCounter();	
		displayLabels();
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
	 * Displays instant feedback labels.
	 */
	private void displayLabels() {
		feedback = Feedback.getInstance();
		instantFeedback.setText(feedback.getMessage());
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
	 * get total number of deadline and event tasks in an list of list of tasks from MainLogic.
	 * @param array : a list of list of tasks
	 * @return The number of deadline and event tasks
	 */
	private int getNoOfDAndETasks(ArrayList<ArrayList<Task>> array) {
		int counter=0;
		for (int i = 0; i < array.size(); i++) {
			if(i != MAIN_LOGIC_FLOATING)
			counter += array.get(i).size();
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
		UserInput userInput = new UserInput(command, USER_INPUT_HELP_TAB);
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
