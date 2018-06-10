package kulka;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.rmi.ConnectIOException;

import javax.print.attribute.standard.JobState;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.net.*;
//import klijent.InetAddress;
import kulka.Klijent;
public class Sterowanie  implements ComponentListener{
	public static Board board;//=new Board();
	public static Sklep sklep;
	public Klijent klijent;
	static JFrame f;
	public static Start start;
	//JButton start;
	JButton zasady;
	JButton wyniki;
	public JPanel kontener;
	static Socket socket=null;
    Container contentPane;
  //  String plansza;//="plansza.txt";
    boolean scanning=true;
    public static boolean online;
    InetAddress ip;
	
	public int level=0;
	public Sterowanie()  {
		 
		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			socket=new Socket(ip.getHostAddress(),2240);
			//socket=new Socket("127.0.0.1",2239);
			online=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("2222");
			online=false;
			//e.printStackTrace();
		}
		
		if (online==true)
		{
			Klijent.wez_sterowanie(socket);
		}
		
		JOptionPane.showMessageDialog(null,online);
	
		f=new JFrame();
		start=new Start(this,f);
		f.add(start);
		f.setVisible(true);
		
		
		//board=new Board(f,this);
		
	//	board.setVisible(false);
	//	Wczytywanie.zapis();
		
		f.addComponentListener(this);
		//f.addKeyListener(board.pilka);
		f.setLocation(100, 100);
		f.setSize(400, 400);;
	//	board.setVisible(true);
		   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void gra(JFrame ff,Sterowanie ss)
	{
		f.requestFocus();
		start.setVisible(false);
		//board=new Board(f,this);
	//	Wczytywanie.wez_plansza_nazwa(0);
		board=new Board(ff,ss);
		f.addKeyListener(board.pilka);
		board.setVisible(true);
		board.czas=System.currentTimeMillis();
		//f.addKeyListener(board.pilka);
		f.add(board);  
	}
	public static void sklep()
	{
		f.requestFocus();
		sklep =new Sklep();
		board.setVisible(false);
		sklep.setVisible(true);
		f.add(sklep);
		//board=new Board(f,this);
		
//
	//	board.setVisible(true);
		//f.add(board);  
	}
	public static void zamknij_sklep()
	{
		f.requestFocus();
		//Sklep sklep =new Sklep(f);
		
		sklep.setVisible(false);
	//	board.setVisible(false);
		board.setVisible(true);
		
		
		f.add(board);  
	}
	public static void koniec()
	{
		f.requestFocus();
		start.setVisible(true);
		//board.setVisible(false);
		//board.czas=System.currentTimeMillis();
		f.add(start);  
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		board.dlugosc=f.getHeight();
		board.szerokosc=f.getWidth();
		//start.start.resize((int)(f.getHeight()/7),(int)(f.getWidth()/5 ));
		//start.start.setSize((int)f.getWidth()/5,(int)f.getHeight()/5);;
		
		//start.setBounds(20, 20, 20, 20);
		//System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
