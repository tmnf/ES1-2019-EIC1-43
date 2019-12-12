package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import MainLogic.Analyzer;
import MainLogic.DataProcesser;
import Models.DefaultRule;
import Models.NormalRule;
import Utils.FileUtils;

@TestMethodOrder(OrderAnnotation.class)
public class AnalyzerTest {

	private static Analyzer analyzer;

	private static DefaultRule rule;

	private static NormalRule normalRuleLong, normalRuleEnvy;

	private static DataProcesser dp;

	private static Sheet testSheet;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dp = DataProcesser.getInstance();

		testSheet = FileUtils.readFile(new File("./files/Long-Method.xlsx").getAbsolutePath());

		dp.initWindow();
		dp.setCurrentSheet(testSheet, true);

		rule = new DefaultRule(Enums.Test.IPLASMA);

		normalRuleLong = new NormalRule("NormalRuleLong", 2f, 1f, false, Enums.Test.LONG_METHOD);
		normalRuleEnvy = new NormalRule("NormalRuleEnvy", 2f, 1f, false, Enums.Test.IS_FEATURE_ENVY);

		analyzer = new Analyzer(rule);
		assertNotNull(analyzer);
	}

	@Test
	@Order(1)
	void testAnalyzer() {
		analyzer = new Analyzer(rule);
		assertNotNull(analyzer);
	}

	@Test
	@Order(2)
	void testAnalyzerFileIPlasma() {
		Analyzer analyzerIPlasma = new Analyzer(new DefaultRule(Enums.Test.IPLASMA));
		analyzerIPlasma.start();
	}

	@Test
	@Order(3)
	void testAnalyzeFilePMD() {
		Analyzer analyzerPMD = new Analyzer(new DefaultRule(Enums.Test.PMD));
		analyzerPMD.start();
	}

	@Test
	@Order(4)
	void testAnalyzeLong() {
		Analyzer analyzerLong = new Analyzer(normalRuleLong);

		analyzerLong.start();

		NormalRule normalRuleLongFalse = new NormalRule("NormalRuleLong", 70f, 70f, true, Enums.Test.LONG_METHOD);
		analyzerLong = new Analyzer(normalRuleLongFalse);
		analyzerLong.start();
	}

	@Test
	@Order(5)
	void testAnalyzeEnvy() {
		Analyzer analyzerEnvy = new Analyzer(normalRuleEnvy);

		analyzerEnvy.start();
	}
}
