/**
 * 
 */
package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.poi.sl.usermodel.Sheet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Enums.Metric;
import MainLogic.Analyzer;
import Models.DefaultRule;
import Models.NormalRule;

/**
 * @author Filipe
 *
 */
class AnalyzerTest {

	static Analyzer analyzer;
	static Enums.Test test = null;
	static Metric m1;
	static Metric m2;
	static Metric m3;
	static DefaultRule rule;
	static DefaultRule rule2;
	static NormalRule normalRule;
	static NormalRule normalRule2;
	static int dci, dii, adci, adii;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		test = test.IPLASMA;
		rule = new DefaultRule(test);
		m1 = m1.ATFD;
		m2 = m2.CYCLO;
		m3 = m3.LAA;
		analyzer = new Analyzer(rule);
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#analyseFile()}.
	 */
	@Test
	void testAnalyseFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#getIsLongList()}.
	 */
	@Test
	void testGetIsLongList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#getIsFeatureEnvyList()}.
	 */
	@Test
	void testGetIsFeatureEnvyList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MainLogic.Analyzer#compareLongMethod()}.
	 */
	@Test
	void testCompareLongMethod() {
		fail("Not yet implemented");
	}

}
