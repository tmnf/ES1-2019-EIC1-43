package Launch;

import javax.swing.UIManager;

import MainLogic.DataProcesser;

/**
 * Iscte Code Analyzer is a program that checks code from another program based
 * on an excel file and checks all the methods containing defects according to
 * rules introduced by the user.
 *
 * @author Tiago Farinha, Tomás Santos, Nuno Martinho, Miguel Neto, Filipe Dias,
 *         Milan Bhatt
 * @version 1.0
 */

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
