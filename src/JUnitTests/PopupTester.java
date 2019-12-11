package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import GUI.MainWindow;
import GUI.Popup;
import MainLogic.DataProcesser;



class PopupTester {
	private static Popup popup;
	private static DataProcesser dp;
	private static MainWindow gui;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dp = DataProcesser.getInstance();
		// testSheet = FileUtils.readFile(new File(System.getProperty("user.dir") +
		// "/files/Long-Method.xlsx").getAbsolutePath());
		dp.initWindow();
	}

	@Test
	void testPopup() {
		popup = new Popup(Popup.TEST_CHOOSER, gui);
		assertEquals(0, Popup.TEST_CHOOSER);
		assertEquals("Escolher um teste", popup.getTitle());
	}

	@Test
	void testPopupLongRuleAdd() {
		popup = new Popup(Popup.LONG_RULE_ADD, gui);
		assertEquals(1, Popup.LONG_RULE_ADD);
	}

	@Test
	void testPopupFeatureRuleAdd() {
		popup = new Popup(Popup.FEATURE_RULE_ADD, gui);
		assertEquals(2, Popup.FEATURE_RULE_ADD);
	}
}
