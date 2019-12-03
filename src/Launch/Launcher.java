package Launch;

import javax.swing.UIManager;

import MainLogic.DataProcesser;

public class Launcher {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		DataProcesser dp = DataProcesser.getInstance();
		dp.initWindow();

	}
}
