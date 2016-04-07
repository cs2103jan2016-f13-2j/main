//@@author A0124487Y
package main.gui.resources;

import javafx.fxml.FXML;
import main.gui.MainApp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    
    
    /**
     * Minimises the application.
     */
    /**
    @FXML
    private void handleMinimise() {
        //code here
    	mainApp.setIconified(true);
    }
    /**/
}