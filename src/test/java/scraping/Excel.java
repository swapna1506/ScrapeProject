package scraping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	static String eliminatedlist ;
	static List<String> list = new ArrayList<>();
   public static void main(String[] args) throws IOException  {
	   String strFilePath  = System.getProperty("user.dir") +
				"/src/test/resources/Exceldata/eliminationlist.xlsx";
		File excelFile = new File(strFilePath);
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int i=0;
		for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			 Row row = sheet.getRow(rowIndex);
			  if (row != null) {
			    Cell cell = row.getCell(4);
			    if (cell != null) {
			      
			   // System.out.println(cell.getStringCellValue());
			    eliminatedlist=cell.getStringCellValue();
			    list.add(eliminatedlist);
			    

			     
			    }
			    System.out.println(eliminatedlist);
			  }
			  
			}
	   
   }
}
