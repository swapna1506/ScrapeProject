package ScrapeRecipes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class scrapePCOS {
	
	
	public static WebDriver driver;
	String url = "https://tarladalal.com/";
	int total_recipes = 0;
	static int flag, allergyFlag = 0, toAddflag = 0;
	static String path  = System.getProperty("user.dir") + 
			"/src/test/resources/TestData/";	
	static String[] eliminatedList = new String[0];
	static String[] allergens = new String[0];
	static String[] toAdd = new String[0];
	

	
	public static void createExcel() throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet("Pcos_Recipes");
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setBold(true);
		style.setFont(font);
		style.setFillPattern(FillPatternType.FINE_DOTS);
		style.setFillBackgroundColor((IndexedColors.YELLOW.getIndex()));
		Row row = worksheet.createRow(0);
		
		row.createCell(0).setCellValue("Recipe Id");
		row.createCell(1).setCellValue("Recipe Name");
		row.createCell(2).setCellValue("Recipe Category");
		row.createCell(3).setCellValue("Food Category");
		row.createCell(4).setCellValue("Ingredients List");
		row.createCell(5).setCellValue("Preparation Time");
		row.createCell(6).setCellValue("Cooking Time");
		row.createCell(7).setCellValue("Preparation Method");
		row.createCell(8).setCellValue("Nutrient Values");
		row.createCell(9).setCellValue("Recipe URL");
		row.createCell(10).setCellValue("Allergy Information");
		for(int j = 0; j<=10; j++)
		{
		 row.getCell(j).setCellStyle(style);
			
		}
		XSSFSheet newsheet = workbook.cloneSheet(0,"Diabetes_recipes");
	    newsheet = workbook.cloneSheet(0,"Hypertension_recipes");
		newsheet = workbook.cloneSheet(0,"Hypothyroidism_recipes");
		
       	File excelFile = new File(path+"ScrappedRecipes.xlsx");
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
				
		workbook.close();
		fos.close();
	}
	public static String[] readEliminatedList(String sheet_name) throws IOException, InterruptedException
	{
		int index_count = 0;
		String[] tempArray = new String[100];
		File excelFile = new File(path+"Team16_Filters.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheet_name);
		Iterator<Row> row = sheet.rowIterator();
	   	int i = 0;
		while(row.hasNext()){
			Row currentRow = row.next();
			Iterator<Cell> cell = currentRow.cellIterator();
			while(cell.hasNext()){
				Cell currentCell = cell.next();
				tempArray[i] = currentCell.getStringCellValue();
				index_count++;
				i++;
			}
		}
		workbook.close();
		eliminatedList = new String[index_count];
        for(i=0;i<eliminatedList.length;i++)
        {
        	eliminatedList[i] = tempArray[i];
        }
		return eliminatedList;
	}
	
    public static String[] readAllergies(String sheet_name) throws IOException
    {
    	int index_count = 0;
    	String[] tempArray = new String[100];
    	File excelFile = new File(path+"Team16_Filters.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheet_name);
		Iterator<Row> row = sheet.rowIterator();
	   	int i = 0;
		while(row.hasNext()){
			Row currentRow = row.next();
			Iterator<Cell> cell = currentRow.cellIterator();
			while(cell.hasNext()){
				Cell currentCell = cell.next();
				tempArray[i] = currentCell.getStringCellValue();
                index_count++;
				i++;
			}
		}
		workbook.close();
		allergens = new String[index_count];
        for(i=0;i<allergens.length;i++)
        {
        	allergens[i] = tempArray[i];
        }
	
		return allergens;
    }
    public static String[] toAddRecipes(String sheet_name) throws IOException
    {
    	int index_count = 0;
    	String[] tempArray = new String[100];
    	File excelFile = new File(path+"Team16_Filters.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheet_name);
		Iterator<Row> row = sheet.rowIterator();
		
		int i = 0;
		while(row.hasNext()){
			Row currentRow = row.next();
			Iterator<Cell> cell = currentRow.cellIterator();
			while(cell.hasNext()){
				Cell currentCell = cell.next();
				tempArray[i] = currentCell.getStringCellValue();
				index_count++;
				i++;
			}
		}
		workbook.close();
		toAdd = new String[index_count];
        for(i=0;i<toAdd.length;i++)
        {
        	toAdd[i] = tempArray[i];
        }
		return toAdd;
    }

		
	@Test(priority = 1)
	public void lauch_website()
	{
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@Test(priority = 2)
	public void search_for_recipes() throws InterruptedException
	{
		driver.findElement(By.id("ctl00_txtsearch")).sendKeys("PCOS Recipes");
		driver.findElement(By.id("ctl00_imgsearch")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//a[@href='recipes-for-pcos-1040']")).click();
		Thread.sleep(10);
	}
	
	@Test(priority = 3)
	public void scrape_recipes_pcos() throws InterruptedException, IOException
	{
		
		int eliminated_recipe_count =  0, filtered_recipe_count = 0;
		readEliminatedList("PCOS_eliminatedlist");
		readAllergies("allergies");
		toAddRecipes("PCOS_toadd");
		createExcel();
			
    	List <WebElement> recipe_link_pages = driver.findElements(By.xpath("//a[@class='respglink']"));
		int recipe_link_pages_size = recipe_link_pages.size()+1;
		System.out.println("Total pages to Scrape: "+recipe_link_pages_size);
		//recipe_link_pages_size
		//traversing thru recipe pages
		for(int i=1;i<=recipe_link_pages_size;i++)
		{
			driver.findElement(By.xpath("//a[@href='/recipes-for-pcos-1040?pageindex="+i+"']")).click();
			String current_url = driver.getCurrentUrl();
			Thread.sleep(10);
			List <WebElement> recipes_url = driver.findElements(By.xpath("//a[@itemprop='url']"));
            int recipe_url_size = recipes_url.size();
            System.out.println("Total Recipe cards in Page"+i+": "+recipe_url_size);
            //recipe_url_size
            //traversing recipe cards in each page
            for(int j =0; j<recipe_url_size ; j++)
            {
    		  recipes_url = driver.findElements(By.xpath("//a[@itemprop='url']"));
              String recipe_name = recipes_url.get(j).getText();
              driver.findElement(By.xpath("//a[text()='" + recipe_name + "']")).click();
              String ingredientsList = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();
       
              flag = 0;
              outer:
              for(int k=0;k<eliminatedList.length;k++)
              {
            	  String s1 = eliminatedList[k].toLowerCase();
            	  String s2 = ingredientsList.toLowerCase();
            	  if(s2.contains(s1))
            	  {
            		  eliminated_recipe_count++;
            		  flag = 1;
            		  break outer;
                  }
            	  
              }
              if (flag == 0)
              {
                  System.out.println(recipe_name);
            	  String allergy_name = "";
            	  String recipe_tags = "";
            	  try
            	  {
            		  recipe_tags = driver.findElement(By.xpath("//div[@id='recipe_tags']")).getText();  
            	  }
            	  catch(NoSuchElementException e)
            	  {
            		  e.printStackTrace();
            	  }
                //  System.out.println(recipe_tags);	  
              	
            	//checking for allergies
            	  outer1:
            	  for(int k=0;k<allergens.length;k++)
                  {
                	  String s1 = allergens[k].toLowerCase();
                	  String s2 = ingredientsList.toLowerCase();
                	  if(s2.contains(s1))
                	  {
             
                		  allergyFlag = 1;
                		  allergy_name = s1;
                		  break outer1;
                      }
                  }
            	  
            	  //filtering good to have recipes
              	  outer2:
                	  for(int k=0;k<toAdd.length;k++)
                      {
                    	  String s1 = toAdd[k].toLowerCase().trim();
                    	  String s2 = recipe_name.toLowerCase().trim();
                    	  String s3 = recipe_tags.toLowerCase().trim();
                    	  if(s2.contains(s1) || s3.contains(s1))
                    	  {
                    		  System.out.println("**Good to have Recipe");
                    		  toAddflag = 1;
                    		  break outer2;
                          }
                      }
            
              	//writing data to excel file
            	String recipe_url = driver.getCurrentUrl();
            	String recipe_id = recipe_url.replaceAll("[^0-9]", "");
            	//recipe_name
            	//Ingredients_list
            	String prep_time = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
                String cook_time = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
            	String prep_method = driver.findElement(By.xpath("//div[@id='recipe_small_steps']")).getText();
            	String nutrient_values = " ";
            	try
            	{
                	nutrient_values = driver.findElement(By.xpath("//table[@id='rcpnutrients']")).getText();
            	} 
            	catch(NoSuchElementException e)
            	{
            		e.printStackTrace();
            	} 
               	String strFilePath  = System.getProperty("user.dir") + 
                         				"/src/test/resources/TestData/ScrappedRecipes.xlsx";
            	//span[contains(text(),'Fiber')]
            	File excelfile = new File(strFilePath);
            	FileInputStream fis = new FileInputStream(excelfile);	
            	XSSFWorkbook workbook = new XSSFWorkbook(fis);
          		XSSFSheet worksheet = workbook.getSheet("PCOS_Recipes");
   
          		XSSFCellStyle style = workbook.createCellStyle();
           		style.setFillPattern(FillPatternType.FINE_DOTS);
                style.setFillBackgroundColor((IndexedColors.TAN.getIndex()));  		
          		
                int rowCount = worksheet.getLastRowNum();
          		
          		Row row = worksheet.createRow(++rowCount);
        		
          		row.createCell(0).setCellValue(recipe_id);
        		row.createCell(1).setCellValue(recipe_name);
        		row.createCell(2).setCellValue(" ");
        		if((allergy_name).contains("egg"))
        		{
        			row.createCell(3).setCellValue("Eggitarian");
                }
        		else if(recipe_tags.toLowerCase().trim().contains("vegan"))
        		{
        			row.createCell(3).setCellValue("Vegan");
        		}
        		else
        		{
            		row.createCell(3).setCellValue("Vegetarian");
            	}
        		row.createCell(4).setCellValue(ingredientsList);
        		row.createCell(5).setCellValue(prep_time);
        		row.createCell(6).setCellValue(cook_time);
        		row.createCell(7).setCellValue(prep_method);
        		row.createCell(8).setCellValue(nutrient_values);
        		row.createCell(9).setCellValue(recipe_url);
        		row.createCell(10).setCellValue("");
    			if(allergyFlag == 1)
        		{   
        			row.createCell(10).setCellValue(allergy_name);
        			allergyFlag = 0;
        		}
        		else 
        		{
        			row.createCell(10).setCellValue(" ");

        		}
                //highlighting good have recipes        	
    			if(toAddflag == 1)
    			{
    				toAddflag = 0;
    				for(int h = 0; h<=10; h++)
    				{
    				 row.getCell(h).setCellStyle(style);
    					
    				}
    			}      
        		File excelFile = new File(strFilePath);
        		FileOutputStream fos = new FileOutputStream(excelFile);
        		workbook.write(fos);
        		workbook.close();
        		fos.close();
        		filtered_recipe_count++;
              } 	  
              driver.get(current_url);
              driver.navigate().refresh();
              Thread.sleep(5);
            }
            total_recipes = total_recipes + recipe_url_size;
            		
		}
        System.out.println("Total Recipes:"+total_recipes);  
        System.out.println("Eliminated Recipes:"+eliminated_recipe_count);
        System.out.println("Filtered Recipes: "+filtered_recipe_count);
     }
}
