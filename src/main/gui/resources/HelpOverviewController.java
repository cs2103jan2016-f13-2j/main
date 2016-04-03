package main.gui.resources;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.gui.MainApp;
import main.logic.MainLogic;
import main.resources.UserInput;




public class HelpOverviewController {
	
	private Boolean controlPressed = false;
    private Boolean zPressed = false;
    private Boolean yPressed = false;
    private Boolean qPressed = false;


	@FXML 
	private TextField commandText;
	
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

	    }
	    
		public void onEnter(){
			String command = commandText.getText(); //string received from user.
			commandText.setText("");
			//System.out.println(command);
			UserInput userInput = new UserInput(command);
			MainLogic.run(userInput);	
			mainApp.showTaskOverview(); 
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
	      } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
	          mainApp.showTaskOverview();
	      } else if (keyEvent.getCode() == KeyCode.F12) {
	          mainApp.showCompletedOverview();
	      }  else if (keyEvent.getCode() == KeyCode.F1) {
	          mainApp.getPrimaryStage().toBack();
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
	}

