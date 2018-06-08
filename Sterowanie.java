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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import kulka.Klijent;
public class Sterowanie  implements ComponentListener{
	public Board board;//=new Board();
	public Klijent klijent;
	JFrame f;
	public Start start;
	//JButton start;
	JButton zasady;
	JButton wyniki;
	public JPanel kontener;
	Socket socket=null;
    Container contentPane;
  //  String plansza;//="plansza.txt";
    boolean scanning=true;

	
	public int level=0;
	public Sterowanie()  {
		 
	
		
		try {
			socket=new Socket("127.0.0.1",2239);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("2222");

			//e.printStackTrace();
		}
		finally
		{
			System.out.print("111");
		}

		//klijent=new Klijent("127.0.0.1",2239);
	//	Klijent.wez_poziom(socket,0);
		//plansza=Wczytywanie.get_plansza_name(0);
		f=new JFrame();
		//f.setSize(czytaj.size_x,board.czytaj.size_y);
		start=new Start(this);
		f.add(start);
		f.setVisible(true);
		
		
			board=new Board(f,this);
		
		board.setVisible(false);
		//Wczytywanie.zapis();
		
		f.addComponentListener(this);
		f.addKeyListener(board.pilka);
		f.setLocation(100, 100);
		board.setVisible(true);
		   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void gra()
	{
		f.requestFocus();
		start.setVisible(false);
		board.setVisible(true);
		board.czas=System.currentTimeMillis();
		f.add(board);  
	}
	public void koniec()
	{
		f.requestFocus();
		start.setVisible(true);
		board.setVisible(false);
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
