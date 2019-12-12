package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import GUI.MainWindow;
import GUI.Popup;
import Models.DefaultRule;
import Models.NormalRule;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import org.apache.poi.ss.usermodel.Sheet;

import MainLogic.DataProcesser;
import Utils.FileUtils;

@TestMethodOrder(OrderAnnotation.class)
public class DataProcessorTest {

	private static MainWindow gui;

	private static DataProcesser dataP;

	private static Sheet testSheet;

	private static DefaultComboBoxModel<DefaultRule> f;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		f = new DefaultComboBoxModel<DefaultRule>();

		dataP = DataProcesser.getInstance();
		dataP.initWindow();

		testSheet = FileUtils.readFile(new File("./files/Long-Method.xlsx").getAbsolutePath());

		dataP.setCurrentSheet(testSheet, false);

		assertEquals(testSheet, dataP.getCurrentSheet());
	}

	@Test
	@Order(1)
	void testAnalizeFile() {
		dataP.analyseFile(new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD));
	}

	@Test
	@Order(2)
	void testAlreadyExists() {
		NormalRule normalRule = new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("NormalRule2", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);
		NormalRule copyNormalRule = new NormalRule("NormalRule1", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		dataP.addToRuleList(normalRule);

		assertFalse(dataP.alreadyExists(normalRule2.getRuleName()));
		assertTrue(dataP.alreadyExists(copyNormalRule.getRuleName()));
	}

	@Test
	@Order(3)
	void testGetRulesList() {
		NormalRule normalRule = new NormalRule("GetRuleNormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("GetRuleNormalRule2", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		dataP.addToRuleList(normalRule);
		dataP.addToRuleList(normalRule2);

		f = dataP.getRulesList();

		assertNotNull(dataP.getRulesList());
		assertEquals(dataP.getRulesList(), f);
	}

	@Test
	@Order(4)
	void testPopup() {
		new Popup(Popup.TEST_CHOOSER, gui);
		new Popup(Popup.LONG_RULE_ADD, gui);
		new Popup(Popup.FEATURE_RULE_ADD, gui);
	}

	@Test
	@Order(5)
	void testPopupConstants() {
		assertEquals(0, Popup.TEST_CHOOSER);
		assertEquals(1, Popup.LONG_RULE_ADD);
		assertEquals(2, Popup.FEATURE_RULE_ADD);
	}
}