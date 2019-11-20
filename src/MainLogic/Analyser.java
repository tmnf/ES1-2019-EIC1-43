package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Utils.FileUtils;

public class Analyser extends Thread {

	private static final float LOC_MAX = 80, CYCLO_MAX = 10, ATFD_MAX = 4, LAA_MAX = 0.42f;

	private static final int LONG_METH = 0, FEATURE_METHOD = 1;

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
		ArrayList<Boolean> res = new ArrayList<Boolean>();

		int[] metrics = getIndexByMethod(method); // Indice do LOC e CYCLO ou ATFD e LAA, evitar percorrer ficheiro
													// varias vezes.
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
		System.out.println(res); // Apagar depois. So para teste
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

	// Compares is_long_method from user with is_long_method, iPlasma and PMD in
	// every method from file
	private void compareLongMethod(ArrayList<Boolean> is_long_list) {

	}

	// Compares is_feature_envy from user with is_feature_envy in every method from
	// file
	private void compareFeatureEnvy(ArrayList<Boolean> is_feature_list) {

	}

}
