package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Enums.Metric;
import Enums.Test;
import MainLogic.DataProcesser;
import Models.DefaultRule;
import Models.NormalRule;

public class Popup extends MainFrame {

	private static final long serialVersionUID = 2419830468873315990L;

	public static final int TEST_CHOOSER = 0, LONG_RULE_ADD = 1, FEATURE_RULE_ADD = 2, RESULTS = 3;

	private static final int WIDTH = 600;

	public static final Font ARIAL_BOLD = new Font("Arial", Font.BOLD, 16),
			ARIAL_PLAIN = new Font("Arial", Font.PLAIN, 16), ARIAL_PLAIN_SMALL = new Font("Arial", Font.PLAIN, 14);

	private MainWindow mw;
	private MainPanel mainPanel;

	public Popup(int type, MainWindow mw) {
		this.mw = mw;
		choosePopup(type);
	}

	private void choosePopup(int type) {
		switch (type) {
		case TEST_CHOOSER:
			openPickATest();
			break;
		case LONG_RULE_ADD:
			openLongRuleAdd();
			break;
		case FEATURE_RULE_ADD:
			openFeatureRuleAdd();
			break;
		default:
			break;
		}
	}

	private void getRuleAddCommons(String title, Metric m1, Metric m2, Test test) {
		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);

		mainPanel = new MainPanel(new BorderLayout());

		JPanel centerPanel = new JPanel(new GridLayout(2, 3, 5, 2));
		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JTextField nameText, metric1Text, metric2Text;
		nameText = new JTextField("Nome da Regra");
		metric1Text = new JTextField(m1.toString());
		metric2Text = new JTextField(m2.toString());

		nameText.setForeground(Color.white);
		metric1Text.setForeground(Color.white);
		metric2Text.setForeground(Color.white);

		nameText.setOpaque(false);
		metric1Text.setOpaque(false);
		metric2Text.setOpaque(false);

		nameText.setEditable(false);
		metric1Text.setEditable(false);
		metric2Text.setEditable(false);

		nameText.setHorizontalAlignment(JTextField.CENTER);
		metric1Text.setHorizontalAlignment(JTextField.CENTER);
		metric2Text.setHorizontalAlignment(JTextField.CENTER);

		nameText.setFont(ARIAL_BOLD);
		metric1Text.setFont(ARIAL_BOLD);
		metric2Text.setFont(ARIAL_BOLD);

		nameText.setBorder(null);
		metric1Text.setBorder(null);
		metric2Text.setBorder(null);

		centerPanel.add(nameText);
		centerPanel.add(metric1Text);
		centerPanel.add(metric2Text);

		JTextField name, metric1, metric2;

		name = new JTextField();
		metric1 = new JTextField();
		metric2 = new JTextField();

		name.setFont(ARIAL_PLAIN);
		metric1.setFont(ARIAL_PLAIN);
		metric2.setFont(ARIAL_PLAIN);

		centerPanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		Button add = new Button("Adicionar");
		add.setPreferredSize(new Dimension(100, 40));

		JRadioButton and, or;

		and = new JRadioButton("AND");
		or = new JRadioButton("OR");

		and.setOpaque(false);
		or.setOpaque(false);

		and.setFont(ARIAL_PLAIN);
		or.setFont(ARIAL_PLAIN);

		and.setForeground(Color.white);
		or.setForeground(Color.white);

		radioListener(and, or);

		and.setSelected(true);

		bottomPanel.add(and);
		bottomPanel.add(or);
		bottomPanel.add(add);

		centerPanel.add(name, BorderLayout.CENTER);
		centerPanel.add(metric1);
		centerPanel.add(metric2);

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		add.addActionListener((e) -> {
			String error = "";

			if (DataProcesser.getInstance().alreadyExists(name.getText()))
				error = "Já existe uma regra com esse nome!";
			else if (name.getText().trim().isEmpty())
				error = "Deve inserir um nome para a regra criada";

			if (!error.isEmpty()) {
				mw.openErrorPopup(error);
				return;
			}

			try {
				addNewRule(metric1.getText(), metric2.getText(), name.getText(), and.isSelected(), test);
				dispose();
			} catch (NumberFormatException e1) {
				mw.openErrorPopup("Verfique que inseriu um número nos campos das métricas");
			}
		});

		showPopup();
	}

	private void addNewRule(String metric1, String metric2, String ruleName, boolean and, Test test) {
		NormalRule createdRule = new NormalRule(ruleName, Float.parseFloat(metric1), Float.parseFloat(metric2), and,
				test);
		DataProcesser.getInstance().addToRuleList(createdRule);

		mw.openWarningPopup("Regra criada com sucesso!");
	}

	private void openLongRuleAdd() {
		getRuleAddCommons("Adicionar Regra de is_long_method", Metric.LOC, Metric.CYCLO, Test.LONG_METHOD);
	}

	private void openFeatureRuleAdd() {
		getRuleAddCommons("Adicionar Regra de is_feature_envy", Metric.ATFD, Metric.LAA, Test.IS_FEATURE_ENVY);
	}

	private void radioListener(JRadioButton and, JRadioButton or) {
		and.addActionListener((e) -> or.setSelected(false));
		or.addActionListener((e) -> and.setSelected(false));
	}

	private void openPickATest() {
		setTitle("Escolher um teste");
		setResizable(false);
		setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);

		mainPanel = new MainPanel(new BorderLayout());

		JPanel bottomPanel = new JPanel(new BorderLayout());

		DefaultComboBoxModel<DefaultRule> tests = DataProcesser.getInstance().getRulesList();

		JComboBox<DefaultRule> testList = new JComboBox<>(tests);
		testList.setPreferredSize(new Dimension(WIDTH - 200, 40));

		Button analise = new Button("Avaliar");
		analise.addActionListener((e) -> {
			DataProcesser.getInstance().analyseFile((DefaultRule) tests.getSelectedItem());
			dispose();
		});

		bottomPanel.add(analise, BorderLayout.EAST);

		analise.setPreferredSize(new Dimension(100, 40));

		testList.setFont(ARIAL_BOLD);

		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		mainPanel.add(testList, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		showPopup();
	}

	/* General */

	private void showPopup() {
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}