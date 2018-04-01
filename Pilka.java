package kulka;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.RepaintManager;

public class Pilka implements KeyListener{
	Wczytywanie czytaj;

	public float dx,dy= 0.01f;
	public float pilka_x=0;
	public float pilka_y=0;
	int zycia;
	public float r=0.1f;
	//public float size_y=0.1f;
	BufferedImage image;
	int p;
	
	
	Pilka(){
		image=Wczytywanie.get_pilka_image("image");
		pilka_x=Wczytywanie.get_pilka("x");
		pilka_y=Wczytywanie.get_pilka("y");
		zycia=(int)Wczytywanie.get_pilka("zycia");
		r=Wczytywanie.get_pilka("r");
	}
	

	
	
	
	public void move() {
		pilka_x+=dx;
		pilka_y+=dy;
	}
	
	
	
	
	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		int key=evt.getKeyCode();
    	
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
		  }
   
		  
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		int key=evt.getKeyCode();

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
        if (key == KeyEvent.VK_P) {
        
        	if(p==1)
        
        	dy=0;
        else
        	dy=(float)0.01;
        }
       // else
        //	dy=(float)0.01;
        }

	//}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
