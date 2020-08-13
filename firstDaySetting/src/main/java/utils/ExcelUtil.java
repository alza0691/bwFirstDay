package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public static Workbook getWorkbook(String filePath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		Workbook wb = null;
		
		if(filePath.toUpperCase().endsWith(".XLS")) {
			try {
				wb = new HSSFWorkbook(fis);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else if(filePath.toUpperCase().endsWith(".XLSX")) {
			try {
				wb = new XSSFWorkbook(fis);
			} catch(IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return wb;
	}
	
	@SuppressWarnings("deprecation")
	public static String cellValue(Cell cell) {
		String value = null;
		if(cell == null) {
			value = "";
		} else {
			switch (cell.getCellType()){
			case Cell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula();
			break;
			case Cell.CELL_TYPE_NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					value = "" + objSimpleDateFormat.format(cell.getDateCellValue());
				} else {
					value = "" + String.format("%.0f", new Double(cell.getNumericCellValue()));
				}
				break;
			default:
			}
		}
		return value.trim();
	}
}
