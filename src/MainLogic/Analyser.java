package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Enums.Metric;
import Enums.Test;
import GUI.ResultsPopup;
import Models.DefaultRule;
import Models.NormalRule;
import Utils.FileUtils;

public class Analyser extends Thread {

	private Sheet sheet;
	private DefaultRule rule;

	private int dci, dii, adci, adii;

	public Analyser(DefaultRule rule) {
		this.sheet = DataProcesser.getInstance().getCurrentSheet();
		this.rule = rule;
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
		switch (rule.getTest()) {
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
			compareFeatureEnvy();
			break;
		default:
			break;
		}
	}

	private void showResults() {
		String test = rule.toString();

		if (rule.getTest() == Test.LONG_METHOD)
			new ResultsPopup(test, dci, dii, adci, adii, getIsLongList());
		else if (rule.getTest() == Test.IS_FEATURE_ENVY)
			new ResultsPopup(test, dci, dii, adci, adii, getIsFeatureEnvyList());
		else
			new ResultsPopup(test, dci, dii, adci, adii, null);
	}

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

		NormalRule ruleAux = (NormalRule) rule;

		int[] metrics = getIndexByMethods(m1, m2);

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			boolean ver1, ver2;

			ver1 = row.getCell(metrics[0]).getNumericCellValue() > ruleAux.getMetric1();

			if (rule.getTest() == Test.LONG_METHOD)
				ver2 = row.getCell(metrics[1]).getNumericCellValue() > ruleAux.getMetric2();
			else
				ver2 = Double.parseDouble(row.getCell(metrics[1]).toString()) < ruleAux.getMetric2();

			if (ruleAux.getAnd())
				res.add(ver1 && ver2);
			else
				res.add(ver1 || ver2);
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
		int longMethodIndex = FileUtils.getCellIndexByText(Test.LONG_METHOD.getRealName());

		if (rule.getTest() == Test.LONG_METHOD) {
			compareLongWithLong(longMethodIndex);
		} else {
			int indexOfValueToCompare = FileUtils.getCellIndexByText(rule.toString());
			compareLongWithNormalTest(indexOfValueToCompare, longMethodIndex);
		}

		showResults();
	}

	private void compareLongWithNormalTest(int indexOfValueToCompare, int longMethodIndex) {
		boolean valueToCompare, isLongValue;

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			isLongValue = row.getCell(longMethodIndex).getBooleanCellValue();
			valueToCompare = row.getCell(indexOfValueToCompare).getBooleanCellValue();

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

			isLongValue = row.getCell(longIndex).getBooleanCellValue();
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
	private void compareFeatureEnvy() {
		ArrayList<Boolean> is_feature_list = getIsFeatureEnvyList();

		int i = 0;
		int featureEnvyIndex = FileUtils.getCellIndexByText(Test.IS_FEATURE_ENVY.getRealName());

		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			defectsLongList(row.getCell(featureEnvyIndex).getBooleanCellValue(), is_feature_list.get(i));
			i++;
		}

		showResults();
	}
}
