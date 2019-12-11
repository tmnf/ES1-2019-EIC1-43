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
	private static Popup popup;
	private static MainWindow gui;

	private static DataProcesser dataP;

	private static Sheet testSheet;
	private static DefaultComboBoxModel<DefaultRule> f;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("AQUI");
		f = new DefaultComboBoxModel<DefaultRule>();
		dataP = DataProcesser.getInstance();
		dataP.initWindow();

		testSheet = FileUtils
				.readFile(new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx").getAbsolutePath());

		dataP.setCurrentSheet(testSheet, false);
		assertEquals(testSheet, dataP.getCurrentSheet());

	}

	@Test
	@Order(1)
	void testAnalizeFile() {
		System.out.println("AQUI1");

		dataP.analyseFile(new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD));
	}

	@Test
	@Order(2)
	void testAlreadyExists() {		System.out.println("AQUI2");

		NormalRule normalRule = new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("NormalRule2", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);
		NormalRule copyNormalRule = new NormalRule("NormalRule1", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		dataP.addToRuleList(normalRule);

		System.out.println();

		assertFalse(dataP.alreadyExists(normalRule2.getRuleName()));
		assertTrue(dataP.alreadyExists(copyNormalRule.getRuleName()));
	}

	@Test
	@Order(3)
	void testGetRulesList() {
		System.out.println("AQUI3");

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
		System.out.println("AQUI4");

		popup = new Popup(Popup.TEST_CHOOSER, gui);
		assertEquals(0, Popup.TEST_CHOOSER);
		assertEquals("Escolher um teste", popup.getTitle());
	}

	@Test
	@Order(5)
	void testPopupLongRuleAdd() {
		System.out.println("AQUI5");

		popup = new Popup(Popup.LONG_RULE_ADD, gui);
		assertEquals(1, Popup.LONG_RULE_ADD);
	}

	@Test
	@Order(6)
	void testPopupFeatureRuleAdd() {
		System.out.println("AQUI6");

		popup = new Popup(Popup.FEATURE_RULE_ADD, gui);
		assertEquals(2, Popup.FEATURE_RULE_ADD);
	}
}