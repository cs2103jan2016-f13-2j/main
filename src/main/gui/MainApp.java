package main.gui;

import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.gui.resources.HelpOverviewController;
import main.gui.resources.TaskOverviewController;
import main.gui.resources.CompletedOverviewController;
import main.resources.Task;

public class MainApp extends Application {

	//Window titles
	private static final String WINDOW_TITLE = "Trekker";
	
	//Layout file paths
	private static final String FXML_ROOT_LAYOUT = "resources/RootLayout.fxml";
	private static final String FXML_TASK_OVERVIEW = "resources/TaskOverview.fxml";
	private static final String FXML_HELP_OVERVIEW = "resources/HelpOverview.fxml";
	private static final String FXML_COMPLETED_OVERVIEW = "resources/CompletedOverview.fxml";
	

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
     * @return
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }

    // ... THE REST OF THE CLASS ...
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image("file:TrekkerIcon.png"));
        this.primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        initRootLayout();
        showTaskOverview();
       // showHelpOverview();
        logger.log(Level.INFO, "UI Opened Successfully.");
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
        }
    }

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
            e.printStackTrace();
        }
    }
    
    
    public void showCompletedOverview() {
        try {
            // Load Task overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(FXML_COMPLETED_OVERVIEW));
            AnchorPane completedOverview = (AnchorPane) loader.load();

            // Set task overview into the center of root layout.
            rootLayout.setCenter(completedOverview);

            // Give the controller access to the main app.
            CompletedOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
//aaa
    public static void main(String[] args) {
        launch(args);
    }
}