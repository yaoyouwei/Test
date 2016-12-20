package org.yyw.code.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 
 * <pre>
 * Excel操作工具类。
 * </pre>
 * 
 * @author snake huang snake.huang@midea.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ExcelUtils {

	/**
	 * 获取Workbook
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Workbook getWorkBook(File file) throws FileNotFoundException, IOException {
		try {
			return new HSSFWorkbook(new FileInputStream(file));
		} catch (Exception e) {
			return new XSSFWorkbook(new FileInputStream(file));
		}
	}

	/**
	 * 获取Workbook
	 * 
	 * @param input
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Workbook getWorkBook(InputStream input) throws FileNotFoundException, IOException {
		try {
			return new HSSFWorkbook(input);
		} catch (Exception e) {
			return new XSSFWorkbook(input);
		}
	}

	/**
	 * 将数组中的值作为一行或一列写出
	 * 
	 * @param sheet
	 *            写入的sheet
	 * @param fillRow
	 *            是写入行还是写入列
	 * @param startRowNum
	 *            开始行号
	 * @param startColumnNum
	 *            开始列号
	 * @param contents
	 *            写入的内容数组
	 * @throws Exception
	 */
	public static void writeArrayToExcel(Sheet sheet, boolean fillRow, int startRowNum, int startColumnNum,
			Object[] contents) throws Exception {
		for (int i = 0, length = contents.length; i < length; i++) {
			int rowNum;
			int columnNum;
			// 以行为单位写入
			if (fillRow) {
				rowNum = startRowNum;
				columnNum = startColumnNum + i;
			}
			// 　以列为单位写入
			else {
				rowNum = startRowNum + i;
				columnNum = startColumnNum;
			}
			writeToCell(sheet, rowNum, columnNum, convertString(contents[i]));
		}
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheet
	 *            sheet
	 * @param columnRowNum
	 *            单元格的位置
	 * @param value
	 *            写入的值
	 * @throws Exception
	 */
	public static void writeToCell(Sheet sheet, String columnRowNum, Object value) throws Exception {
		writeToCell(sheet,columnRowNum,value,null);
	}
	
	/**
	 * 向一个单元格写入值
	 * @param sheet
	 * @param columnRowNum
	 * @param value
	 * @param cellStyle
	 * @throws Exception
	 */
	public static void writeToCell(Sheet sheet, String columnRowNum, Object value,CellStyle cellStyle) throws Exception {
		int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
		int rowNum = rowNumColumnNum[0];
		int columnNum = rowNumColumnNum[1];
		writeToCell(sheet, rowNum, columnNum, value,cellStyle);
	}

	/**
	 * 将单元格的行列位置转换为行号和列号
	 * 
	 * @param columnRowNum
	 *            行列位置
	 * @return 长度为2的数组，第1位为行号，第2位为列号
	 */
	private static int[] convertToRowNumColumnNum(String columnRowNum) {
		columnRowNum = columnRowNum.toUpperCase();
		char[] chars = columnRowNum.toCharArray();
		int rowNum = 0;
		int columnNum = 0;
		for (char c : chars) {
			if ((c >= 'A' && c <= 'Z')) {
				columnNum = columnNum * 26 + ((int) c - 64);
			} else {
				rowNum = rowNum * 10 + new Integer(c + "");
			}
		}
		return new int[] { rowNum - 1, columnNum - 1 };
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheet
	 *            sheet
	 * @param rowNum
	 *            行号
	 * @param columnNum
	 *            列号
	 * @param value
	 *            写入的值
	 * @throws Exception
	 */
	public static void writeToCell(Sheet sheet, int rowNum, int columnNum, Object value) throws Exception {
		writeToCell(sheet,rowNum,columnNum,value,null);
	}
	
	/**
	 * 向一个单元格写入值
	 * @param sheet
	 * @param rowNum
	 * @param columnNum
	 * @param value
	 * @param cellStyle
	 * @throws Exception
	 */
	public static void writeToCell(Sheet sheet, int rowNum, int columnNum, Object value,CellStyle cellStyle) throws Exception {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.getCell(columnNum);
		if (cell == null) {
			cell = row.createCell(columnNum);
		}
		cell.setCellValue(convertString(value));
		
		if(cellStyle != null){
			cell.setCellStyle(cellStyle);
		}
	}
	
	

	/**
	 * 读取一个单元格的值
	 * 
	 * @param sheet
	 * @param rowNum
	 * @param columnNum
	 * @return
	 */
	public static Object readCellValue(Sheet sheet, int rowNum, int columnNum) {
		Row row = sheet.getRow(rowNum);
		if (row != null) {
			Cell cell = row.getCell(columnNum);
			if (cell != null) {
				return getCellValue(cell);
			}
		}
		return "";
	}

	/**
	 * 读取一个单元格的值
	 * 
	 * @param sheet
	 *            sheet
	 * @param columnRowNum
	 *            单元格的位置
	 * @return
	 * @throws Exception
	 */
	public static Object readCellValue(Sheet sheet, String columnRowNum) throws Exception {
		int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
		int rowNum = rowNumColumnNum[0];
		int columnNum = rowNumColumnNum[1];
		Row row = sheet.getRow(rowNum);
		if (row != null) {
			Cell cell = row.getCell(columnNum);
			if (cell != null) {
				return getCellValue(cell);
			}
		}
		return "";
	}

	/**
	 * 获取单元格中的值
	 * 
	 * @param cell
	 *            单元格
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
		int type = cell.getCellType();
		DecimalFormat df = new DecimalFormat("#");
		
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			return (Object) warpString(cell.getStringCellValue());
		case Cell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				return HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
			}else{
//				return df.format(cell.getNumericCellValue());
				Double value = cell.getNumericCellValue();
				if(Double.parseDouble(df.format(value)) == value.doubleValue()){
					return df.format(value);
				}else{
					return value;
				}
//				return (Object) (value.intValue());
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return (Object) cell.getBooleanCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return (Object) warpString(cell.getArrayFormulaRange().formatAsString());
		case Cell.CELL_TYPE_BLANK:
			return (Object) "";
		default:
			return "";
		}
	}

	/**
	 * 过滤空格
	 * 
	 * @param str
	 * @return
	 */
	private static String warpString(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return str;
		}
	}

	/**
	 * 插入一行并参照与上一行相同的格式
	 * 
	 * @param sheet
	 *            sheet
	 * @param rowNum
	 *            插入行的位置
	 * @throws Exception
	 */
	public static void insertRowWithFormat(Sheet sheet, int rowNum) throws Exception {
		sheet.shiftRows(rowNum, rowNum + 1, 1);
		Row newRow = sheet.createRow(rowNum);
		Row oldRow = sheet.getRow(rowNum - 1);
		for (int i = oldRow.getFirstCellNum(); i < oldRow.getLastCellNum(); i++) {
			Cell oldCell = oldRow.getCell(i);
			if (oldCell != null) {
				CellStyle cellStyle = oldCell.getCellStyle();
				newRow.createCell(i).setCellStyle(cellStyle);
			}
		}
	}

	/**
	 * 写入Excel文件并关闭
	 
	public static void writeAndClose(HSSFWorkbook workbook, File excelFile) {
		if (workbook != null) {
			try {
				FileOutputStream fileOutStream = new FileOutputStream(excelFile);
				workbook.write(fileOutStream);
				if (fileOutStream != null) {
					fileOutStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

	
	/**
	 * 写入Excel文件并关闭
	 */
	public static void writeAndClose(Workbook workbook, File excelFile) {
		if (workbook != null) {
			try {
				FileOutputStream fileOutStream = new FileOutputStream(excelFile);
				workbook.write(fileOutStream);
				if (fileOutStream != null) {
					fileOutStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String convertString(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	/**
	 * 自动调整列宽
	 */
	public static void adjustColumnSize(Sheet sheet, int columnSize) {
		for (int i = 0; i <= columnSize; i++) {
			sheet.autoSizeColumn(i, true);
		}
	}

	/**
	 * 边框
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle createStyleCell(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		// 设置一个单元格边框颜色
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 设置一个单元格边框颜色
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return cellStyle;
	}

}
