package scraping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.List;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;



//import com.google.common.collect.Table.Cell;

public class HyperTension {
	
	static WebDriver driver = new ChromeDriver();
	static String eliminatedlist ;
	static String allergylist ;
	static String toadd;
	static List<String> list = new ArrayList<>();
	//static List<String> alllist = new ArrayList<>();
	static List<String> alllist = new ArrayList<>();
	static List<String> to_addlist = new ArrayList<>();
	
	static String Allergy_Name;
	static int allergyflag;
	//static String[] ingredientlist;
	
	
@Test(priority=1)	
public static void LaunchBrowser() throws IOException {
	
		
	driver.get("https://www.tarladalal.com/");
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.manage().window().maximize();
	Actions action = new Actions(driver);
	
	  
	}
@Test(priority=2)
	public static void recipes_search() {
	
	driver.findElement(By.xpath("//div[text() = 'RECIPES']")).click();
	
	((JavascriptExecutor)driver).executeScript("window.scrollBy(0,1500)");
	
	driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht167")).click();
	}
@SuppressWarnings("unlikely-arg-type")
@Test(priority=3)
	public static void ScrapeRecipes_HyperTension() throws InterruptedException, IOException{
	
	((JavascriptExecutor)driver).executeScript("window.scrollBy(0,6000)");
	
	List<WebElement> recipes = driver.findElements(By.xpath("//span/a[@itemprop ='url']"));
	
	int size =recipes.size();
	System.out.println(size);
	List<WebElement> NoOfPages = driver.findElements(By.xpath("//div[@id = 'pagination']/a"));
	int number = NoOfPages.size();
	int k=1;
	for(int x=1;x<=number;x++) {
		WebElement pages = driver.findElement(By.xpath("//div[@id = 'pagination']/a[" + x + "]"));	
		pages.click();
	
	 XSSFWorkbook workbook = new XSSFWorkbook();
     XSSFSheet sheet = workbook.createSheet("HyperTension");
     Row row = sheet.createRow(0);
     CellStyle cellstyle = workbook.createCellStyle();
     cellstyle.setWrapText(true);
     Cell cellRei = row.createCell(0);
     cellRei.setCellValue("Receipe Name");
     Cell cell = row.createCell(1);
     cell.setCellValue("Recipe ID");
     Cell cell1 = row.createCell(2);
     sheet.autoSizeColumn(2);
     cell1.setCellValue("Ingredients");
     Cell cell2 = row.createCell(3);
     cell2.setCellValue("Preparation Time");
     Cell cell3 = row.createCell(4);
     cell3.setCellValue("Cooking Time");
     Cell cell4 = row.createCell(5);
     
     cell4.setCellValue("Preparation Method");
     cell4.setCellStyle(cellstyle);

	  CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 2, 2, 3);
	  sheet.addMergedRegion(cellRangeAddress);
     Cell cell5 = row.createCell(6);
     cell5.setCellValue("Nutrient Values");
     Cell cell6 = row.createCell(7);
     cell6.setCellValue("Recipe URL");
     Cell cell7 = row.createCell(8);
     cell7.setCellValue("Allergy Ingredients");
     Cell cell8 = row.createCell(9);
     cell8.setCellValue("Food Category");
    
     int rowCount =1;
     
	for(int i=1;i<size;i++) {
		
		WebElement recipe = driver.findElement(By.xpath("//article[@itemprop = 'itemListElement'][" + i + "]/div/span/a[@itemprop ='url']"));
		
		recipe.click();
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,4000)");
		
		
		
		List<WebElement> RecIngredients = driver.findElements(By.xpath("//span[@itemprop = 'recipeIngredient']"));
		int ingredientssize = RecIngredients.size();
		
		List<String> ingredientlist = new ArrayList<>();
		
		String  ingredientsString = "";
		
		for(int j=1;j<ingredientssize;j++) {
			
			
	WebElement ingredients =driver.findElement(By.xpath("//span[@itemprop = 'recipeIngredient'][" +j + "]/a/span"));
		
		String ing = ingredients.getText().toLowerCase();
		
		
		ingredientlist.add(ing);
		
		}
		String Nutrientvalues=" ";
		
		if((!ingredientlist.contains(list))&&!ingredientlist.contains("salt")) {
			row = sheet.createRow(rowCount);
			rowCount++;
			
			String recipeName =driver.findElement(By.xpath("//span[@id = 'ctl00_cntrightpanel_lblRecipeName']")).getText();
			row.createCell(0).setCellValue(recipeName);
			sheet.autoSizeColumn(20);
			System.out.println(k+" : "+recipeName);
			System.out.println("   Ingredients:   ");
			
			row.createCell(2).setCellValue(ingredientlist.toString());
			String PrepTime = driver.findElement(By.xpath("//div[@id = 'ctl00_cntrightpanel_pnlRecipeScale']/section/p[2]/time[1]")).getText();
			
			row.createCell(3).setCellValue(PrepTime);
			String CookTime =driver.findElement(By.xpath("//div[@id = 'ctl00_cntrightpanel_pnlRecipeScale']/section/p[2]/time[2]")).getText();
			row.createCell(4).setCellValue(CookTime);
			
			String PrepMethod = driver.findElement(By.id("recipe_small_steps")).getText();
			row.createCell(5).setCellValue(PrepMethod);
			
			String RecipeTags = "";
            RecipeTags = driver.findElement(By.xpath("//div[@id='recipe_tags']")).getText();  
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            
		WebElement	Nutrivalues = driver.findElement(By.xpath("//table[@id = 'rcpnutrients']"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		if( driver.findElements(By.xpath("//table[@id = 'rcpnutrients']")).size()!=0) {
			Nutrientvalues = Nutrivalues.getText();
			row.createCell(6).setCellValue(Nutrientvalues);

			}
		else {
			driver.navigate().back();
		}
			
			
			
			
			//}
			
			String strUrl = driver.getCurrentUrl();
			row.createCell(7).setCellValue(strUrl);
			
			driver.navigate().back();
			String recipeId = driver.findElement(By.xpath("//article[@itemprop = 'itemListElement'][" + i + "]/div[2]/span")).getText();
			String[] trimmedText = recipeId.split("\n",2);
			String str = trimmedText[0];
           
			String Id =str.replaceAll("[^0-9]", "");
            row.createCell(1).setCellValue(Id);
          //  row.createCell(6).setCellValue(Nutrientvalues);
			   
            System.out.println("Allergy Ingredients");
            String[] array =new String[alllist.size()];
            String[] arr = new String[ingredientlist.size()];
    		for(int y =0;y<ingredientlist.size();y++) {
    			arr[y] = ingredientlist.get(y);
    		}
    		 for (String s : arr)
    	            System.out.print(s + " ");
           for(String z :array)
        		 System.out.println(z+" ");  
            try {
            outer1:
            for(int m=0;m<alllist.size();m++) {
      		  
            String s1 =arr[m];
      	  String s2 = array[m];
            if(s1.contains(s2) ){
            	allergyflag =1; 
        	   Allergy_Name = s2;
        	 
        	
               
           break outer1;
          }
           
          } 
            }
            catch(NullPointerException e) {
            	e.printStackTrace();
            }
            
            if(allergyflag==1) {
            	row.createCell(8).setCellValue(Allergy_Name);
            }
            if((Allergy_Name).contains("egg")){
            	row.createCell(9).setCellValue("Eggitarian");
            	
            }
            else if(RecipeTags.toLowerCase().trim().contains("vegan")) {
            	row.createCell(9).setCellValue("vegan")	;
            	
            }
            else {
            	row.createCell(9).setCellValue("Vegetarian");
            }
            System.out.println("****************************************");
			
            
			k++;
			
		}
		
		else {
			driver.navigate().back();
			
		}
		
	}
	driver.navigate().back();
	
	 try (FileOutputStream outputStream = new FileOutputStream("Scraping.xlsx")) {
         workbook.write(outputStream);
     }
	}
}

@Test
public static  void Eliminatedlist(String sheetname) throws InterruptedException, IOException {
	
	String strFilePath  = System.getProperty("user.dir") +
			"/src/test/resources/Exceldata/eliminationlist.xlsx";
	File excelFile = new File(strFilePath);
	FileInputStream fis = new FileInputStream(excelFile);
	XSSFWorkbook workbook = new XSSFWorkbook(fis);
	XSSFSheet sheet = workbook.getSheet(sheetname);
	if(sheetname.contains("eliminatedlist")) {
	for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
		 Row row = sheet.getRow(rowIndex);
		  if (row != null) {
		    Cell cell = row.getCell(4);
		    if (cell != null) {
		  		  
		    eliminatedlist=cell.getStringCellValue().toLowerCase();
		    list.add(eliminatedlist);
		      }
		    
		     }
		  
	}
	
	}
	
