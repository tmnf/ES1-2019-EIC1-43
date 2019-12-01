package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;

import Enums.Test;
import GUI.MainWindow;
import Models.Rule;
import Utils.FileUtils;

public class DataProcesser {

	private static DataProcesser INSTANCE;

	private MainWindow gui;
	public Rule rule;

	private Sheet currentSheet;

	public ArrayList<Rule> tests;

	private DataProcesser() {
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

	public void analyseFile(Test methodToCompare) {
		new Analyser(DataProcesser.getInstance().getCurrentSheet(), methodToCompare).start();
	}

	public void initWindow() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				tests = new ArrayList<Rule>();
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
	
	public ArrayList<Rule> getRulesList(){
		return tests;
	}

}
