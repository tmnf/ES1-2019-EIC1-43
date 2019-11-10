package Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.SystemOutLogger;

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
					System.out.println(currentCell);
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

	public static Cell getCellAt(Row row, int index) throws IOException {
		datatypeSheet = workbook.getSheetAt(0);
		Cell cell = null;
		cell = row.getCell(index);
		for (int i = 0; i < datatypeSheet.getLastRowNum(); i++) {
			if (cell != null) {
				System.out.println(cell + " - ");
				// cell = row.getCell(i);
			}
		}
		return cell;

	}

	public boolean CellToCompare(Cell c) {
		datatypeSheet = workbook.getSheetAt(0);
		for (Row r : datatypeSheet) {
			for (int i = 0; i < datatypeSheet.getLastRowNum(); i++) {
				if (r.getCell(i) != null && r.getCell(i).equals(c))
					return true;
			}
		}
		return false;
	}

	
	public static Cell getCellAtByText(Row row, String text) {
		Cell selectedCell = null;
		int index = -1;
		datatypeSheet = workbook.getSheetAt(0);
		for(Cell c: datatypeSheet.getRow(0)) {
				if(c.getStringCellValue().equals(text))
					index = c.getColumnIndex();
		}
		
		if(index != -1)
			selectedCell = row.getCell(index);
				
		return selectedCell;
		
	}
}
