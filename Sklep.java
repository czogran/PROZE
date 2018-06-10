package kulka;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import javax.xml.transform.Source;

public class Sklep extends JPanel{
	JButton mala_pilka;
	JButton zycia;
	JButton wznow;
	ImageIcon test;
	JTextArea dane;
	JTextArea opis_zycia;
	JTextArea opis_mala;
	JTextArea opis_wznow;
	Sklep()
	{
		dane=new JTextArea();
		mala_pilka=new JButton("MALA_PILKA");
		wznow=new JButton("WZNOW");
		zycia=new JButton("ZYCIA");
		wznow=new JButton("WZNOW");
		
		dane.setText("GOTOWKA: "+Board.gotowka+" ZYCIA: "+Pilka.zycia);
		dane.setBackground(Color.RED);
		dane.setEditable(false);
		
		opis_mala=new JTextArea();
		opis_mala.setText("mala kulka pozwili Ci sie wcisnac \ntam gdzie nie mogles \ndo tej pory sie dostac\nkoszt:8");
		opis_mala.setEditable(false);
		
		opis_zycia=new JTextArea();
		opis_zycia.setText("za zebrane monety\n mozesz kupic \ndodatkowe zycia\nkoszt:5");
		opis_zycia.setEditable(false);
		
		opis_wznow=new JTextArea();
		opis_wznow.setText("jezeli skonczyles \nzakupy wznow gre");
		opis_wznow.setEditable(false);
		
		this.setLayout(null);
		
		dane.setBounds((int)(Board.szerokosc*0.01),(int)( Board.dlugosc*0),(int)(Board.szerokosc*0.8),(int)( Board.dlugosc*0.1));
		zycia.setBounds((int)(Board.szerokosc*0.01),(int)( Board.dlugosc*0.22),(int)(Board.szerokosc*0.5),(int)( Board.dlugosc*0.2));
		opis_zycia.setBounds((int)(Board.szerokosc*0.55),(int)( Board.dlugosc*0.22),(int)(Board.szerokosc*0.5),(int)( Board.dlugosc*0.2));

		mala_pilka.setBounds((int)(Board.szerokosc*0.01),(int)( Board.dlugosc*0.44),(int)(Board.szerokosc*0.5),(int)( Board.dlugosc*0.2));
		opis_mala.setBounds((int)(Board.szerokosc*0.55),(int)( Board.dlugosc*0.44),(int)(Board.szerokosc*0.5),(int)( Board.dlugosc*0.2));
		wznow.setBounds((int)(Board.szerokosc*0.01),(int)( Board.dlugosc*0.66),(int)(Board.szerokosc*0.5),(int)( Board.dlugosc*0.2));
		opis_wznow.setBounds((int)(Board.szerokosc*0.55),(int)( Board.dlugosc*0.66),(int)(Board.szerokosc*0.5),(int)( Board.dlugosc*0.2));

		
		ImageIcon zdjecie=new ImageIcon();
		ImageIcon zdjecie1=new ImageIcon();

		Image obraz;
	    //File inputFile = new File(plansza);
		try {		
			File imageFile= new File ("mala_kulka.png");
			obraz = ImageIO.read(imageFile).getScaledInstance(45,45,5);
			zdjecie.setImage(obraz);
			mala_pilka.setIcon((zdjecie));
			imageFile= new File ("apteczka.jpg");
			obraz = ImageIO.read(imageFile).getScaledInstance(45,45,5);
			zdjecie1.setImage(obraz);
			zycia.setIcon(zdjecie1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.add(dane);
		this.add(zycia);
		this.add(mala_pilka);
		this.add(wznow);
		this.add(opis_mala);
		this.add(opis_zycia);
		this.add(opis_wznow);
		zycia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//Sterowanie.zamknij_sklep();
				if(Board.gotowka>=5)
				{
				Board.gotowka-=5;	
				Pilka.zycia++;
				dane.setText("GOTOWKA: "+Board.gotowka+" ZYCIA: "+Pilka.zycia);

				}
			}
		});
		wznow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Sterowanie.zamknij_sklep();
			//	Pilka.pause=false;
			}
		});
		mala_pilka.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(Board.gotowka>=8)
				{
				Board.gotowka-=8;	
				Pilka.zycia++;
				dane.setText("GOTOWKA: "+Board.gotowka+" ZYCIA: "+Pilka.zycia);
				Pilka.r=Pilka.r/2;
				Pilka.image=Wczytywanie.get_image_static("mala_kulka.png");
				}
			}
		});
		

	}
	
	
}
