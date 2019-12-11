package JUnitTests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import JUnitTests.AnalyzerTest;
import JUnitTests.DataProcessorTest;
import JUnitTests.DefaultRuleTest;
import JUnitTests.FileUtilsTest;
import JUnitTests.NormalRuleTest;
import JUnitTests.PopupTester;

@RunWith(JUnitPlatform.class)
@SelectClasses({PopupTester.class, AnalyzerTest.class, DataProcessorTest.class, DefaultRuleTest.class, FileUtilsTest.class, NormalRuleTest.class})
public class AllTests {

}

