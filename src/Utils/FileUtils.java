package Utils;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import GUI.MainWindow;

public class FileUtils {
	public static Workbook workbook;
	public static Sheet datatypeSheet;

	// Descomentar o metodo a implementar

	public static Sheet readFile(String path) {
		try {

			workbook = WorkbookFactory.create(new File(path));
			//datatypeSheet = workbook.getSheetAt(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public static String fileToString(Sheet file) {
	//datatypeSheet = workbook.getSheetAt(0);
//	}
//
//	public static Cell getCellAt(Row row, int index) {
//
//	}
//
//	public static Cell getCellAtByText(Row row, String text) {
//
//	}
}
