package MainLogic;

import javax.swing.DefaultComboBoxModel;

import org.apache.poi.ss.usermodel.Sheet;

import Enums.Test;
import GUI.MainWindow;
import Models.DefaultRule;
import Models.NormalRule;
import Utils.FileUtils;

public class DataProcesser {

	private static DataProcesser INSTANCE;

	private MainWindow gui;

	private Sheet currentSheet;

	private DefaultComboBoxModel<DefaultRule> tests;

	private DataProcesser() {
		initTestList();
	}

	private void initTestList() {
		tests = new DefaultComboBoxModel<>();
	}

	public static DataProcesser getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DataProcesser();
		return INSTANCE;
	}

	public void setCurrentSheet(Sheet sheet, boolean keepRules) {
		this.currentSheet = sheet;

		if (!keepRules) {
			tests.removeAllElements();

			addToRuleList(new DefaultRule(Test.IPLASMA));
			addToRuleList(new DefaultRule(Test.PMD));
		}

		gui.displayText(FileUtils.fileToString(currentSheet));
	}

	public void analyseFile(DefaultRule rule) {
		new Analyser(rule).start();
	}

	public void initWindow() {
		new Thread(() -> {
			gui = new MainWindow();
			gui.openWindow();
		}).start();
	}

	public Sheet getCurrentSheet() {
		return currentSheet;
	}

	public MainWindow getGraphicalInterface() {
		return gui;
	}

	public void addToRuleList(DefaultRule DefaultRule) {
		tests.addElement(DefaultRule);
	}

	public boolean alreadyExists(String name) {
		int i = 0;
		while (i != tests.getSize()) {
			DefaultRule rule = tests.getElementAt(i);
			if (rule instanceof NormalRule)
				if (((NormalRule) rule).getNomeDaRegra().equals(name))
					return true;
			i++;
		}

		return false;

	}

	public DefaultComboBoxModel<DefaultRule> getRulesList() {
		return tests;
	}

}
