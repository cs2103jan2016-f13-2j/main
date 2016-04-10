//@@author A0124487Y
package main.gui.resources;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import main.gui.MainApp;
import main.logic.MainLogic;
import main.resources.Task;
import main.resources.UserInput;




public class HelpOverviewController {
	
	private Boolean controlPressed = false;
    private Boolean zPressed = false;
    private Boolean yPressed = false;
    private Boolean qPressed = false;


	@FXML 
	private TextField commandText;
	
	@FXML private Label overdueCounter;
	@FXML private Rectangle overdueRectangle;
	 
	private MainApp mainApp;

	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public HelpOverviewController() {
	    	
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
			if (getNoOfDAndFTasks(MainLogic.getExpiredTasks()) > 0) {
				overdueCounter.setText(""+getNoOfDAndFTasks(MainLogic.getExpiredTasks()));
				} else {
					overdueRectangle.setOpacity(0);
				}
	    }
	    
		private int getNoOfDAndFTasks(ArrayList<ArrayList<Task>> array) {
			int counter=0;
			for (int i = 0; i < array.size()-1; i++) {
				counter += array.get(i).size();
				}
			return counter;
		}
	    
		public void onEnter(){
			String command = commandText.getText(); //string received from user.
			commandText.setText("");
			UserInput userInput = new UserInput(command, 1);
			MainLogic.run(userInput);	
			mainApp.showTaskOverview(); 
		}    
		
		
		
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

