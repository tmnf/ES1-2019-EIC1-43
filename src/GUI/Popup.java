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

/**
 * Popup represents the graphical view of most of the popup windows of Iscte
 * Code Analyzer.
 */
public class Popup {

	/**
	 * Represents different types of popups available to create
	 */
	public static final int TEST_CHOOSER = 0, LONG_RULE_ADD = 1, FEATURE_RULE_ADD = 2, RESULTS = 3;

	/**
	 * Represents default width of a popup
	 */
	private static final int WIDTH = 600;

	/**
	 * Represents different fonts to use all over graphical interface
	 */
	public static final Font ARIAL_BOLD = new Font("Arial", Font.BOLD, 16),
			ARIAL_PLAIN = new Font("Arial", Font.PLAIN, 16), ARIAL_PLAIN_SMALL = new Font("Arial", Font.PLAIN, 14);

	/**
	 * Reference to a MainWindow instance
	 */
	private MainWindow mw;

	/**
	 * Reference to a MainPanel instance used in the popup created
	 */
	private MainPanel mainPanel;

	/**
	 * Popup's Constructor
	 * 
	 * @param type defines which popup will be created
	 * @param mw   passes a reference to main window
	 */
	public Popup(int type, MainWindow mw) {
		this.mw = mw;
		choosePopup(type);
	}

	/**
	 * Filters which popup will be created
	 * 
	 * @param type defines which popup will be created
	 */
	private void choosePopup(int type) {
		switch (type) {
		case TEST_CHOOSER:
			openPickATest();
			break;
		case LONG_RULE_ADD:
			openLongRuleAdd(null);
			break;
		case FEATURE_RULE_ADD:
			openFeatureRuleAdd(null);
			break;
		default:
			break;
		}
	}

	/**
	 * Generates general components of rule adding popups
	 * 
	 * @param title title of rule being added
	 * @param m1    first metric to be defined (LOC or ATFD)
	 * @param m2    second metric to be defined (CYCLO or LAA)
	 * @param test  type of rule being added
	 * @param rule  indicated rule if popup window is edit type
	 */
	private void getRuleAddCommons(String title, Metric m1, Metric m2, Test test, NormalRule rule) {
		MainFrame frame = new MainFrame();

		frame.setTitle(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);

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
				if (rule != null) {
					rule.setRuleName(name.getText());
					rule.setMetric1(Float.parseFloat(metric1.getText()));
					rule.setMetric2(Float.parseFloat(metric2.getText()));
					rule.setAnd(and.isSelected());

					mw.openWarningPopup("Regra editada com sucesso!");
				} else
					addNewRule(metric1.getText(), metric2.getText(), name.getText(), and.isSelected(), test);
				frame.dispose();
			} catch (NumberFormatException e1) {
				mw.openErrorPopup("Verfique que inseriu um número nos campos das métricas");
			}
		});

		if (rule != null) {
			frame.setTitle(rule.getRuleName() + " - Edit");
			name.setText(rule.getRuleName());
			metric1.setText(rule.getMetric1() + "");
			metric2.setText(rule.getMetric2() + "");
			add.setText("Editar");

			if (rule.getAnd()) {
				and.setSelected(true);
				or.setSelected(false);
			} else {
				and.setSelected(false);
				or.setSelected(true);
			}
		}

		showPopup(frame);
	}

	/**
	 * Adds a new rule to DataProcesser's rule list
	 * 
	 * @param metric1  first metric (LOC or ATFD)
	 * @param metric2  second metric (CYCLO or LAA)
	 * @param ruleName unique name to identify different rules
	 * @param and      defines if AND or OR was selected (OR = !AND)
	 * @param test     sets type of rule being added
	 */
	private void addNewRule(String metric1, String metric2, String ruleName, boolean and, Test test) {
		NormalRule createdRule = new NormalRule(ruleName, Float.parseFloat(metric1), Float.parseFloat(metric2), and,
				test);
		DataProcesser.getInstance().addToRuleList(createdRule);

		mw.openWarningPopup("Regra criada com sucesso!");
	}

	/**
	 * Opens a popup to add a new IsLongMethod rule
	 * 
	 * @param rule rule to be edited
	 */
	private void openLongRuleAdd(NormalRule rule) {
		getRuleAddCommons("Adicionar Regra de is_long_method", Metric.LOC, Metric.CYCLO, Test.LONG_METHOD, rule);
	}

	/**
	 * Opens a popup to add a new IsFeatureEnvy rule
	 * 
	 * @param rule rule to be edited
	 */
	private void openFeatureRuleAdd(NormalRule rule) {
		getRuleAddCommons("Adicionar Regra de is_feature_envy", Metric.ATFD, Metric.LAA, Test.IS_FEATURE_ENVY, rule);
	}

	/**
	 * Sets listeners to AND and OR radio buttons
	 * 
	 * @param and first radio button to add listener
	 * @param or  second radio button to add listener
	 */
	private void radioListener(JRadioButton and, JRadioButton or) {
		and.addActionListener((e) -> or.setSelected(false));
		or.addActionListener((e) -> and.setSelected(false));
	}

	/**
	 * Opens a popup to choose, from the rule list, the rule that will be analyzed
	 */
	private void openPickATest() {
		MainFrame frame = new MainFrame();
		frame.setTitle("Escolher um teste");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);

		mainPanel = new MainPanel(new BorderLayout());

		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel bottomAuxPanel = new JPanel(new GridLayout(1, 2, 5, 0));
		bottomAuxPanel.setOpaque(false);

		DefaultComboBoxModel<DefaultRule> tests = DataProcesser.getInstance().getRulesList();

		JComboBox<DefaultRule> testList = new JComboBox<>(tests);
		testList.setPreferredSize(new Dimension(WIDTH - 200, 40));

		Button analise = new Button("Avaliar");
		analise.addActionListener((e) -> {
			DataProcesser.getInstance().analyseFile((DefaultRule) tests.getSelectedItem());
			frame.dispose();
		});

		Button edit = new Button("Editar");
		edit.addActionListener((e) -> {
			if (tests.getSelectedItem() instanceof NormalRule)
				openLongRuleAdd((NormalRule) tests.getSelectedItem());
			else
				mw.openWarningPopup("Testes padrão não podem ser editados...");
		});

		bottomAuxPanel.add(edit);
		bottomAuxPanel.add(analise);

		bottomPanel.add(bottomAuxPanel, BorderLayout.EAST);

		analise.setPreferredSize(new Dimension(100, 40));

		testList.setFont(ARIAL_BOLD);

		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		mainPanel.add(testList, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		showPopup(frame);
	}

	/**
	 * Used by every popup. Sets the popup visible and shows it in the screen
	 * 
	 * @param frame frame to set visible
	 */
	private void showPopup(MainFrame frame) {
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}