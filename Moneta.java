package kulka;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Moneta {
	public float dx,dy= 0.01f;
	public float moneta_x=0;
	public float moneta_y=0;
	
	long time=0;
	long time_dif=0;
	public static float r=0.1f;
	public static boolean pobrane=false;
	static BufferedImage obrazek;
	
	/**
	 *konstruktor inicjujacy pilke, biory jej wspolzedne, zdjecie itp
	 */
	Moneta(){
		//if(pobrane==false)
			obrazek=Wczytywanie.wez_moneta_obraz();//("image");
		
		Random rand = new Random();
		moneta_y=rand.nextFloat()*0.5f;
		moneta_x=rand.nextFloat();
	}
	

	public void rysuj(Graphics g)
	{
		//g.drawImage(obrazek,(int)(moneta_x*Board.szerokosc),(int)moneta_y*Board.dlugosc),(int)(r*Board.szerokosc),(int)(r*Board.dlugosc),Board.this);

	}
	
	public void move() {
		//moneta_x+=dx;
		moneta_y+=dy;
	}
	
	
	



}
