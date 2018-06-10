package kulka;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Start extends JPanel {
JButton start;
JButton zasady;
JButton wyniki;
JButton nick;
TextArea tekst;
public static String nick_gracza="gosc";
int x=91;
int y=20;

/**
 * sluzy do obslugi poczatka gry
 * @param s to nazwa klasy sterowanie na rzecz ktorej bedziemy wywolywac elemnty, wczytywac nick itp
 */

	Start(Sterowanie s, JFrame f){
		start=new JButton("START");
		zasady=new JButton("ZASADY");
		wyniki=new JButton("WYNIKI");
		nick= new JButton("NICK");
		tekst=new TextArea();
		//start.setSize(11, 11,11,11);//;(x++,y++);;
		setLayout(new GridLayout(4, 1,10,10));
		//setPreferredSize(new Dimension(20,20));
	//	this.add(tekst);
		this.add(start);
		this.add(zasady);
		this.add(wyniki);
		this.add(nick);
	
		
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				s.level=11;
				
			    // JOptionPane.showMessageDialog(s.f.getComponent(0), "Hello World");
				s.gra(f,s);
				start.setSize(x++,y++);
				System.out.println("Do Something Clicked");
				//this.
				
			}
		});
		zasady.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			     JOptionPane.showMessageDialog(s.f.getComponent(0), "I tak przegrasz, \n sss\n strza³ka w góre");
				
			}
		});
		nick.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame f= new JFrame("PODAJ NICK");
				JPanel j=new JPanel();
		
				JButton ok=new JButton("OK");
				JTextField nameField = new JTextField(nick_gracza);
				nameField.add(ok);
				
				j.add(nameField);
				j.add(ok);
				
				j.setLayout(new GridLayout(2, 1));
				f.setSize(300,100);
				f.add(j);
				//f.add(nameField);
				f.setVisible(true); 
				//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
				ok.addActionListener(new ActionListener() {
					 
					@Override
					public void actionPerformed(ActionEvent arg0) {
						nick_gracza=nameField.getText();
						f.setVisible(false);
						f.dispose();
						System.out.println(nick_gracza);
					}
				});
				
			}
		});
	
		wyniki.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Wyniki w=new Wyniki();
			}
		
		});
		
	}


	

}
