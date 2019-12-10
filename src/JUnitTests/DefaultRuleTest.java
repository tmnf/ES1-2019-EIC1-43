package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Models.DefaultRule;



class DefaultRuleTest {
	
	 static Enums.Test test1;
	 static Enums.Test test2;
	 
	 static DefaultRule dr1;

	 static String metricName;
	 

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		test1 = Enums.Test.IPLASMA;
		test2 = Enums.Test.IPLASMA;
		dr1 = new DefaultRule(test1);
		String n = test1.getRealName();
	}

	@Test
	void testDefaultRule() {
		assertEquals(test1,test2);
	
	}

	@Test
	void testGetTest() {
		assertEquals(test1,dr1.getTest());
		
	}

	@Test
	void defaultRuleTestToString() {
		assertEquals(dr1.getTest().getRealName(),dr1.toString());
		
	}
	
	@Test
	void enumTestToString() {
		assertEquals(dr1.getTest().getRealName(),test1.toString());
		
	}

}
