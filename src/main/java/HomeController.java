import java.io.IOException;
import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomeController {
   @FXML
   StackPane sp_root;

   @FXML
   VBox vb_container;

   @FXML
   Button btn_startSort;

   @FXML
   void switchToSort() throws IOException {
      Parent nextScene = App.loadFXML("sort");
      sp_root.getChildren().add(nextScene);
      nextScene.translateYProperty().set(sp_root.getScene().getHeight());

      GridPane grid = (GridPane) sp_root.getScene().lookup("#grid");

      // Create a timeline instance
      Timeline timeline = new Timeline();
      // Create a keyValue. We need to slide in -- We gradually decrement Y value to
      // Zero
      KeyValue kv = new KeyValue(nextScene.translateYProperty(), 0, Interpolator.EASE_OUT);
      // Create keyframe of 1s with keyvalue kv
      KeyFrame kf = new KeyFrame(Duration.seconds(2), kv);
      // Add frame to timeline
      timeline.getKeyFrames().add(kf);

      // Start animation
      timeline.setOnFinished((e) -> {
         sp_root.getChildren().remove(vb_container);
         sp_root.getChildren().remove(nextScene);

         double[] array = new double[grid.getColumnCount()];

         for (int i = 0; i < array.length; i++) {
            array[i] = App.RAND.nextDouble() * grid.getHeight();
         }

         App.setArray(array);

         try {
            App.setRoot("sort");
         } catch (IOException err) {
            err.printStackTrace();
         }
      });

      timeline.play();
   }
}