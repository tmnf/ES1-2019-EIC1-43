package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import MainLogic.DataProcesser;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import Utils.FileUtils;

class FileUtilsTest {
	private static String filePath;
	private static Sheet datatypeSheet;
	private static Sheet testSheet;
	private static String fNull = null;
	private static DataProcesser dp;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dp = DataProcesser.getInstance();
		dp.initWindow();
		File f = new File(System.getProperty("user.dir") + "/files/Long-Method.xlsx");
		filePath = f.getAbsolutePath();

		Workbook workbook = WorkbookFactory.create(new File(filePath));
		datatypeSheet = workbook.getSheetAt(0);
//		
		testSheet = FileUtils.readFile(filePath);
		dp.setCurrentSheet(testSheet, false);

	}

	@Test
	void testReadFile(){

		assertNotNull(testSheet);
	}
/*
	@Test
	public void testExceptionReadFile() throws Exception {
		assertNull(FileUtils.readFile(fNull));
		
	}
	*/
//	@Test
//	public void testExceptionReadFile() throws Exception{
//		assertThrows(Exception.class, () -> {
//			assertNull(FileUtils.readFile(fNull));
//			Workbook workbook = WorkbookFactory.create(new File(fNull));
//		});
//	}
	

	@Test
	void testFileToString() {
		assertNotNull(FileUtils.fileToString(datatypeSheet));
	}
	/*
	@Test
	void testExceptionFileToString() {
		Sheet exceptionSheet = null;
		assertNotNull(FileUtils.fileToString(exceptionSheet));
	}
*/
	
	
	
	
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
		//assertNotEquals(stringCellTest.toString(), FileUtils.getCellAtByText(exampleRow, "method").toString());
		
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

//  É capaz de não ser necessário fazer
//	@Test
//	void testSaveFile() {
//		fail("Not yet implemented");
//	}
//
	
//	@Test
//	void testLoadRules() {
//		FileUtils.loadRules("");
//	}
//	
//
//	@Test
//	void testAddRulesToListFromArray() {
//		fail("Not yet implemented");
//	}

}
