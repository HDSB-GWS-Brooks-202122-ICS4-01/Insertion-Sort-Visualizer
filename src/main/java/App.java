
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static double[] array;
    public static Random RAND = new Random();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"));
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(WindowEvent -> {
            System.exit(0);
        });
    }

    static public void setArray(double[] arr) {
        array = arr;
    }

    static public double[] getArray() {
        return array;
    }

    static void setRoot(Parent parent) throws IOException {
        scene.setRoot(parent);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }

}