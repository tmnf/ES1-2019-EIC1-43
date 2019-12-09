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

/**
 * FileUtils represents all auxiliary methods used along the program.
 */

public class FileUtils {

	/**
	 * Opens an excel file given a path
	 * 
	 * @param path path to excel file
	 * @return excel Sheet From File
	 */
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

	/**
	 * Given an excel file converts it to a String
	 * 
	 * @param file excel file in Sheet format
	 * @return excel file converted to String
	 */
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

	/**
	 * Extracts a cell from a row based on the column name
	 * 
	 * @param row    row to extract cell
	 * @param String name of cell's category
	 * @return a cell
	 */
	public static Cell getCellAtByText(Row row, String text) {
		Cell selectedCell = null;

		for (Cell c : DataProcesser.getInstance().getCurrentSheet().getRow(0))
			if (c.getStringCellValue().equals(text)) {
				selectedCell = row.getCell(c.getColumnIndex());
				break;
			}

		return selectedCell;
	}

	/**
	 * Searches column titles to find the desired category and returns it's index
	 * 
	 * @param text name of the column desired
	 * @return index of the column desired
	 */
	public static int getCellIndexByText(String text) {
		int cellIndex = -1;

		for (Cell c : DataProcesser.getInstance().getCurrentSheet().getRow(0))
			if (c.getStringCellValue().equals(text)) {
				cellIndex = c.getColumnIndex();
				break;
			}

		return cellIndex;
	}

	/**
	 * Saves a list of tests to a local binary file
	 * 
	 * @param path  path of the file to save
	 * @param tests list of tests to save
	 */
	public static void saveFile(String path, DefaultComboBoxModel<DefaultRule> tests) {
		ArrayList<DefaultRule> aux = getRulesFromModel(tests);
		try {

			if (!path.contains(".rl"))
				path += ".rl";

			File f = new File(path);

			if (f.exists())
				f.delete();

			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(aux);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a list of tests from a local binary file
	 * 
	 * @param path path of the file to load
	 * @return a list of rules
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<DefaultRule> loadRules(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);

			ArrayList<DefaultRule> ruleList = (ArrayList<DefaultRule>) ois.readObject();

			ois.close();
			return ruleList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds all the rules from an ArrayList to a DefaultComboBoxModel
	 * 
	 * @param rules    rules to add to DefaultComboBoxModel
	 * @param ruleList DefaultComboBoxModel that will receive the rules
	 */
	public static void addRulesToListFromArray(ArrayList<DefaultRule> rules,
			DefaultComboBoxModel<DefaultRule> ruleList) {
		for (DefaultRule x : rules)
			ruleList.addElement(x);
	}

	/**
	 * @param ruleList DefaultComboBoxModel containing a list of rules
	 * @return an ArrayList of rules passed from the ruleList
	 */
	private static ArrayList<DefaultRule> getRulesFromModel(DefaultComboBoxModel<DefaultRule> ruleList) {
		ArrayList<DefaultRule> rules = new ArrayList<>();
		for (int i = 0; i != ruleList.getSize(); i++)
			rules.add(ruleList.getElementAt(i));
		return rules;
	}

}
