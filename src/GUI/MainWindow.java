package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	private final static String TITLE = "Iscte Code Analyser";
	private final static int WIDTH = 1000, HEIGHT = 600;

	private JPanel mainPanel, rightPanel, leftPanel, bottomPanel;

	private JButton analyseBt, openBt;

	private JTextArea fileDisplay;
	private JTextField fileName, locName, cycloName, atfdName, laaName;

	public MainWindow() {
		initComponents();
		formatComponents();
		addListeners();
	}

	private void initComponents() {
		setTitle(TITLE);
		setMinimumSize(new Dimension(WIDTH - 200, HEIGHT - 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4, 2));
		
		leftPanel = new JPanel();
		bottomPanel = new JPanel();

		fileName = new JTextField("Ficheiro Selecionado");
		locName = new JTextField("LOC");
		cycloName = new JTextField("CYCLO");
		atfdName = new JTextField("ATFD");
		laaName = new JTextField("LAA");
		
		rightPanel.add(locName);
		rightPanel.add(cycloName);
		rightPanel.add(atfdName);
		rightPanel.add(laaName);

		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(leftPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		add(mainPanel);
	}

	private void formatComponents() {
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		resizeComps();

		rightPanel.setBackground(Color.RED);
		leftPanel.setBackground(Color.BLUE);
		bottomPanel.setBackground(Color.GREEN);
	}

	private void addListeners() {

	}

	/* RESPONSIVE HANDLE METHODS */

	public void setFrameResponsive() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);

				MainWindow.this.resizeComps(); // Responsive GUI
			}
		});
	}

	private void resizeComps() {
		rightPanel.setPreferredSize(getRelativeSize(0.2, 0.8));
		leftPanel.setPreferredSize(getRelativeSize(0.8, 0.8));
		bottomPanel.setPreferredSize(getRelativeSize(1, 0.15));
	}

	private Dimension getRelativeSize(double width_ratio, double height_ratio) {
		int curr_width = getWidth();
		int curr_height = getHeight();

		if (curr_width == 0) {
			curr_width = WIDTH;
			curr_height = HEIGHT;
		}

		return new Dimension((int) (width_ratio * curr_width), (int) (height_ratio * curr_height));
	}

	public void openWindow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
