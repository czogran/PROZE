package serwer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Polecenia {

	public static String odpowiedz (String wiadomosc)
	{
		String odpowiedz1=null;
		BufferedReader br = null;

		if(wiadomosc.contains("sterowanie"))
		{
		//	JOptionPane.showMessageDialog(null,odpowiedz1);

			//BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader("sterowanie_serwer.txt"));
				   

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
            	
				odpowiedz1="sterowanie"+br.readLine();
			//	JOptionPane.showMessageDialog(null,odpowiedz1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(wiadomosc.contains("poziom"))
		{
		//	System.out.println("plansza_serwer"+wiadomosc.subSequence(6, 7)+".txt");
			String nazwa_pliku="plansza_serwer"+wiadomosc.subSequence(6, 7)+".txt";
			try {
				br= new BufferedReader(new FileReader(nazwa_pliku));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				odpowiedz1="poziom"+br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(wiadomosc.contains("wez_wynik"))
		{
			String nazwa_pliku="wyniki_serwer.txt";
			try {
		
				
				br= new BufferedReader(new FileReader(nazwa_pliku));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				odpowiedz1="wyniki"+br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		
		
		else if(wiadomosc.contains("wyslij_wynik"))
		{
			//String nazwa_pliku="wyniki_serwer.txt";
			//try {
				//InputStream is = s.getInputStream();
		          //BufferedReader br = new BufferedReader(new InputStreamReader(is));
				//br= new BufferedReader(new FileReader(nazwa_pliku));
				
				String info,nick;
				
				int punkty,indeks;
				//try {
					//info = br.readLine();
					JOptionPane.showMessageDialog(null, wiadomosc);
					indeks=wiadomosc.indexOf("marker");
					//indeks.IndexOf("marker");
					JOptionPane.showMessageDialog(null, indeks);

					nick=wiadomosc.substring(12,indeks);
					JOptionPane.showMessageDialog(null, nick);

					punkty=Integer.parseInt(wiadomosc.substring(indeks+6));
					JOptionPane.showMessageDialog(null, punkty);

					//info=br.r
					//nick=
					zapis(nick, punkty);
				//	JOptionPane.showMessageDialog(null,nick+punkty);

			//	} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
			//	}
		          
			//} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			
		}
		
		else if(wiadomosc.contains("zdjecie"))
		{
			String nazwa_zdjecia=wiadomosc.substring(7);
			
			File file = new File(nazwa_zdjecia);
				//JOptionPane.showMessageDialog(null,nazwa_zdjecia);
				byte imageData[] ;
	            // Reading a Image file from file system
	            FileInputStream imageInFile;
				try {
					imageInFile = new FileInputStream(file);
					 imageData =new byte[(int) file.length()];
		            try {
						imageInFile.read(imageData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            odpowiedz1 ="zdjecie"+ Base64.getEncoder().encodeToString(imageData);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		            
		 
		            // Converting Image byte array into Base64 String
		         
		}
		else if(wiadomosc.contains("koniec"))
		{
			JOptionPane.showMessageDialog(null,"koniec");			
			odpowiedz1="koniec";
		}
		return odpowiedz1;
		
	}
	
	private static void JOptionPane(Object object, String string) {
		// TODO Auto-generated method stub
		
	}

	public static void zapis(String nick_gracza, int punkty)
	{
		NodeList nList;
		
		try {
	         File inputFile = new File("wyniki_serwer.txt");

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
		
			    }
	    

	         // write the content on console
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         System.out.println("-----------Modified File-----------");
	         StreamResult consoleResult = new StreamResult(System.out);
	         StreamResult consoleResult1 = new StreamResult("wyniki_serwer.txt");

	         transformer.transform(source, consoleResult1);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		
	   }
	
}
