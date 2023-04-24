package ScrapeRecipes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class dummy {
	
	@Test
	public static void writeExcel() throws IOException
	{
		String strFilePath  = System.getProperty("user.dir") + 
				"/src/test/resources/TestData/ScrappedRecipes.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet("PCOS_Recipes");
		
				
			Row row = worksheet.createRow(0);
			//Font font = worksheet.
		   // /row.setRowStyle(null);
			//int colNum = 0;
			row.createCell(0).setCellValue("Recipe Id");
			row.createCell(1).setCellValue("Recipe Name");
			row.createCell(2).setCellValue("Ingredients List");
			row.createCell(3).setCellValue("Preparation Time");
			row.createCell(4).setCellValue("Recipe URL");
		
            //cell.setCellValue("Recipe ID");
        	//cell = row.createCell(colNum++);
            //cell.setCellValue("Recipe Name");
        	//cell = row.createCell(colNum++);
            //cell.setCellValue("Ingredients List");
			
		
		File excelFile = new File(strFilePath);
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
		workbook.close();
		fos.close();
	}

}
