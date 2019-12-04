package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import MainLogic.DataProcesser;
import Models.DefaultRule;

public class FileUtils {

	/* Returns Excel Sheet From File */
	public static Sheet readFile(String path) {
		Sheet datatypeSheet = null;
		try {
			Workbook workbook = WorkbookFactory.create(new File(path));
			datatypeSheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datatypeSheet;
	}

	/* Converts excel file into a String */
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
						res += currentCell.getStringCellValue();
					else if (currentCell.getCellType() == CellType.NUMERIC)
						res += currentCell.getNumericCellValue();
					else if (currentCell.getCellType() == CellType.BOOLEAN)
						res += currentCell.getBooleanCellValue();

					res += ":";
				}
				res += "--\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/* Returns a cell from a row */
	public static Cell getCellAt(Row row, int index) {
		return row.getCell(index);
	}

	/* Returns a cell from a row based on desired category */
	public static Cell getCellAtByText(Row row, String text) {
		Cell selectedCell = null;

		for (Cell c : DataProcesser.getInstance().getCurrentSheet().getRow(0))
			if (c.getStringCellValue().equals(text)) {
				selectedCell = row.getCell(c.getColumnIndex());
				break;
			}

		return selectedCell;
	}

	// Returns cell index
	public static int getCellIndexByText(String text) {
		int cellIndex = -1;

		for (Cell c : DataProcesser.getInstance().getCurrentSheet().getRow(0))
			if (c.getStringCellValue().equals(text)) {
				cellIndex = c.getColumnIndex();
				break;
			}

		return cellIndex;
	}

	public static void saveFile(String path, ArrayList<DefaultRule> tests) {
		try {
			File f = new File(path);
			
			if(f.exists())
				f.delete();
			
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tests);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<DefaultRule> loadRules(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			ArrayList<DefaultRule> ruleList = (ArrayList<DefaultRule>) ois.readObject();
			ois.close();
			return ruleList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addRulesToListFromArray(ArrayList<DefaultRule> rules,
			DefaultComboBoxModel<DefaultRule> ruleList) {
		for (DefaultRule x : rules)
			ruleList.addElement(x);
	}

	public static ArrayList<DefaultRule> getRulesFromModel(DefaultComboBoxModel<DefaultRule> ruleList) {
		ArrayList<DefaultRule> rules = new ArrayList<>();
		for (int i = 0; i != ruleList.getSize(); i++)
			rules.add(ruleList.getElementAt(i));
		return rules;
	}

}
