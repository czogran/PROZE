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

	
	public int level=1;
	public Sterowanie() {
		f=new JFrame();
		f.setSize(200,200);
		start=new Start(this);
		//s=new JPanel();
		f.add(start);
		
		//board=new Board(f,this);
		//f.add(kontener);
		//f.add(contentPane.add(start));
		board=new Board(f,this);
		board.setVisible(false);
		//
		//start=board;
		//f.add(board);
	//	f.add(start);
		f.setVisible(true);
		f.addComponentListener(this);
	//	f.addKeyListener();
		f.addKeyListener(board);
		f.setLocation(100, 100);
		//this.gra();
		//gra();
		board.setVisible(true);
		   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+level+"           ");
	//	System.gc();
		
	}
	public void gra()
	{
		//f.getContentPane().removeAll();
		//f.getContentPane().add(board);
		//f.getContentPane().addKeyListener(board);
		//f.add(start);
		//board=new Board(f,this);
		start.setVisible(false);
		board.setVisible(true);
		f.remove(start);
		f.add(board);
		//start.setVisible(false);
		
		
		System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+level );  
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
		
		//System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
