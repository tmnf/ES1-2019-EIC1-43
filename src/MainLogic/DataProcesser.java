package MainLogic;

import javax.swing.DefaultComboBoxModel;

import org.apache.poi.ss.usermodel.Sheet;

import Enums.Test;
import GUI.MainWindow;
import Models.DefaultRule;
import Utils.FileUtils;

public class DataProcesser {

	private static DataProcesser INSTANCE;

	private MainWindow gui;
	public DefaultRule DefaultRule;

	private Sheet currentSheet;

	private DefaultComboBoxModel<DefaultRule> tests;

	private DataProcesser() {
		initTestList();
	}

	private void initTestList() {
		tests = new DefaultComboBoxModel<>();
		addToRuleList(new DefaultRule(Test.IPLASMA));
		addToRuleList(new DefaultRule(Test.PMD));
	}

	public static DataProcesser getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DataProcesser();
		return INSTANCE;
	}

	public void setCurrentSheet(Sheet sheet) {
		this.currentSheet = sheet;

		gui.displayText(FileUtils.fileToString(currentSheet));
	}

	public void analyseFile(DefaultRule rule) {
		new Analyser(rule).start();
	}

	public void initWindow() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				gui = new MainWindow();
				gui.openWindow();
			}
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

	public DefaultComboBoxModel<DefaultRule> getRulesList() {
		return tests;
	}

}
