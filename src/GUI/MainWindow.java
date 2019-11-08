package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -1572507198564655896L;
	
	private final static String TITLE = "Iscte Code Analyser";
	
	private final static int WIDTH = 1000, HEIGHT = 600;

	private JPanel mainPanel, rightPanel, leftPanel, bottomPanel;

	private JButton analyseBt, openBt;

	private JTextArea fileDisplay;
	private JScrollPane fileScroll;

	private JTextField fileName, locName, cycloName, atfdName, laaName;
	private JTextField locText, cycloText, atfdText, laaText;

	public MainWindow() {
		initComponents();
		formatComponents();
		addListeners();
	}

	private void initComponents() {
		setTitle(TITLE);
		setMinimumSize(new Dimension(WIDTH - 200, HEIGHT - 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel(new BorderLayout());

		rightPanel = new JPanel(new GridLayout(4, 2));

		leftPanel = new JPanel(new BorderLayout());

		bottomPanel = new JPanel(new BorderLayout());
		JPanel bottAuxPanel = new JPanel(new GridLayout(1, 2));
		bottomPanel.add(bottAuxPanel, BorderLayout.WEST);

		fileName = new JTextField("Ficheiro Selecionado");
		locName = new JTextField("LOC");
		cycloName = new JTextField("CYCLO");
		atfdName = new JTextField("ATFD");
		laaName = new JTextField("LAA");

		locText = new JTextField();
		cycloText = new JTextField();
		atfdText = new JTextField();
		laaText = new JTextField();

		analyseBt = new JButton("Analisar Ficheiro");
		openBt = new JButton("Abrir Ficheiro");

		fileDisplay = new JTextArea();
		fileScroll = new JScrollPane(fileDisplay);

		rightPanel.add(locName);
		rightPanel.add(locText);

		rightPanel.add(cycloName);
		rightPanel.add(cycloText);

		rightPanel.add(atfdName);
		rightPanel.add(atfdText);

		rightPanel.add(laaName);
		rightPanel.add(laaText);

		leftPanel.add(fileName, BorderLayout.NORTH);
		leftPanel.add(fileScroll, BorderLayout.CENTER);

		bottAuxPanel.add(analyseBt);
		bottAuxPanel.add(openBt);

		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(leftPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		add(mainPanel);
	}

	private void formatComponents() {
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		fileName.setEditable(false);
		locName.setEditable(false);
		cycloName.setEditable(false);
		atfdName.setEditable(false);
		laaName.setEditable(false);
		fileDisplay.setEditable(false);

		resizeComps();
	}

	private void addListeners() {
		analyseBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				analyseAction();
			}
		});

		openBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
	}

	private void analyseAction() {
		System.out.println("");
		System.out.println("=================");
		System.out.println("Analisar Ficheiro");

	}

	private void openFile() {
		JFileChooser fc = new JFileChooser(".");
		FileFilter filter = new FileNameExtensionFilter("Excel File", "xlsx");
		fc.setFileFilter(filter);

		fc.showOpenDialog(this);

		if (fc.getSelectedFile() != null) {
			//DataProcesser.getInstance().setCurrentSheet(FileUtils.readFile(fc.getSelectedFile().getPath()));
		}
	}

	public void displayText(String text) {
		fileDisplay.setText(text);
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
