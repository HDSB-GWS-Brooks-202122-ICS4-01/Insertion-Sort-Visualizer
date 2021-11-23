import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomeController {
   @FXML
   VBox vb_root;

   @FXML
   Button btn_startSort;

   @FXML
   void switchToSort() throws IOException {
      Parent nextScene = App.loadFXML("sort");
      vb_root.getChildren().add(nextScene);
      nextScene.translateYProperty().set(App.getScene().getHeight());

      // Create a timeline instance
      Timeline timeline = new Timeline();
      // Create a keyValue. We need to slide in -- We gradually decrement Y value to
      // Zero
      KeyValue kv = new KeyValue(nextScene.translateYProperty(), App.getScene().getHeight() * -1, Interpolator.EASE_IN);
      // Create keyframe of 1s with keyvalue kv
      KeyFrame kf = new KeyFrame(Duration.seconds(2), kv);
      // Add frame to timeline
      timeline.getKeyFrames().add(kf);

      // Start animation
      timeline.play();
      timeline.setOnFinished((e) -> {
         try {
            App.setRoot("sort");
         } catch (IOException err) {
            err.printStackTrace();
         }
      });
   }
}