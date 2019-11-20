package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Utils.FileUtils;

public class Analyser extends Thread {

	private static final float LOC_MAX = 80, CYCLO_MAX = 10, ATFD_MAX = 4, LAA_MAX = 0.42f;

	private static final int LONG_METH = 0, FEATURE_METHOD = 1;

	private static final int PMD = 0, iPlasma = 1, GET_LONG_LIST = 2;

	private Sheet sheet;

	private int dci, dii, adci, adii;

	public Analyser(Sheet sheet) {
		this.sheet = sheet;
	}

	public void run() {
		analyseFile();
	}

	public void analyseFile() {
		ArrayList<Boolean> is_long_list, is_feature_list;

		is_long_list = getIsLongList();
		is_feature_list = getIsFeatureEnvyList();

		generateQuality(is_long_list, PMD, is_feature_list);
	}

	private void generateQuality(ArrayList<Boolean> is_long_list, int methodLong, ArrayList<Boolean> is_feature_list) {
		compareLongMethod(is_long_list, methodLong);
		compareFeatureEnvy(is_feature_list);

		showResults();
	}

	private void showResults() {
		// Apresentar resultados. Nao implementar por agora
	}

	/* ===================== METHODS TO IMPLEMENT ===================== */

	/*
	 * Para primeira versão usar como métricas as constantes definidas no inicio da
	 * classe
	 */

	// Returns is_long_method for all methods in file using user rules and metrics
	public ArrayList<Boolean> getIsLongList() {
		return getResultList(LONG_METH);
	}

	// Returns is_feature_envy for all methods in file using user rules and metrics
	public ArrayList<Boolean> getIsFeatureEnvyList() {
		return getResultList(FEATURE_METHOD);
	}

	private ArrayList<Boolean> getResultList(int method) {
		ArrayList<Boolean> res = new ArrayList<Boolean>();

		int[] metrics = getIndexByMethod(method);

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			if (method == LONG_METH)
				res.add((FileUtils.getCellAt(row, metrics[0]).getNumericCellValue()) > LOC_MAX
						&& FileUtils.getCellAt(row, metrics[1]).getNumericCellValue() > CYCLO_MAX);
			else if (method == FEATURE_METHOD)
				res.add(FileUtils.getCellAt(row, metrics[0]).getNumericCellValue() > ATFD_MAX
						&& Double.parseDouble(FileUtils.getCellAt(row, metrics[1]).toString()) < LAA_MAX);
		}

		return res;
	}

	private int[] getIndexByMethod(int method) {
		int[] metrics = new int[2];

		if (method == LONG_METH) {
			metrics[0] = FileUtils.getCellIndexByText("LOC");
			metrics[1] = FileUtils.getCellIndexByText("CYCLO");
		} else if (method == FEATURE_METHOD) {
			metrics[0] = FileUtils.getCellIndexByText("ATFD");
			metrics[1] = FileUtils.getCellIndexByText("LAA");
		}

		return metrics;
	}

	private int getLongListIndexByMethod(int method) {
		int metric = -1;

		if (method == PMD)
			metric = FileUtils.getCellIndexByText("PMD");
		else if (method == iPlasma)
			metric = FileUtils.getCellIndexByText("iPlasma");

		return metric;
	}

	// Compares is_long_method from user with is_long_method, iPlasma and PMD in
	// every method from file
	public void compareLongMethod(ArrayList<Boolean> is_long_list, int method) {
		boolean booleanListComp = false;
		int is_long_list_position = 0;

		String namePresented = "";

		int metric = getLongListIndexByMethod(method);

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			boolean booleanExcelLongLists = FileUtils.getCellAtByText(row, "is_long_method").getBooleanCellValue();

			if (method == PMD) {
				namePresented = "PMD";
				booleanListComp = FileUtils.getCellAt(row, metric).getBooleanCellValue();
			} else if (method == iPlasma) {
				namePresented = "iPlasma";
				booleanListComp = FileUtils.getCellAt(row, metric).getBooleanCellValue();
			} else if (method == GET_LONG_LIST) {
				namePresented = "getLongList()";
				booleanListComp = is_long_list.get(is_long_list_position);
			}

			defectsLongList(booleanListComp, booleanExcelLongLists);
			is_long_list_position++;
		}
		System.out.println();
		System.out.println("==========================");
		System.out.println("DCI (" + namePresented + "): " + dci);
		System.out.println("DII (" + namePresented + "): " + dii);
		System.out.println("ADCI (" + namePresented + "): " + adci);
		System.out.println("ADII (" + namePresented + "): " + adii);
		System.out.println("==========================");

		System.out.println("");

		System.out.println("--------------------------");

		System.out.println("Para o getIsLongList(): " + (dci + dii + adci + adii));
		System.out.println("");

	}

	// Compares is_feature_envy from user with is_feature_envy in every method from
	// file

	private void defectsLongList(boolean booleanToCheck, boolean booleanIsLongExcel) {
		if (booleanToCheck == true && booleanIsLongExcel == true)
			dci++;
		if (booleanToCheck == true && booleanIsLongExcel == false)
			dii++;
		if (booleanToCheck == false && booleanIsLongExcel == false)
			adci++;
		if (booleanToCheck == false && booleanIsLongExcel == true)
			adii++;
	}

	private void compareFeatureEnvy(ArrayList<Boolean> is_feature_list) {

	}

}
