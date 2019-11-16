package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Utils.FileUtils;

public class Analyser extends Thread {

	private static final float LOC_MAX = 80, CYCLO_MAX = 10, ATFD_MAX = 4, LAA_MAX = 0.42f;

	private Sheet sheet;

	private int dci, dii, adci, adii;

	public Analyser(Sheet sheet) {
		this.sheet = sheet;
	}

	public void run() {
		analyseFile();
	}

	private void analyseFile() {
		ArrayList<Boolean> is_long_list, is_feature_list;

		is_long_list = getIsLongList();
		is_feature_list = getIsFeatureEnvyList();

		generateQuality(is_long_list, is_feature_list);
	}

	private void generateQuality(ArrayList<Boolean> is_long_list, ArrayList<Boolean> is_feature_list) {
		compareLongMethod(is_long_list);
		compareFeatureEnvy(is_feature_list);
	}

	/* ===================== METHODS TO IMPLEMENT ===================== */

	/*
	 * Para primeira versão usar como métricas as constantes definidas no inicio da
	 * classe
	 */

	// Returns is_long_method for all methods in file using user rules and metrics
	private ArrayList<Boolean> getIsLongList() {
		ArrayList<Boolean> res = new ArrayList<>();

		return res;
	}

	// Returns is_feature_envy for all methods in file using user rules and metrics
	private ArrayList<Boolean> getIsFeatureEnvyList() {
		ArrayList<Boolean> res = new ArrayList<>();

		return res;
	}

	public ArrayList<Boolean> isLongMethod() {
		ArrayList<Boolean> b = new ArrayList<Boolean>();
		for (Row row : sheet) {
			if ((FileUtils.getCellAtByText(row, "LOC").getNumericCellValue()) > LOC_MAX
					&& FileUtils.getCellAtByText(row, "CYCLO").getNumericCellValue() > CYCLO_MAX)
				b.add(true);
			else
				b.add(false);
		}
		return b;
	}

	public ArrayList<Boolean> isFeatureEnvy() {
		ArrayList<Boolean> b = new ArrayList<Boolean>();
		for (Row row : sheet) {
			if (FileUtils.getCellAtByText(row, "ATFD").getNumericCellValue() > ATFD_MAX
					&& FileUtils.getCellAtByText(row, "LAA").getNumericCellValue() < LAA_MAX) {
				b.add(true);
			} else {
				b.add(false);
			}
		}
		return b;
	}

	// Compares is_long_method from user with is_long_method, iPlasma and PMD in
	// every method from file
	private void compareLongMethod(ArrayList<Boolean> is_long_list) {

	}

	// Compares is_feature_envy from user with is_feature_envy in every method from
	// file
	private void compareFeatureEnvy(ArrayList<Boolean> is_feature_list) {

	}

}
