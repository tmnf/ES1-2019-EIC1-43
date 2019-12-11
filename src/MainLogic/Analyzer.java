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

/**
 * Analyzer represents the main functionality of this program and it's
 * responsible for analyzing a full excel sheet according with user implemented
 * rules or default ones.
 * 
 */
public class Analyzer extends Thread {

	/**
	 * Excel sheet to analyze
	 */
	private Sheet sheet;

	/**
	 * Rule containing the metrics and operators used in file analysis
	 */
	private DefaultRule rule;

	/**
	 * Final program quality indicators
	 */
	private int dci, dii, adci, adii;

	/**
	 * Analyzer's Constructor. Creates a new Thread responsible of analyzing an
	 * entire file based on a given rule
	 * 
	 * @param rule sets the metrics and operators used in the created analisis
	 */
	public Analyzer(DefaultRule rule) {
		this.sheet = DataProcesser.getInstance().getCurrentSheet();
		this.rule = rule;
	}

	/**
	 * Overrides Thread method run. Calls analyzeFile and terminates thread
	 * afterwards
	 */
	public void run() {
		try {
			analyzeFile();
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Based on the given rule, decides which test algorithm should be used
	 */
	public void analyzeFile() {
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

	/**
	 * Creates a new popup window with the generated results in the end
	 */
	private void showResults() {
		String test = rule.toString();

		if (rule.getTest() == Test.LONG_METHOD)
			new ResultsPopup(test, dci, dii, adci, adii, getIsLongList());
		else if (rule.getTest() == Test.IS_FEATURE_ENVY)
			new ResultsPopup(test, dci, dii, adci, adii, getIsFeatureEnvyList());
		else
			new ResultsPopup(test, dci, dii, adci, adii, null);
	}

	/**
	 * Generates a boolean list containing if each method passed the isLong test
	 * being applied or not
	 * 
	 * @return list of boolean (isLong) results
	 */
	public ArrayList<Boolean> getIsLongList() {
		return getResultList(Metric.LOC, Metric.CYCLO);
	}

	/**
	 * Generates a boolean list containing if each method passed the isFeatureEnvy
	 * test being applied or not
	 * 
	 * @return list of boolean (isFeatureEnvy) results
	 */
	public ArrayList<Boolean> getIsFeatureEnvyList() {
		return getResultList(Metric.ATFD, Metric.LAA);
	}

	/**
	 * General method used by getIsLongList() and getIsFeatureEnvyList(), generates
	 * the wanted results list based on the given metrics
	 * 
	 * @param m1 first metric given to perform a verification (LOC or ATFD)
	 * @param m2 first metric given to perform a verification (CYCLO or LAA)
	 * @return list of boolean results
	 */
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

	/**
	 * Gets column index of two given metrics
	 * 
	 * @param m1 first metric to be checked
	 * @param m2 second metric to be checked
	 * @return Integer array containing m1's column index [0] and m2's column index
	 *         [1]
	 */
	private int[] getIndexByMethods(Metric m1, Metric m2) {
		int[] metrics = new int[2];

		metrics[0] = FileUtils.getCellIndexByText(m1.toString());
		metrics[1] = FileUtils.getCellIndexByText(m2.toString());

		return metrics;
	}

	/**
	 * Compares is_long_method from file with iPlasma and PMD or is_long_method from
	 * user rule with file's is_long_method
	 */
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

	/**
	 * Auxiliary method of compareLongMethod, compares is_long_method from file with
	 * IPLASMA or PMD
	 */
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

	/**
	 * Auxiliary method of compareLongMethod, compares is_long_method from user rule
	 * with is_long_method from file
	 */
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

	/**
	 * Checks and sets quality indicators of the rule being analyzed
	 */
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

	/**
	 * Compares is_feature_envy from user inputed rule with file's is_feature_envy
	 */
	public void compareFeatureEnvy() {
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
