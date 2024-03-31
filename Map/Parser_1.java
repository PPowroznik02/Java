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
    Double[][] geometria;
    Warstwy(Double tab[][])
    {
        this.geometria = new Double[tab.length][2];
        this.geometria = tab;
    }
}

class Handler_1 extends DefaultHandler 
 {
  String wartosci;
  String loc_name;
  @Override
  public void startElement(String uri, 
                           String localName, 
			   String qName, 
			   Attributes attributes)
  throws SAXException 
   {
   int licznik = 0;
    ArrayList<Warstwy> geometryList = new ArrayList<>();

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
                geometryList.add(new Warstwy(wierzcholki));
                licznik += 1;
            }
        }
     }     
    for (Warstwy geom: geometryList) {
        for(int l=0; l<geom.geometria.length; l++)
        {
            System.out.println(geom.geometria[l][0] + ", " + geom.geometria[l][1]);
        }
    }

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


public class Parser_1 
 {

  public static void main(String[] args) 
   {
   
    try 
     {
      File inputFile = new File("points.xml");
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
    
    
      Handler_1 handler_1 = new Handler_1();

      saxParser.parse(inputFile, handler_1);     
     } 
    catch (Exception e) 
     {
      e.printStackTrace();
     }
   }   

 }
