import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SortController {
   private Rectangle[] array;
   private Rectangle[] defaultArray;
   private Stage stage;
   private final Random RAND = new Random();
   private boolean oppositeDirection = false;
   private boolean playAuto = false;

   private final Timer TIMER = new Timer();
   private final TimerTask AUTO = new TimerTask() {
      public void run() {
         if (playAuto)
            sort(true, oppositeDirection);
      }
   };

   private int iSort = 0, jSort = 0;

   @FXML
   Pane grid;

   @FXML
   private void switchToPrimary() throws IOException {
      App.setRoot("primary");
   }

   @FXML
   /**
    * This method initializes this scene
    */
   public void initialize() {
      double[] arrayHeights = App.getArray();

      if (arrayHeights != null) {
         stage = App.getStage();

         array = new Rectangle[arrayHeights.length];

         // for (int i = array.length - 1; i >= 0; i--) {
         // Rectangle rect = (Rectangle) grid.getChildren().get(array.length - i - 1);

         // double height = arrayHeights[i];

         // if (height < 0.2)
         // height += 0.3;

         // int rectHeight = (int) height;

         // rect.setFill(new Color(App.RAND.nextDouble(), App.RAND.nextDouble(),
         // App.RAND.nextDouble(), 1.0));
         // rect.setHeight(rectHeight);
         // rect.setOpacity(0);

         // FadeTransition fiTransition = new FadeTransition(Duration.seconds(3), rect);
         // fiTransition.setFromValue(0.0);
         // fiTransition.setToValue(1.0);
         // fiTransition.play();

         // array[array.length - i - 1] = rect;
         // }

         for (int i = 0; i < array.length; i++) {
            Rectangle rect = (Rectangle) grid.getChildren().get(i);

            double height = arrayHeights[i];

            if (height < 0.2)
               height += 0.3;

            int rectHeight = (int) height;

            rect.setFill(new Color(App.RAND.nextDouble(), App.RAND.nextDouble(), App.RAND.nextDouble(), 1.0));
            rect.setHeight(rectHeight);
            rect.setOpacity(0);

            FadeTransition fiTransition = new FadeTransition(Duration.seconds(1.5), rect);
            fiTransition.setFromValue(0.0);
            fiTransition.setToValue(1.0);
            fiTransition.play();

            array[i] = rect;
         }

         defaultArray = Arrays.copyOf(array, array.length);

         stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Rectangle r : array) {
               double scale = (double) newVal / (double) stage.getMinWidth();
               r.setScaleX(scale);
            }
         });

         stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            for (Rectangle r : array) {
               double scale = (double) newVal / (double) stage.getMinHeight();
               r.setScaleY(scale);
            }
         });
      }
   }

   /**
    * This method will sort the array in a manner that corrosponds with the
    * paramateres
    */
   public void sort(boolean isStep, boolean isReveresed) {
      System.out.println(true);

      if (iSort == jSort && jSort == array.length - 1) {
         //TODO: Tell user sorting is complete
         return;
      }

      if (isStep) {
         Rectangle one = array[iSort];
         Rectangle two = array[jSort];

         if ((isReveresed && two.getHeight() > one.getHeight()) || (!isReveresed && two.getHeight() < one.getHeight())) {
            TranslateTransition ttOne = new TranslateTransition(Duration.seconds(1), one);
            double oneX = one.getX();
            double twoX = two.getX();

            // TranslateTransition ttTwo = new TranslateTransition(Duration.seconds(1),
            // two);

            // ttOne.setFromX(-400);
            // ttOne.setToX(twoX);
            // ttOne.setToY(100);

            // // ttTwo.setFromX(400);
            // // ttTwo.setToX(oneX);
            // // ttTwo.setToY(100);

            one.setX(twoX);
            two.setX(oneX);

            array[iSort] = two;
            array[jSort] = one;
         }
      }

      if (jSort >= array.length - 1) {
         iSort++;
         jSort = iSort;
      } else
         jSort++;
   }

   /**
    * This method will only run one step of the sort.
    */
   @FXML
   public void runStep() {
      playAuto = false;
      sort(true, oppositeDirection);

   }

   /**
    * This method will run the sort at some constant speed.
    */
   @FXML
   public void runAuto() {
      playAuto = true;
      try {
         TIMER.scheduleAtFixedRate(AUTO, 0, 100);
      } catch (IllegalStateException e) {
      } // Just means the task is already scheduled
   }

   /**
    * This method will reset the sort to it's original array.
    */
   @FXML
   public void runReset() {
      System.out.println(true);
      array = Arrays.copyOf(defaultArray, defaultArray.length);

      grid.getChildren().clear();

      for (int i = 0; i < array.length; i++) {
         Rectangle rect = array[i];

         grid.getChildren().add(rect);
         rect.setX(rect.getWidth() * i);
      }
      
      iSort = 0;
      jSort = 0;
   }

   /**
    * This method will reverse the direction of the sort.
    */
   @FXML
   public void runSwitchDirection() {
      oppositeDirection = !oppositeDirection;
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