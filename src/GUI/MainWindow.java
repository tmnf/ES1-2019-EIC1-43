package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import MainLogic.DataProcesser;
import Utils.FileUtils;

/**
 * MainWindow represents main graphical interface of Iscte Code Analyzer
 * software.
 */

public class MainWindow extends MainFrame {

	private static final long serialVersionUID = -1572507198564655896L;

	/**
	 * Iscte Code Analyzer main title
	 */
	private final static String TITLE = "Iscte Code Analyzer";

	/**
	 * Main Window default width and height
	 */
	private final static int WIDTH = 1000, HEIGHT = 600;

	/**
	 * Main file chooser to open the desired excel file
	 */
	private JFileChooser fc;

	/**
	 * Main panel that contains every component of graphical interface
	 */
	private MainPanel mainPanel;

	/**
	 * Auxiliar panels
	 */
	private JPanel rightPanel, leftPanel, bottomPanel, bottAuxPanel;

	/**
	 * Functionality buttons
	 */
	private Button analyseBt, openBt, longAddBt, featureAddBt;

	/**
	 * File display area. Where the excel file will be shown
	 */
	private JTable fileDisplay;

	/**
	 * Auxiliary table model used by fileDisplay
	 */
	private DefaultTableModel tableModel;

	/**
	 * Scroll bars that allow file navigation
	 */
	private JScrollPane fileScroll;

	/**
	 * Opened file name
	 */
	private JTextField fileName;

	/**
	 * MainWindow constructor
	 */
	public MainWindow() {
		initComponents();
		formatComponents();
		addListeners();
	}

	/**
	 * Starts every component in main graphical interface with the correct arguments
	 * and layouts
	 */
	private void initComponents() {
		setTitle(TITLE);
		setMinimumSize(new Dimension(WIDTH - 200, HEIGHT - 200));
		setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);

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

		generatePopupMenu();

