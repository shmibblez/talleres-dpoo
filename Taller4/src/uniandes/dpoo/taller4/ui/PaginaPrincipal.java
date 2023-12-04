package uniandes.dpoo.taller4.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.lang.model.type.NullType;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uniandes.dpoo.taller4.modelo.RegistroTop10;
import uniandes.dpoo.taller4.modelo.Tablero;
import uniandes.dpoo.taller4.modelo.Top10;

public class PaginaPrincipal extends JFrame {
	private Tablero tablero;
	private Top10 top10;
	private int tamano = 5;
	private String nombre;
	private PanelInferior panelInferior;
	private DibujarTablero dibujarTablero;
	private int windowWidth;
	private int windowHeight;

	@SuppressWarnings("unchecked")
	public PaginaPrincipal() {
		nuevoTablero();
		top10 = new Top10();
		panelInferior = new PanelInferior(tablero.darJugadas(), nombre);
		dibujarTablero = new DibujarTablero(tablero,
				new Callable<NullType>() {

					@Override
					public NullType call() throws Exception {
						panelInferior.actualizar(tablero.darJugadas(), nombre);
						return null;
					}

				}, new Callable<NullType>() {

					@Override
					public NullType call() throws Exception {
						nuevoTablero();
						return null;
					}

				});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(new BotonesArriba(e -> {
			tamano = ((JComboBox<String>) e.getSource()).getSelectedIndex() + 1;
			nuevoTablero();
		}, tamano - 1), BorderLayout.NORTH);
		add(dibujarTablero, BorderLayout.CENTER);
		add(new BotonesDerecha(e -> {
			nuevoTablero();
		}, e -> {
			nuevoTablero();
		}, e -> {
			mostrarTop10();
		}, e -> {
			cambiarJugador();
		}), BorderLayout.EAST);
		add(panelInferior, BorderLayout.SOUTH);
		// pack();
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		windowWidth = gd.getDisplayMode().getWidth();
		windowHeight = gd.getDisplayMode().getHeight();
		setSize(windowWidth / 2, (int) Math.round(windowHeight * (2.0 / 3.0)));
		setResizable(false);
	}

	public void nuevoTablero() {
		this.tablero = new Tablero(tamano);
		if (this.dibujarTablero != null) {
			dibujarTablero.resetear(tamano);
		}
	}

	public void mostrarTop10() {
		JFrame top10Frame = new JFrame();
		top10Frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		top10Frame.setLayout(new BoxLayout(top10Frame.getContentPane(), BoxLayout.Y_AXIS));
		top10Frame.setBackground(new Color(255, 0, 0));
		Collection<RegistroTop10> top = top10.darRegistros();
		int i = 1;
		for (RegistroTop10 r : top) {
			JTextField texto = new JTextField("" + i + " " + r.darNombre() + " " + r.darPuntos());
			texto.setEditable(false);
			top10Frame.add(texto);
			i++;
		}
		top10Frame.setSize(windowWidth / 3, windowHeight / 2);
		top10Frame.setVisible(true);
	}

	public void cambiarJugador() {
		JFrame cambioFrame = new JFrame();
		cambioFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cambioFrame.setLayout(new BorderLayout());
		cambioFrame.setBackground(new Color(255, 0, 0));
		JTextField nombre = new JTextField();
		nombre.setEditable(true);
		JButton aceptar = new JButton("aceptar");
		aceptar.addActionListener(e -> {
			this.nombre = nombre.getText();
			this.panelInferior.actualizar(tablero.darJugadas(), this.nombre);
			cambioFrame.dispatchEvent(new WindowEvent(cambioFrame, WindowEvent.WINDOW_CLOSING));
		});
		cambioFrame.add(nombre, BorderLayout.CENTER);
		cambioFrame.add(aceptar, BorderLayout.SOUTH);
		cambioFrame.setSize(windowWidth / 3, windowHeight / 2);
		cambioFrame.setBackground(new Color(255, 0, 0));
		cambioFrame.setVisible(true);
	}

	public static void main(String[] args) {
		PaginaPrincipal pp = new PaginaPrincipal();
		pp.setVisible(true);
		pp.cambiarJugador();
		pp.nuevoTablero();
	}
}

class BotonesArriba extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7877595358152366423L;

	public BotonesArriba(ActionListener cambioDeTamano, int defecto) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		int maxTamano = 5;
		String[] tamanos = new String[maxTamano];
		for (int i = 0; i < maxTamano; i++) {
			tamanos[i] = "" + (i + 1) + "x" + (i + 1);
		}
		JComboBox<String> combo = new JComboBox<String>(tamanos);
		combo.setSelectedIndex(defecto);
		add(combo);
		combo.addActionListener(cambioDeTamano);
	}

}

