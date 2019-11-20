package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Enums.Metric;
import Enums.Test;
import Utils.FileUtils;

public class Analyser extends Thread {

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

		generateQuality(is_long_list, Test.PMD, is_feature_list); // TESTE PMD SO EM FASE DE TESTES
	}

	private void generateQuality(ArrayList<Boolean> is_long_list, Test methodLong, ArrayList<Boolean> is_feature_list) {
		compareLongMethod(is_long_list, methodLong);
		compareFeatureEnvy(is_feature_list);

//		showResults();
	}

	private void showResults(String test) { // MANTER ESTA IMPLEMENTAÇãO EM TESTE
		int total = dci + dii + adci + adii;

		float dciAux, diiAux, adciAux, adiiAux;
		dciAux = ((float) dci / total) * 100;
		diiAux = ((float) dii / total) * 100;
		adciAux = ((float) adci / total) * 100;
		adiiAux = ((float) adii / total) * 100;

		System.out.println();
		System.out.println("==========================");
		System.out.println("DCI (" + test + "): " + dciAux );
		System.out.println("DII (" + test + "): " + diiAux );
		System.out.println("ADCI (" + test + "): " + adciAux);
		System.out.println("ADII (" + test + "): " + adiiAux);
		System.out.println("---------------------------");

		System.out.println("Total (" + test + "): " + (total));
		System.out.println("==========================");
	}

	/* ===================== METHODS TO IMPLEMENT ===================== */

	/*
	 * Para primeira versão usar como métricas as constantes definidas no inicio da
	 * classe
	 */

	// Returns is_long_method for all methods in file using user rules and metrics
	public ArrayList<Boolean> getIsLongList() {
		return getResultList(Metric.LOC, Metric.CYCLO);
	}

	// Returns is_feature_envy for all methods in file using user rules and metrics
	public ArrayList<Boolean> getIsFeatureEnvyList() {
		return getResultList(Metric.ATFD, Metric.LAA);
	}

	private ArrayList<Boolean> getResultList(Metric m1, Metric m2) {
		ArrayList<Boolean> res = new ArrayList<Boolean>();

		int[] metrics = getIndexByMethods(m1, m2);

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			boolean ver1, ver2;

			ver1 = FileUtils.getCellAt(row, metrics[0]).getNumericCellValue() > m1.getMax();

			if (m2 == Metric.CYCLO)
				ver2 = FileUtils.getCellAt(row, metrics[1]).getNumericCellValue() > m2.getMax();
			else
				ver2 = Double.parseDouble(FileUtils.getCellAt(row, metrics[1]).toString()) < m2.getMax();

			res.add(ver1 && ver2);
		}

		return res;
	}

	private int[] getIndexByMethods(Metric m1, Metric m2) {
		int[] metrics = new int[2];

		metrics[0] = FileUtils.getCellIndexByText(m1.toString());
		metrics[1] = FileUtils.getCellIndexByText(m2.toString());

		return metrics;
	}

	// Compares is_long_method from user with is_long_method, iPlasma and PMD in
	// every method from file
	public void compareLongMethod(ArrayList<Boolean> is_long_list, Test method) {
		boolean valueToCompare, isLongValue;

		int indexOfValueToCompare = FileUtils.getCellIndexByText(method.getRealName());

		int i = 0;
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			isLongValue = FileUtils.getCellAtByText(row, "is_long_method").getBooleanCellValue();

			if (method == Test.LONG_METHOD) {
				valueToCompare = is_long_list.get(i);
				i++;
			} else
				valueToCompare = FileUtils.getCellAt(row, indexOfValueToCompare).getBooleanCellValue();

			defectsLongList(valueToCompare, isLongValue);
		}

		showResults(method.getRealName()); // So para testar
	}

	private void defectsLongList(boolean valueToCheck, boolean isLong) {
		if (valueToCheck && isLong)
			dci++;
		else if (valueToCheck && !isLong)
			dii++;
		else if (!valueToCheck && !isLong)
			adci++;
		else if (!valueToCheck && isLong)
			adii++;
	}

	// Compares is_feature_envy from user with is_feature_envy in every method from
	// file
	private void compareFeatureEnvy(ArrayList<Boolean> is_feature_list) {

	}

}
