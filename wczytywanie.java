package kulka;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Wczytywanie {

	
	public float[] x1,x2,y1,y2;
	NodeList nList;
	File imageFile;
	public BufferedImage image;
	
	Wczytywanie(){
		imageFile= new File ("pilka3.png");
		try {
			
		    File inputFile = new File("plansza.txt");
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();
		    
		    
		    
		    
		    
		   // System.out.println(doc.getDocumentElement().getNodeName());
		   //  nList = doc.getElementsByTagName("klocek");
		   // System.out.println(doc.getElementsByTagName("klocek").getLength());
		    NodeList ramka=doc.getElementsByTagName("ramka");
		    //Node nNode = nList.item(0);
		    
		    
		    
		   
		    System.out.println(doc.getDocumentElement().getNodeName());
		     nList = doc.getElementsByTagName("klocek");
		     x1=new float[nList.getLength()];
		     y1=new float[nList.getLength()];
		     x2=new float[nList.getLength()];
		     y2=new float[nList.getLength()];
		    System.out.println(doc.getElementsByTagName("klocek").getLength());
		    System.out.println(nList.getLength());
		   // NodeList klocek=doc.getElementsByTagName("klocek");
		    Node nNode = nList.item(0);
		    Element e=(Element) nNode;
		    for (int i=0;i<nList.getLength();i++)
		    {
		    	e=(Element)nList.item(i);
		    	x1[i]=Float.parseFloat(e.getElementsByTagName("x1").item(0).getTextContent());
		    	y1[i]=Float.parseFloat(e.getElementsByTagName("y1").item(0).getTextContent());
		    	x2[i]=Float.parseFloat(e.getElementsByTagName("x2").item(0).getTextContent());
		    	y2[i]=Float.parseFloat(e.getElementsByTagName("y2").item(0).getTextContent());
		    	System.out.println(x1[i]+"   "+x2[i]+"    "+y1[i]+"     "+y2[i]);

		    }
		  
		 } catch (Exception e) {
		    e.printStackTrace();
		 }
			}
	
	public static float get_pilka(String szukana)
	{
		float dana;
		NodeList nlist1;
		dana=0f;
	try {
			
		    File inputFile = new File("plansza.txt");
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();
		
		    

		    System.out.println(doc.getDocumentElement().getNodeName());
		     nlist1 = doc.getElementsByTagName("pilka");
		   
		    Node nNode = nlist1.item(0);
		    Element e=(Element) nNode;
		  //x=e.getElementsByTagName("x1").item(0).;
		    
		    dana=Float.parseFloat(e.getElementsByTagName(szukana).item(0).getTextContent());
		    //System.out.println(x);
		    //System.out.println(e.getAttribute("x1"));
		 } catch (Exception e) {
		    e.printStackTrace();
		 }
	  
	
		
		return dana;
	}
	
	public void get_image( String nazwa) {
		File imageFile= new File (nazwa);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
			
	
	}
	

