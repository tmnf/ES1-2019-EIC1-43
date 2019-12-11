package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import org.junit.jupiter.api.Test;
import MainLogic.DataProcesser;
import Models.DefaultRule;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeAll;

import Utils.FileUtils;

public class FileUtilsTest {
	private static String filePath;
	private static Sheet datatypeSheet;
	private static Sheet testSheet;
	private static String fNull = null;
	private static DataProcesser dp;
	private static DefaultComboBoxModel<DefaultRule> rulesList;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dp = DataProcesser.getInstance();
		dp.initWindow();
		File f = new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx");
		filePath = f.getAbsolutePath();

		Workbook workbook = WorkbookFactory.create(new File(filePath));
		datatypeSheet = workbook.getSheetAt(0);
		testSheet = FileUtils.readFile(filePath);
		dp.setCurrentSheet(testSheet, false);

	}

	@Test
	void testFileToString() {
		assertNotNull(FileUtils.fileToString(datatypeSheet));
	}

	@Test
	void testGetCellAtByText() {
		String stringCellTest = "GrammerException(int,String)";
		Row exampleRow = datatypeSheet.getRow(8);

		assertEquals(stringCellTest.toString(), FileUtils.getCellAtByText(exampleRow, "method").toString());

	}

	@Test
	void testFailedGetCellAtByText() {
		String exampleWrongString = "ExampleLine(int,String)";
		Row exampleRow = datatypeSheet.getRow(8);

		assertNull(FileUtils.getCellAtByText(exampleRow, exampleWrongString));

	}

	@Test
	void testGetCellIndexByText() {
		int indexOfLoc = 4;
		assertEquals(indexOfLoc, FileUtils.getCellIndexByText("LOC"));
	}

	@Test
	void testFailedGetCellIndexByText() {
		int indexOfLoc = -1;
		assertEquals(indexOfLoc, FileUtils.getCellIndexByText("NOTINTHEEXCEL"));
	}

	@Test

	void testSaveFile() {
		rulesList = dp.getRulesList();
		String pathToRuleFile = new File(System.getProperty("user.dir") + "/files/RegraLongExample.rl")
				.getAbsolutePath();

		FileUtils.saveFile(pathToRuleFile, rulesList);
	}

	@Test
	void testLoadRules() {
		rulesList = dp.getRulesList();
		assertNotNull(testSheet);
		String pathToRuleFileV2 = new File(System.getProperty("user.dir") + "/files/RegraLongExample.rl")
				.getAbsolutePath();

		FileUtils.loadRules(pathToRuleFileV2);
		FileUtils.saveFile(pathToRuleFileV2, rulesList);

		assertNotNull(FileUtils.loadRules(pathToRuleFileV2));
	}

	@Test
	void testLoadFileReturnNull() {
		rulesList = dp.getRulesList();

		assertNull(FileUtils.loadRules(fNull));
	}

	@Test
	void testAddRulesToListFromArray() {
		rulesList = dp.getRulesList();
		int ruleListSize = rulesList.getSize();
		String pathToRuleFile = new File(System.getProperty("user.dir") + "/files/RegraLongExample.rl")
				.getAbsolutePath();

		FileUtils.addRulesToListFromArray(FileUtils.loadRules(pathToRuleFile), rulesList);
		assertEquals(ruleListSize + FileUtils.loadRules(pathToRuleFile).size(), rulesList.getSize());
	}

}
