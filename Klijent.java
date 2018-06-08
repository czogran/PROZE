package kulka;

import java.net.*;
//import java.util.Base64;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import javax.swing.JOptionPane;
//import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class Klijent {

	  Socket socket = null;
	  PrintWriter wyjscie = null;
	  BufferedReader wejscie=null;
	  DataOutputStream out=null;
	  OutputStream outToServer=null;
	public Klijent(String adres, int port) {
		// TODO Auto-generated constructor stub
		 try {
			 socket = new Socket(adres, port);
	         outToServer = socket.getOutputStream();

	      out = new DataOutputStream(outToServer);

			 wyjscie = new PrintWriter(socket.getOutputStream(), true);
			 wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	         System.out.println("Connected");
	        // out.writeUTF("koniec");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("tu blad");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//wyjscie.print("koniec");
		}
			
		  

	}
	
	
	public static void wez_sterowanie (Socket serverSocket){
      try{
  		System.out.println("wez_s");

          OutputStream os = serverSocket.getOutputStream();
          PrintWriter pw = new PrintWriter(os, true);
          pw.println("sterowanie");
          InputStream is = serverSocket.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          //String settings = br.readLine();
          String wiadomosc=br.readLine();
          if (wiadomosc.contains("sterowanie"))
          {
          	wiadomosc=wiadomosc.substring(10);
          	PrintWriter out1 = new PrintWriter("sterowanie_klijent.txt");
          	System.out.println("wez_s3");	
          	out1.println(wiadomosc);        
          	out1.close();
          }
      }
      catch (IOException e){
      
          System.out.println(e);
      }
      }
	
	public static void wez_poziom (Socket serverSocket,int nr_poziomu){
      try{
  		System.out.println("wez_s");

          OutputStream os = serverSocket.getOutputStream();
          PrintWriter pw = new PrintWriter(os, true);
          pw.println("poziom"+nr_poziomu);
          InputStream is = serverSocket.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          //String settings = br.readLine();
          String wiadomosc=br.readLine();
          if (wiadomosc.contains("poziom"))
          {
          	wiadomosc=wiadomosc.substring(6);
          	PrintWriter out1 = new PrintWriter("plansza_klijent.txt");
          	//System.out.println("wez_s3");	
          	out1.println(wiadomosc);        
          	out1.close();
          }
      }
      catch (IOException e){
      
          System.out.println(e);
      }
      }
	
	public static void wez_obraz (Socket serwerSocket,String nazwa_zdjecia){
      try{
  		System.out.println("wez_s");

          OutputStream os = serwerSocket.getOutputStream();
          PrintWriter pw = new PrintWriter(os, true);
          pw.println("zdjecie"+nazwa_zdjecia);
          InputStream is = serwerSocket.getInputStream();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          //String settings = br.readLine();
          String wiadomosc=br.readLine();

          if (wiadomosc.contains("zdjecie"))
          {
             
          	wiadomosc=wiadomosc.substring(7);
              System.out.println(wiadomosc);

          	byte[] zdjecieTablicaBitow = Base64.getDecoder().decode(wiadomosc);

          //	Base64.decodeToFile(wiadomosc,nazwa_zdjecia);
          	//byte[] imageByteArray = Base64.decodeBase64(wiadomosc);
          	 FileOutputStream zdjeciePlikWyjsciowy = new FileOutputStream(
                      "a"+nazwa_zdjecia);

          	 zdjeciePlikWyjsciowy.write(zdjecieTablicaBitow);

              
          	 zdjeciePlikWyjsciowy.close();
          }
      }
      catch (IOException e){
      
          System.out.println(e);
      }
      }
	

	
	/*public static void main(String[] args) {
		
		
		Klijent klijent = new Klijent("127.0.0.1",2239);
		//klijent.wyjscie.print("koniec")
		System.out.println("klijent koniec");
					Klijent.wez_sterowanie(klijent.socket);
					Klijent.wez_poziom(klijent.socket,0);
					Klijent.wez_poziom(klijent.socket,1);
					Klijent.wez_obraz(klijent.socket, "mina.jpg");

		/*try {
			//klijent.out.writeUTF("sterowanie");
			//klijent.out.writeUTF("koniec");

		} catch (IOException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		klijent.wyjscie.println("koniec");			
		try {

			klijent.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//klijent.wyjscie.write("koniec");
		
		//klijent.wyjscie.print("");
		
		System.out.println("klijent koniec2");
		klijent.wyjscie.print("koniecdd");

	 }

	*/
}

