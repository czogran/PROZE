package kulka;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Wyniki extends JPanel {
	JFrame f;
	JPanel panel;
	Graphics g;
	Image image;
	Wyniki()
	{
		f=new JFrame("WYNIKI");
		panel =new JPanel();
		f.setSize(200,200);
		f.setVisible(true);
		f.add(this);
		
		
	}
	public void paint(Graphics g)
	{
	
		super.paint(g);
		image=Wczytywanie.get_image_static("tlo.jpg");
		g.drawImage(image, 0,0,f.getWidth(),f.getHeight(), this);
		
		Wczytywanie.wyniki_pisz(g);

	}
}
