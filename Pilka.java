package kulka;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.xml.crypto.Data;

public class Pilka extends Thread implements KeyListener{
	//Wczytywanie czytaj;

	public float dx,dy= 0.01f;
	public float pilka_x=0;
	public float pilka_y=0;
	public static int zycia;
	long time=0;
	long time_dif=0;
	public static float r=0.1f;
	//public float size_y=0.1f;
	static BufferedImage image;
	int p;
	static boolean pause;
	/**
	 *konstruktor inicjujacy pilke, biory jej wspolzedne, zdjecie itp
	 */
	Pilka(){
		image=Wczytywanie.get_pilka_image();//("image");
		pilka_x=Wczytywanie.get_pilka("x");
		pilka_y=Wczytywanie.get_pilka("y");
		zycia=(int)Wczytywanie.get_pilka("zycia");
		r=Wczytywanie.get_pilka("r");
		p=0;
		pause=false;
	}
	

	
	
	/**
	 * definiuje zmiane pozycji kulki
	 */
	public void move() {
		pilka_x+=dx;
		pilka_y+=dy;
	}
	
	
	
	/**
	 * do wczytywania jak kulka ma sie poruszac
	 */
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		int key=evt.getKeyCode();
    	if(pause==false)
    	{
        if (key == KeyEvent.VK_LEFT) {
            dx = (float) -0.01;
            System.out.println("cccccccccccccccc");
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = (float) 0.01;
        }
      
        if (key == KeyEvent.VK_UP) {
            dy = (float)-0.01;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = (float)0.03;
        }
        //tylko do testow
        if (key == KeyEvent.VK_1) {
           Klijent.koniec(Sterowanie.socket);
           System.out.println("test konca");
        	// dy = (float)0.03;
        }
    	}
        if (key == KeyEvent.VK_P) {
         //f()
        	
        	pause=!pause;
        	System.out.println(pause);
        	if(pause==true)
        		{
        		time=System.currentTimeMillis();
        		
        		Sterowanie.sklep();
        		}
        		//if(dy==0)
        	else
        	{
        		dy=(float) 0.01;
        		time_dif=System.currentTimeMillis()-time;
        	}
        
        }
        	
    	/*
		  if (key == KeyEvent.VK_P) {
		        	System.out.println("bbbbbbb");
			  dy=p/100;
			  if(p==1)
			  {
		        	System.out.println("cccccccccccccccc");

				  p=0;
			  }
			  else
			  {
		        	System.out.println("ddddddddddddd");
		        	
				  p=1;
			  }
		  }*/
   
		  
	}
/**
 * funkcja przywracajaca wartosci domyslne po puszczeniu klawisza
 */
	@Override
	public void keyReleased(KeyEvent evt) {
		int key=evt.getKeyCode();
		if(pause==false)
		{
		 if (key == KeyEvent.VK_UP) {
	            dy = 0.01f;
	        }
		 if (key == KeyEvent.VK_DOWN) {
	            dy = 0.01f;
	        }
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
            
        }
		}
		else
		{
			dx=0;
			dy=0;
			
		
		}
        /*
        if (key == KeyEvent.VK_P) {
        
        	if(p==0)
        	{
        	dy=0;
        	p=1;
        	}
        else
        {
        	dy=(float)0.01;
        	p=0;
        }
        	
        }
       // else
        //	dy=(float)0.01;*/
        }
	
	//}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
