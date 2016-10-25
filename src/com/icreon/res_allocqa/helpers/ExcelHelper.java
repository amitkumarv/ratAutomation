package com.icreon.res_allocqa.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ExcelHelper {
	
	HashMap<String, String> hm = new HashMap<String, String>();
	HashMap<String, String> testData = new HashMap<String, String>();
	
	
	public HashMap<String, String> getTestCasestoRun(String testCaseXls) throws IOException {
		FileInputStream fis = new FileInputStream(testCaseXls);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		for(int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cellCase = row.getCell(1);
			Cell cellMode = row.getCell(2); 
			String testCase = returnStringTypeof(cellCase);
			String runMode = returnStringTypeof(cellMode);
			hm.put(testCase, runMode);
		}
		return hm;
	}
	
	public HashMap<String, String> getTestData(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		for(int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cellCase = row.getCell(3);
			Cell cellMode = row.getCell(4); 
			String testDataKey = returnStringTypeof(cellCase);
			String testDataVal = returnStringTypeof(cellMode);
			testData.put(testDataKey, testDataVal);
		}
		return testData;
	}
	
	public void updateTestDataValue(String fileName, String key, String Value) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cellCase = row.getCell(3);
			String testDataKey = returnStringTypeof(cellCase);
			if(testDataKey.equals(key)) {
				Cell cellStatus = row.createCell(4); 
				cellStatus.setCellValue(Value);
			}
		}
		FileOutputStream outFile =new FileOutputStream(new File(fileName));
	    workbook.write(outFile);
	    outFile.close();
	}
	
	public Boolean getRunMode(String testKey) {
		if(hm.get(testKey).toString().equals("no")) {
			return true;
		} else if(hm.get(testKey).toString().equals("yes")) {
			return false;
		} else {
			return false;
		}
	}
	
	public String getBrowser() {
		return null;
	}
	
	private String returnStringTypeof(Cell cellType) {
		String cellValue = "";
		if (cellType != null) {
			switch (cellType.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cellType.toString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = convertNumToString(cellType).toString();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				boolean cellVal = cellType.getBooleanCellValue();
				cellValue = new Boolean(cellVal).toString();
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = cellType.getStringCellValue();
				break;
			default:
			}
		}
		return cellValue.trim();
	}
	
	
	private String convertNumToString(Cell cellType) {
		int cellValue = (int) cellType.getNumericCellValue();
		String str = Integer.toString(cellValue);
		return str;
	}
	
	
	
}
