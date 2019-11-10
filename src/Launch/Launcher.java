package Launch;

import MainLogic.DataProcesser;

public class Launcher {

	public static void main(String[] args) {
		DataProcesser dp = DataProcesser.getInstance();
		dp.initWindow();
	}
	public static String testFileReading() {
		String res = "";
		try {
			//Workbook workbook = FileUtils.workbook;
			//Sheet datatypeSheet = workbook.getSheetAt(0);
			

			Iterator<Row> iterator = FileUtils.datatypeSheet.iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();

					if (currentCell.getCellType() == CellType.STRING)
						res += currentCell.getStringCellValue() + " : ";
					else if (currentCell.getCellType() == CellType.NUMERIC)
						res += currentCell.getNumericCellValue() + " : ";
					else if (currentCell.getCellType() == CellType.BOOLEAN)
						res += currentCell.getBooleanCellValue() + " : ";
				}
				res += "\n";

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



}
