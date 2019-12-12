package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Models.NormalRule;

public class NormalRuleTest {

	private static float m1, m2, m3, m4;

	private static NormalRule normalRule, normalRule2, normalRule3, normalRule4;

	private static boolean and, and2, and3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		m1 = 1.0f;
		m2 = 2.0f;
		m3 = 2.0f;
		m4 = 5.0f;

		and = true;
		and2 = false;
		and3 = false;

		normalRule = new NormalRule("rule1", m4, m1, true, Enums.Test.LONG_METHOD);
		normalRule2 = new NormalRule("rule2", m2, m1, false, Enums.Test.PMD);
		normalRule3 = new NormalRule("rule3", m4, m1, true, Enums.Test.IPLASMA);
		normalRule4 = new NormalRule("rule4", m2, m3, true, Enums.Test.IPLASMA);
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
