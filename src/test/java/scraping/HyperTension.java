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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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

import utilities.Excel_Writer;

//import com.google.common.collect.Table.Cell;

public class HyperTension {
	
	static WebDriver driver = new ChromeDriver();
	static String eliminatedlist ;
	static List<String> list = new ArrayList<>();
	
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
@Test(priority=3)
	public static void ScrapeRecipes_HyperTension() throws InterruptedException, IOException{
	
	((JavascriptExecutor)driver).executeScript("window.scrollBy(0,6000)");
	
	List<WebElement> recipes = driver.findElements(By.xpath("//span/a[@itemprop ='url']"));
	
	int size =recipes.size();
	System.out.println(size);
	List<WebElement> NoOfPages = driver.findElements(By.xpath("//div[@id = 'pagination']/a"));
	int number = NoOfPages.size();
	int k=1;
	//for(int x=1;x<=number;x++) {
	//	WebElement pages = driver.findElement(By.xpath("//div[@id = 'pagination']/a[" + x + "]"));	
	//	pages.click();
	
	 XSSFWorkbook workbook = new XSSFWorkbook();
     XSSFSheet sheet = workbook.createSheet("Receipe List");
     Row row = sheet.createRow(0);
     Cell cellRei = row.createCell(0);
     cellRei.setCellValue("Receipe Name");
     Cell cell = row.createCell(1);
     cell.setCellValue("Ingredients");
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
		
		ingredientsString = ing + "/n" + ingredientsString ;
		
		
	}
		
		if((!ingredientlist.contains(list))&&!ingredientlist.contains("salt")) {
			row = sheet.createRow(rowCount);
			rowCount++;
			
			String recipeName =driver.findElement(By.xpath("//span[@id = 'ctl00_cntrightpanel_lblRecipeName']")).getText();
			row.createCell(0).setCellValue(recipeName);
			System.out.println(k+" : "+recipeName);
			System.out.println("   Ingredients:   ");
			
			row.createCell(1).setCellValue(ingredientsString);
			String PrepTime = driver.findElement(By.xpath("//div[@id = 'ctl00_cntrightpanel_pnlRecipeScale']/section/p[2]/time[1]")).getText();
			System.out.println("Preparation Time: "+PrepTime);
			String CookTime =driver.findElement(By.xpath("//div[@id = 'ctl00_cntrightpanel_pnlRecipeScale']/section/p[2]/time[2]")).getText();
			System.out.println("Cooking Time : "+CookTime);
			String PrepMethod = driver.findElement(By.id("recipe_small_steps")).getText();
			System.out.println("   Preparation Method : ");
			System.out.println(PrepMethod);
			System.out.println("Nutrient Values: ");
			WebElement Nutrientvalues= driver.findElement(By.xpath("//table[@id = 'rcpnutrients']"));
			if(Nutrientvalues.isDisplayed() ) {
			String NutrientVal =Nutrientvalues.getText();
			System.out.println(NutrientVal);
			}
			else {
				System.out.println("Not Available");
			}
			String strUrl = driver.getCurrentUrl();
			System.out.println("Recipe Url :"+ strUrl);
			driver.navigate().back();
			String recipeId = driver.findElement(By.xpath("//article[@itemprop = 'itemListElement'][" + i + "]/div[2]/span")).getText();
			String[] trimmedText = recipeId.split(" ");
			String str = trimmedText[1];

			
			    System.out.println("Recipe# "+str);
			    System.out.println("****************************************");
				
			k++;
			
			
			
		}
		
		
		else {
			driver.navigate().back();
			
		}
		
	}
	//driver.navigate().back();
	
	 try (FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx")) {
         workbook.write(outputStream);
     }
	}


public static List<String> Eliminatedlist() throws InterruptedException, IOException {
	
	String strFilePath  = System.getProperty("user.dir") +
			"/src/test/resources/Exceldata/eliminationlist.xlsx";
	File excelFile = new File(strFilePath);
	FileInputStream fis = new FileInputStream(excelFile);
	XSSFWorkbook workbook = new XSSFWorkbook(fis);
	XSSFSheet sheet = workbook.getSheetAt(0);
	for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
		 Row row = sheet.getRow(rowIndex);
		  if (row != null) {
		    Cell cell = row.getCell(4);
		    if (cell != null) {
		  		   // System.out.println(cell.getStringCellValue());
		    eliminatedlist=cell.getStringCellValue().toLowerCase();
		    list.add(eliminatedlist);
		      }
		    
		     }
		  
	}
	return list;
	
}
}
