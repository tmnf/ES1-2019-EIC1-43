package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Enums.Metric;
import Enums.Test;
import Utils.FileUtils;

public class Analyser extends Thread {

	private Sheet sheet;
	private Test test;

	private int dci, dii, adci, adii;

	public Analyser(Sheet sheet, Test test) {
		this.sheet = sheet;
		this.test = test;
	}

	public void run() {
		try {
			analyseFile();
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void analyseFile() {
		switch (test) {
		case IPLASMA:
			compareLongMethod();
			break;
		case PMD:
			compareLongMethod();
			break;
		case LONG_METHOD:
			compareLongMethod();
			break;
		case IS_FEATURE_ENVY:
//			compareFeatureEnvy();
			break;
		default:
			break;
		}
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
		System.out.println("DCI (" + test + "): " + dciAux);
		System.out.println("DII (" + test + "): " + diiAux);
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
	public void compareLongMethod() {
		int indexOfValueToCompare, longMethodIndex;

		indexOfValueToCompare = FileUtils.getCellIndexByText(test.getRealName());
		longMethodIndex = FileUtils.getCellIndexByText(Test.LONG_METHOD.getRealName());

		if (test == Test.LONG_METHOD) {
			compareLongWithLong(longMethodIndex);
		} else {
			compareLongWithNormalTest(indexOfValueToCompare, longMethodIndex);
		}

		showResults(test.getRealName());
	}

	private void compareLongWithNormalTest(int indexOfValueToCompare, int longMethodIndex) {
		boolean valueToCompare, isLongValue;

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			isLongValue = FileUtils.getCellAt(row, longMethodIndex).getBooleanCellValue();
			valueToCompare = FileUtils.getCellAt(row, indexOfValueToCompare).getBooleanCellValue();

			defectsLongList(valueToCompare, isLongValue);
		}
	}

	private void compareLongWithLong(int longIndex) {
		boolean valueToCompare, isLongValue;

		ArrayList<Boolean> is_long_ist = getIsLongList();

		int i = 0;
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			isLongValue = FileUtils.getCellAt(row, longIndex).getBooleanCellValue();

			valueToCompare = is_long_ist.get(i);
			i++;

			defectsLongList(valueToCompare, isLongValue);
		}
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
	private void compareIsFeatureEnvy(ArrayList<Boolean> is_feature_list) {
		int i = 0;
		for (Row row : sheet) {
			if (i != 0) {

				/*Usa isto para saltar a primeira linha
				 * if (row.getRowNum() == 0) continue;
				 * 
				 * Apaga o if (i != 0) e usa o i normal para ir buscar à lista.
				 * Usa o metodo defectsLongList() para comparar os dois booleans.
				 */

				if (FileUtils.getCellAtByText(row, "is_feature_envy").getBooleanCellValue() == true
						&& is_feature_list.get(i - 1) == true)
					dci++;
				else if (FileUtils.getCellAtByText(row, "is_feature_envy").getBooleanCellValue() == true
						&& is_feature_list.get(i - 1) == false)
					dii++;
				else if (FileUtils.getCellAtByText(row, "is_feature_envy").getBooleanCellValue() == false
						&& is_feature_list.get(i - 1) == false)
					adci++;
				else
					adii++;

			}
			i++;
		}

	}

}
