/* *****************************************************************************
 *  Name:    Selim Abdelwahab
 *
 *  Description: This class is the Controller for the Sort scene. All the beauty happens in this class, sorting, animations, UI, pretty much the whole program.
 *
 *  Written:       23/11/2021
 *  Last updated:  26/11/2021
 **************************************************************************** */

import java.io.IOException;
import java.util.Arrays;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Sort controller class
 */
public class SortController {
   private Rectangle[] array;                   // Array containing all the rectangles
   private Rectangle[] defaultArray;            // Copy of the array
   private boolean oppositeDirection = false;   // Direction of comparisons

   private int iSort = 0, jSort = 1;            // Acts as the i and j of a nested loop
   private double inactiveOpacity = 0.1;        // The opacity of inactive rectangles

   private Rectangle previous = null;           // Previous rectangle that was checked

   private double autoTimeInterval = 1000;      // Time of auto sort
   private boolean sortComplete = false;        // Boolean value for if the sort is complete
   private boolean playAuto = false;            // Boolean value for the auto sort.

   private int reminaingComparisons;            // Number of remaining comparisions
   private int defaultRemainingComparisons;     // A copy of the number of remaining comparisons

   private boolean allowAdvance = true;         // If in an animation this is set to false
   private boolean playAnimation = true;        // Play animations

   @FXML
   StackPane sp_root;

   @FXML
   HBox hb_container;

   @FXML
   Slider slider_speed;

   @FXML
   ImageView img_iArrow, img_jArrow;

   @FXML
   Pane grid;

   @FXML
   // Label name of algorithm //Label speed of animation // Label number of
   // rectangles // Label remaining
   // comparisons // Label main
   // rectangle // Label sub rectangle // Label positive comparison // Lable sort
   // complete
   Label lbl_title, lbl_soa, lbl_nor, lbl_rc, lbl_mr, lbl_sr, lbl_pc, lbl_sc;

   @FXML
   CheckBox cb_displayArrows, cb_playAnimations;

   Timeline autoSort;

