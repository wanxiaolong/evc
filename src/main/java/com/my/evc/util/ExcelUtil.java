package com.my.evc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.my.evc.common.ErrorEnum;
import com.my.evc.exception.BusinessException;

/**
 * 本类是操作Excel的工具类。
 * 用于从Excel导入成绩到数据库，也用于从数据库导出成绩到Excel
 */
public class ExcelUtil {
	
	private static final Logger LOGGER = Logger.getLogger(ExcelUtil.class);
	
	/**
	 * 读取成绩表的Excel，第一行为表头，剩下的行数为成绩。结果将包含在一个List&lt;Map&gt;中，
	 * List中的每一个Map表示一个学生一次考试的所有科目的成绩。
	 */
	public static List<Map<String, String>> loadExcel(InputStream stream, String name) 
			throws BusinessException, IOException {
		//把读取到的成绩保存到这个List<Map>中，每一行用一个Map装
		List<Map<String, String>> scoreList = new ArrayList<Map<String, String>>();
		//创建工作簿
		Workbook workbook = createWorkbook(stream, isNewVersionExcel(name));
		//获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		//创建公式计算器
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		//获取行数
		int rows = sheet.getPhysicalNumberOfRows();
		//读取前先检查有效行数。第一行为表头，所以此处的行数应>=2
		if (rows < 2) {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_NO_SCORE);
		}
		
		//处理表头
		Map<Integer, String> headerMap = getHeaderRow(sheet);
		
		//获取表头单元格个数
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();
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
							break;
						case STRING:
							value = cell.getStringCellValue();
							break;
						case FORMULA://Excel中有的单元格是公式
							value = evaluateFormulaCell(evaluator, cell);
							break;
						default:
							break;
					}
				}
				//去掉成绩末尾的".0"（如果有）
				if (value.endsWith(".0")) {
					value = value.substring(0, value.length() - 2);
				}
				
				//把成绩保存到Score对象中
				scoreMap.put(headerMap.get(j), value);
			}
			scoreList.add(scoreMap);
		}
		
		//读取完成后再次检查
		if (scoreList.size() == 0) {
			throw new BusinessException(ErrorEnum.INVALID_EXCEL_NO_SCORE);
		}
		
		LOGGER.info("读取Excel完成！读取到的成绩：" + scoreList.size());
		return scoreList;
	}
	
	/**
	 * @see #loadExcel(InputStream, String)
	 */
	public static List<Map<String, String>> loadExcel(FileItem item) 
			throws BusinessException, IOException {
		return loadExcel(item.getInputStream(), item.getName());
	}
	
	/**
	 * 获取Excel中的第一行。用于校验该Excel中的科目名称和顺序是否和数据库中的“考试”对象一致。
	 * @return Map Key是列的索引（从0开始），Value是列名字
	 * @see #getExcelHeaderRow(InputStream, String)
	 */
	public static Map<Integer, String> getHeaderRow(Sheet sheet) {
		//获取表头单元格个数
		int cells = sheet.getRow(0).getPhysicalNumberOfCells();
		//处理表头，把表头的数据按照索引放在Map中，便于以后使用
		Row headerRow = sheet.getRow(0);
		Map<Integer, String> headerMap = new HashMap<Integer, String>();
		for (int j=0; j<cells; j++) {
			Cell cell = headerRow.getCell(j);
			headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());
		}
		return headerMap;
	}
	
	/**
	 * 获取该流代表的Excel的第一个Sheet。
	 */
	public static Sheet getSheet0(InputStream is, String fileName) throws IOException {
		//创建工作簿
		Workbook workbook = createWorkbook(is, isNewVersionExcel(fileName));
		//获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		return sheet;
	}
	
	/**
	 * 获取一个公式单元格的值。
	 */
	private static String evaluateFormulaCell(FormulaEvaluator evaluator, Cell cell) {
		CellValue cellValue = evaluator.evaluate(cell);
		String value = "";
		if (cellValue.getCellTypeEnum() == CellType.NUMERIC) {
			//如果结果是数字
			value = Double.toString(cellValue.getNumberValue());
		} else if (cellValue.getCellTypeEnum() == CellType.STRING) {
			//如果结果是字符串
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
	private static Workbook createWorkbook(InputStream is, boolean isNewVersionExcel) throws IOException {
		if (isNewVersionExcel) {
			return new XSSFWorkbook(is);
		}
		return new HSSFWorkbook(is);
	}
	
	/**
	 * 判定一个文件是否是新版的Excel，系统将根据不同的Excel后缀进行不同的处理。
	 */
	private static boolean isNewVersionExcel(String fileName) {
		String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
		return ".xlsx".equalsIgnoreCase(fileNameSuffix);
	}
}

