package serwer;

	import java.io.*;
	import java.net.Socket;

import javax.swing.JOptionPane;


public class Serwer_Elementy implements Runnable 
	 {
	
	
	 
	    private Socket socket;

	  
	    public Serwer_Elementy(Socket socket){
	        this.socket=socket;
	        System.out.print("nowy serwer");
	    }
	    @Override
	    public void run() {
	        try {					  
	        //	JOptionPane.showMessageDialog(null, "aaaa");

	            while (true) {

	                InputStream strumien_wejscowy = socket.getInputStream();

	                BufferedReader br = new BufferedReader(new InputStreamReader(strumien_wejscowy));

	                DataInputStream in = new DataInputStream(socket.getInputStream());


	                OutputStream os = socket.getOutputStream();
	          	  DataOutputStream out= new DataOutputStream(os);

	                PrintWriter pw = new PrintWriter(os, true);

	                String wiadomosc = br.readLine();

	             //   wiadomosc=String(in);
	              //  wiadomosc=in.readUTF();

                    System.out.println("OD:KLIJENTA: " + wiadomosc);
	  		//		   JOptionPane.showMessageDialog(null,"koniec85");

   			//	   JOptionPane.showMessageDialog(null,wiadomosc);

	                if(wiadomosc!=null) {
	    				//JOptionPane.showMessageDialog(null,"wiad"+wiadomosc);

	                	System.out.println("FROM_CLIENT: " + wiadomosc);
	                    String odpowiedz = Polecenia.odpowiedz(wiadomosc);
	     				 //  JOptionPane.showMessageDialog(null,odpowiedz+"odp");

	                    if(odpowiedz=="koniec"){
		     				//   JOptionPane.showMessageDialog(null,"koniec1");
	                    	Serwer.stan=false;
	                        socket.close();
	     				  JOptionPane.showMessageDialog(null,"koniec3");

	                        System.out.println("serwer koniec");
	                    }
	                    if(odpowiedz=="CONNECTION_REJECTED")
	                        socket.close();
	                    else {
	                    //   out.writeUTF(odpowiedz);
	                    	pw.println(odpowiedz);
	                        pw.flush();
	                        if (odpowiedz == "LOGGEDOUT") {
	                            socket.close();
	                        }
	                    }
	                }  
	               
	            }
	         
	        } catch (Exception e) {
	            System.err.println(e.getMessage());
	        }
	    }
	}


