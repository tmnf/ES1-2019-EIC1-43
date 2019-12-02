package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Enums.Metric;
import MainLogic.DataProcesser;
import Models.DefaultRule;
import Models.NormalRule;

public class Popup2 extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int TEST_CHOOSER = 0, LONG_RULE_ADD = 1, FEATURE_RULE_ADD = 2;
	private static final int WIDTH = 400, HEIGHT = 30;

	private MainWindow mw;

	private JPanel mainPanel;

	public Popup2(int type, MainWindow mw) {
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

	private void getRuleAddCommons(String title, Metric m1, Metric m2) {
		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mainPanel = new JPanel(new BorderLayout());

		JPanel centerPanel = new JPanel(new GridLayout(1, 3));
		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

		JTextField name, metric1, metric2;

		name = new JTextField();
		name.setText("Nome da Regra");
		metric1 = new JTextField(m1.toString());
		metric2 = new JTextField(m2.toString());

		JButton add = new JButton("Adicionar");

		JRadioButton and, or;

		and = new JRadioButton("AND");
		or = new JRadioButton("OR");

		and.setEnabled(true);

		bottomPanel.add(and);
		bottomPanel.add(or);
		bottomPanel.add(add);

		centerPanel.add(name, BorderLayout.CENTER);
		centerPanel.add(metric1);
		centerPanel.add(metric2);

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (and.isEnabled() || or.isEnabled()) {
						addNewRule(metric1.getText(), metric2.getText(), name.getText(), and.isEnabled());
						dispose();
					}
				} catch (NumberFormatException e1) {
					mw.openErrorPopup("Verfique que inseriu um número nos campos das métricas");
				}
			}
		});

		showPopup();
	}

	private void addNewRule(String metric1, String metric2, String ruleName, boolean and) {
		NormalRule createdRule = new NormalRule(ruleName, Float.parseFloat(metric1), Float.parseFloat(metric2), and);
		DataProcesser.getInstance().addToRuleList(createdRule);

		mw.openWarningPopup("Regra criada com sucesso!");
	}

	private void openLongRuleAdd() {
		getRuleAddCommons("Adicionar Regra de is_long_method", Metric.LOC, Metric.CYCLO);
	}

	private void openFeatureRuleAdd() {
		getRuleAddCommons("Adicionar Regra de is_feature_envy", Metric.ATFD, Metric.LAA);
	}

	private void openPickATest() {
		setTitle("Escolher um teste");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mainPanel = new JPanel(new BorderLayout());

		JPanel bottomPanel = new JPanel(new BorderLayout());

		DefaultComboBoxModel<DefaultRule> tests = DataProcesser.getInstance().getRulesList();

		JComboBox<DefaultRule> testList = new JComboBox<>(tests);
		testList.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JButton analise = new JButton("Avaliar");
		analise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataProcesser.getInstance().analyseFile((DefaultRule) tests.getSelectedItem());
			}
		});

		bottomPanel.add(analise, BorderLayout.EAST);

		mainPanel.add(testList, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		showPopup();
	}

	private void showPopup() {
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}