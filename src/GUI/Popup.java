package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Enums.Metric;
import Enums.Test;
import MainLogic.DataProcesser;
import Models.Rule;

public class Popup extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int TEST_CHOOSER = 0, LONG_RULE_ADD = 1, FEATURE_RULE_ADD = 2;
	private static final int WIDTH = 400, HEIGHT = 30;
	private Test[] fixedTests = { Test.PMD, Test.IPLASMA };
	private JComboBox<Object> testList;
	private Object[] array;
	private ArrayList<Rule> rulesList;
	private Boolean andOr;
	private JPanel mainPanel;
	private JRadioButton or, and;
	private JTextField metric1, metric2, name;
	private String givenRuleName;
	private int fixedTestListLength;
	public static Rule createdRule;

	public Popup(int type) {
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

		name = new JTextField();
		name.setText("Nome da Regra");
		metric1 = new JTextField(m1.toString());
		metric2 = new JTextField(m2.toString());

		JButton add = new JButton("Adicionar");

		and = new JRadioButton("AND");
		or = new JRadioButton("OR");

		ButtonGroup group = new ButtonGroup();
		group.add(and);
		group.add(or);

		bottomPanel.add(and);
		bottomPanel.add(or);
		bottomPanel.add(add);

		centerPanel.add(name, BorderLayout.CENTER);
		centerPanel.add(metric1);
		centerPanel.add(metric2);

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		andOrChanger();

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (group.getSelection() != null) {
						addNewRule(m1, m2);
						dispose();

					} else {
						buttonNotSelected("Botão não selecionado: Por favor selecione And ou Or");
					}
				} catch (NumberFormatException e1) {
					buttonNotSelected("Verfique que inseriu um número nos campos das métricas");

				}
			}
		});
		showPopup();
	}

	private void addNewRule(Metric firstMetric, Metric secondMetric) {
		givenRuleName = name.getText();
		firstMetric.setMax(Float.parseFloat(metric1.getText()));
		secondMetric.setMax(Float.parseFloat(metric2.getText()));

		createdRule = new Rule(givenRuleName, firstMetric.getMax(), secondMetric.getMax(), andOr);

		createdRule.toString();

		DataProcesser.getInstance().getRulesList().add(createdRule);
		ruleCreated("Regra criada com sucesso!");
	}

	private void andOrChanger() {
		and.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				andOr = true;
			}
		});
		or.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				andOr = false;
			}
		});
	}

	private void openLongRuleAdd() {
		getRuleAddCommons("Adicionar Regra de is_long_method", Metric.LOC, Metric.CYCLO);
	}

	private void openFeatureRuleAdd() {
		getRuleAddCommons("Adicionar Regra de is_feature_envy", Metric.ATFD, Metric.LAA);
	}

	private void ruleList() {
		rulesList = DataProcesser.getInstance().getRulesList();
		array = new Object[rulesList.size() + fixedTestListLength];

		for (int i = 0; i < array.length; i++) {
			if (i < fixedTestListLength) {
				array[i] = fixedTests[i];
			} else
				array[i] = rulesList.get(i - fixedTestListLength);
		}
	}

	private void presentResultsTemp() {
		int testListSelectedIndex = testList.getSelectedIndex();
		if (testListSelectedIndex < fixedTestListLength) {
			DataProcesser.getInstance().analyseFile((Test) testList.getSelectedItem());
		} else {
			System.out.println(((Rule) testList.getSelectedItem()).getNomeDaRegra());
		}
	}

	private void openPickATest() {
		setTitle("Escolher um teste");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		fixedTestListLength = fixedTests.length;

		mainPanel = new JPanel(new BorderLayout());

		JPanel bottomPanel = new JPanel(new BorderLayout());

		ruleList();
		System.out.println(array);

		testList = new JComboBox<>(array);
		testList.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JButton analise = new JButton("Avaliar");
		analise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				presentResultsTemp();
			}
		});

		bottomPanel.add(analise, BorderLayout.EAST);

		mainPanel.add(testList, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		showPopup();
	}

	private void ruleCreated(String aviso) {
		JOptionPane.showMessageDialog(this, aviso, "Sucesso!", 1);
	}

	private void buttonNotSelected(String aviso) {
		JOptionPane.showMessageDialog(this, aviso, "ERRO", 1);
	}

	private void showPopup() {
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}