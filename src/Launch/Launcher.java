package Launch;

import MainLogic.DataProcesser;

public class Launcher {

	public static void main(String[] args) {
		DataProcesser dp = DataProcesser.getInstance();
		dp.initWindow();
	}

}
