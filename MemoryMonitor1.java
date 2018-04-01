
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
 * <p>Wyœwietla liczbê bajtów zaalokowanej i u¿ywanej pamiêci oraz ma³y wykres
 * zmian tych liczb w czasie. Tak¿e liczbê aktywnych w¹tków.</p>
 * 
 * <p>Inna wersja - opieraj¹ca siê na klasie Timer. Nie jest thread-safe!</p>
 * 
 * <p>Naciœniêcie przycisku myszy wewn¹trz komponentu bêdzie powodowaæ
 * zatrzymanie/uruchomienie monitorowania.</p>
 * 
 * <p><em>Na podstawie MemoryMonitor z JDK Java2D Demo.</em></p>
 * 
 * @author Krystian Ignasiak
 */
public class MemoryMonitor1 extends Panel {
	/**
	 * Flaga chwilowego zatrzymania monitorowania pamiêci.
	 */
	private boolean postpone = false;

	/**
	 * Timer napêdzaj¹cy monitorowanie
	 */
	private Timer timer = null;

	/**
	 * Bufor. Do podwójnego buforowania.
	 */
	private Image buf;

	/**
	 * Kontekst graficzny bufora.
	 */
	private Graphics bufg;

	/**
	 * Wektor kolejnych stanów pamiêci.
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
	 * Ustawia flagê zminimalizowania.
	 */
	private void setIconified(boolean iconified) {
		this.iconified = iconified;
	}

	/**
	 * Zwraca flagê zminimalizowania.
	 */
	private boolean isIconified() {
		return iconified;
	}

	/**
	 * Przycisk uruchamiaj¹cy <i>garbage collector</i>.
	 */
	private Button gcb;

	/**
	 * Preferowana szerokoœæ monitora.
	 */
	private static final int prefWidth = 150;

	/**
	 * Preferowana wysokoœæ monitora.
	 */
	private static final int prefHeight = 100;

	/**
	 * Rozmiar fontu informacji wyœwietlanych tekstowo.
	 */
	private static final int fontSize = 14;

	/**
	 * Szerokoœæ monitora.
	 */
	private int w;

	/**
	 * Wysokoœæ monitora.
	 */
	private int h;

	/**
	 * Odstêp ramki <em>oscyloskopu</em> od lewej krawêdzi monitora.
	 */
	private static final int x0 = fontSize + (fontSize >> 2) + 1;

	/**
	 * Odstêp ramki <em>oscyloskopu</em> od górnej krawêdzi monitora.
	 */
	private int y0;

	/**
	 * Wspó³rzêdna prawej krawêdzi ramki <em>oscyloskopu</em>.
	 */
	private int xm;

	/**
	 * Wspó³rzêdna dolnej krawêdzi ramki <em>oscyloskopu</em>.
	 */
	private int ym;

	/**
	 * Szerokoœæ ramki <em>oscyloskopu</em>.
	 */
	private int wm;

	/**
	 * Wysokoœæ ramki <em>oscyloskopu</em>.
	 */
	private int hm;

	/**
	 * Szerokoœæ oczka ramki <em>oscyloskopu</em>.
	 */
	private int gWidth;

	/**
	 * Wysokoœæ oczka ramki <em>oscyloskopu</em>.
	 */
	private int gHeight;

	/**
	 * Przesuniêcie p³yn¹cej siatki.
	 */
	private int flowGrid = 0;

	/**
	 * Konstrukcja monitora. 
	 * 
	 * @param updTime
	 *            czas pomiêdzy kolejnymi uaktualnieniami wyœwietlanej
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
	 * Konstrukcja monitora odœwie¿aj¹cego informacje co 1 sekundê.
	 * Monitorowanie jest uruchamiane natychmiast.
	 */
	public MemoryMonitor1() {
		this(1000);
	}

	/**
	 * System zawiadamia: dodano komponent do kontenera. Szuka siê wœród
	 * rodziców ramki i dodaje do niej s³uchacza zdarzeñ okienkowych, który
	 * wy³¹czy rysowanie, gdy okno bêdzie zminimalizowane.
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
		} catch (Exception ignore) { // trzeba to przechwyciæ, bo czasami
			// (zw³aszcza w apletach) nie mo¿na do³¹czyæ s³uchacza
			// do ramki top-level.
		}
	}

	/**
	 * System zawiadamia: zmieniono rozmiar monitora - trzeba zmieniæ rozmiar
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
		xm = w - (fontSize / 3); // wsp. koñca ramki
		ym = h - fontSize - 1;
		wm = xm - x0 - 1; // szerokoœæ, wysokoœæ ramki
		hm = ym - y0 - 1;
		gWidth = wm / 9; // szerokoœæ, wysokoœæ oczka siatki
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
	 * Dla unikniêcia mrugania.
	 */
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Narysowanie zawartoœci komponentu.
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
		
		// przetestuj program z wywo³aniem funkcji test0, test1 i bez nich
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
	 * Test - funkcja tworzy co 100 ms w¹tek, który zasypia na losowy czas.
	 */
	private static void test0() {
		while (true) { // ci¹gle,
			try {
				Thread.sleep(100); // co tyle czasu,
			} catch (InterruptedException e) {
				break;
			}
			new Thread(new Runnable() { // tworzony jest watek,
				public void run() {
					try {
						Thread.sleep((int) (Math.random() * 3000));// który usypia na tyle czasu
					} catch (InterruptedException interrupt) {
						return;
					}
				}
			}).start();
		}
	}

	/**
	 * Test - funkcja alokuje co pewien czas obszar pamiêci o losowym rozmiarze.
	 */
	private static void test1() {
		while (true) { // ci¹gle
			try {
				Thread.sleep(10); // co tyle czasu
			} catch (InterruptedException e) {
				break;
			}
			int amount = (int) (Math.random() * 15_000_000); // tyle bajtów
			byte t[] = new byte[amount]; // jest alokowanych
			for (int i = 0; i < t.length; i++) {
				t[i] = (byte) 0;
			}
		}
	}

	/**
	 * Liczba u¿ywanych ostatnio kilobajtów pamiêci.
	 */
	private int used = 0;

	/**
	 * Liczba zaalokowanych ostatnio kilobajtów pamiêci.
	 */
	private int total = 0;

	/**
	 * Zwraca liczbê u¿ywanych kilobajtów pamiêci. Jednoczeœnie ustawia pola
	 * <code>used</code> i <code>total</code>.
	 */
	private int getMemoryUsage() {
		total = ((int) Runtime.getRuntime().totalMemory()) >> 10;
		return (used = total
				- (((int) Runtime.getRuntime().freeMemory()) >> 10));
	}
}
