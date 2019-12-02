package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ResultsPopup extends JFrame {

	private final int WIDTH = 300, HEIGHT = 105;

	private String rule;

	private int dci, dii, adci, adii;

	private ArrayList<Integer> methods;

	public ResultsPopup(String rule, int dci, int dii, int adci, int adii, ArrayList<Boolean> results) {
		this.rule = rule;
		this.dci = dci;
		this.dii = dii;
		this.adci = adci;
		this.adii = adii;

		methods = getMethods(results);

		initWindow();
	}

	// Para apagar
	public ResultsPopup() {
		rule = "Teste";
		initWindow();
	}

	private void initWindow() {
		setTitle("Resultados (" + rule + ")");
		setResizable(false);
		
		Font f = new Font("Arial", Font.PLAIN, 16);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JPanel topPanel, botPanel;

		topPanel = new JPanel(new BorderLayout());
		botPanel = new JPanel(new BorderLayout());

		JTextArea qualityDisplay = new JTextArea();
		qualityDisplay.setEditable(false);
		qualityDisplay.setBorder(new EmptyBorder(5,5,5,5));
		qualityDisplay.setOpaque(false);
		qualityDisplay.setFont(f);
		
		topPanel.setBackground(Color.RED);

		int total = dci + dii + adci + adii;
		String quality = "DCI: " + dci + " || DII: " + dii + " || ADCI: " + adci + " || ADII: " + adii + "\n\nTotal: "
				+ total;
		qualityDisplay.setText(quality);

		Button showMethods = new Button("Deteção de Defeitos");
		showMethods.setPreferredSize(new Dimension(150, 40));

		topPanel.add(qualityDisplay, BorderLayout.CENTER);
		botPanel.add(showMethods, BorderLayout.EAST);

		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(botPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private ArrayList<Integer> getMethods(ArrayList<Boolean> results) {
		return null;
	}

	private void showDefects() {

	}

}
