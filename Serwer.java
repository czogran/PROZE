package serwer;
	import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
	import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Serwer  {
	
	static ServerSocket serverSocket = null;

	   
	    private int port_serwera;
	    public static boolean stan=true;
	  

	    public void wlacz_serwer() {
	      
	    	JFrame rama=new JFrame("serwer");
	    	JPanel panel=new JPanel();
	    	JButton wlacz=new JButton("WLACZ");
	    	JButton wylacz=new JButton("WYLACZ");
	    	
	    	
	    	//wlacz.addActionListener(new ActionListener() {
				
			//	@Override
			//	public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				
		//	new Thread() {
		//	public void run() {
	    	try {
	         //   BufferedReader br = new BufferedReader(new FileReader("ConfigFiles\\IPCONFIG.txt"));
	            port_serwera = 2240;//Integer.parseInt(br.readLine());
	           // port_serwera.setSoTimeout(10000);
	            ServerSocket serverSocket = new ServerSocket(port_serwera);
	            //serverSocket.setReuseAddress(true);
	           // serverSocket.bind(new InetSocketAddress(port_serwera));
	            System.out.println("Server is up! Waiting for connections...");
				   JOptionPane.showMessageDialog(null,"SSSS");
				   InetAddress ip;
					  try {

						ip = InetAddress.getLocalHost();
						System.out.println("Current IP address : " + ip.getHostAddress());

					  } catch (UnknownHostException e) {

						e.printStackTrace();

					  }
	            while (true){
	            //	if(wylacz.)
	                Socket socket = serverSocket.accept();
	                new Thread(new Serwer_Elementy(socket)).start();
	                if(stan==false)
	                {
	                	serverSocket.close();
	                	break;
	                }
	            }
	        }
	        catch (IOException e){
	            System.out.println("Serwer nie móg³ zostaæ uruchomiony");
	            System.err.println(e);
	        }
				

	    	
				
				
	    //	}	}.start();
			
				//}
			//	}});
	    	
	    	
	    	/*	wylacz.addActionListener(new ActionListener() {
	    	
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					//new Thread() {
			    	//	public void run() {
			    			
			    		
					try {	
						System.out.print("AAAA");

						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			    //		}}.start();
			}});
			*/
	    //	panel.add(wlacz);
	    //	panel.add(wylacz);
	    	//rama.add(panel);
	    	//rama.setSize(200, 200);
	    	///rama.setVisible(true);
	    	//rama.setDefaultCloseOperation(serverSocket.close());
	    	//wlacz.setD
	    	//rama.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }

	}


