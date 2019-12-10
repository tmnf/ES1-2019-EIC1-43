package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Enums.Metric;
import Models.DefaultRule;
import Models.NormalRule;

import java.io.File;

import javax.swing.DefaultComboBoxModel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import MainLogic.DataProcesser;
import Utils.FileUtils;

class DataProcessorTest {
	private static DataProcesser dp;
	private static Sheet testSheet;
	private static DefaultComboBoxModel<DefaultRule> f;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		f = new DefaultComboBoxModel<DefaultRule>();
		dp = DataProcesser.getInstance();
		testSheet = FileUtils
				.readFile(new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx").getAbsolutePath());

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetInstance() {
		DataProcesser dpi = DataProcesser.getInstance();
		assertNotNull(dpi);
	}

	@Test
	void testSetCurrentSheet() {
		dp.setCurrentSheet(testSheet, true);
		assertEquals(testSheet, dp.getCurrentSheet());
	}

//	@Test
//	void testAnalyseFile() {
//		Enums.Test test1 = Enums.Test.IPLASMA;
//		DefaultRule dr1 = new DefaultRule(test1);
//		dp.analyseFile(dr1);
//	}

	@Test
	void testAlreadyExists() {


	
		NormalRule normalRule = new NormalRule("NormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("NormalRule2",2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);
		NormalRule copyNormalRule = new NormalRule("NormalRule1",2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		
		dp.addToRuleList(normalRule);
		
		System.out.println();

		
		assertFalse(dp.alreadyExists(normalRule2.getRuleName() ));
		assertTrue(dp.alreadyExists(copyNormalRule.getRuleName() ));
	}
	
	

//	@Test
//	void testSaveRules() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testLoadRules() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetRulesList() {
		NormalRule normalRule = new NormalRule("GetRuleNormalRule1", 1f, 2f, true, Enums.Test.LONG_METHOD);
		NormalRule normalRule2 = new NormalRule("GetRuleNormalRule2",2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		
		dp.addToRuleList(normalRule);
		dp.addToRuleList(normalRule2);
		
		f= dp.getRulesList();
		
		assertNotNull(dp.getRulesList());
		assertEquals(dp.getRulesList(), f);
	}
}
