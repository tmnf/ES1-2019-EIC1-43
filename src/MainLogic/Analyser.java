package MainLogic;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.SystemOutLogger;

import Utils.FileUtils;

public class Analyser extends Thread {

	private static final float LOC_MAX = 80, CYCLO_MAX = 10, ATFD_MAX = 4, LAA_MAX = 0.42f;

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
	}

	/* ===================== METHODS TO IMPLEMENT ===================== */

	/*
	 * Para primeira versão usar como métricas as constantes definidas no inicio da
	 * classe
	 */

	// Returns is_long_method for all methods in file using user rules and metrics
	public ArrayList<Boolean> getIsLongList() {
		ArrayList<Boolean> res = new ArrayList<Boolean>();
		int i = 0;

		for (Row row : sheet) {
			
			if(FileUtils.getCellAtByText(row, "LOC").getCellType() == CellType.STRING || FileUtils.getCellAtByText(row, "CYCLO").getCellType() == CellType.STRING) {
				continue;
			}
			i++;
			res.add((FileUtils.getCellAtByText(row, "LOC").getNumericCellValue()) > LOC_MAX
					&& FileUtils.getCellAtByText(row, "CYCLO").getNumericCellValue() > CYCLO_MAX);
		}
		System.out.println("Long: "+ i);
		System.out.println(res);
		return res;
	}

	// Returns is_feature_envy for all methods in file using user rules and metrics
	public ArrayList<Boolean> getIsFeatureEnvyList() {
		ArrayList<Boolean> res = new ArrayList<Boolean>();
		int i = 0;

		for (Row row : sheet) {
			
			if(FileUtils.getCellAtByText(row, "ATFD").getCellType() == CellType.STRING ||
					(FileUtils.getCellAtByText(row, "LAA").getCellType() == CellType.STRING &&  FileUtils.getCellAtByText(row, "LAA").toString() == "LAA")) {
				continue;
			}
			i++;
			res.add(FileUtils.getCellAtByText(row, "ATFD").getNumericCellValue() > ATFD_MAX
					&& Double.parseDouble(FileUtils.getCellAtByText(row, "LAA").toString()) < LAA_MAX);
		}
		System.out.println("Feature Heavy: "+i);
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
