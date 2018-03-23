package kulka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Start extends JPanel {
JButton start;
JButton zasady;
JButton wyniki;
	Start(Sterowanie s){
		start=new JButton("START");
		this.add(start);
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				s.level=11;
				s.gra();
				
				System.out.println("Do Something Clicked");
				//this.
				
			
				
			}
		});
	}
}
