package main.gui;

import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.gui.resources.TaskOverviewController;
import main.resources.Task;

public class MainApp extends Application {

	//Window titles
	private static final String WINDOW_TITLE = "Trekker";
	
	//Layout file paths
	private static final String FXML_ROOT_LAYOUT = "resources/RootLayout.fxml";
	private static final String FXML_TASK_OVERVIEW = "resources/TaskOverview.fxml";
	

    private Stage primaryStage;
    private BorderPane rootLayout;

 // ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of Tasks.
     */
    private ObservableList<Task> taskData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        /*
    	taskData.add(new Task("wash hand", "Muster"));
        taskData.add(new Task("wash leg", "Mueller"));
        taskData.add(new Task("wash clothes", "Kurz"));
        taskData.add(new Task("wash car", "Meier"));
        taskData.add(new Task("wash nose", "Meyer"));
        taskData.add(new Task("wash hair", "Kunz"));
        taskData.add(new Task("wash shoe", "Best"));
        taskData.add(new Task("wash toilet", "Meier"));
        taskData.add(new Task("do work", "Mueller"));
        */
        
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
        this.primaryStage.setTitle(WINDOW_TITLE);
        initRootLayout();

        showTaskOverview();
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