package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import MainLogic.Analyzer;
import MainLogic.DataProcesser;
import Models.DefaultRule;
import Models.NormalRule;
import Utils.FileUtils;

public class AnalyzerTest {

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
		testSheet = FileUtils
				.readFile(new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx").getAbsolutePath());
		dp.initWindow();
		dp.setCurrentSheet(testSheet, true);

		test = Enums.Test.IPLASMA;
		rule = new DefaultRule(test);

		normalRuleLong = new NormalRule("NormalRuleLong", 2f, 1f, false, Enums.Test.LONG_METHOD);
		normalRuleEnvy = new NormalRule("NormalRuleEnvy", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		analyzer = new Analyzer(rule);
		assertNotNull(analyzer);
	}

	@Test
	void testAnalyzer() {
		analyzer = new Analyzer(rule);
		assertNotNull(analyzer);

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

		NormalRule normalRuleLongFalse = new NormalRule("NormalRuleLong", 70f, 70f, true, Enums.Test.LONG_METHOD);
		analyzerLong = new Analyzer(normalRuleLongFalse);
		analyzerLong.analyzeFile();
	}

	@Test
	void testAnalyzeEnvy() {
		Analyzer analyzerEnvy = new Analyzer(normalRuleEnvy);

		analyzerEnvy.analyzeFile();
	}
}
