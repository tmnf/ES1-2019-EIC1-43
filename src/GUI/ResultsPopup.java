package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import MainLogic.DataProcesser;
import Utils.FileUtils;

public class ResultsPopup extends MainFrame {

	private static final long serialVersionUID = 1L;

	private final int WIDTH = 500, HEIGHT = 105;

	private String rule;

	private int dci, dii, adci, adii;

	private ArrayList<Integer> methods;

	public ResultsPopup(String rule, int dci, int dii, int adci, int adii, ArrayList<Boolean> results) {
		this.rule = rule;
		this.dci = dci;
		this.dii = dii;
		this.adci = adci;
		this.adii = adii;

		if (results != null)
			methods = getMethods(results);

		initWindow();
	}

	private void initWindow() {
		setTitle("Resultados (" + rule + ")");
		setResizable(false);

		MainPanel mainPanel = new MainPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JPanel topPanel, botPanel;

		topPanel = new JPanel(new BorderLayout());
		botPanel = new JPanel(new BorderLayout());

		topPanel.setOpaque(false);
		botPanel.setOpaque(false);

		JTextArea qualityDisplay = new JTextArea();
		qualityDisplay.setEditable(false);
		qualityDisplay.setOpaque(false);

		qualityDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));
		qualityDisplay.setForeground(Color.WHITE);
		/* ============== */

		qualityDisplay.setFont(Popup.ARIAL_BOLD);

		int total = dci + dii + adci + adii;
		String quality = "DCI: " + dci + " || DII: " + dii + " || ADCI: " + adci + " || ADII: " + adii + "\nTotal: "
				+ total;
		qualityDisplay.setText(quality);

		if (methods != null) {
			Button showMethods = new Button("Deteção de Defeitos");
			showMethods.setPreferredSize(new Dimension(200, 40));

			showMethods.addActionListener((e) -> showDefects());

			botPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

			botPanel.add(showMethods, BorderLayout.EAST);
			mainPanel.add(botPanel, BorderLayout.SOUTH);
		}

		topPanel.add(qualityDisplay, BorderLayout.CENTER);

		mainPanel.add(topPanel, BorderLayout.CENTER);

		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private ArrayList<Integer> getMethods(ArrayList<Boolean> results) {
		ArrayList<Integer> methodIds = new ArrayList<>();

		int i = 0;
		while (i != results.size()) {
			if (results.get(i))
				methodIds.add(i + 1);

			i++;
		}

		return methodIds;
	}

	private void showDefects() {
		MainFrame frame = new MainFrame();
		frame.setTitle("Métodos Detectados - (" + rule + ")");
		frame.setResizable(false);

		MainPanel mainPanel = new MainPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT + 100));

		DefaultListModel<String> res = new DefaultListModel<>();
		JList<String> results = new JList<>(res);

		results.setOpaque(false);
		results.setForeground(Color.WHITE);

		results.setCellRenderer(new ListRenderer());

		JScrollPane scroll = new JScrollPane(results);

		results.setFont(Popup.ARIAL_PLAIN);
		int indexOfMethods = FileUtils.getCellIndexByText("method");

		for (int x : methods) {
			String method = DataProcesser.getInstance().getCurrentSheet().getRow(x).getCell(indexOfMethods)
					.getStringCellValue().split("\\(")[0];

			res.addElement("ID: " + x + " - " + method + "(...)");
		}

		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);

		mainPanel.add(scroll);

		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public class ListRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			setBackground(Color.DARK_GRAY);
			setOpaque(isSelected);
			return this;
		}
	}

}
