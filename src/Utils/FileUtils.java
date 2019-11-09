package Utils;

import java.io.File;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
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
			datatypeSheet = workbook.getSheetAt(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datatypeSheet;
	}

	public static String fileToString(Sheet file) {
		String res = "";
		try {

			Iterator<Row> iterator = file.iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();

					if (currentCell.getCellType() == CellType.STRING)
						res += currentCell.getStringCellValue() + " :: ";
					else if (currentCell.getCellType() == CellType.NUMERIC)
						res += currentCell.getNumericCellValue() + " :: ";
					else if (currentCell.getCellType() == CellType.BOOLEAN)
						res += currentCell.getBooleanCellValue() + " :: ";
				}
				res += "\n";

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// datatypeSheet = workbook.getSheetAt(0);

//
//	public static Cell getCellAt(Row row, int index) {
//
//	}
//
//	public static Cell getCellAtByText(Row row, String text) {
//
//	}
}
