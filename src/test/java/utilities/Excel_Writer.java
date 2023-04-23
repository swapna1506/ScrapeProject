package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//import CrossBrowser.Driverfactory;


public class Excel_Writer {
	
 public static WebDriver driver = new ChromeDriver();
	 
	 String path;
		public FileInputStream fi;
		public XSSFWorkbook workbook;
		public XSSFSheet sheet;
		public XSSFRow row;
		public XSSFCell cell;
		public FileOutputStream fo;
		public XSSFCellStyle cellStyle;
		
		public Excel_Writer(String path) {
			this.path = path;
		}
		
		public int getRowCount(String sheetName) throws IOException {
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);
			sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			workbook.close();
			fi.close();
			return rowCount;
		}
		
		public int getCellCount(String sheetName,int rowNum) throws IOException {
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			int cellCount = row.getLastCellNum();
			workbook.close();
			fi.close();		
			return cellCount;
		}
		
		public String getCellData(String sheetName,int rowNum,int colNum) throws IOException {
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			
			DataFormatter formatter = new DataFormatter();
			String data;
			
			try {
				data = formatter.formatCellValue(cell);  //Returns the formatted cell value as a string regardless of the 
			}
			catch(Exception e) { 
				data = "";
			}
			workbook.close();
			fi.close();
			return data;
	  	}
		
		// To Write data in the XLSheet
		public void setCellData(String sheetName,int rowNum,int colNum,String data) throws IOException {
			File xlFile = new File(path);
			
			// if file not exists then create a new file
			if (!xlFile.exists()) {         
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
			}
			
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);
			
			// if Sheet not exists then create a new sheet
			if (workbook.getSheetIndex(sheetName) == -1) {
				workbook.createSheet(sheetName);
			}
			sheet = workbook.getSheet(sheetName);
			
			// if row not exists then create a new row
			if (sheet.getRow(rowNum) == null) {
				sheet.createRow(rowNum);
			}
			row = sheet.getRow(rowNum);
			
			cell = row.createCell(colNum);
			cell.setCellValue(data);
			fo = new FileOutputStream(path);
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();
			}

}
