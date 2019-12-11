package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Models.NormalRule;

public class NormalRuleTest {

	private static Enums.Test test;
	private static Enums.Test test2;
	private static Enums.Test test3;
	private static float m1;
	private static float m2;
	private static float m3;
	private static float m4;
	private static NormalRule normalRule;
	private static NormalRule normalRule2;
	private static NormalRule normalRule3;
	private static NormalRule normalRule4;
	private static boolean and;
	private static boolean and2;
	private static boolean and3;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		test = Enums.Test.IPLASMA;
		test2 = Enums.Test.PMD;
		test3 = Enums.Test.LONG_METHOD;
		m1 = (float) 1.0;
		m2 = (float) 2.0;
		m3 = (float) 2.0;
		m4 = (float) 5.0;
		and = true;
		and2 = false;
		and3 = false;
		normalRule = new NormalRule("rule1", m4, m1, true, test3);
		normalRule2 = new NormalRule("rule2", m2, m1, false, test2);
		normalRule3 = new NormalRule("rule3", m4, m1, true, test);
		normalRule4 = new NormalRule("rule4", m2, m3, true, test);

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
