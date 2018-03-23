package kulka;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel implements ComponentListener, KeyListener{
	Wczytywanie czytaj;
	JFrame f;
	int szerokosc=500, dlugosc=500;
	BufferedImage image;
	int x1,y1,x2,y2;
	Pilka pilka;
	Shape oval;
	
	Board(JFrame f,Sterowanie s){
		czytaj=new Wczytywanie();
		pilka=new Pilka();
		//addKeyListener(pilka);
		//f=new JFrame();
		//f.setVisible(true);
		//f.setSize(szerokosc,dlugosc);
		//f.addComponentListener(this);
		//f.addKeyListener(this);
		//f.add(this);
		s.level=10;
		//this.addComponentListener(this);
	}
	Board(){
		czytaj=new Wczytywanie();
		pilka=new Pilka();
		f=new JFrame();
		f.setVisible(true);
		f.setSize(szerokosc,dlugosc);
		f.addComponentListener(this);
		f.addKeyListener(this);
		f.add(this);
		oval = new Ellipse2D.Float(10, 10, 11,11);	
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		czytaj.get_image("ceg³y.jpg");
		image=czytaj.image;
		g.drawImage(image,0,0,szerokosc, dlugosc,this);
		czytaj.get_image("pilka3.png");
		image=czytaj.image;
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
			//rect.reshape(x1, y1, x2, y2);
			if(i==0)
				czytaj.get_image("mapa1.jpg");
			else
				czytaj.get_image("ceg³y.jpg");
			image=czytaj.image;
			g.drawImage(image,x1,y1,x2, y2,this);
			czytaj.get_image("pilka3.png");
			image=czytaj.image;
			Shape oval = new Ellipse2D.Float((int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc));
			g.drawImage(image,(int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc),this);
			System.out.println(x1+"    "+ y1+"    "+ x2+"    "+ y2 +dlugosc);
			
			if(i!=0&&oval.intersects(re))
				g.drawImage(image,0,0,szerokosc, dlugosc,this);
		}
		pilka.move();
		if (pilka.p==0)
			repaint();
		//repaint();
		//System.out.println(x1+"    "+ y1+ x2+ y2);
		//g.drawRect(x1, y1, x2, y2);
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
	
	
}
