package kulka;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Pilka implements KeyListener{

	public float dx,dy;
	public float pilka_x=0;
	public float pilka_y=0;
	public float r=0.1f;
	//public float size_y=0.1f;
	BufferedImage image;
	int p;
	
	
	Pilka(){
		pilka_x=Wczytywanie.get_pilka("x");
		pilka_y=Wczytywanie.get_pilka("y");
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
