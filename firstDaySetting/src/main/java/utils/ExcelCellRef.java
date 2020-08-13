package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;

public class ExcelCellRef {
	public static String getName(Cell cell, int cellIndex) {
		int cellNum = 0;
		if(cell != null) {
			cellNum = cell.getColumnIndex();
		} else {
			cellNum = cellIndex;
		}
		return CellReference.convertNumToColString(cellNum);
	}
	
	@SuppressWarnings("deprecation")
	public static String getValue(Cell cell) {
		String value="";
		
		if(cell == null) {
			value="";
		} else {
			if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				value = cell.getNumericCellValue() + "";
			} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
				value = cell.getNumericCellValue() + "";
			} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
				value = cell.getStringCellValue() + "";
			} else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				value = cell.getBooleanCellValue() + "";
			} else if(cell.getCellType() == Cell.CELL_TYPE_ERROR) {
				value = cell.getErrorCellValue() + "";
			} else if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				value = "";
			} else {
				value = cell.getStringCellValue();
			}
		}
		return value;
	}
}
