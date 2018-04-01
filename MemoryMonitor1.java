
package kulka;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>Wy�wietla liczb� bajt�w zaalokowanej i u�ywanej pami�ci oraz ma�y wykres
 * zmian tych liczb w czasie. Tak�e liczb� aktywnych w�tk�w.</p>
 * 
 * <p>Inna wersja - opieraj�ca si� na klasie Timer. Nie jest thread-safe!</p>
 * 
 * <p>Naci�ni�cie przycisku myszy wewn�trz komponentu b�dzie powodowa�
 * zatrzymanie/uruchomienie monitorowania.</p>
 * 
 * <p><em>Na podstawie MemoryMonitor z JDK Java2D Demo.</em></p>
 * 
 * @author Krystian Ignasiak
 */
public class MemoryMonitor1 extends Panel {
	/**
	 * Flaga chwilowego zatrzymania monitorowania pami�ci.
	 */
	private boolean postpone = false;

	/**
	 * Timer nap�dzaj�cy monitorowanie
	 */
	private Timer timer = null;

	/**
	 * Bufor. Do podw�jnego buforowania.
	 */
	private Image buf;

	/**
	 * Kontekst graficzny bufora.
	 */
	private Graphics bufg;

	/**
	 * Wektor kolejnych stan�w pami�ci.
	 */
	private int mem[] = null;

	/**
	 * Kolor siatki <i>oscyloskopu</i> monitora.
	 */
	private Color gridColor = new Color(46, 139, 87);

	/**
	 * Flaga. <code>true</code> gdy zminimalizowano okno.
	 */
	private boolean iconified = false;

	/**
	 * Ustawia flag� zminimalizowania.
	 */
	private void setIconified(boolean iconified) {
		this.iconified = iconified;
	}

	/**
	 * Zwraca flag� zminimalizowania.
	 */
	private boolean isIconified() {
		return iconified;
	}

	/**
	 * Przycisk uruchamiaj�cy <i>garbage collector</i>.
	 */
	private Button gcb;

	/**
	 * Preferowana szeroko�� monitora.
	 */
	private static final int prefWidth = 150;

	/**
	 * Preferowana wysoko�� monitora.
	 */
	private static final int prefHeight = 100;

	/**
	 * Rozmiar fontu informacji wy�wietlanych tekstowo.
	 */
	private static final int fontSize = 14;

	/**
	 * Szeroko�� monitora.
	 */
	private int w;

	/**
	 * Wysoko�� monitora.
	 */
	private int h;

	/**
	 * Odst�p ramki <em>oscyloskopu</em> od lewej kraw�dzi monitora.
	 */
	private static final int x0 = fontSize + (fontSize >> 2) + 1;

	/**
	 * Odst�p ramki <em>oscyloskopu</em> od g�rnej kraw�dzi monitora.
	 */
	private int y0;

	/**
	 * Wsp�rz�dna prawej kraw�dzi ramki <em>oscyloskopu</em>.
	 */
	private int xm;

	/**
	 * Wsp�rz�dna dolnej kraw�dzi ramki <em>oscyloskopu</em>.
	 */
	private int ym;

	/**
	 * Szeroko�� ramki <em>oscyloskopu</em>.
	 */
	private int wm;

	/**
	 * Wysoko�� ramki <em>oscyloskopu</em>.
	 */
	private int hm;

	/**
	 * Szeroko�� oczka ramki <em>oscyloskopu</em>.
	 */
	private int gWidth;

	/**
	 * Wysoko�� oczka ramki <em>oscyloskopu</em>.
	 */
	private int gHeight;

	/**
	 * Przesuni�cie p�yn�cej siatki.
	 */
	private int flowGrid = 0;

