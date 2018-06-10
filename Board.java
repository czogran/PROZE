package kulka;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
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
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements Runnable{//implements ComponentListener, KeyListener{
	Wczytywanie czytaj=new Wczytywanie();
	JFrame f;
	public static int szerokosc, dlugosc;
	public static BufferedImage image;
	
	/** pozycja klockow na planszy */
	int x1,y1,x2,y2;
	int level,liczba_leveli;
	Pilka pilka;
	Moneta moneta;
	Shape oval;
	long test;
	public Timer time;
	long czas;
	long czas_roznica;
	long koniec;
	String wiadomosc;
	String plansza;
	Font font;
	int indeks=0;
	private ArrayList<Moneta> monety;
    private Thread animator;
    private Thread moneta_watek;
    public static int gotowka=0;
   
    BufferStrategy strategia;
    Window w;
    public boolean game;

	public Graphics podwojne;
	public Image obraz_do_podwojnego;
	
	/**
	 * konstruktor board wczytuje pierwsza plansze na podstawie pierwszego numeru levelu, tak samo wczytuje skorke pierwszej pilki
	 * ponadto ustawia rozmiar jFrame
	 * definiuje czcinke napisu
	 * @param f parametr frame na ktorym mamy rysowac z klasy sterowanie
	 * @param s klasa sterowanie z ktorej bierzemy nr levelu
	 */
	Board(JFrame f,Sterowanie s){
		liczba_leveli=Wczytywanie.wez_liczbe_leveli();
		if(Sterowanie.online==true)
		{
			Klijent.wez_poziom(Sterowanie.socket, 0);	
			//plansza=Wczytywanie.wez_plansza_nazwa(0);

		}
		 //w=new Window(f);
		//w.setVisible(true);
		f.createBufferStrategy(2);
		 strategia = f.getBufferStrategy();
		
		 
		plansza=Wczytywanie.wez_plansza_nazwa(0);
		
		System.out.println("test:"+plansza);
		czytaj=new Wczytywanie(plansza);
		pilka=new Pilka();
		f.setSize(czytaj.size_x,czytaj.size_y);
	//	Thread pilka= new Pilka(); 
		s.level=0;
		level=s.level;
		System.out.println("tu  "+s.level+"  lavel base"+liczba_leveli);
		szerokosc=czytaj.size_x;
		dlugosc=czytaj.size_y;
		font=new Font("Courier New", 1, 10);
		System.out.println(czytaj.size_x+ "  czas");
		czas_roznica=0;
		game=true;
		 monety = new ArrayList<Moneta>();
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
	/*@Override
	public void paint(Graphics g)
	{
		obraz_do_podwojnego=createImage(getWidth(),getHeight());
		podwojne=obraz_do_podwojnego.getGraphics();
		//podwojne=strategia.getDrawGraphics();
		
		
		/*do {
		    try{
		        podwojne = (Graphics2D) strategia.getDrawGraphics();
		        paintComponent(podwojne);
		    } finally {
		           podwojne.dispose();
		    }
		    strategia.show();
		} while (strategia.contentsLost());*
		//podwojne=strategy.getDrawGraphics();
		//podwojne.dispose();
		//strategy.show();
	//	strategia.show();
		//w.setVisible(true);
		//w.dispose();
		paintComponent(podwojne);
		g.drawImage(obraz_do_podwojnego, 0,0,this);
	}*/
	
	
	public void paintComponent(Graphics g) {
		Shape oval;
		//System.out.println(czas+ "  czas");
 /**wywolanie funkcji super */
		//super.paint(g);
		czytaj.get_image(czytaj.tlo_planszy);
		image=czytaj.image;
		g.drawImage(image,0,0,szerokosc, dlugosc,this);
		//czytaj.get_image("pilka3.png");
		image=pilka.image;
		 oval = new Ellipse2D.Float((int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc));
		 
		
			for (int i=0;i<czytaj.x1.length ;i++)
			{
				//System.out.println(test+ "  czas");
	
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
				// oval = new Ellipse2D.Float((int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc));
				g.drawImage(image,(int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc),this);
				
			
				if (i==czytaj.x1.length-1&&oval.intersects(re))
				{
					//koniec=czas-System.currentTimeMillis();
					//time_dif=koniec;
					if (level==liczba_leveli-1)
					{
						System.out.println(plansza+"ssssssssssssssssssssssss");
	
						Wczytywanie.zapis(Start.nick_gracza, (int)(11000+koniec));
						int help=(int)(11000+koniec);	
						game=false;
						
					   JOptionPane.showMessageDialog(null, Start.nick_gracza+"   "+(help));
					   System.exit(1);


					}
					System.out.println("tu  "+level+"  lavel max"+liczba_leveli);
					if (level<liczba_leveli-1)
						//Pilka.pause=true;
						level=level+1;
					 //  JOptionPane.showMessageDialog(null, level);

					System.out.println("tu  "+level+"  lavel idf: "+liczba_leveli);
					if(Sterowanie.online==true)
					{
						Klijent.wez_poziom(Sterowanie.socket, level);
					}
					plansza=Wczytywanie.wez_plansza_nazwa(level);
					//System.out.println(plansza);
					czytaj=new Wczytywanie(plansza);
					pilka.image=Wczytywanie.get_pilka_image();
					pilka.pilka_x=Wczytywanie.get_pilka("x");
					pilka.pilka_y=Wczytywanie.get_pilka("y");
					pilka.r=Wczytywanie.get_pilka("r");
					//Pilka.pause=false;
					
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
//						pilka.pilka_y=Wczytywanie.get_pilka("r");

					}
					//koniec gry
					else
					{
						//this.setVisible(false);
						//this.remove(this);
						//Sterowanie.koniec();
							reset();
					}
				}
				//przeciecie z ramka boczna
				else if(i==0&&!re.contains((int)(pilka.pilka_x*szerokosc),(int)(pilka.pilka_y*dlugosc),(int)(pilka.r*szerokosc),(int)(pilka.r*dlugosc)))//(i==0&&((int)pilka.pilka_x*szerokosc>re.getMinX()&&((int)(pilka.pilka_x+pilka.r*2)*szerokosc)<re.getMaxX()))
				{
					
					if (pilka.zycia>0)
					{
						pilka.zycia-=1;
						pilka.pilka_x=Wczytywanie.get_pilka("x");
						pilka.pilka_y=Wczytywanie.get_pilka("y");
					}
					//koniec gry
					else
					{
						//this.setVisible(false);
						//this.remove(this);
						//Sterowanie.koniec();
						reset();
						
					}
					
					//System.out.println((int)(pilka.pilka_x*szerokosc)+(int)(pilka.pilka_y*dlugosc)+(int)(pilka.r*szerokosc)+(int)(pilka.r*dlugosc));
					g.drawImage(image,0,0,szerokosc, dlugosc,this);
					
				//	this.setVisible(false);
				}
			
		}
		
		if (pilka.pause==false)
		{
			pilka.move();
			
			//indeks jest po to, by wiadomo bylo czy wchodzi czy wychodzi z pausy
			if (indeks==0)
			{
			czas_roznica=czas_roznica+pilka.time_dif;
			}
			
			koniec=czas-System.currentTimeMillis()+czas_roznica;
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
		
	
		
		//rysuje monety
		rysuj_monety(g);
	
		System.out.println("aaaaaaaaaaaa");
	
	
	}
	
	void rysuj_monety(Graphics g)
	{
		float odleglosc,pomoc1,pomoc2,odleglosc_r;
		odleglosc_r=((float)Math.pow(((pilka.r+Moneta.r)/2), 2));
		for (int i=0;i<monety.size();i++)
		{
		//	Shape moneta_kszta³t= new Ellipse2D.Float((int)(monety.get(i).moneta_x*szerokosc),(int)(monety.get(i).moneta_y*dlugosc),(int)(monety.get(i).r*szerokosc),(int)(monety.get(i).r*dlugosc));
			pomoc1=(float)Math.pow((pilka.pilka_x-monety.get(i).moneta_x+pilka.r-monety.get(i).r),2);
			pomoc2=(float)Math.pow((pilka.pilka_y-monety.get(i).moneta_y+pilka.r-monety.get(i).r),2);
			odleglosc=pomoc1+pomoc2;
			if (pilka.pause==false)
				monety.get(i).move();
			if(monety.get(i).moneta_y>1)
				monety.remove(i);
			if(odleglosc<odleglosc_r)
			{
				gotowka++;
				monety.remove(i);
			}
			System.out.println((int)(monety.get(0).moneta_x*szerokosc)+"  "+(int)monety.get(0).moneta_y*dlugosc);
			g.drawImage(monety.get(i).obrazek,(int)(monety.get(i).moneta_x*szerokosc),(int)(monety.get(i).moneta_y*dlugosc),(int)(monety.get(i).r*szerokosc),(int)(monety.get(i).r*dlugosc),this);
		}
	}
	
	
	void reset()
	{
		//level=0;
		//plansza=Wczytywanie.wez_plansza_nazwa(level);
		//czytaj=new Wczytywanie(plansza);
		gotowka=0;
		//pilka.image=Wczytywanie.get_pilka_image();
		//pilka.pilka_x=Wczytywanie.get_pilka("x");
		//pilka.pilka_y=Wczytywanie.get_pilka("y");
		//pilka.zycia=(int)Wczytywanie.get_pilka("zycia");
		koniec=0;
	//	g.drawString("PRZEGRANA", 10, 10);
		System.out.print("bbbbbaaa");
		this.setVisible(false);
		this.remove(this);
		Sterowanie.koniec();
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
		Random rand = new Random();
		
		while (game) {
			if(!pilka.pause)
			{
				//rand.nextDouble();
				if(rand.nextDouble()<0.0000001)
					monety.add(new Moneta());
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
