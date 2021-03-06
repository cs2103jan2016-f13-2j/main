//@@author A0124487Y
package main.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.gui.view.CompleteOverviewController;
import main.gui.view.HelpOverviewController;
import main.gui.view.OverdueOverviewController;
import main.gui.view.TaskOverviewController;
import main.gui.view.TodayOverviewController;
import main.gui.view.UpcomingOverviewController;
import main.resources.Task;

public class MainApp extends Application {

	//Window titles
	private static final String WINDOW_TITLE = "Trekker";
	
	//Layout file paths
	private static final String FXML_ROOT_LAYOUT = "view/RootLayout.fxml";
	private static final String FXML_TASK_OVERVIEW = "view/TaskOverview.fxml";
	private static final String FXML_HELP_OVERVIEW = "view/HelpOverview.fxml";
	private static final String FXML_COMPLETE_OVERVIEW = "view/CompleteOverview.fxml";
	private static final String FXML_TODAY_OVERVIEW = "view/TodayOverview.fxml";
	private static final String FXML_UPCOMING_OVERVIEW = "view/UpcomingOverview.fxml";
	private static final String FXML_OVERDUE_OVERVIEW = "view/OverdueOverview.fxml";
	

    private Stage primaryStage;
    private BorderPane rootLayout;

    Logger logger = Logger.getLogger("UI");
 // ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of Tasks.
     */
    private ObservableList<Task> taskData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {       
    }

    /**
     * Returns the data as an observable list of Tasks. 
     * @return the taskData list.
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }

    // ... THE REST OF THE CLASS ...
    
    @Override
    public void start(Stage primaryStage) {
        initializePrimaryStage(primaryStage);
        initRootLayout();
        showTaskOverview();
        logger.log(Level.INFO, "UI Opened Successfully.");
    }

    /**
     * Initializes primary stage.
     * @param primaryStage : the primary stage.
     */
	private void initializePrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image("file:TrekkerIcon.png"));
        this.primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
	}

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_ROOT_LAYOUT));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
        	e.printStackTrace();
        	logger.log(Level.INFO, "UI Root Initialization failed.");
        	e.printStackTrace();
        }
    }

    /**
     * Shows the task overview inside the root layout.
     */
    public void showTaskOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_TASK_OVERVIEW));
            AnchorPane taskOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(taskOverview);

            // Give the controller access to the main app.
            TaskOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	e.printStackTrace();
        	logger.log(Level.INFO, "UI TaskOverview initialization Failed.");
        }
    }
    
    /**
     * Shows the help overview inside the root layout.
     */
    public void showHelpOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_HELP_OVERVIEW));
            AnchorPane helpOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(helpOverview);

            // Give the controller access to the main app.
            HelpOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	logger.log(Level.INFO, "UI HelpOverview initialization Failed.");
        }
    }
    
    /**
     * Shows the completed task overview inside the root layout.
     */
    public void showCompleteOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_COMPLETE_OVERVIEW));
            AnchorPane completeOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(completeOverview);

            // Give the controller access to the main app.
            CompleteOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	logger.log(Level.INFO, "UI CompleteOverview initialization Failed.");
        }
    }

    /**
     * Shows today's task overview inside the root layout.
     */
    public void showTodayOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_TODAY_OVERVIEW));
            AnchorPane todayOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(todayOverview);

            // Give the controller access to the main app.
            TodayOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	logger.log(Level.INFO, "UI TodayOverview initialization Failed.");
        }
    }
    
    /**
     * Shows the upcoming task overview inside the root layout.
     */
    public void showUpcomingOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_UPCOMING_OVERVIEW));
            AnchorPane upcomingOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(upcomingOverview);

            // Give the controller access to the main app.
            UpcomingOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	logger.log(Level.INFO, "UI UpcomingOverview initialization Failed.");
        }
    }
    
    /**
     * Shows the overdue task overview inside the root layout.
     */
    public void showOverdueOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_OVERDUE_OVERVIEW));
            AnchorPane overdueOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(overdueOverview);

            // Give the controller access to the main app.
            OverdueOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	logger.log(Level.INFO, "UI OverdueOverview initialization Failed.");
        }
    }
    
    /**
     * Returns the main stage.
     * @return the primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}