package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Enums.Test;
import MainLogic.DataProcesser;
import Models.NormalRule;
import Utils.FileUtils;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -1572507198564655896L;

	private final static String TITLE = "Iscte Code Analyser";

	private final static int WIDTH = 1000, HEIGHT = 600;

	private JFileChooser fc;

	private MainPanel mainPanel;

	private JPanel rightPanel, leftPanel, bottomPanel, bottAuxPanel;

	private Button analyseBt, openBt, longAddBt, featureAddBt;

	private JTable fileDisplay;
	private DefaultTableModel tableModel;

	private JScrollPane fileScroll;

	private JTextField fileName;

	public MainWindow() {
		initComponents();
		formatComponents();
		addListeners();
	}

	private void initComponents() {
		setTitle(TITLE);
		setMinimumSize(new Dimension(WIDTH - 200, HEIGHT - 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new MainPanel(new BorderLayout());
		rightPanel = new JPanel(new GridLayout(10, 1, 0, 5));
		leftPanel = new JPanel(new BorderLayout());

		bottomPanel = new JPanel(new BorderLayout());
		bottAuxPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		bottomPanel.add(bottAuxPanel, BorderLayout.WEST);

		fileName = new JTextField("Ficheiro Selecionado");

		analyseBt = new Button("Escolher");
		openBt = new Button("Abrir Ficheiro");

		longAddBt = new Button("Adicionar Regra Long");
		featureAddBt = new Button("Adicionar Regra Feature");

		tableModel = new DefaultTableModel();
		fileDisplay = new JTable(tableModel);
		fileScroll = new JScrollPane(fileDisplay);

		rightPanel.add(longAddBt);
		rightPanel.add(featureAddBt);

		leftPanel.add(fileName, BorderLayout.NORTH);
		leftPanel.add(fileScroll, BorderLayout.CENTER);

		bottAuxPanel.add(analyseBt);
		bottAuxPanel.add(openBt);

		JPanel emptyPanel = new JPanel();
		emptyPanel.setBorder(null);
		emptyPanel.setOpaque(false);
		emptyPanel.setPreferredSize(new Dimension(10, 20));

		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(emptyPanel, BorderLayout.WEST);
		mainPanel.add(leftPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
	}

	private void formatComponents() {
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainPanel.setBackground(Color.DARK_GRAY);

		rightPanel.setBorder(new EmptyBorder(15, 5, 5, 5));
		bottomPanel.setBorder(new EmptyBorder(5, 10, 5, 5));

		fileDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
		fileName.setFont(new Font("Arial", Font.PLAIN, 16));
		fileName.setForeground(Color.WHITE);

		fileDisplay.setOpaque(false);
		fileScroll.getViewport().setBackground(Color.LIGHT_GRAY);
		fileScroll.getViewport().setBorder(null);
		fileScroll.setBorder(null);

		bottomPanel.setOpaque(false);
		bottAuxPanel.setOpaque(false);
		rightPanel.setOpaque(false);
		leftPanel.setOpaque(false);

		fileName.setEditable(false);
		fileName.setBorder(null);
		fileName.setOpaque(false);

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
				if (DataProcesser.getInstance().getCurrentSheet() != null) {
					int option = openConfirmPopup(
							"Se carregar outro ficheiro irá perder as regras adicionadas e os resultados. Deseja continuar?");
					if (option == 1 || option == 2)
						return;
				}

				openFile();
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
		new Popup(type, this);
	}

	private void openFile() {
		fc = new JFileChooser(".");
		FileFilter filter = new FileNameExtensionFilter("Excel File", "xlsx");
		fc.setFileFilter(filter);

		fc.showOpenDialog(this);

		if (fc.getSelectedFile() != null) {
			DataProcesser.getInstance().setCurrentSheet(FileUtils.readFile(fc.getSelectedFile().getPath()));
			fileName.setText(fc.getSelectedFile().getName());
		}
	}

	public void displayText(String text) {
		String[] info = text.split("--");

		tableModel.setRowCount(0);
		tableModel.setColumnCount(0);

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
				MainWindow.this.resizeComps(); // Responsive GUI
				super.componentResized(e);
			}
		});
	}

	private void resizeComps() {
		rightPanel.setPreferredSize(getRelativeSize(0.2, 0.8));
		leftPanel.setPreferredSize(getRelativeSize(0.8, 0.8));
		bottomPanel.setPreferredSize(getRelativeSize(1, 0.1));
	}

	private Dimension getRelativeSize(double width_ratio, double height_ratio) {
		int curr_width = mainPanel.getWidth();
		int curr_height = mainPanel.getHeight();

		if (curr_width == 0) {
			curr_width = WIDTH;
			curr_height = HEIGHT;
		}

		return new Dimension((int) (width_ratio * curr_width), (int) (height_ratio * curr_height));
	}

	public void openErrorPopup(String error) {
		JOptionPane.showMessageDialog(this, error, "Aviso!", JOptionPane.ERROR_MESSAGE);
	}

	public void openWarningPopup(String error) {
		JOptionPane.showMessageDialog(this, error, "Aviso!", JOptionPane.WARNING_MESSAGE);
	}

	private int openConfirmPopup(String warning) {
		return JOptionPane.showConfirmDialog(this, warning, "Atenção!", JOptionPane.WARNING_MESSAGE);
	}

	public void openWindow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
