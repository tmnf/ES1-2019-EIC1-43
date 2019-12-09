package MainLogic;

import java.util.ArrayList;

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
		new Analyzer(rule).start();
	}

	public void initWindow() {
		gui = new MainWindow();
		gui.openWindow();
	}

	public Sheet getCurrentSheet() {
		return currentSheet;
	}

	public void addToRuleList(DefaultRule rule) {
		tests.addElement(rule);
	}

	public boolean alreadyExists(String name) {
		int i = 0;
		while (i != tests.getSize()) {
			DefaultRule rule = tests.getElementAt(i);
			if (rule instanceof NormalRule)
				if (((NormalRule) rule).getRuleName().equals(name))
					return true;
			i++;
		}

		return false;
	}

	public void saveRules(String path) {
		FileUtils.saveFile(path, tests);
		gui.openWarningPopup("Regras Guardadas Com Sucesso");
	}

	public void loadRules(String path) {
		tests.removeAllElements();
		ArrayList<DefaultRule> rulesLoaded = FileUtils.loadRules(path);
		FileUtils.addRulesToListFromArray(rulesLoaded, tests);
		gui.openWarningPopup("Regras Carregadas Com Sucesso");
	}

	public DefaultComboBoxModel<DefaultRule> getRulesList() {
		return tests;
	}
}