class BotonesDerecha extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5435758998687130202L;

	private JButton nuevo;
	private JButton reiniciar;
	private JButton top10;
	private JButton cambiarJugador;

	public BotonesDerecha(ActionListener nuevoAction, ActionListener reiniciarAction, ActionListener top10Action,
			ActionListener cambiarJugdorAction) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0, 0, 255));
		nuevo = new JButton("NUEVO");
		reiniciar = new JButton("REINICIAR");
		top10 = new JButton("TOP-10");
		cambiarJugador = new JButton("CAMBIAR JUGADOR");
		add(nuevo);
		add(reiniciar);
		add(top10);
		add(cambiarJugador);
		nuevo.addActionListener(nuevoAction);
		reiniciar.addActionListener(reiniciarAction);
		top10.addActionListener(top10Action);
		cambiarJugador.addActionListener(cambiarJugdorAction);
	}
}

class PanelInferior extends JPanel {
	JTextField jugadasTF;
	JTextField nombreTF;

	protected PanelInferior(int jugadas, String nombre) {
		setLayout(new FlowLayout());
		setBackground(new Color(0, 255, 0));
		jugadasTF = new JTextField("" + jugadas);
		jugadasTF.setEditable(false);
		nombreTF = new JTextField(nombre);
		nombreTF.setEditable(false);
		JTextField jugadasTitulo = new JTextField("Jugadas:");
		jugadasTitulo.setEditable(false);
		JTextField jugadorTitulo = new JTextField("Jugador:");
		jugadorTitulo.setEditable(false);
		add(jugadasTitulo);
		add(jugadasTF);
		add(jugadorTitulo);
		add(nombreTF);
	}

	protected void actualizar(int jugadas, String nombre) {
		this.jugadasTF.setText("" + jugadas);
		this.nombreTF.setText(nombre);
	}
}

class DibujarTablero extends JComponent {

	private Tablero tablero;
	private int h;
	private int w;
	protected Callable<NullType> gano;

	public DibujarTablero(Tablero tablero, Callable<NullType> parentOnClick, Callable<NullType> gano) {
		setBackground(new Color(0, 0, 0));
		setSize(new Dimension(500, 500));
		this.tablero = tablero;
		this.h = this.getHeight() / tablero.darTablero().length;
		this.w = this.getWidth() / tablero.darTablero()[0].length;
		this.gano = gano;
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					parentOnClick.call();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				onClick(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});
	}

	protected void resetear(int tamano) {
		this.tablero = new Tablero(tamano);
		this.tablero.desordenar(50);
		this.h = this.getHeight() / tablero.darTablero().length;
		this.w = this.getWidth() / tablero.darTablero()[0].length;
		this.revalidate();
		this.repaint();
	}

	private void onClick(MouseEvent e) {
		int row = (int) (e.getY() / h);
		int col = (int) (e.getX() / w);
		System.out.println("h: " + h + ", w: " + w + ", e.getX(): " + e.getX() + ", x%w: " + col);

		this.tablero.darTablero()[row][col] = !tablero.darTablero()[row][col];
		if (row > 0) {
			this.tablero.darTablero()[row - 1][col] = !tablero.darTablero()[row - 1][col];
		}
		if (col > 0) {
			this.tablero.darTablero()[row][col - 1] = !tablero.darTablero()[row][col - 1];
		}
		if (row < tablero.darTablero().length - 1) {
			this.tablero.darTablero()[row + 1][col] = !tablero.darTablero()[row + 1][col];
		}
		if (col < tablero.darTablero().length - 1) {
			this.tablero.darTablero()[row][col + 1] = !tablero.darTablero()[row][col + 1];
		}

		this.revalidate();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// cajas
		boolean todoTRUE = true;
		for (int row = 0; row < tablero.darTablero().length; row++) {
			for (int col = 0; col < tablero.darTablero()[0].length; col++) {
				g2.setColor(tablero.darTablero()[row][col] ? Color.YELLOW : Color.BLACK);
				g2.fillRect(w * col, h * row, w, h);
				if (tablero.darTablero()[row][col] == false) {
					todoTRUE = false;
				}
			}
		}
		if (todoTRUE) {
			try {
				gano.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// // move origin to center
		// g2.translate(getWidth() / 2, getHeight() / 2);
		// // set color and thickness
		// g2.setColor(Color.red);
		// g2.setStroke(new BasicStroke(0.001f));
		// // draw coordinate lines
		// g2.draw(new Line2D.Float(-1f, 0f, 1.0f, 0f));
		// g2.draw(new Line2D.Float(0, -1.0f, 0.0f, 1.0f));
		// // draw a vector
		// g2.draw(new Line2D.Float(0f, 0f, 0.25f, 0.25f));
	}
}