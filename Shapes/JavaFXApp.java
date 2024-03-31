    import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.*;

import javafx.concurrent.*;

import javafx.beans.*;
import javafx.beans.value.*;

 import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;


 class Shape {
    String color;
    double x, y;
    double area;
    double xCentroid, yCentroid;



    public void draw(GraphicsContext gc){}

    public void calcArea(){}

    public void getCentroids(){}

}

 class Square extends Shape {
    double width;

    public Square(double x, double y, String color, double width){
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;

        calcArea();
        getCentroids();
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.valueOf(color));
        gc.setLineWidth(5);
        gc.fillRect(x,y,width,width);
    }

    public void calcArea(){
        area = width*width;
    }

    public void getCentroids(){
        xCentroid = x + (width/2);
        yCentroid = y + (width/2);
    }
}

 class Rect extends Shape {
    double width;
    double height;

    public Rect(double x, double y, String color, double width, double height){
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;

        calcArea();
        getCentroids();

    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.valueOf(color));
        gc.setLineWidth(5);
        gc.fillRect(x,y,width,height);
    }

    public void calcArea(){
        area = width * height;
    }

    public void getCentroids(){
        xCentroid = x + (width/2);
        yCentroid = y + (height/2);
    }
}


 class Circle extends Shape {
    double d;

    public Circle(double x, double y, String color, double d){
        this.x = x;
        this.y = y;
        this.color = color;
        this.d = d;

        calcArea();
        getCentroids();

    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.valueOf(color));
        gc.setLineWidth(5);
        gc.fillOval(x,y,d,d);
    }

    public void calcArea(){
        area = Math.PI * (d/2)*(d/2);
    }

    public void getCentroids(){
        xCentroid = x + (d/2);
        yCentroid = y + (d/2);
    }
}

class M_c implements Comparator <Shape>{
    public int compare(Shape s1, Shape s2){
        if (s1.area > s2.area){
            return -1;
        } else if (s1.area == s2.area){
            return 0;
        } else{
            return 1;
        }
    }
}

public class JavaFXApp extends Application
 {

  private static final int FRAME_WIDTH  = 960;
  private static final int FRAME_HEIGHT = 600;
  Stage stage;

  GraphicsContext gc;
  Canvas canvas;

  Shape sh[];


  public static void main(String[] args) {
            launch(args);
	        }

@Override
  public void start(Stage primaryStage) {
  primaryStage.setTitle("JavaFX App");
  AnchorPane root = new AnchorPane();

  canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
  gc = canvas.getGraphicsContext2D();


  stage = primaryStage;

  Menu menu1 = new Menu("File");

  MenuItem menuItem1 = new MenuItem("Item 1");

  MenuItem menuItem2 = new MenuItem("Exit");

  menuItem2.setOnAction(e -> {
                              System.out.println("Exit Selected");

                              exit_dialog();

                             });

  menu1.getItems().add(menuItem1);
  menu1.getItems().add(menuItem2);


  MenuBar menuBar = new MenuBar();

  menuBar.getMenus().add(menu1);

  VBox vBox = new VBox(menuBar);



  vBox.getChildren().add(canvas);
  Scene scene = new Scene(vBox, 960, 600);


   sh = new Shape[5];

  sh[0] = (Square) new Square(100,100,"blue",40);
  sh[1] = (Circle) new Circle(300,500,"green",20);
  sh[2] = (Rect) new Rect(150,400,"purple",50, 90);
  sh[3] = (Rect) new Rect(600,150,"red",15, 60);
  sh[4] = (Circle) new Circle(350,200,"pink", 90);

  Arrays.sort(sh, new M_c());

  for(int i=0; i<sh.length; i++){
    if(sh[i] instanceof Rect){
      sh[i].draw(gc);
    }
    System.out.println(sh[i].area);
    //System.out.println(sh[i].xCentroid);
    //System.out.println(sh[i].yCentroid);
  }


  primaryStage.setScene(scene);

  primaryStage.setOnCloseRequest(e -> {
                                       e.consume();
                                       exit_dialog();
                                      });

  primaryStage.show();

 }




 public void item_1()
  {
   System.out.println("item 1");
  }

 public void exit_dialog()
  {
   System.out.println("exit dialog");


   Alert alert = new Alert(AlertType.CONFIRMATION,
                           "Do you really want to exit the program?.",
 			    ButtonType.YES, ButtonType.NO);

   alert.setResizable(true);
   alert.onShownProperty().addListener(e -> {
                                             Platform.runLater(() -> alert.setResizable(false));
                                            });

  Optional<ButtonType> result = alert.showAndWait();
  if (result.get() == ButtonType.YES)
   {
    Platform.exit();
   }
  else
   {
   }

  }
}
