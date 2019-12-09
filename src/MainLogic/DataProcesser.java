package MainLogic;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import org.apache.poi.ss.usermodel.Sheet;

import Enums.Test;
import GUI.MainWindow;
import Models.DefaultRule;
import Models.NormalRule;
import Utils.FileUtils;

/**
 * DataProcesser represents the main class of this program, containing all
 * interactions between the Analyzer and User's graphical interface.
 */

public class DataProcesser {

	/**
	 * Saves an instance to DataProcesser. DataProcesser can only be created or used
	 * by accessing this method's getter
	 */
	private static DataProcesser INSTANCE;

	/**
	 * Saves an instance to user's graphical interface
	 */
	private MainWindow gui;

	/**
	 * Excel sheet loaded by the user, that's going to be used in analysis
	 */
	private Sheet currentSheet;

	/**
	 * List of default tests available as well as user added rules
	 */
	private DefaultComboBoxModel<DefaultRule> tests;

	/**
	 * DataProcesser's constructor. Set to private, so that instances of
	 * DataProcesser can only be created through getInstance() method, avoiding more
	 * than one instance being created
	 */
	private DataProcesser() {
		tests = new DefaultComboBoxModel<>();
	}

	/**
	 * Returns an instance of DataProcesser if one was created already, if not it
	 * creates a new one
	 * 
	 * @return instance of DataProcesser
	 */
	public static DataProcesser getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DataProcesser();
		return INSTANCE;
	}

	/**
	 * Saves an instance of an excel sheet loaded and calls the graphical interface
	 * to display it to the user. If user already had a file loaded, it will decide
	 * if the current rules are kept or not
	 * 
	 * @param sheet     instance of loaded sheet to save
	 * @param keepRules controls if rules are kept or not
	 */
	public void setCurrentSheet(Sheet sheet, boolean keepRules) {
		this.currentSheet = sheet;

		if (!keepRules) {
			tests.removeAllElements();

			addToRuleList(new DefaultRule(Test.IPLASMA));
			addToRuleList(new DefaultRule(Test.PMD));
		}

		gui.displayText(FileUtils.fileToString(currentSheet));
	}

	/**
	 * Starts an Analyzer thread
	 * 
	 * @param rule chosen by the user to control how the analysis will be performed
	 */
	public void analyseFile(DefaultRule rule) {
		new Analyzer(rule).start();
	}

	/**
	 * Starts a new Graphical Interface and saves its instance
	 */
	public void initWindow() {
		gui = new MainWindow();
		gui.openWindow();
	}

	/**
	 * @return instance of sheet loaded
	 */
	public Sheet getCurrentSheet() {
		return currentSheet;
	}

	/**
	 * @param rule indicates the rule to be added to the list
	 */
	public void addToRuleList(DefaultRule rule) {
		tests.addElement(rule);
	}

	/**
	 * Checks if a given rule name already exists in test list
	 * 
	 * @param name Rule name
	 * 
	 * @return true if there's a rule with the given name
	 * @return false if there's no rule with the given name
	 */
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

	/**
	 * Saves the current test list to a local file
	 * 
	 * @param path path to save file
	 */
	public void saveRules(String path) {
		FileUtils.saveFile(path, tests);
		gui.openWarningPopup("Regras Guardadas Com Sucesso");
	}

	/**
	 * Loads a rule containing file to the current tests' list
	 * 
	 * @param path path to load file
	 */
	public void loadRules(String path) {
		tests.removeAllElements();
		ArrayList<DefaultRule> rulesLoaded = FileUtils.loadRules(path);
		FileUtils.addRulesToListFromArray(rulesLoaded, tests);
		gui.openWarningPopup("Regras Carregadas Com Sucesso");
	}

	/**
	 * @return the current test list
	 */
	public DefaultComboBoxModel<DefaultRule> getRulesList() {
		return tests;
	}
}
