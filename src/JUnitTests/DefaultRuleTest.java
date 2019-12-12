package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Models.DefaultRule;

public class DefaultRuleTest {

	private static DefaultRule dr1, dr2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dr1 = new DefaultRule(Enums.Test.IPLASMA);
		dr2 = new DefaultRule(Enums.Test.IPLASMA);
	}

	@Test
	void testDefaultRule() {
		assertEquals(dr1.getTest(), dr2.getTest());
	}

	@Test
	void testGetTest() {
		assertEquals(Enums.Test.IPLASMA, dr1.getTest());
	}

	@Test
	void defaultRuleTestToString() {
		assertEquals(dr1.getTest().getRealName(), dr1.toString());
	}

	@Test
	void enumTestToString() {
		assertEquals(dr1.getTest().getRealName(), Enums.Test.IPLASMA.toString());
	}

}
