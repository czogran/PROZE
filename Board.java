package kulka;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements Runnable{//implements ComponentListener, KeyListener{
	Wczytywanie czytaj=new Wczytywanie();
	JFrame f;
	int szerokosc, dlugosc;
	BufferedImage image;
	
	/** pozycja klockow na planszy */
	int x1,y1,x2,y2;
	int level;
	Pilka pilka;
	Shape oval;
	long test;
	public Timer time;
	long czas;
	long time_dif;
	long koniec;
	String wiadomosc;
	String plansza;
	Font font;
	int indeks=0;
    private Thread animator;
    public boolean game;

	
	
	/**
	 * konstruktor board wczytuje pierwsza plansze na podstawie pierwszego numeru levelu, tak samo wczytuje skorke pierwszej pilki
	 * ponadto ustawia rozmiar jFrame
	 * definiuje czcinke napisu
	 * @param f parametr frame na ktorym mamy rysowac z klasy sterowanie
	 * @param s klasa sterowanie z ktorej bierzemy nr levelu
	 */
	Board(JFrame f,Sterowanie s){
		plansza=Wczytywanie.get_plansza_name(s.level);
		czytaj=new Wczytywanie(plansza);
		pilka=new Pilka();
		f.setSize(czytaj.size_x,czytaj.size_y);
	//	Thread pilka= new Pilka(); 
		level=s.level;
		szerokosc=czytaj.size_x;
		dlugosc=czytaj.size_y;
		font=new Font("Courier New", 1, 10);
		System.out.println(czytaj.size_x+ "  czas");
		time_dif=0;
		game=true;
		//this.to_repaint();
	}
	Board(){}
	
	/**
	 * obecnie super nie poprawnie napisana funkcja, trzeba ja odciazyc, ale dziala
	 * generalnie paint robi wszytskie najwazniejsze rzeczy w tym programie, rysuje, koloruje, sprawdza przeciecia oraz liczy punlty
	 * nie wiem jak elegancko zdekomponowac ten problem
	 * obecnie pomimo swojej wieleozadanowosci zajmuje zaskakujaco malo miejsca, ciezko bedzie ja rozbic na wiecej funkcji ktrore zajma w przyblizeniu tyle samo kodu
	 * @throws InterruptedException 
	 */
	public void to_repaint() {
		while(true)
		{
			//if(!pilka.pause)
			 //repaint();
			//  Thread.sleep(20);
		}
	}
	
	public void paint(Graphics g) {
		//System.out.println(czas+ "  czas");
 /**wywolanie funkcji super */
		//super.paint(g);
		czytaj.get_image(czytaj.background);
		image=czytaj.image;
		g.drawImage(image,0,0,szerokosc, dlugosc,this);
		//czytaj.get_image("pilka3.png");
		image=pilka.image;
		//Font font=new Font("Courier New", 1, 17);
	//	czas=1525602999;

		//g.drawImage(image,(int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc),this);
		
			for (int i=0;i<czytaj.x1.length ;i++)
			{
				System.out.println(test+ "  czas");
	
				//System.out.println("zzzaaaaaa");
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
				
				/*
				koniec=czas-System.currentTimeMillis();
				//koniec=System.currentTimeMillis();
	
				wiadomosc="ZYCIA= "+pilka.zycia+" PUNKTY= "+(11000+koniec);
				
				g.setFont(font);
				g.drawString(wiadomosc, 10,30 );
				*/
				//g.setColor(Color.red);
				//System.out.println(x1+"    "+ y1+"    "+ x2+"    "+ y2 +dlugosc);
				if (i==czytaj.x1.length-1&&oval.intersects(re))
				{
					//koniec=czas-System.currentTimeMillis();
					//time_dif=koniec;
					if (level==1)
					{
						System.out.println(plansza+"ssssssssssssssssssssssss");
	
						Wczytywanie.zapis(Start.nick_gracza, (int)(11000+koniec));
						int help=(int)(11000+koniec);	
						game=false;
						
					   JOptionPane.showMessageDialog(null, Start.nick_gracza+"   "+(help));
					   System.exit(1);


					}
					if (level<1)
						level=level+1;
					
					System.out.println(level+"  lavel idf");
					plansza=Wczytywanie.get_plansza_name(level);
					//System.out.println(plansza);
					czytaj=new Wczytywanie(plansza);
					pilka.image=Wczytywanie.get_pilka_image();
					pilka.pilka_x=Wczytywanie.get_pilka("x");
					pilka.pilka_y=Wczytywanie.get_pilka("y");
					
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
						//g.drawString("PRZEGRANA", 10, 10);
						
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
					//	this.
						this.setVisible(false);
						this.remove(this);
					}
					
					//System.out.println((int)(pilka.pilka_x*szerokosc)+(int)(pilka.pilka_y*dlugosc)+(int)(pilka.r*szerokosc)+(int)(pilka.r*dlugosc));
					g.drawImage(image,0,0,szerokosc, dlugosc,this);
					
				//	this.setVisible(false);
				}
			
		}
		
		if (pilka.pause==false)
		{
			pilka.move();
			
			if (indeks==0)
			{
			time_dif=time_dif+pilka.time_dif;
			}
			
			koniec=czas-System.currentTimeMillis()+time_dif;
			//koniec=System.currentTimeMillis();
			//time_dif=koniec;
			indeks=1;
		}
		else
		{
			
			indeks=0;
		//	time_dif=koniec;
		}
		wiadomosc="ZYCIA= "+pilka.zycia+" PUNKTY= "+(11000+koniec);
		g.setFont(font);
		g.drawString(wiadomosc, 10,30 );
		
	
		//	repaint();
		
		
		System.out.println("aaaaaaaaaaaa");
	
	
	}
	
	 @Override
	    public void addNotify() {
	        super.addNotify();

	        animator = new Thread(this);
	        animator.start();
	    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (game) {
			if(!pilka.pause)
			{
				repaint();
			}
			else
			{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//System.out.print("");
			}
		}
		
	}
	
	
	
}
