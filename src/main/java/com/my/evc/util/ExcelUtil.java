package com.my.evc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 本类是操作Excel的工具类。
 * 用于从Excel导入成绩到数据库，也用于从数据库导出成绩到Excel
 */
public class ExcelUtil {
	public static void main(String[] v) throws Exception {
		String fileName = "期末考试成绩.xlsx";
		String path = "C:\\Users\\万小龙\\Desktop";
		FileInputStream fis = new FileInputStream(path + "\\" + fileName);
		
		List<Map<String, String>> scoreList = readExcel(fis, fileName);
		System.out.println(scoreList.size());
	}
	
	/**
	 * 读取成绩表的Excel，第一行为表头，剩下的行数为成绩。结果将包含在一个List&lt;Map&gt;中，
	 * List中的每一个Map表示一个学生一次考试的所有科目的成绩。
	 */
	public static List<Map<String, String>> readExcel(InputStream is, String fileName) throws IOException {
		//创建工作簿
		Workbook workbook = createWorkbook(is, fileName);
		//创建公式计算器
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		//获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		//获取行数
		int rows = sheet.getPhysicalNumberOfRows();
		if (rows == 0) {
			throw new IOException("Illegal Excel file. No records found.");
		}
		//获取表头单元格个数
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();
		
		//处理表头，把表头的数据按照索引放在Map中，便于以后使用
		Row headerRow = sheet.getRow(0);
		Map<Integer, String> headerMap = new HashMap<Integer, String>();
		for (int j=0; j<cells; j++) {
			Cell cell = headerRow.getCell(j);
			headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());
		}
		
		//把读取到的成绩保存到这个List<Map>中，每一行用一个Map装
		List<Map<String, String>> scoreList = new ArrayList<Map<String, String>>();
		//成绩的行数从1开始
		for (int i=1; i<rows; i++) {
			Map<String, String> scoreMap = new HashMap<String, String>();
			Row row = sheet.getRow(i);
			for (int j=0; j<cells; j++) {
				String value = "";
				//如果单元格没有值，则这里的getCell(j)会返回null
				Cell cell = row.getCell(j);
				if (cell != null) {
					CellType cellType = cell.getCellTypeEnum();
					//根据不同的单元格类型，获取值
					switch (cellType) {
						case NUMERIC:
							value = Double.toString(cell.getNumericCellValue());
							if (value.endsWith(".0")) {
								value = value.substring(0, value.length() - 2);
							}
							break;
						case STRING:
							value = cell.getStringCellValue();
							break;
						case FORMULA:
							value = evaluateFormulaCell(evaluator, cell);
							break;
						default:
							break;
					}
				}
				
				//把成绩保存到Score对象中
				scoreMap.put(headerMap.get(j), value);
			}
			scoreList.add(scoreMap);
		}
		System.out.println("读取完成！");
		return scoreList;
	}
	
	private static String evaluateFormulaCell(FormulaEvaluator evaluator, Cell cell) {
		CellValue cellValue = evaluator.evaluate(cell);
		String value = "";
		if (cellValue.getCellTypeEnum() == CellType.NUMERIC) {
			value = Double.toString(cellValue.getNumberValue());
		} else if (cellValue.getCellTypeEnum() == CellType.STRING) {
			value = cellValue.getStringValue();
		} else {
			//不支持其他的结果类型
		}
		return value;
	}
	
	/** 
	 * 根据excel文件后缀名，生成不同的workbook。
	 * <ul>
	 * <li>.xls  -> from Excel versions 97/2000/XP/2003</li>
	 * <li>.xlsx -> from Excel new versions</li>
	 * </ul>
	 */
	private static Workbook createWorkbook(InputStream is, String fileName) throws IOException{
		if (fileName.endsWith(".xls")) {
			return new HSSFWorkbook(is);
		}else if (fileName.endsWith(".xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}
}
