import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;



class Warstwy
{
    //String id;
    double[] geometriax;
    double[] geometriay;
    int typ;
    String kolor;
    String kolor_linii;
    int szerokoscLinii;
    int[] przerywanaLinia;


    Warstwy(Double tab[][], int t)
    {
        this.geometriax = new double[tab.length];
        this.geometriay = new double[tab.length];
        for(int i=0; i<tab.length; i++)
        {
            this.geometriax[i] = tab[i][0];
            this.geometriay[i] = tab[i][1];
        }
        this.typ = t;
    }



}



class Handler_1 extends DefaultHandler
 {
  String wartosci;
  String loc_name;
  ArrayList<Warstwy> geometryList = new ArrayList<>();

  @Override
  public void startElement(String uri,
                           String localName,
			   String qName,
			   Attributes attributes)
  throws SAXException
   {
   int typ = 1;
    double trans_x = 0;
    double trans_y = 0;
    double skala_x = 1;
    double skala_y = 1;



    //System.out.println("Start Element :" + qName);

    for (int i=0; i < attributes.getLength(); i++)
     {
      loc_name = attributes.getQName(i);

        if (qName.equalsIgnoreCase("g") || qName.equalsIgnoreCase("path") ) {
            if (loc_name.equalsIgnoreCase("d")){
                wartosci = attributes.getValue(loc_name);
                if(wartosci.indexOf("a") != -1) continue;
                wartosci = wartosci.replaceAll("( )+", " ");
                wartosci = wartosci.replace("M", "");
                wartosci = wartosci.trim();
                wartosci = wartosci.replace("l", "");
                String[] s = wartosci.split(" ");

                //System.out.println("attr name: : " + loc_name + " value:" + attributes.getValue(loc_name));

                Double[][] wierzcholki = new Double[s.length][2];
                Double x,y;
                String id;
                for(int j=0; j < s.length; j++)
                {
                    if(s[j].equalsIgnoreCase("z")){
                        wierzcholki[j][0] = wierzcholki[0][0];
                        wierzcholki[j][1] = wierzcholki[0][1];
                        typ = 0;
                    }
                    else{
                        String[] wierzcholek = s[j].split(",");
                        x = Double.parseDouble(wierzcholek[0]);
                        y = Double.parseDouble(wierzcholek[1]);

                        if(j != 0){
                            x = wierzcholki[j-1][0] + x;
                            y = wierzcholki[j-1][1] + y;
                        }
                        wierzcholki[j][0] = x;
                        wierzcholki[j][1] = y;
                    }
                }
                 geometryList.add( new Warstwy(wierzcholki, typ));
            }
            else if(loc_name.equalsIgnoreCase("fill")){

                Warstwy obiekt = geometryList.get(geometryList.size()-1);
                obiekt.kolor = attributes.getValue(loc_name);

                geometryList.set(geometryList.size()-1, obiekt);

            }
            else if(loc_name.equalsIgnoreCase("stroke")){

                Warstwy obiekt = geometryList.get(geometryList.size()-1);
                obiekt.kolor_linii = attributes.getValue(loc_name);

                geometryList.set(geometryList.size()-1, obiekt);

            }
            else if(loc_name.equalsIgnoreCase("stroke-width")){

                Warstwy obiekt = geometryList.get(geometryList.size()-1);
                obiekt.szerokoscLinii = Integer.parseInt(attributes.getValue(loc_name));

                geometryList.set(geometryList.size()-1, obiekt);

            }
            /*
            else if(loc_name.equalsIgnoreCase("stroke-dasharray")){

                Warstwy obiekt = geometryList.get(geometryList.size()-1);
                obiekt.przerywanaLinia = attributes.getValue(loc_name);

                geometryList.set(geometryList.size()-1, obiekt);

            }
            */
        }

        if (qName.equalsIgnoreCase("use") ) {
            if(loc_name.equalsIgnoreCase("x")){
                trans_x = Double.parseDouble(attributes.getValue(loc_name));
            }
            if(loc_name.equalsIgnoreCase("y")){
                trans_y = Double.parseDouble(attributes.getValue(loc_name));
            }
            if(loc_name.equalsIgnoreCase("translate")){
                wartosci = attributes.getValue(loc_name);

                wartosci = wartosci.replace("translate(", "");
                wartosci = wartosci.replace(",", " ");
                wartosci = wartosci.replace(")", "");
                wartosci = wartosci.replace("scale(", " ");
                wartosci = wartosci.replaceAll("( )+", " ");
                wartosci = wartosci.trim();

                String[] s = wartosci.split(" ");
                trans_x = Double.parseDouble(s[0]);
                trans_y = Double.parseDouble(s[1]);
                skala_x = Double.parseDouble(s[2]);
                skala_y = Double.parseDouble(s[3]);
            }
            if(loc_name.equalsIgnoreCase("xlink:href")){
                if(attributes.getValue(loc_name).equalsIgnoreCase("#skala_cz")){

                }
                if(attributes.getValue(loc_name).equalsIgnoreCase("#skala_sz")){

                }
            }




        }




     }
//     for (Warstwy geom: geometryList) {
//         for(int l=0; l<geom.geometria.length; l++)
//         {
//             System.out.println(geom.geometria[l][0] + ", " + geom.geometria[l][1]);
//         }
//     }

    if (qName.equalsIgnoreCase("svg"))
     {

     }
   }

  @Override
  public void endElement(String uri,
                         String localName,
		     String qName)
  throws SAXException
   {
    if (qName.equalsIgnoreCase("svg"))
     {
      //System.out.println("End Element :" + qName);
     }
   }


  @Override
  public void characters(char ch[], int start, int length) throws SAXException
   {
    System.out.println(new String(ch, start, length));
   }
 }




