package Launch;

import java.io.File;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;

import MainLogic.DataProcesser;
import Utils.FileUtils;

public class Launcher {

	public static void main(String[] args) {
		DataProcesser dp = DataProcesser.getInstance();
		dp.initWindow();
	}
}
