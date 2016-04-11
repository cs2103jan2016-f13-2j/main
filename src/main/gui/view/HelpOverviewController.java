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
		initializeLabels();
	}


	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private void initializeLabels() {
		feedback = Feedback.getInstance();
		instantFeedback.setText(feedback.getMessage());
	}

	/**
	 * displays the number of overdued tasks if any
	 */
	private void showOverdueCounter() {
		if (getNoOfDAndFTasks(MainLogic.getExpiredTasks()) > 0) {
		overdueCounter.setText(""+getNoOfDAndFTasks(MainLogic.getExpiredTasks()));
		} else {
			overdueRectangle.setOpacity(0);
		}
	}
	
	/**
	 * get total number of deadline and floating tasks in an ArrayList<ArrayList<Task>> from MainLogic
	 * @param array
	 * @return
	 */
	private int getNoOfDAndFTasks(ArrayList<ArrayList<Task>> array) {
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
	 * @param keyEvent
	 */
	@FXML
	void onKeyPressed(KeyEvent keyEvent) {
	  processKeyEventPressed(keyEvent);
	}

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

	@FXML
	void onKeyReleased(KeyEvent keyEvent) {
	  processKeyEventReleased(keyEvent);
	}

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
	 * when overdue tab is clicked
	 */
	@FXML 
	void onClickedOverdue() {
		mainApp.showOverdueOverview();
	}
	
	/**
	 * when help tab is clicked
	 */
	@FXML
	void onClickedHelp(){
		mainApp.showHelpOverview();
	}
	
	/** 
	 * when all tasks tab is clicked
	 */
	@FXML
	void onClickedAllTask(){
		mainApp.showTaskOverview();
	}
	
	/**
	 * when today tab is clicked
	 */
	@FXML
	void onClickedToday(){
		mainApp.showTodayOverview();
	}
	
	/**
	 * when upcoming tab is clicked
	 */
	@FXML
	void onClickedUpcoming(){
		mainApp.showUpcomingOverview();
	}
	
	/**
	 * when complete tab is clicked
	 */
	@FXML
	void onClickedComplete(){
		mainApp.showCompleteOverview();
	}
}
