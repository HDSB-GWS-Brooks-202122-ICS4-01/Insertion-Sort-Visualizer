import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SortController {
   private Rectangle[] array;
   private final Random RAND = new Random();

   @FXML
   GridPane grid;

   @FXML
   private void switchToPrimary() throws IOException {
      App.setRoot("primary");
   }

   @FXML
   public void initialize() {
      double[] arrayHeights = App.getArray();

      if (arrayHeights != null && arrayHeights.length == grid.getColumnCount()) {
         array = new Rectangle[arrayHeights.length];

         for (int i = 0; i < array.length; i++) {
            Rectangle rect = (Rectangle) grid.getChildren().get(i);
            double height = arrayHeights[i];

            if (height < 0.2)
               height += 0.3;

            int rectHeight = (int) height;

            rect.setFill(Color.RED);
            rect.setHeight(rectHeight);
            rect.setOpacity(0);

            FadeTransition fiTransition = new FadeTransition(Duration.seconds(3), rect);
            fiTransition.setFromValue(0.0);
            fiTransition.setToValue(1.0);
            fiTransition.play();

            array[i] = rect;
         }
      }
      // TIMER.schedule(CONFIG, 30);
   }

   /**
    * This method will only run one step of the sort.
    */
   @FXML
   public void runStep() {
      System.out.println(grid.getHeight());
   }

   /**
    * This method will run the sort at some constant speed.
    */
   @FXML
   public void runAuto() {

   }

   /**
    * This method will reset the sort to it's original array.
    */
   @FXML
   public void runReset() {

   }

   /**
    * This method will reverse the direction of the sort.
    */
   @FXML
   public void runReverseDirection() {

   }

   /**
    * This method will reverse a step.
    */
   @FXML
   public void runReverseStep() {

   }

   /**
    * This method will re randomize the array.
    */
   @FXML
   public void runRandomize() {

   }
}