public class Map_1 extends Application
 {
  private static final int FRAME_WIDTH  = 640;
  private static final int FRAME_HEIGHT = 480;  


  int x, y;

  GraphicsContext gc;
  Canvas canvas;

      
  public static void main(String[] args) 
   {
    launch(args);
   }


    
    @Override
    public void start(Stage primaryStage) 
     {
    try
     {
      File inputFile = new File("points.xml");
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      Handler_1 handler_1 = new Handler_1();

      saxParser.parse(inputFile, handler_1);

        for (Warstwy geom: handler_1.geometryList) {
            for(int l=0; l<geom.geometriax.length; l++)
            {
                System.out.println(geom.geometriax[l] + ", " + geom.geometriay[l]);
            }
            System.out.println(geom.kolor);
            System.out.println(" ");
        }



      double x_p[] = new double[4];
      double y_p[] = new double[4];

      AnchorPane root = new AnchorPane();
      primaryStage.setTitle("Map");

      canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
      canvas.setOnMousePressed(this::mouse);

      gc = canvas.getGraphicsContext2D();


      // A
      Image image = new Image("map.jpg");

      gc.drawImage(image, 0, 0, FRAME_WIDTH, FRAME_HEIGHT);
      
      // B     
      x_p[0] = 100;
      y_p[0] = 100;
      x_p[1] = 400;
      y_p[1] = 90;      
      x_p[2] = 300;
      y_p[2] = 200;      
      x_p[3] = 80;
      y_p[3] = 180;      
      

/*      gc.setFill(Color.GRAY);
      gc.fillPolygon(x_p, y_p, 4);
      
      gc.setLineWidth(2);
      
      gc.strokePolygon(x_p, y_p, 4);
*/

       for (Warstwy geom: handler_1.geometryList) {
           if(geom.szerokoscLinii != 0){
                gc.setLineWidth(geom.szerokoscLinii);
            }
            else{
                gc.setLineWidth(1);
            }

            if(geom.typ == 0){
                if(!geom.kolor.equalsIgnoreCase("none")){
                    gc.setFill(Color.valueOf(geom.kolor));
                    gc.fillPolygon(geom.geometriax, geom.geometriay, geom.geometriax.length);
                }
                if(geom.kolor_linii != null){
                    gc.setStroke(Color.valueOf(geom.kolor_linii));
                    gc.strokePolygon(geom.geometriax, geom.geometriay, geom.geometriax.length);
                }
            }
            else{
                if(geom.kolor_linii != null){
                        gc.setStroke(Color.valueOf(geom.kolor_linii));
                        gc.strokePolyline(geom.geometriax, geom.geometriay, geom.geometriax.length);
                }
            }
        }
      	
      root.getChildren().add(canvas);	
      
      RadioButton rbtn1 = new RadioButton();
      rbtn1.setText("Woods");
      rbtn1.setSelected(true);      
      rbtn1.setOnAction(this::woods);	

      root.getChildren().add(rbtn1);
      AnchorPane.setBottomAnchor( rbtn1, 5.0d );
      AnchorPane.setLeftAnchor( rbtn1, 50.0d );      


      RadioButton rbtn2 = new RadioButton();
      rbtn2.setText("Rocks");
      rbtn2.setSelected(true);            
      rbtn2.setOnAction(this::rocks);	

      root.getChildren().add(rbtn2);
      AnchorPane.setBottomAnchor( rbtn2, 5.0d );
      AnchorPane.setLeftAnchor( rbtn2, 200.0d );      


     

      Scene scene = new Scene(root);
      primaryStage.setTitle("Dolina Bedkowska");
      primaryStage.setScene( scene );
      primaryStage.setWidth(FRAME_WIDTH + 10);
      primaryStage.setHeight(FRAME_HEIGHT+ 80);
      primaryStage.show();   
    }
    catch (Exception e)
     {
      e.printStackTrace();
     }
    }

   private void woods(ActionEvent e)    
    {
     System.out.println("woods");
    }

   private void rocks(ActionEvent e)    
    {
     System.out.println("rocks");
    }


   
   private void mouse(MouseEvent e)
    {
     System.out.println("X=" + e.getX());
     System.out.println("Y=" + e.getY());         
    }   
    
  
    
}	
