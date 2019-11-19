package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Utils.FileUtils;

public class Analyser extends Thread {

	private static final float LOC_MAX = 80, CYCLO_MAX = 10, ATFD_MAX = 4, LAA_MAX = 0.42f;

	private static final int LONG_METH = 0, FEATURE_METHOD = 1;

	private Sheet sheet;

	private DataProcesser dp;

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

		generateQuality(is_long_list, is_feature_list);
	}

	private void generateQuality(ArrayList<Boolean> is_long_list, ArrayList<Boolean> is_feature_list) {
		compareLongMethod(is_long_list);
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
		int i = 0;
		ArrayList<Boolean> res = new ArrayList<Boolean>();

		for (Row row : sheet) {
			if (i != 0) {
				if (method == FEATURE_METHOD) 
					res.add(FileUtils.getCellAtByText(row, "ATFD").getNumericCellValue() > ATFD_MAX
							&& Double.parseDouble(FileUtils.getCellAtByText(row, "LAA").toString()) < LAA_MAX);
	
				else if (method == LONG_METH) 
					res.add((FileUtils.getCellAtByText(row, "LOC").getNumericCellValue()) > LOC_MAX
							&& FileUtils.getCellAtByText(row, "CYCLO").getNumericCellValue() > CYCLO_MAX);
				

			} else {
				i++;
			}
		}
		System.out.println(res);
		return res;
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
