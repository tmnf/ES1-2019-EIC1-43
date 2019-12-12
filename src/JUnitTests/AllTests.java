package JUnitTests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ AnalyzerTest.class, 
				 DataProcessorTest.class, 
				 DefaultRuleTest.class, 
				 FileUtilsTest.class,
				 NormalRuleTest.class })

public class AllTests {

}
