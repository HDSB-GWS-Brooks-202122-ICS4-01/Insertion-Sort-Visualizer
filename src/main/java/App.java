/* *****************************************************************************
 *  Name:    Selim Abdelwahab
 *
 *  Description: Entry point of the program, used for loading scenes and as a setter or getter.
 *
 *  Written:       23/11/2021
 *  Last updated:  26/11/2021
 **************************************************************************** */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * App class
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static double[] array;
    private static String algorithm;
    private static int rectWidth;
    public static Random RAND = new Random();

    @Override
    /**
     * Acts as a constructor for the App class.
     */
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"));
        stage.setTitle("Sorting Algorithms");

        stage.setMaxWidth(stage.getWidth());
        stage.setMaxHeight(stage.getHeight());

        stage.setScene(scene);
        stage.show();

        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                stage.setMaximized(false);
        });

        App.stage = stage;
    }

    /**
     * This method will set the width of the rectangles
     * @param d
     */
    public static void setWidth(int width) {
        rectWidth = width;
    }

    /**
     * This method will return the rectangle widths.
     * 
     * @return integer value.
     */
    static public int getRectWidth() {
        return rectWidth;
    }

    /**
     * This method will generate an array of type double and return it
     * 
     * @param size  Size of the array
     * @param bound Upper limit of the maximum value in the array.
     * @return Array of type double.
     */
    static double[] generateArray(int size, double bound) {
        double[] array = new double[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = App.RAND.nextDouble() * bound;
        }

        return array;
    }

    /**
     * This method will set the array for heights.
     * 
     * @param arr array of type double.
     */
    static public void setArray(double[] arr) {
        array = arr;
    }

    /**
     * This method will return the array of double, containing height values.
     * 
     * @return array of type double
     */
    static public double[] getArray() {
        return array;
    }

    /**
     * This method will set the name of the algorithm.
     * 
     * @param name String value for the algorithm.
     */
    public static void setAlgorithm(String name) {
        algorithm = name;
    }

    /**
     * This method returns the name of the algorithm.
     * 
     * @return String value of the algorithm.
     */
    public static String getAlgorithm() {
        return algorithm;
    }

    /**
     * This method will set the scene to an fxml file.
     * 
     * @param parent Root node.
     */
    static void setRoot(Parent parent) {
        scene.setRoot(parent);
    }

    /**
     * This method will set the scene to an fxml file.
     * 
     * @param fxml String name of file (exluding .fxml).
     * @throws IOException Will throw an IOException if the fxml file is not found.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * This method will load the fxml file specified.
     * 
     * @param fxml The string name for the fxml file.
     * @return Parent or root node with all its childrent.
     * @throws IOException Will throw an IOException if the fxml file is not found.
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * This method returns the scene.
     * 
     * @return The scene
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * This method will return the stage (window).
     * 
     * @return The stage.
     */
    static Stage getStage() {
        return stage;
    }

    /**
     * Entry point of the program.
     * 
     * @param args Run arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}