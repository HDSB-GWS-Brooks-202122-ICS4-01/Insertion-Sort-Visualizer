/* *****************************************************************************
 *  Name:    Selim Abdelwahab
 *
 *  Description: This class is the Controllor for the home screen. Responsible for minor calculations and switching to the next scene.
 *
 *  Written:       23/11/2021
 *  Last updated:  26/11/2021
 **************************************************************************** */

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class HomeController {
   @FXML
   StackPane sp_root;

   @FXML
   HBox hb_container;

   @FXML
   Slider slider_nor;

   @FXML
   Button btn_insertionSort;

   @FXML
   /**
    * This method will call switch to sort with the correct algorithim name.
    * 
    * @throws IOException Will throw an error if the FXML file is unable to load.
    */
   void loadInsertionSort() throws IOException {
      switchToSort("Insertion Sort");
   }

   /**
    * This method will play a scene animation, while also creating an array and
    * switching to the next scene.
    * 
    * @param name Name of the algorithim
    * @throws IOException Will throw an error if the FXML file is unable to load.
    */
   void switchToSort(String name) throws IOException {
      Parent nextScene = App.loadFXML("sort");
      sp_root.getChildren().add(nextScene);
      nextScene.translateYProperty().set(sp_root.getScene().getHeight());

      Pane grid = (Pane) sp_root.getScene().lookup("#grid");

      // Create a timeline
      Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), new KeyValue(nextScene.translateYProperty(), 0, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.seconds(1),
                  new KeyValue(hb_container.translateYProperty(), -sp_root.getHeight(), Interpolator.EASE_BOTH)));
      
      // Play ani
      timeline.play();

      if (slider_nor.getValue() < 2)
         slider_nor.setValue(2);

      timeline.setOnFinished((e) -> {
         // Remove containers
         sp_root.getChildren().remove(hb_container);
         sp_root.getChildren().remove(nextScene);

         int width = (int) Math.round(grid.getWidth() / slider_nor.getValue());
         int size = (int) Math.round(slider_nor.getValue());

         App.setWidth(width);

         App.setArray(App.generateArray(size, grid.getHeight()));
         App.setAlgorithm(name);

         // Sneakily set the scen ;)
         try {
            App.setRoot("sort");
         } catch (IOException err) {
            err.printStackTrace();
         }
      });

      timeline.play();
   }
}