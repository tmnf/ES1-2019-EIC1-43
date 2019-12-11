package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import MainLogic.Analyzer;
import MainLogic.DataProcesser;
import Models.DefaultRule;
import Models.NormalRule;
import Utils.FileUtils;

/**
 * @author Filipe
 *
 */
class AnalyzerTest {
	
	

	static Analyzer analyzer;
	static Enums.Test test = null;
	
	static DefaultRule rule;
	static DefaultRule rule2;
	static NormalRule normalRuleLong;
	static NormalRule normalRuleEnvy;
	static int dci, dii, adci, adii;
	private static DataProcesser dp;
	private static Sheet testSheet;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dp = DataProcesser.getInstance();
		testSheet = FileUtils.readFile(new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx").getAbsolutePath());
		dp.initWindow();
		dp.setCurrentSheet(testSheet, false);
		
		
		test = Enums.Test.IPLASMA;
		rule = new DefaultRule(test);
	
		normalRuleLong = new NormalRule("NormalRuleLong", 2f, 1f, false,Enums.Test.LONG_METHOD);
		normalRuleEnvy = new NormalRule("NormalRuleEnvy", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);
		
		analyzer = new Analyzer(rule);
		assertNotNull(analyzer);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#Analyzer(Models.DefaultRule)}.
	 */
	@Test
	void testAnalyzer() {
		analyzer = new Analyzer(rule);
		assertNotNull(analyzer);
	
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#getIsLongList()}.
	 */
	@Test
	void testGetIsLongList() {
		Analyzer analyzerTest = new Analyzer(normalRuleLong);
		assertTrue(analyzerTest.getIsLongList().get(0));
		NormalRule normalRuleLongFalse = new NormalRule("NormalRuleLong", 70f, 70f, true, Enums.Test.LONG_METHOD);
		Analyzer analyzerTestFalse = new Analyzer(normalRuleLongFalse);
		
		assertFalse(analyzerTestFalse.getIsLongList().get(0));
		
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#getIsFeatureEnvyList()}.
	 */
	@Test
	void testGetIsFeatureEnvyList() {
		Analyzer analyzerTest = new Analyzer(normalRuleEnvy);
		assertFalse(analyzerTest.getIsFeatureEnvyList().get(0));
		
		NormalRule normalRuleEnvyFalse = new NormalRule("NormalRuleEnvy", 70f, 70f, false, Enums.Test.IS_FEATURE_ENVY );
		Analyzer analyzerTestFalse = new Analyzer(normalRuleEnvyFalse);
		assertTrue(analyzerTestFalse.getIsFeatureEnvyList().get(0));
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#compareLongMethod()}.
	 */
	@Test
	void testCompareLongMethod() {
		Analyzer analyzerEnvy = new Analyzer(normalRuleEnvy);

		analyzerEnvy.compareFeatureEnvy();
	
	}
	
	@Test
	void testAnalyzerFileIPlasma() {
		Analyzer analyzerIPlasma = new Analyzer(new DefaultRule(Enums.Test.IPLASMA));
		analyzerIPlasma.analyzeFile();
		
	}
	
	@Test
	void testAnalyzeFilePMD() {
		Analyzer analyzerPMD = new Analyzer(new DefaultRule(Enums.Test.PMD));
		analyzerPMD.analyzeFile();
	}
	
	@Test
	void testAnalyzeLong() {
		Analyzer analyzerLong = new Analyzer(normalRuleLong);
		
		analyzerLong.analyzeFile();
	}
	
	@Test
	void testAnalyzeEnvy() {
		Analyzer analyzerEnvy = new Analyzer(normalRuleEnvy);
		
		analyzerEnvy.analyzeFile();
	}
	
	@Test
	void testRun() {
		analyzer.run();
	}
	


}
