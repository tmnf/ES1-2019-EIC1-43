package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Enums.Metric;
import Models.NormalRule;

class NormalRuleTest {

	static Enums.Test test;
	static Enums.Test test2;
	static Enums.Test test3;
	static Enums.Test test4;
	static float m1;
	static float m2;
	static float m3;
	static float m4;
	static NormalRule normalRule;
	static NormalRule normalRule2;
	static NormalRule normalRule3;
	static NormalRule normalRule4;
	static boolean and;
	static boolean and2;
	static boolean and3;
	static boolean and4;
	static String ruleName1;
	static String ruleName2;
	static String ruleName3;
	static String ruleName4;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		test = test.IPLASMA;
		test2 = test.IPLASMA;
		test3 = test3.LONG_METHOD;
		test4 = test4.IS_FEATURE_ENVY;
		m1 = (float) 1.0;
		m2 = (float) 2.0;
		m3 = (float) 2.0;
		m4 = (float) 5.0;
		and = true;
		and2 = false;
		and3 = false;
		and4 = true;
		normalRule = new NormalRule("rule1", m4, m1, true, test.LONG_METHOD);
		normalRule2 = new NormalRule("rule2", m2, m1, false, test.PMD);
		normalRule3 = new NormalRule("rule3", m4, m1, true, test.IPLASMA);
		normalRule4 = new NormalRule("rule4", m2, m3, true, test.IPLASMA);

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
	void testGetRuleName() {
		assertNotNull(normalRule3.getRuleName());
		assertNotNull(normalRule2.getRuleName());
		assertNotNull(normalRule4.getRuleName());
		assertNotNull(normalRule.getRuleName());
	}

	@Test
	void testGetAnd() {
		assertNotEquals(normalRule.getAnd(), normalRule2.getAnd());
		assertEquals(normalRule3.getAnd(), normalRule4.getAnd());
		assertSame(and2, and3);
		assertNotSame(and, and3);
	}

	@Test
	void testGetMetric1() {
		assertEquals(normalRule.getMetric1(), normalRule3.getMetric1());
		assertEquals(normalRule2.getMetric1(), normalRule4.getMetric1());
		assertNotEquals(normalRule4.getMetric1(), normalRule3.getMetric1());
	}

	@Test
	void testGetMetric2() {
		assertEquals(normalRule.getMetric2(), normalRule3.getMetric2());
		assertNotEquals(normalRule2.getMetric2(), normalRule4.getMetric2());
		assertNotEquals(normalRule4.getMetric2(), normalRule3.getMetric2());

	}

	@Test
	void testToString() {
		assertNotEquals(normalRule.toString(), normalRule2.toString());
	}

}
