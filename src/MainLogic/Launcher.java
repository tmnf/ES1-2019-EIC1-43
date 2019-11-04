package MainLogic;

import java.io.File;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Launcher {

	public static void main(String[] args) {
		testFileReading();
	}

	// METHOD TO DELETE
	public static void testFileReading() {
		try {
			Workbook workbook = WorkbookFactory.create(new File("files/Long-Method.xlsx"));
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();

					if (currentCell.getCellType() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + " : ");
					} else if (currentCell.getCellType() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + " : ");
					}

				}
				System.out.println("\n=================================");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
