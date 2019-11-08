package MainLogic;

import org.apache.poi.ss.usermodel.Sheet;

import GUI.MainWindow;

public class DataProcesser {

	private static DataProcesser INSTANCE;

	private MainWindow gui;

	private Sheet currentSheet;

	private DataProcesser() {

	}

	public static DataProcesser getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DataProcesser();
		return INSTANCE;
	}

	public void setCurrentSheet(Sheet sheet) {
		this.currentSheet = sheet;
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

}