	/**
	 * Konstrukcja monitora. 
	 * 
	 * @param updTime
	 *            czas pomi�dzy kolejnymi uaktualnieniami wy�wietlanej
	 *            informacji.
	 */
	public MemoryMonitor1(int updTime) {

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				postpone = !postpone;
			}
		});
	//	Sterowanie s=new Sterowanie();
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ce) {
				monitorResized(ce.getComponent().getSize());
			}
		});

		setFont(new Font("Serif", Font.PLAIN, fontSize));
		setBackground(Color.black);

		gcb = new Button("gc");
		gcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.gc();
			}
		});

		setLayout(null);
		add(gcb);

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (postpone) {
					return;
				}
				repaint();
				nextStep();
			}
		}, 1000, updTime);
	}

	/**
	 * Konstrukcja monitora od�wie�aj�cego informacje co 1 sekund�.
	 * Monitorowanie jest uruchamiane natychmiast.
	 */
	public MemoryMonitor1() {
		this(1000);
	}

	/**
	 * System zawiadamia: dodano komponent do kontenera. Szuka si� w�r�d
	 * rodzic�w ramki i dodaje do niej s�uchacza zdarze� okienkowych, kt�ry
	 * wy��czy rysowanie, gdy okno b�dzie zminimalizowane.
	 */
	public void addNotify() {
		super.addNotify();
		int recursionLevel = 0;
		Component c = this;
		while (!((c = c.getParent()) instanceof java.awt.Frame)) {
			if (++recursionLevel > 15) {
				return;
			}
		}
		try {
			((java.awt.Frame) c).addWindowListener(new WindowAdapter() {
				public void windowIconified(WindowEvent we) {
					setIconified(true);
				}
				public void windowDeiconified(WindowEvent we) {
					setIconified(false);
				}
			});
		} catch (Exception ignore) { // trzeba to przechwyci�, bo czasami
			// (zw�aszcza w apletach) nie mo�na do��czy� s�uchacza
			// do ramki top-level.
		}
	}

	/**
	 * System zawiadamia: zmieniono rozmiar monitora - trzeba zmieni� rozmiar
	 * bufora.
	 */
	private void monitorResized(Dimension size) {
		if ((w = size.width) == 0) {
			return;
		}
		if ((h = size.height) == 0) {
			return;
		}
		buf = createImage(w, h);
		bufg = buf.getGraphics();

		y0 = fontSize + 1;
		xm = w - (fontSize / 3); // wsp. ko�ca ramki
		ym = h - fontSize - 1;
		wm = xm - x0 - 1; // szeroko��, wysoko�� ramki
		hm = ym - y0 - 1;
		gWidth = wm / 9; // szeroko��, wysoko�� oczka siatki
		gHeight = h / 9;

		gcb.setBounds(xm - fontSize * 3, ym, fontSize << 1, fontSize + 1);

		try {
			if (mem.length != wm) {
				int t[] = new int[wm];
				System.arraycopy(mem, Math.max(0, mem.length - wm), t,
						Math.max(0, wm - mem.length), Math.min(mem.length, wm));
				for (int i = 0, x = wm - mem.length; i < x; i++) {
					t[i] = t[x];
				}
				mem = t;
			}
		} catch (NullPointerException mem_is_null_so_alloc_mem) {
			mem = new int[wm];
			for (int i = 0; i < wm; i++) {
				mem[i] = getMemoryUsage();
			}
		}
	}

	/**
	 * Dokonuje pomiaru i przesuwa dane.
	 */
	private void nextStep() {
		System.arraycopy(mem, 1, mem, 0, mem.length - 1);
		mem[mem.length - 1] = getMemoryUsage();
		if (++flowGrid >= gWidth) {
			flowGrid = 0;
		}
	}

	/**
	 * Wrysowuje stan do bufora.
	 */
	private void draw() {
		bufg.setColor(Color.black);
		bufg.fillRect(0, 0, w, h);

		bufg.setColor(gridColor); // skala oscyloskopu
		bufg.drawRect(x0, y0, wm, hm);

		ym -= 2;
		for (int i = x0 + gWidth - flowGrid; i < xm; i += gWidth) // poziome
		{
			bufg.drawLine(i, y0, i, ym);
		}
		ym += 2;
		xm -= 2;
		for (int i = y0 + gHeight; i < ym; i += gHeight) // pionowe
		{
			bufg.drawLine(x0, i, xm, i);
		}
		xm += 2;

		bufg.setColor(Color.yellow);
		bufg.drawString(total + "K allocated", 4, fontSize - 2);
		bufg.drawString(used + "K used", 4, h - 2);
		bufg.drawString("" + Thread.activeCount(), 2, fontSize << 1);

		int ph = (mem[0] * hm) / total;
		int min = Integer.MAX_VALUE, max = 0, mm;
		ym--;
		for (int i = 1; i < wm; i++) {
			int ah = ((mm = mem[i]) * hm) / total;
			// bufg.drawLine(xm-wm+i, ym-ah-1, xm-wm+i-1, ym-ph-1);
			bufg.drawLine(xm - wm + i, ym - ah, xm - wm - 1 + i, ym - ph);
			ph = ah;

			if (mm < min) {
				min = mm;
			} else if (mm > max) {
				max = mm;
			}
		}
		ym++;

		bufg.drawString("" + (min * 100) / total, 2, fontSize * 3);
		bufg.drawString("" + (max * 100) / total, 2, fontSize << 2);

		if (postpone) {
			bufg.setColor(Color.red);
		} else {
			bufg.setColor(Color.green);
		}
		bufg.fillOval(xm - 8, ym + (fontSize >> 1) - 3, 6, 6);
	}

	/**
	 * Dla unikni�cia mrugania.
	 */
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Narysowanie zawarto�ci komponentu.
	 */
	public void paint(Graphics g) {
		if (isIconified()) {
			return;
		}
		draw();
		g.drawImage(buf, 0, 0, this);
	}

	/**
	 * Zwraca minimalne wymiary monitora.
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * Zwraca maksymalne wymiary monitora.
	 */
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/**
	 * Zwraca preferowane wymiary monitora.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(prefWidth, prefHeight);
	}

	/**
	 * Test klasy.
	 */
	public static void main(String s[]) {
		MemoryMonitor1 mm = new MemoryMonitor1(125);

		Frame f = new Frame("MemoryMonitor1 Demo");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(1);
			}
		});
		f.add(mm);
		f.pack();
		f.setVisible(true);
		
		// przetestuj program z wywo�aniem funkcji test0, test1 i bez nich
		new Thread(new Runnable() {
			public void run() {
				test0();
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				test1();
			}
		}).start();
	}


	/**
	 * Test - funkcja tworzy co 100 ms w�tek, kt�ry zasypia na losowy czas.
	 */
	private static void test0() {
		while (true) { // ci�gle,
			try {
				Thread.sleep(100); // co tyle czasu,
			} catch (InterruptedException e) {
				break;
			}
			new Thread(new Runnable() { // tworzony jest watek,
				public void run() {
					try {
						Thread.sleep((int) (Math.random() * 3000));// kt�ry usypia na tyle czasu
					} catch (InterruptedException interrupt) {
						return;
					}
				}
			}).start();
		}
	}

	/**
	 * Test - funkcja alokuje co pewien czas obszar pami�ci o losowym rozmiarze.
	 */
	private static void test1() {
		while (true) { // ci�gle
			try {
				Thread.sleep(10); // co tyle czasu
			} catch (InterruptedException e) {
				break;
			}
			int amount = (int) (Math.random() * 15_000_000); // tyle bajt�w
			byte t[] = new byte[amount]; // jest alokowanych
			for (int i = 0; i < t.length; i++) {
				t[i] = (byte) 0;
			}
		}
	}

	/**
	 * Liczba u�ywanych ostatnio kilobajt�w pami�ci.
	 */
	private int used = 0;

	/**
	 * Liczba zaalokowanych ostatnio kilobajt�w pami�ci.
	 */
	private int total = 0;

	/**
	 * Zwraca liczb� u�ywanych kilobajt�w pami�ci. Jednocze�nie ustawia pola
	 * <code>used</code> i <code>total</code>.
	 */
	private int getMemoryUsage() {
		total = ((int) Runtime.getRuntime().totalMemory()) >> 10;
		return (used = total
				- (((int) Runtime.getRuntime().freeMemory()) >> 10));
	}
}