   @FXML
   /**
    * This method initializes this scene
    */
   public void initialize() {
      double[] arrayHeights = App.getArray();

      if (arrayHeights != null) {
         array = new Rectangle[arrayHeights.length];

         reminaingComparisons = 0;

         // Loop through the array of heights
         for (int i = 0; i < array.length; i++) {
            reminaingComparisons += i;

            // Create a new rectangle and define a few properties
            Rectangle rect = new Rectangle(App.getRectWidth(), 0);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(App.getRectWidth() / 5);

            // Add the rectangle to the scene
            grid.getChildren().add(rect);

            // Adjust it's x position
            rect.setX(App.getRectWidth() * i);

            double height = arrayHeights[i];

            // Adjust the height if neccessary
            if (height / grid.getMinHeight() < 0.1)
               height *= 2;

            // rect.setFill(new Color(App.RAND.nextDouble(), App.RAND.nextDouble(),
            // App.RAND.nextDouble(), 1.0));

            // Set color, height and opacity
            rect.setFill(Color.GREY);
            rect.setHeight(height);
            rect.setOpacity(0);

            // Subtle fade in animation
            FadeTransition fiTransition = new FadeTransition(Duration.seconds(1.5), rect);
            fiTransition.setFromValue(0.0);
            fiTransition.setToValue((i > 1) ? inactiveOpacity : 1.0);
            fiTransition.play();

            // Finally assing the rectangle a position in the array
            array[i] = rect;
         }

         // Copy the array over to another one should the user wish to reset
         defaultArray = Arrays.copyOf(array, array.length);

         defaultRemainingComparisons = reminaingComparisons;

         // Notify the user with number of rectangles
         lbl_nor.setText("Number Of Rectangles: " + array.length);

         // Notify user with remianing comparisons
         lbl_rc.setText("Remianing Comparisons: " + reminaingComparisons);

         // Event handler to check if the speed slider is changed, if so the speed of the
         // auto animation will change.
         slider_speed.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging,
                  Boolean changing) {
               updateAutoSort();
            }
         });
         slider_speed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
               updateAutoSort();
            }
         });

         cb_displayArrows.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging,
                  Boolean changing) {
               if (cb_displayArrows.isSelected()) {
                  img_iArrow.setVisible(true);
                  img_jArrow.setVisible(true);

                  Timeline ani = new Timeline();
                  KeyFrame frame1 = new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(img_iArrow.translateYProperty(), 0));
                  KeyFrame frame2 = new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(img_jArrow.translateYProperty(), 0));

                  ani.getKeyFrames().addAll(frame1, frame2);

                  ani.play();
               } else {
                  Timeline ani = new Timeline();
                  KeyFrame frame1 = new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(img_iArrow.translateYProperty(), -1000));
                  KeyFrame frame2 = new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(img_jArrow.translateYProperty(), -1000));

                  ani.getKeyFrames().addAll(frame1, frame2);

                  ani.setOnFinished((e) -> {
                     img_iArrow.setVisible(false);
                     img_jArrow.setVisible(false);
                  });

                  ani.play();
               }
            }
         });

         cb_playAnimations.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging,
                  Boolean changing) {
               playAnimation = cb_playAnimations.isSelected();
            }
         });

         img_iArrow.setX(-img_iArrow.getFitWidth() / 3);
         img_jArrow.setX(-img_iArrow.getFitWidth() / 3);

         lbl_title.setText("Algorithm: " + App.getAlgorithm().toUpperCase());
      }
   }

   /**
    * This method will re define the autoSort timeline as the user changes the
    * duration.
    */
   private void updateAutoSort() {
      resetAnimation();
      int speed = (int) ((slider_speed.getMax() - slider_speed.getValue() + 0.01) * 1000);
      autoTimeInterval = speed;

      autoSort = new Timeline(new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent ae) {
            // Call the sort method.
            if (playAuto && allowAdvance)
               sort(false, oppositeDirection, false);
         }
      }));

      // Set cyle to infinite
      autoSort.setCycleCount(Timeline.INDEFINITE);

      autoSort.play();

      if (speed == 1000)
         lbl_soa.setText("Speed: 1s");
      else
         lbl_soa.setText("Speed: " + speed + "ms");
   }

   /**
    * This method will reset the auto play animation
    */
   private void resetAnimation() {
      if (autoSort != null) {
         // Every single one of those calls are neccessary or an infinite loop happens.
         autoSort.pause();
         autoSort.stop();
         autoSort.getKeyFrames().clear();
         autoSort.setCycleCount(0);
         autoSort.setRate(0);

         autoSort = null;
      }
   }

   /**
    * This method will sort the array in a manner that corrosponds with the
    * paramateres
    * 
    * @param isStep      If the method is being called as a single step
    * @param isReveresed Direction of sort
    * @param fullSort    Sort all at once
    */
   public void sort(boolean isStep, boolean isReveresed, boolean fullSort) {
      // Sort is already complete -> exit function
      if (sortComplete)
         return;

      // Sort is comeplete do last bit of animation
      if (iSort > array.length || jSort > array.length - 1) {
         for (Rectangle rect : array) {
            rect.setOpacity(1.0);

            rect.setFill(Color.TURQUOISE);
         }

         if (!sortComplete) {
            img_iArrow.setX(img_jArrow.getX());
            lbl_sc.setText("Sort Complete: True");
            sortComplete = true;
         }
         return;
      }

      lbl_mr.setText("Main Rectangle: " + iSort);
      lbl_sr.setText("Sub Rectangle: " + jSort);

      boolean sorted = false;

      // Fetch the current comparisons
      Rectangle one = array[iSort];
      Rectangle two = array[jSort];

      // Reset opacity of previous rectangle
      if (previous != null)
         previous.setOpacity(inactiveOpacity);

      previous = two;

      // Set opcatity of current rectangles
      one.setOpacity(1.0);
      two.setOpacity(1.0);

      // Set x positions of arrows
      if (!fullSort) {
         img_iArrow.setX(one.getX() - img_iArrow.getFitWidth() / 3);
         img_jArrow.setX(two.getX() - img_iArrow.getFitWidth() / 3);
      }

      // Check comparison
      if ((isReveresed && two.getHeight() > one.getHeight()) || (!isReveresed && two.getHeight() < one.getHeight())) {
         double oneX = one.xProperty().getValue();
         double twoX = two.xProperty().getValue();

         if (!fullSort && playAnimation) {
            allowAdvance = false;

            // Create animation
            Timeline ani = new Timeline();
            KeyFrame transfer1;
            KeyFrame transfer2;

            // Add keyframes
            if (isStep) {
               transfer1 = new KeyFrame(Duration.seconds(1), new KeyValue(two.xProperty(), oneX));
               transfer2 = new KeyFrame(Duration.seconds(1), new KeyValue(one.xProperty(), twoX));
            } else {
               transfer1 = new KeyFrame(Duration.millis(autoTimeInterval), new KeyValue(two.xProperty(), oneX));
               transfer2 = new KeyFrame(Duration.millis(autoTimeInterval), new KeyValue(one.xProperty(), twoX));
            }

            ani.getKeyFrames().addAll(transfer1, transfer2);
            ani.play();
            ani.setOnFinished((e) -> {
               // Reset and set some values
               allowAdvance = true;

               array[iSort] = two;
               array[jSort] = one;

               increment(one, two);
            });

            sorted = true;
         } else {
            // Change positions
            one.setX(twoX);
            two.setX(oneX);

            array[iSort] = two;
            array[jSort] = one;

            if (!playAnimation) {
               increment(one, two);
               sorted = true;
            }
         }
      }

      if (!sorted) {
         lbl_pc.setText("Positive Comparison: False");

         // Sorted so set color to green
         if (jSort >= array.length - 1) {
            one.setFill(Color.GREEN);
            one.setOpacity(1.0);

            iSort++;
            jSort = iSort + 1;

         } else {
            // iterate to the next index.
            jSort++;

         }

         reminaingComparisons--;

         lbl_rc.setText("Remaining Comparisons: " + reminaingComparisons);
      }
   }

   /**
    * This method will increment the i and j sort values by one and also updat
    * labels.
    * 
    * @param one The main rectangle
    * @param two The sub rectangle
    */
   private void increment(Rectangle one, Rectangle two) {
      lbl_pc.setText("Positive Comparison: True");
      two.setOpacity(1.0);

      previous = one;

      // Sorted so set color to green
      if (jSort >= array.length - 1) {
         two.setFill(Color.GREEN);
         two.setOpacity(1.0);

         iSort++;
         jSort = iSort + 1;

      } else {
         // iterate to the next index.
         jSort++;

      }

      reminaingComparisons--;

      lbl_rc.setText("Remaining Comparisons: " + reminaingComparisons);
   }

   /**
    * This method will only run one step of the sort.
    */
   @FXML
   public void runStep() {
      if (!sortComplete && allowAdvance) {
         resetAnimation();

         sort(true, oppositeDirection, false);
      }

   }

   /**
    * This method will run the sort at some constant speed.
    */
   @FXML
   public void runAuto() {
      playAuto = true;

      updateAutoSort();
   }

   /**
    * This method will reset the sort to it's original array.
    */
   @FXML
   public void runReset() {
      reset();

      displayArray();
   }

   /**
    * This method will reverse the direction of the sort.
    */
   @FXML
   public void runSwitchDirection() {
      oppositeDirection = !oppositeDirection;
   }

   /**
    * This sort the entire array in an istant.
    */
   @FXML
   public void runFullSort() {
      while (!sortComplete) {
         sort(false, oppositeDirection, true);
      }

      img_iArrow.setX(-img_iArrow.getFitWidth() / 3);
      img_jArrow.setX(-img_jArrow.getFitWidth() / 3);

      img_iArrow.setX(-img_iArrow.getFitWidth() / 3);
      img_jArrow.setX(-img_iArrow.getFitWidth() / 3);
   }

   /**
    * This method will re randomize the array.
    */
   @FXML
   public void runRandomize() {
      reset();
      double[] arr = App.generateArray(array.length, grid.getHeight());

      // Pretty much does the same thing in the initialize but instead it creates it's
      // own array
      for (int i = 0; i < array.length; i++) {

         Rectangle rect = new Rectangle(App.getRectWidth(), 0);
         rect.setStroke(Color.BLACK);
         rect.setStrokeWidth(App.getRectWidth() / 5);

         grid.getChildren().add(rect);

         rect.setX(App.getRectWidth() * i);

         double height = arr[i];

         if (height < 0.2)
            height += 0.3;

         // Set color, height and opacity
         rect.setFill(Color.GREY);
         rect.setHeight(height);
         rect.setOpacity(0);

         // Subtle fade in animation
         FadeTransition fiTransition = new FadeTransition(Duration.seconds(1.5), rect);
         fiTransition.setFromValue(0.0);
         fiTransition.setToValue((i > 1) ? inactiveOpacity : 1.0);
         fiTransition.play();

         array[i] = rect;
      }

      defaultArray = Arrays.copyOf(array, array.length);
   }

   /**
    * This method will reset values
    */
   private void reset() {
      resetAnimation();
      reminaingComparisons = defaultRemainingComparisons;

      sortComplete = false;

      // Reset array
      array = Arrays.copyOf(defaultArray, defaultArray.length);

      iSort = 0;
      jSort = 1;

      // Reset arrows
      img_iArrow.setX(-img_iArrow.getFitWidth() / 3);
      img_jArrow.setX(-img_jArrow.getFitWidth() / 3);

      img_iArrow.setX(-img_iArrow.getFitWidth() / 3);
      img_jArrow.setX(-img_iArrow.getFitWidth() / 3);

      grid.getChildren().clear();

      // Reset labels
      lbl_rc.setText("Remaining Comparisons: " + reminaingComparisons);
      lbl_mr.setText("Main Rectangle: 0");
      lbl_sr.setText("Sub Rectangle: 1");
      lbl_pc.setText("Positive Comparison: False");
      lbl_sc.setText("Sort Complete: False");
   }

   /**
    * This method will display the array to the screen.
    */
   private void displayArray() {
      for (int i = 0; i < array.length; i++) {
         Rectangle rect = array[i];

         grid.getChildren().add(rect);
         rect.setX(rect.getWidth() * i);

         rect.setFill(Color.GREY);
         rect.setOpacity((i > 1) ? inactiveOpacity : 1.0);
      }
   }

   /**
    * This method will play a scene animation and return to the home screen.
    * 
    * @throws IOException Will throw an error if the FXML file is unable to load.
    */
   @FXML
   void switchToHome() throws IOException {
      Parent nextScene = App.loadFXML("home");
      sp_root.getChildren().add(nextScene);
      nextScene.translateYProperty().set(-sp_root.getScene().getHeight());

      // Create a timeline
      Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), new KeyValue(nextScene.translateYProperty(), 0, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.seconds(1),
                  new KeyValue(hb_container.translateYProperty(), sp_root.getScene().getHeight(),
                        Interpolator.EASE_BOTH)));
      timeline.play();

      // Start animation
      timeline.setOnFinished((e) -> {
         App.setArray(null);
         sp_root.getChildren().remove(hb_container);
         sp_root.getChildren().remove(nextScene);

         try {
            App.setRoot("home");
         } catch (IOException err) {
            err.printStackTrace();
         }
      });

      timeline.play();
   }
}