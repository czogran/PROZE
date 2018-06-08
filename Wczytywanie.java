package kulka;

import java.awt.Graphics;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Wczytywanie {

	
	public float[] x1,x2,y1,y2;
	public static int size_x, size_y;
	public String[] nazwa_fotek;
	NodeList nList;
	File imageFile;
	//List listWithNames = new LinkedList<>();
	/** okresla obrazek ktory ma byc dokola*/
	public BufferedImage image;
	public String background; 
	
	String nazwa;
	public static String  plansza;
	
	
	Wczytywanie()
	{}
	/**
	 * 
	 * 
	 * Klasa wczytuj¹ca wszystkie kafelki do swojej pamiêci, by nie wywo³ywaæ jej wiele razy tworzony jest obiekt, który to wszystko przetrzymuje
	 * 
	*@param plansza mowi ktora plansze mamy wczytac
	 */
	Wczytywanie(String plansza){
		this.plansza=plansza;
	//	imageFile= new File ("pilka3.png");
		try {
			
		    File inputFile = new File(plansza);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();
		    
		    
		    
		    
		    
		   // System.out.println(doc.getDocumentElement().getNodeName());
		   //  nList = doc.getElementsByTagName("klocek");
		   // System.out.println(doc.getElementsByTagName("klocek").getLength());
		 //   NodeList ramka=doc.getElementsByTagName("ramka");
		    //Node nNode = nList.item(0);
		    
		    
		    
		   
		    System.out.println(doc.getDocumentElement().getNodeName());
		     nList = doc.getElementsByTagName("klocek");
		     x1=new float[nList.getLength()];
		     y1=new float[nList.getLength()];
		     x2=new float[nList.getLength()];
		     y2=new float[nList.getLength()];
		     nazwa_fotek=new String[nList.getLength()];
		    // foty= new BufferedImage[nList.getLength()];
		    System.out.println(doc.getElementsByTagName("klocek").getLength());
		    System.out.println(nList.getLength());
		   // NodeList klocek=doc.getElementsByTagName("klocek");
		    Node nNode = nList.item(0);
		    Element e=(Element) nNode;
		 //   e.appendChild(doc.createTextNode("aaaa"));
		   // Element carname = doc.createElement("carname");
		//   doc.appendChild(carname);
		    e.setAttribute("aa", "bb");
		    
		    
		    for (int i=0;i<nList.getLength();i++)
		    {
		    	e=(Element)nList.item(i);
		    	x1[i]=Float.parseFloat(e.getElementsByTagName("x1").item(0).getTextContent());
		    	y1[i]=Float.parseFloat(e.getElementsByTagName("y1").item(0).getTextContent());
		    	x2[i]=Float.parseFloat(e.getElementsByTagName("x2").item(0).getTextContent());
		    	y2[i]=Float.parseFloat(e.getElementsByTagName("y2").item(0).getTextContent());
		    	nazwa_fotek[i]=e.getElementsByTagName("image").item(0).getTextContent();
		    //	foty[i]=this.get_image()
		    	System.out.println(x1[i]+"   "+x2[i]+"    "+y1[i]+"     "+y2[i]);
		    	//nazwa=e.getElementsByTagName("z").item(0).getTextContent();
		    }
		    
		  nList= doc.getElementsByTagName("ramka");   
		  nNode = nList.item(0);
		  e=(Element) nNode;
		  //x=e.getElementsByTagName("x1").item(0).;
		   
		    size_x=Integer.parseInt(e.getElementsByTagName("size_x").item(0).getTextContent());
		    size_y=Integer.parseInt(e.getElementsByTagName("size_y").item(0).getTextContent());
		    
		   
		    background=e.getElementsByTagName("background").item(0).getTextContent();
		   
		   
		    
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		 }
		
		
		
			}
	
	
	
	/**
	 * get pilka bierze szukana i znajduje szukana wartosc dla danego parametru
	 * @param szukana
	 * @return dana-jest to parametr, o ktory prosimy w funkcji
	 */
	public static float get_pilka(String szukana)
	{
		float dana;
		NodeList nlist1;
		dana=0f;
		
	try {
			
		    File inputFile = new File(plansza);//("plansza1.txt");
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();
		
		    

		    System.out.println(doc.getDocumentElement().getNodeName());
		    nlist1 = doc.getElementsByTagName("pilka");
		   
		    Node nNode = nlist1.item(0);
		    Element e=(Element) nNode;
		   
		    dana=Float.parseFloat(e.getElementsByTagName(szukana).item(0).getTextContent());
		    //System.out.println(x);
		    //System.out.println(e.getAttribute("x1"));
		 } catch (Exception e) {
		    e.printStackTrace();
		 }
	  
	
		
		return dana;
	}
	
	/**
	 * funkcja sluzy wczytwaniu zdjecia pilki
	 * @return zdjecie pilki
	 */
	public static BufferedImage get_pilka_image ()// (String szukana)
	{
		BufferedImage image = null;
		String plansza;
		NodeList nlist1;
		
	try {
			
		    File inputFile = new File(Wczytywanie.plansza);//("plansza.txt");
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();
		
		    

		    System.out.println(doc.getDocumentElement().getNodeName());
		     nlist1 = doc.getElementsByTagName("pilka");
		   
		    Node nNode = nlist1.item(0);
		    Element e=(Element) nNode;
		  //x=e.getElementsByTagName("x1").item(0).;
		    plansza=e.getElementsByTagName("image").item(0).getTextContent();
		    File imageFile= new File (plansza);
		    //File inputFile = new File(plansza);
			image = ImageIO.read(imageFile);
		   // plansza=e.getElementsByTagName(szukana).item(0).getTextContent();
		    //System.out.println(x);
		    //System.out.println(e.getAttribute("x1"));
		 } catch (Exception e) {
		    e.printStackTrace();
		 }
	  	
	
		
		return image;
	}
	
	/**
	 * funkcja sworzona po to by jak bedzie potrzeba zamienic zdjecie bedace w klasie wczytywanie na inne
	 * @param nazwa nazwa
	 */
	
	public void get_image( String nazwa) {
		File imageFile= new File (nazwa);
		
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * w odroznieniu do image, zwaraca obraz, np. po to by jakis obraz zamienic 
	 * @param nazwa
	 * @return zdjecie
	 */
	public BufferedImage get_image1( String nazwa) {
		BufferedImage image = null;
		//String plansza;
		
		File imageFile= new File (nazwa);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * funkcja stworzona po to, by w razie potrzeby wczytac zdjecie
	 * @param nazwa
	 * @return zdjecie
	 */
	public static BufferedImage get_image_static( String nazwa) {
		BufferedImage image = null;
		//String plansza;
		
		File imageFile= new File (nazwa);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	
	
	
	/**
	 * 
	 * @param nr_levelu int mowiacy nam dla ktorego levelu mamy brac nazwe planszy
	 * @return plansza nazwa pliku tekstowego dla danego levelu
	 */
	public static String get_plansza_name (int nr_levelu)
	{
		String szukaj,plansza="aa";
		//szukaj="level"+nr_levelu;
		szukaj= new String("level"+nr_levelu);
	    System.out.println(szukaj+"       szukaj");

		
		try {

	    File inputFile = new File("sterowanie.txt");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(inputFile);
	    doc.getDocumentElement().normalize();
	 
	    plansza=doc.getElementsByTagName(szukaj).item(0).getTextContent();
		}
		 catch (Exception e) {
			    e.printStackTrace();
			 }
	    System.out.println(plansza+"        get plansza name");

		return plansza;
	}
	
	/**
	 * funkcja sluzoca do zapisu najlepszych wynikow do pliku wyniki.txt
	 * @param nick_gracza tak sie nazywal gracz
	 * @param punkty tyle punktow uzyksal gracz
	 */
			public static void zapis(String nick_gracza, int punkty)
			{
				NodeList nList;
				try {
			         File inputFile = new File("wyniki.txt");

				  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			         Document doc = docBuilder.parse(inputFile);
			         Node wynki = doc.getFirstChild();
			         Node gracz = doc.getElementsByTagName("gracz").item(0);
		    			System.out.println("wynikiaaaaaaaa111"+punkty);

			         nList = doc.getElementsByTagName("gracz");
			             Node nNode = nList.item(0);
					Element e=(Element) nNode;
					Element pomocniczy=(Element) nNode;
					int wynik;
					//int punkty=0;
					    
					    
					    for (int i=0;i<nList.getLength();i++)
					    {
					    	e=(Element)nList.item(i);
					    	wynik=Integer.parseInt(e.getTextContent());
					    	System.out.println(wynik);
					    	if (i==0&&wynik>punkty)
					    		break;
					    	
					    	else if(wynik<=punkty&&i>0)
					    		{
					    			pomocniczy=(Element)nList.item(i-1);
					    			String zapis=new String (""+wynik);
					    			pomocniczy.setTextContent(zapis);
					    			//e.setAttribute("aa", "cc");
					    			pomocniczy.setAttribute("nick", e.getAttribute("nick"));
					    			
					    			//zapis=test;
					    			System.out.println("wynikiaaaaaaaa");
					    			pomocniczy.setTextContent(zapis);
					    			//e.setAttribute("nick", "cc");
					    			if(i==(nList.getLength()-1))
					    			{
					    			//	pomocniczy=(Element)nList.item(i-1);
					    				 zapis=new String (""+punkty);
						    			//zapis=test;
						    			System.out.println("wynikiaaaaaaaa111");
						    			e.setTextContent(zapis);
						    			//e.setAttribute("aa", "cc");
						    			e.setAttribute("nick", nick_gracza);
					    			}
					    			
					    		}
					    	else if (wynik>punkty)
					    	{
					    		pomocniczy=(Element)nList.item(i-1);
					    		String zapis=new String (""+punkty);
				    			//zapis=test;
				    			System.out.println("wynikiaaaaaaaa111");
				    			pomocniczy.setTextContent(zapis);
				    			//e.setAttribute("aa", "cc");
				    			pomocniczy.setAttribute("nick", nick_gracza);
				    			break;
					    	}
					    	/*else if(i==nList.getLength()-1)
					    	{
					    		String zapis=new String (""+test);
				    			//zapis=test;
				    			System.out.println("wynikiaaaaaaaa111");
				    			e.setTextContent(zapis);
				    			//e.setAttribute("aa", "cc");
				    			e.setAttribute("nick", "player");
					    	
					    	
					    	}*/
					    		
					    		
					    	//System.out.println("wynikiaaaaaaaa");
					    }
			    

			         // write the content on console
			         TransformerFactory transformerFactory = TransformerFactory.newInstance();
			         Transformer transformer = transformerFactory.newTransformer();
			         DOMSource source = new DOMSource(doc);
			         System.out.println("-----------Modified File-----------");
			         StreamResult consoleResult = new StreamResult(System.out);
			         StreamResult consoleResult1 = new StreamResult("wyniki.txt");

			         transformer.transform(source, consoleResult1);
			      } catch (Exception e) {
			         e.printStackTrace();
			      }
			   }
			
			
	/**
	 * wypisuje wyniki na danym komponencie graficznym
	 * @param g
	 */
		public static void wyniki_pisz(Graphics g)
		{
			NodeList nList;
			try {
		         File inputFile = new File("wyniki.txt");

			  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		         Document doc = docBuilder.parse(inputFile);
		         Node wynki = doc.getFirstChild();
		         Node gracz = doc.getElementsByTagName("gracz").item(0);
		        
		         nList = doc.getElementsByTagName("gracz");
		             Node nNode = nList.item(0);
				Element e=(Element) nNode;
				Element pomocniczy=(Element) nNode;
				int wynik;
				String nick_gracza;
				String napis;
				int y=10;
				    
				    
				   // for (int i=0;i<nList.getLength();i++)
				for (int i=nList.getLength();i>0;i--)
				    {
					
				    	e=(Element)nList.item(i-1);
				    	wynik=Integer.parseInt(e.getTextContent());
				    	nick_gracza=new String( e.getAttribute("nick"));
				    	napis= new String(11-i+".  "+nick_gracza+"  punkty:  "+wynik);
				    	g.drawString(napis, 10,y+=10 );
				    	
				    }
			}
			 catch (Exception e) {
		         e.printStackTrace();
		      }
			
		}
			
	}
	

