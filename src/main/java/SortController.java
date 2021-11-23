import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
      array = new Rectangle[grid.getColumnCount()];

      for (int i = 0; i < array.length; i++) {
         Rectangle rect = (Rectangle) grid.getChildren().get(i);
         double height = grid.getHeight();

         System.out.println(grid.getHeight());
         int rectHeight = (int)(RAND.nextDouble() * height);

         rect.setFill(Color.RED);
         // rect.setHeight(rectHeight);

         System.out.println(height);

         array[i] = rect;
      }
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