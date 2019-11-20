package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Enums.Test;
import MainLogic.DataProcesser;
import Utils.FileUtils;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -1572507198564655896L;

	private final static String TITLE = "Iscte Code Analyser";

	private final static int WIDTH = 1000, HEIGHT = 600;

	private JFileChooser fc;

	private JPanel mainPanel, rightPanel, leftPanel, bottomPanel;

	private JButton analyseBt, openBt, longAddBt, featureAddBt;

	private JTable fileDisplay;
	private DefaultTableModel tableModel;

	private JScrollPane fileScroll;

	private JTextField fileName;

	private int i = 0;
	
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
		rightPanel = new JPanel(new GridLayout(10, 1));
		leftPanel = new JPanel(new BorderLayout());

		bottomPanel = new JPanel(new BorderLayout());
		JPanel bottAuxPanel = new JPanel(new GridLayout(1, 2));
		bottomPanel.add(bottAuxPanel, BorderLayout.WEST);

		fileName = new JTextField("Ficheiro Selecionado");

		analyseBt = new JButton("Escolher");
		openBt = new JButton("Abrir Ficheiro");

		longAddBt = new JButton("Adicionar Regra Is_Long_Method");
		featureAddBt = new JButton("Adicionar Regra Is_Feature_Envy");

		tableModel = new DefaultTableModel();
		fileDisplay = new JTable(tableModel);
		fileScroll = new JScrollPane(fileDisplay);
		
		rightPanel.add(longAddBt);
		rightPanel.add(featureAddBt);

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

		fileDisplay.setFont(new Font("Arial", Font.PLAIN, 16));

		fileName.setEditable(false);

		resizeComps();
	}

	private void addListeners() {
		analyseBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryToPopup(Popup.TEST_CHOOSER);
			}
		});

		openBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(i != 0){
					openErrorPopup("Já carregou um ficheiro");
					return;
				}
				openFile();
				i++;
			}
		});
		
		longAddBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryToPopup(Popup.LONG_RULE_ADD);
			}
		});
		
		featureAddBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryToPopup(Popup.FEATURE_RULE_ADD);
			}
		});
	}
	
	private void tryToPopup(int type) {
		if (DataProcesser.getInstance().getCurrentSheet() == null) {
			openErrorPopup("Carregue um ficheiro primeiro...");
			return;
		}
		new Popup(type);
	}

	private void openFile() {
		fc = new JFileChooser(".");
		FileFilter filter = new FileNameExtensionFilter("Excel File", "xlsx");
		fc.setFileFilter(filter);

		fc.showOpenDialog(this);

		if (fc.getSelectedFile() != null) {
			DataProcesser.getInstance().setCurrentSheet(FileUtils.readFile(fc.getSelectedFile().getPath()));
		}
	}

	public void displayText(String text) {
		String[] info = text.split("--");

		addColumns(info);
		addData(info);
	}

	private void addColumns(String[] info) {
		for (String x : info[0].split(":"))
			tableModel.addColumn(x);
	}

	private void addData(String[] info) {
		for (String x : info)
			if (!x.equals(info[0])) {
				String[] row = x.split(":");

				tableModel.addRow(row);
			}
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

	private void openErrorPopup(String error) {
		JOptionPane.showMessageDialog(this, error, "Aviso!", 1);
	}

	public void openWindow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
