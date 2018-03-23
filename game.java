package kulka;

import java.awt.EventQueue;





	public class Game {
		public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					//new Graph();
					//new KeyTest();
					//new Kulka();
					//new Board();
					//new MemoryMonitor1();
					new Sterowanie();
					//new parser();
					//new ToyTiled();
					//new GPanell();
				}
			});
			
			//new Sterowanie();
			System.gc();
	}
	}
