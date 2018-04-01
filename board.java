package kulka;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {//implements ComponentListener, KeyListener{
	Wczytywanie czytaj;
	JFrame f;
	int szerokosc=500, dlugosc=500;
//	int zycia;
	BufferedImage image;
	int x1,y1,x2,y2;
	int level;
	Pilka pilka;
	Shape oval;
	public Timer time;
	long czas;
	long koniec;
	String wiadomosc;
	String plansza;
	
	
	Board(JFrame f,Sterowanie s){
		plansza=Wczytywanie.get_plansza_name(s.level);
		czytaj=new Wczytywanie(plansza);
		pilka=new Pilka();
		level=s.level;
		
		System.out.println(s.level +"aaaaa                   aaaa");
		
		
		//this.add(label);
	}
	Board(){
		czytaj=new Wczytywanie("plansza.txt");
		pilka=new Pilka();
		f=new JFrame();
		f.setVisible(true);
		f.setSize(szerokosc,dlugosc);
		//f.addComponentListener(this);
		//f.addKeyListener(this);
		f.add(this);
		oval = new Ellipse2D.Float(10, 10, 11,11);	
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		czytaj.get_image("cegly.jpg");
		image=czytaj.image;
		g.drawImage(image,0,0,szerokosc, dlugosc,this);
		//czytaj.get_image("pilka3.png");
		image=pilka.image;
		
		
		//g.drawImage(image,(int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc),this);
		for (int i=0;i<czytaj.x1.length ;i++)
		{
			x1=(int)((float)szerokosc*czytaj.x1[i]);
			x2=(int)((float)szerokosc*czytaj.x2[i]);
			y1=(int)((float)dlugosc*czytaj.y1[i]);
			y2=(int)((float)dlugosc*czytaj.y2[i]);
			g.drawRect(x1, y1, x2, y2);
			
			//rect.setRect(x1, y1, x2, y2);
			Rectangle r=new Rectangle(x1, y1, x2, y2);
			Rectangle2D re=new Rectangle(x1, y1, x2, y2);
	
			image=czytaj.get_image1(czytaj.nazwa_fotek[i]);
			g.drawImage(image,x1,y1,x2, y2,this);
			//czytaj.get_image("pilka3.png");
			
			image=pilka.image;
			Shape oval = new Ellipse2D.Float((int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc));
			g.drawImage(image,(int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc),this);
			
			//g.drawString("SIEMA", 20, 20);
			koniec=czas-System.currentTimeMillis();
			wiadomosc="ZYCIA= "+pilka.zycia+" PUNKTY= "+(5000+koniec);
			g.drawString(wiadomosc, 10,30 );
			
			//g.setColor(Color.red);
			//System.out.println(x1+"    "+ y1+"    "+ x2+"    "+ y2 +dlugosc);
			if (i==czytaj.x1.length-1&&oval.intersects(re))
			{
				koniec=czas-System.currentTimeMillis();
				level=1;
				System.out.println(level+"  lavel idf");
				plansza=Wczytywanie.get_plansza_name(level);
				System.out.println(plansza);
				czytaj=new Wczytywanie(plansza);
				//this.setVisible(false);
			}
			else if(i!=0&&oval.intersects(re))
			{
				g.drawImage(image,0,0,szerokosc, dlugosc,this);
				if (pilka.zycia>0)
				{
					pilka.zycia-=1;
					pilka.pilka_x=Wczytywanie.get_pilka("x");
					pilka.pilka_y=Wczytywanie.get_pilka("y");
				}
				else
				{
					g.drawString("PRZEGRANA", 10, 10);
					
					this.setVisible(false);
				}
			}
			else if(i==0&&!re.contains((int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc)))//(i==0&&((int)pilka.pilka_x*szerokosc>re.getMinX()&&((int)(pilka.pilka_x+pilka.r*2)*szerokosc)<re.getMaxX()))
			{
				
				if (pilka.zycia>0)
				{
					pilka.zycia-=1;
					pilka.pilka_x=Wczytywanie.get_pilka("x");
					pilka.pilka_y=Wczytywanie.get_pilka("y");
				}
				else
				{
					g.drawString("PRZEGRANA", 10, 10);
					
					this.setVisible(false);
				}
				
				//System.out.println((int)(pilka.pilka_x*szerokosc)+(int)(pilka.pilka_y*dlugosc)+(int)(pilka.r*szerokosc)+(int)(pilka.r*dlugosc));
				g.drawImage(image,0,0,szerokosc, dlugosc,this);
				
			//	this.setVisible(false);
			}
		}	
			
		pilka.move();
		if (pilka.p==0)
			repaint();
	
	
	}
	
	
	
	/*
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
		szerokosc=f.getWidth();
		dlugosc=f.getHeight();
		
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		pilka.keyPressed(evt);
		System.out.println("aaa");
		
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		// TODO Auto-generated method stub
		pilka.keyReleased(evt);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	*/
	
}
