package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import GUI.MainWindow;
import GUI.Popup;
import Models.DefaultRule;
import Models.NormalRule;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import org.apache.poi.ss.usermodel.Sheet;

import MainLogic.DataProcesser;
import Utils.FileUtils;

public class DataProcessorTest {
	private static Popup popup;
	private static MainWindow gui;

	private static DataProcesser dp;
	private static Sheet testSheet;
	private static DefaultComboBoxModel<DefaultRule> f;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		f = new DefaultComboBoxModel<DefaultRule>();
		dp = DataProcesser.getInstance();
		dp.initWindow();
		testSheet = FileUtils
				.readFile(new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx").getAbsolutePath());

		dp.setCurrentSheet(testSheet, false);

	}

	@Test
	void testGetInstance() {
		DataProcesser dpi = DataProcesser.getInstance();
		assertNotNull(dpi);
	}

	@Test
	void testAnalizeFile() {
		dp.analyseFile(new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD));
	}

	@Test
	void testSetCurrentSheet() {
		dp.setCurrentSheet(testSheet, true);
		assertEquals(testSheet, dp.getCurrentSheet());
	}

	@Test
	void testAlreadyExists() {

		NormalRule normalRule = new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("NormalRule2", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);
		NormalRule copyNormalRule = new NormalRule("NormalRule1", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		dp.addToRuleList(normalRule);

		System.out.println();

		assertFalse(dp.alreadyExists(normalRule2.getRuleName()));
		assertTrue(dp.alreadyExists(copyNormalRule.getRuleName()));
	}

	@Test
	void testGetRulesList() {
		NormalRule normalRule = new NormalRule("GetRuleNormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("GetRuleNormalRule2", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		dp.addToRuleList(normalRule);
		dp.addToRuleList(normalRule2);

		f = dp.getRulesList();

		assertNotNull(dp.getRulesList());
		assertEquals(dp.getRulesList(), f);
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