		setContentPane(mainPanel);
	}

	/**
	 * Generates popupmenu that can be accessed by right clicking the frame. Holds
	 * load and save options
	 */
	private void generatePopupMenu() {
		JPopupMenu menu = new JPopupMenu("Tools");

		JMenuItem load, save;
		load = new JMenuItem("Carregar Regras");
		save = new JMenuItem("Guardar Regras");

		JFileChooser jf = new JFileChooser(".");
		FileFilter filter = new FileNameExtensionFilter("Rules File (.rl)", "rl");
		jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jf.setFileFilter(filter);

		load.addActionListener((e) -> {
			if (DataProcesser.getInstance().getCurrentSheet() == null)
				openErrorPopup("Carregue um ficheiro primeiro!");
			else if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				DataProcesser.getInstance().loadRules(jf.getSelectedFile().getPath());
		});

		save.addActionListener((e) -> {
			if (DataProcesser.getInstance().getCurrentSheet() == null)
				openErrorPopup("Carregue um ficheiro primeiro!");
			else if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				DataProcesser.getInstance().saveRules(jf.getSelectedFile().getPath());
		});

		save.setFont(Popup.ARIAL_PLAIN_SMALL);
		load.setFont(Popup.ARIAL_PLAIN_SMALL);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e))
					menu.show(MainWindow.this, e.getX(), e.getY());
				else
					super.mouseClicked(e);
			}
		});

		menu.add(save);
		menu.add(load);

		add(menu, BorderLayout.NORTH);
	}

	/**
	 * Formats each components to the right shape and location
	 */
	private void formatComponents() {
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainPanel.setBackground(Color.DARK_GRAY);

		rightPanel.setBorder(new EmptyBorder(15, 5, 5, 5));
		bottomPanel.setBorder(new EmptyBorder(5, 10, 5, 5));

		fileDisplay.setFont(Popup.ARIAL_PLAIN);
		fileName.setFont(Popup.ARIAL_PLAIN);
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
		setFrameResponsive();
	}

	/**
	 * Adds listeners to every button available
	 */
	private void addListeners() {
		openBt.addActionListener((e) -> {
			boolean keepRules = false;
			if (DataProcesser.getInstance().getCurrentSheet() != null) {
				int option = openOptionPopup(
						"Está a tentar carregar outro ficheiro. Deseja manter as regras criadas até agora?");

				if (option == 1)
					keepRules = true;
				else if (option != 1 && option != 0)
					return;
			}

			openFile(keepRules);
		});

		analyseBt.addActionListener((e) -> tryToPopup(Popup.TEST_CHOOSER));
		longAddBt.addActionListener((e) -> tryToPopup(Popup.LONG_RULE_ADD));
		featureAddBt.addActionListener((e) -> tryToPopup(Popup.FEATURE_RULE_ADD));
	}

	/**
	 * Tries to open a new popup if there's a file loaded
	 * 
	 * @param type defines wich type of popup will be created
	 */
	private void tryToPopup(int type) {
		if (DataProcesser.getInstance().getCurrentSheet() == null) {
			openErrorPopup("Carregue um ficheiro primeiro...");
			return;
		}
		new Popup(type, this);
	}

	/**
	 * Opens a new excel file
	 * 
	 * @param keepRules indicates if user wants to keep rules previously created
	 */
	private void openFile(boolean keepRules) {
		fc = new JFileChooser(".");
		FileFilter filter = new FileNameExtensionFilter("Excel File (.xlsx)", "xlsx");
		fc.setFileFilter(filter);

		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			DataProcesser.getInstance().setCurrentSheet(FileUtils.readFile(fc.getSelectedFile().getPath()), keepRules);
			fileName.setText(fc.getSelectedFile().getName());
		}
	}

	/**
	 * Coordinates file to string division, in order to show all the information
	 * about the file ordered and correct on screen
	 * 
	 * @param text excel file converted to string
	 */
	public void displayText(String text) {
		String[] info = text.split("--");

		tableModel.setRowCount(0);
		tableModel.setColumnCount(0);

		addColumns(info);
		addData(info);
	}

	/**
	 * Adds category columns to file display
	 * 
	 * @param info excel file rows in array
	 */
	private void addColumns(String[] info) {
		for (String x : info[0].split(":"))
			tableModel.addColumn(x);
	}

	/**
	 * Adds rows to file display
	 * 
	 * @param info excel file rows in array
	 */
	private void addData(String[] info) {
		for (String x : info)
			if (!x.equals(info[0])) {
				String[] row = x.split(":");
				tableModel.addRow(row);
			}
	}

	/* RESPONSIVE HANDLE METHODS */

	/**
	 * Adds listener to frame, so that each time user resizes it, all components get
	 * resized
	 */
	private void setFrameResponsive() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				MainWindow.this.resizeComps();
				super.componentResized(e);
			}
		});
	}

	/**
	 * Resizes the three major auxiliary panels to the relative size given
	 */
	private void resizeComps() {
		rightPanel.setPreferredSize(getRelativeSize(0.2, 0.8));
		leftPanel.setPreferredSize(getRelativeSize(0.8, 0.8));
		bottomPanel.setPreferredSize(getRelativeSize(1, 0.1));
	}

	/**
	 * Generates the size of a component according to the window size and ratio
	 * given
	 * 
	 * @param width_ratio  percentage of window width the component will occupy
	 * @param height_ratio percentage of window height the component will occupy
	 * 
	 * @return the generated dimension
	 */
	private Dimension getRelativeSize(double width_ratio, double height_ratio) {
		int curr_width = mainPanel.getWidth();
		int curr_height = mainPanel.getHeight();

		if (curr_width == 0) {
			curr_width = WIDTH;
			curr_height = HEIGHT;
		}

		return new Dimension((int) (width_ratio * curr_width), (int) (height_ratio * curr_height));
	}

	/**
	 * Opens a new error popup
	 * 
	 * @param error custom message to warn the user
	 */
	public void openErrorPopup(String error) {
		JOptionPane.showMessageDialog(this, error, "Aviso!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Opens a new warning
	 * 
	 * @param error custom message to warn the user
	 */
	public void openWarningPopup(String error) {
		JOptionPane.showMessageDialog(this, error, "Aviso!", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Opens a new option popup
	 * 
	 * @param warning custom message to ask the user
	 */
	private int openOptionPopup(String warning) {
		String[] options = { "Novas Regras", "Manter Regras", "Cancelar" };
		return JOptionPane.showOptionDialog(this, warning, "Atenção!", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	}

	/**
	 * Packs window frame, sets it in the center of screen and turns it visible to
	 * the user
	 */
	public void openWindow() {
		pack();
		setLocationRelativeTo(null);
		
		/* IMPORTANTE É NECESSÁRIO ALTERAR PARA TRUE ANTES DE ENTREGAR; ESTÁ A FALSE SÓ PARA OS TESTES*/
		
		setVisible(false);
	}
}
