package kulka;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sterowanie  implements ComponentListener{
	public Board board;
	JFrame f;
	public Start start;
	//JButton start;
	JButton zasady;
	JButton wyniki;
	public JPanel kontener;
    Container contentPane;
  //  String plansza;//="plansza.txt";

	
	public int level=0;
	public Sterowanie() {
		//plansza=Wczytywanie.get_plansza_name(0);
		f=new JFrame();
		f.setSize(200,200);
		start=new Start(this);
		f.add(start);
		f.setVisible(true);
		
		board=new Board(f,this);
		board.setVisible(false);
		
		
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
		//start.setVisible(false);
		
		
		//System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+level );  
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
