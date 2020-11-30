package Flipkart.Test;

import org.testng.annotations.Test;

import junit.framework.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class NewTest {
 
	WebDriver driver;
	
	
	By user_id = By.xpath("//input[@class='_2IX_2- VJZDxU']");
	By pass = By.xpath("//input[@class='_2IX_2- _3mctLh VJZDxU']");
	By login = By.xpath("//button[@class='_2KpZ6l _2HKlqd _3AWRsL']");
	By books = By.xpath("//span[text()='Sports, Books & More']");
	By pens = By.xpath("//a[@title='Pens']");
	By min = By.xpath("(//select[@class='_2YxCDZ'])[1]");
	By max = By.xpath("(//select[@class='_2YxCDZ'])[2]");
	By rating  = By.xpath("(//div[@class='_3879cV'])[4]");
	By parker = By.xpath("//div[text()='Parker']");
	By faberCastell = By.xpath("//div[text()='Faber-Castell']");
	By more = By.xpath("(//span[starts-with(text(),'1')])[1]");
	By c = By.xpath("//div[text()='C' and @class='_3t3VNA _19swAM']");
	By craftwaft = By.xpath("//div[text()='CRAFTWAFT']");
	By applyFilter =By.xpath("//div[@class='THxusM _3yuvK8']");
	By showMore =By.xpath("//span[text()='Show  more']");
	By seven = By.linkText("7");
	By lastElement = By.xpath("(//div[@class='CXW8mj'])[40]");
	//By pin = By.xpath("//input[@class='_36yFo0']");
	By addCart = By.xpath("//button[@class='_2KpZ6l _2U9uOA _3v1-ww']");
	By pin =By.xpath("//div[@class='_12cXX4']");
	By deliveryPincode =By.xpath("//input[@class='cfnctZ']");
	By check =By.xpath("//span[@class='UgLoKg']");
	
	
	String ratingActual;
	String ratingExcepted = "3? & above";
	
	String parkerActual;
	String parkerExcepted = "Parker";
	
	String fabercastellActual;
	String fabercastellExcepted = "Faber-Castell";
	
	String craftwaftActual;
	String craftwaftExcepted = "CRAFTWAFT";
	
	
	@SuppressWarnings("deprecation")
	@BeforeTest
	public void browser_properties()
	{
		System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}
	
	@Test(dataProvider="ExcelData")
	public void testcase1(String url , String user_name , String password , String minimum , String maximum , String pincode) throws InterruptedException
	{
		driver.get(url);
		
		driver.findElement(user_id).sendKeys(user_name);
		driver.findElement(pass).sendKeys(password);
		driver.findElement(login).click();
		Thread.sleep(7000);
		driver.findElement(books).click();
		driver.findElement(pens).click();
		new Select(driver.findElement(min)).selectByValue(minimum);
		Thread.sleep(5000);
		new Select(driver.findElement(max)).selectByValue(maximum);
		Thread.sleep(5000);
		driver.findElement(rating).click();
		ratingActual = driver.findElement(rating).getText();
		//Assert.assertEquals(ratingPredicted, ratingActual);
		
		Thread.sleep(5000);
		driver.findElement(parker).click();
		System.out.println(driver.findElement(parker).getText());
		parkerActual = driver.findElement(parker).getText();
		Assert.assertEquals(parkerExcepted, parkerActual);
		
		Thread.sleep(5000);
		driver.findElement(faberCastell).click();
		System.out.println(driver.findElement(faberCastell).getText());
		fabercastellActual =driver.findElement(faberCastell).getText();
		Assert.assertEquals(fabercastellExcepted, fabercastellActual);
		
		Thread.sleep(2000);
		driver.findElement(more).click();
		Thread.sleep(2000);
		driver.findElement(c).click();
		driver.findElement(craftwaft).click();
		System.out.println(driver.findElement(craftwaft).getText());
		craftwaftActual =driver.findElement(craftwaft).getText();
		Assert.assertEquals(craftwaftExcepted, craftwaftActual);
		
		driver.findElement(applyFilter).click();
		Thread.sleep(2000);
		driver.findElement(showMore).click();
		driver.findElement(seven).click();
		Thread.sleep(2000);
		driver.findElement(lastElement).click();
		
		
		Set<String>windowsId = driver.getWindowHandles();
		Iterator<String> iter =windowsId.iterator();
		String mainWindow =iter.next();
		String nextwindow =iter.next();
		
		driver.switchTo().window(nextwindow);
		driver.findElement(addCart).click();
		Thread.sleep(2000);
		driver.findElement(pin).click();
		driver.findElement(deliveryPincode).sendKeys(pincode);
		driver.findElement(check).click();
	}
	
	@DataProvider (name="ExcelData")
	public Object[][]  getdata() throws BiffException
	{
		Object[][] data = getExcelData("C:\\kjkj\\Test\\src\\test\\FlipkartTestData.xls","Sheet_1");
		return data;
	}
	
	
	//Fetching the data from the Excel Sheet
			public String[][] getExcelData(String fileName , String sheetName) throws BiffException
			{
				String[][] arrayExcelData = null;
				try {
					FileInputStream fs = new FileInputStream(fileName);
					Workbook wb = Workbook.getWorkbook(fs);
					Sheet sh = wb.getSheet(sheetName);

					int totalNoOfCols = sh.getColumns();
					int totalNoOfRows = sh.getRows();

					arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

					for (int i = 1; i < totalNoOfRows; i++) {

						for (int j = 0; j < totalNoOfCols; j++) {
							arrayExcelData[i - 1][j] = sh.getCell(j, i).getContents();
						}

					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					e.printStackTrace();
				}
				return arrayExcelData;
				
			}
			
			@AfterTest                                                        //This is after test method
			public void closeBrowser()
			{
				driver.quit();
			}
}
