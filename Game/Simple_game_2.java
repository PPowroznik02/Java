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
import javafx.scene.layout.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

class Ball
{
    double x, y, x0, y0, v, a;
    double g=9.81;
    int d;
    int check = 0;
    int check2 = 0;
    double licznik = 0;

    Ball(){
        this.x0 = 76;
        this.y0 = 307;
        this.x = 0;
        this.y = 0;
        this.a = 30;
        this.v = 10;
        this.d = 30;
    }



    void getNewXY(double t, int MAXHEIGHT){
        x = x0 + v*Math.cos(Math.toRadians(a))*t;
        y = MAXHEIGHT-(MAXHEIGHT-y0 + v*Math.sin(Math.toRadians(a))*t-g*t*t/2);
        //y = y0 + v*Math.sin(Math.toRadians(a))*t-g*t*t/2;
        //325, 450, 325, 260
        //20, 450, 630, 450)
        if(check==0) netCollision(t);
        else x = 325 +(325-x0) - 60 - v*Math.cos(Math.toRadians(a))*t;

        if(check2==0){
            licznik+=0.2;
            groundCollision(t, MAXHEIGHT);
        }
        else y = MAXHEIGHT-(MAXHEIGHT - 450 + (450 - y0) + 15 - v*Math.sin(Math.toRadians(a))*(t-licznik)-g*(t-licznik)*(t-licznik)/2);


    }

    boolean detectColision(int MAXHEIGHT, int MAXWIDTH){
        if(x + d >= MAXWIDTH || y -100 >= MAXHEIGHT || y + d <= 0 || x <= 0)
            return true;
        else
            return false;
    }

    void netCollision(double t){
        int netX = 325, netMinY = 450, netMaxY=260;
        if (x+d >= netX && x+d <= netX+15 && y+d*0.5>=260 ){
            check = 1;
            x = 325 +(325-x0) - 60 - v*Math.cos(Math.toRadians(a))*t;
        }
    }

     void groundCollision(double t, int MAXHEIGHT){
        int grY = 450;
        if (y+d >= grY && y+d <= grY+15){
            check2 = 1;
           // x = 325 +(325-x0) - 60 - v*Math.cos(Math.toRadians(a))*t;
            //y = MAXHEIGHT-(MAXHEIGHT-  450 + v*Math.sin(Math.toRadians(a))*t-g*t*t/2);
        }
    }
}

public class Simple_game_2 extends Application implements ChangeListener<Number>
 {
  private static final int FRAME_WIDTH  = 640;
  private static final int FRAME_HEIGHT = 480;  

  int x, y;
  double t, a;

  Timeline timeline;
  GraphicsContext gc;
  Canvas canvas;
  Slider alpha, v;
  Ball ball = new Ball();
      
  public static void main(String[] args) 
   {
    launch(args);
   }


    
    @Override
     public void start(Stage primaryStage)
     {
      AnchorPane root = new AnchorPane();
      primaryStage.setTitle("Volleyball");
	
      canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
      canvas.setOnMousePressed(this::mouse);
      

      gc = canvas.getGraphicsContext2D();


      drawShapes(gc);


      gc.fillOval(ball.x0, ball.y0, ball.d, ball.d);

      x = 10;
      y = 10;
      t = 0;

      root.getChildren().add(canvas);	
	
      Button btn = new Button();
      btn.setText("Play");
      btn.setOnAction(this::play);	

      root.getChildren().add(btn);
      AnchorPane.setBottomAnchor( btn, 5.0d );

      Slider alpha, v;

      alpha = new Slider(30, 80, 5);
      alpha.setShowTickMarks(true);
      alpha.setShowTickLabels(true);
      alpha.valueProperty().addListener(new ChangeListener<Number>() 
                             {
                              public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
			       {    
			        System.out.println("alpha=" + new_val);  
			        ball.a = new_val.doubleValue();
			       }
			     });
			       
			       
			       
      root.getChildren().add(alpha);      

      AnchorPane.setBottomAnchor( alpha, 2.0d );
      AnchorPane.setLeftAnchor( alpha, 150.0d );      
      
      
      v = new Slider(10, 100, 10);
      v.setShowTickMarks(true);      
      v.setShowTickLabels(true);
      v.valueProperty().addListener(this::changed);
      
      root.getChildren().add(v);
            
      AnchorPane.setBottomAnchor( v, 2.0d );
      AnchorPane.setLeftAnchor( v, 300.0d );            
      
      Scene scene = new Scene(root);
      primaryStage.setTitle("Volleybal");
      primaryStage.setScene( scene );
      primaryStage.setWidth(FRAME_WIDTH + 10);
      primaryStage.setHeight(FRAME_HEIGHT+ 80);
      primaryStage.show();   



    }

    private void drawShapes(GraphicsContext gc) {
       // gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(20, 450, 630, 450);
        gc.strokeLine(325, 450, 325, 260);
    }






    public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
     {    
      System.out.println("v=" + new_val);  
      ball.v = new_val.doubleValue();
     }


    
    private void step()
     {
      System.out.println("step");     
       //gc.fillRect(x, y, 100, 100);
       //gc.fillOval(x, 60, 30, 30);

        ball.getNewXY(t, FRAME_HEIGHT);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawShapes(gc);
        if(ball.detectColision(FRAME_HEIGHT, FRAME_WIDTH)){
            System.out.println(ball.check);
            gc.fillOval(ball.x0, ball.y0, ball.d, ball.d);
            t=0;
            timeline.pause();
        }
        else{

            gc.fillOval(ball.x, ball.y, ball.d, ball.d);
        }
      x+=10;
      t+=0.1;
     }    
   private void mouse(MouseEvent e)
    {
     System.out.println("X=" + e.getX());
     System.out.println("Y=" + e.getY());
    // gc.setFill(Color.WHITE);
   //  gc.setStroke(Color.WHITE);
    // gc.fillOval(ball.x0, ball.y0, 35, 35);
    gc.clearRect(ball.x0, ball.y0, 35, 35);
     ball.x0 = e.getX();
     ball.y0 = e.getY();
    // gc.setStroke(Color.BLACK);
     //gc.setFill(Color.BLACK);
     gc.fillOval(ball.x0, ball.y0, ball.d, ball.d);
    //t = 0;
    }
    
   private void play(ActionEvent e)    
    {
     //Timeline timeline;
     
     timeline = new Timeline(new KeyFrame(Duration.millis(20), e1->step()));

     timeline.setCycleCount(Timeline.INDEFINITE);
     //timeline.setCycleCount(20);

     timeline.play();
     t=0;
     ball.check=0;
     ball.check2=0;
     ball.licznik = 0;
     
    }    
    
}	