	if(sheetname.contains("toadd")) {
		
		for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			 Row row = sheet.getRow(rowIndex);
			  if (row != null) {
			    Cell cell = row.getCell(5);
			    if (cell != null) {
		
		toadd = cell.getStringCellValue().toLowerCase();
		to_addlist.add(toadd);
		
	}
}
		}
		
	}
	
	
}
@Test
public static  List<String> Allergylist() throws InterruptedException,IOException {
	String strFilePath1  = System.getProperty("user.dir") +
			"/src/test/resources/Exceldata/Recipe-filters-ScrapperHackathon.xlsx";
	String[] temp = new String[100];
	int count=0;
	File excelFile1 = new File(strFilePath1);
	FileInputStream fis1 = new FileInputStream(excelFile1);
	XSSFWorkbook workbook = new XSSFWorkbook(fis1);
	XSSFSheet sheet1 = workbook.getSheetAt(2);
	for (int rowIndex = 2; rowIndex <= sheet1.getLastRowNum(); rowIndex++) {
		 Row row = sheet1.getRow(rowIndex);
		  if (row != null) {
		    Cell cell = row.getCell(1);
		    if (cell != null) {
		  		  // System.out.println(cell.getStringCellValue());
		 //  alllist = new String[count];
		  // for(int l =0;l<alllist.length;l++) {
			//   alllist[l] = temp[l];
		 //  }
		    	allergylist= cell.getStringCellValue().toLowerCase();
		    	alllist.add(allergylist);
		     }
		  
	}
		  
	}
	return alllist;
		  

}
}