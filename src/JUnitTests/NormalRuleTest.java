package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Models.NormalRule;

public class NormalRuleTest {

	private static NormalRule normalRule, normalRule2, normalRule3, normalRule4;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		normalRule = new NormalRule("rule1", 5.0f, 1.0f, true, Enums.Test.LONG_METHOD);
		normalRule2 = new NormalRule("rule2", 2.0f, 1.0f, false, Enums.Test.PMD);
		normalRule3 = new NormalRule("rule3", 5.0f, 1.0f, true, Enums.Test.IPLASMA);
		normalRule4 = new NormalRule("rule4", 2.0f, 2.0f, true, Enums.Test.IPLASMA);
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
