package commonfunctions;

import java.io.File;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.beust.jcommander.internal.Lists;
import com.google.common.io.Files;
import com.mongodb.MapReduceCommand.OutputType;

import uitilies.PropertyFileUitilies;

public class CommonFunction {
	
	public static WebDriver driver;
	public static String expectedvalue = "";
	
	// Method for web driver
	public static WebDriver startBrowser() throws Throwable {
		
		if(PropertyFileUitilies.getValueKey("Browser").equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Webdrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		else if (PropertyFileUitilies.getValueKey("Browser").equalsIgnoreCase("firefox")) 
		{		
			System.setProperty("Webdriver.driver.firefox", "geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			
		}
		else {
			System.out.println("Unable to load webdriver");
		}
		return driver;
	}

	// Method for launch url from anywhere
	public static void openUrl(WebDriver driver) throws Throwable {

		driver.get(PropertyFileUitilies.getValueKey("Url"));
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//li[@data-cy='account']")).click();
		
	}

	// Method of waiting any element
	public static void waitForElement(WebDriver driver, String LocatorType, String LocatorValue, String TestData) throws Throwable {
		
//		System.out.println(LocatorType+" "+ LocatorValue+" "+ TestData);
		
		WebDriverWait mywait = new WebDriverWait(driver, Integer.parseInt(TestData));
		
		if(LocatorType.equalsIgnoreCase("name")) {
//			System.out.println("name if");
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if (LocatorType.equalsIgnoreCase("id")) {
//			System.out.println("id if");
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if (LocatorType.equalsIgnoreCase("xpath")) {
//			System.out.println("xpath if");
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		else {
			System.out.println("Unable to find element");
		}
		
	}

	// Method for validating title
	public static void validateTitle(WebDriver driver, String expectedtitle ) {
		
		String actualtitle = driver.getTitle();
		Assert.assertEquals(actualtitle, expectedtitle);

	}
	
	// Method for clickActon
	public static void clickAction(WebDriver driver, String LocatorType, String LocatorValue) {
		
		
			if(LocatorType.equalsIgnoreCase("xpath")) {
				
				try {
					driver.findElement(By.xpath(LocatorValue)).click();
					driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//					Thread.sleep(5000);
				} catch (Exception e) {
					driver.findElement(By.xpath(LocatorValue)).sendKeys(Keys.ENTER);
				}
			
			}
			else if (LocatorType.equalsIgnoreCase("id")) {
				driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);;

			}
			else if (LocatorType.equalsIgnoreCase("name")) {
				driver.findElement(By.name(LocatorValue)).click();
			}
			else if (LocatorType.equalsIgnoreCase("class")) {
				driver.findElement(By.name(LocatorValue)).sendKeys(Keys.ENTER);;
			}
			else{
				System.out.println("Element not clicked");
			}
		
		
	}
	
	// Mehtod for sending data
	public static void typeAction(WebDriver driver, String LocatorType, String LocatorValue, String TestData) {
		
//		System.out.println(TestData);
		if(LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		else if (LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		else if (LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		
	}
	
	// Method for select from list
	public static void selectFromList(WebDriver driver, String LocatorType, String LocatorValue, String TestData) {
				
		if(LocatorType.equalsIgnoreCase("xpath")) {
			java.util.List<WebElement>  sugg_li;
//			sugg_ul = driver.findElements(By.xpath("//div[contains(@class,'react-autosuggest__section-container--first')]//ul"));
			sugg_li = driver.findElements(By.xpath(LocatorValue));
//			System.out.println(sugg_li.size());
			for(WebElement ele:sugg_li) 
			{
				if(ele.getText().contains(TestData)) 
				{
//					System.out.println(ele.getText());
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", ele);
					break;
				}
			}			
		
		}	
	}

	// Method for select date 
	public static void selectDate(WebDriver driver, String LocatorType, String LocatorValue, String TestData) throws InterruptedException {
		
//		System.out.println(date);
		if(LocatorType.equalsIgnoreCase("xpath")) {

			String[] temp = TestData.split("-");//May-10-2023
			String month = temp[0];
			String date1 = temp[1];
//			System.out.println(date1);
			String year = temp[2];

			String mmyy = month+" "+year;
//			System.out.println(mmyy);
			String mmyy1 = month+""+year;
			String calyear = driver.findElement(By.xpath(LocatorValue)).getText();
			
			boolean res = calyear.contains(" ");
//			System.out.println(res);
			
			if(res) {
				while(!calyear.equalsIgnoreCase(mmyy)) 
				{
					driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
					calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
			}else {
				while(!calyear.equalsIgnoreCase(mmyy1)) 
				{
					driver.findElement(By.xpath("(//span[@tabindex=0])[2]")).click();
					calyear = driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]//div")).getText();
				}
			}

			try {
				java.util.List<WebElement> cols;
				cols = driver.findElements(By.xpath("//div[@class='DayPicker-Month'][1]//div[@class='dateInnerCell']//p[1] | //div[@class='DayPicker-Month'][1]//div[@class='DayPicker-Day']"));
//				System.out.println(cols.size());
				for(WebElement element : cols) 
					if(element.getText().contains(date1)) 
					{
						try {
							element.click();
							break;
						} catch (Exception e) {
							break;
						}
					}
			} catch (Exception e) {
				System.out.println("steale");
			}
			
			
			
		}
			
			
		
	}

	// Method for tab switch
	public static void switchTab(WebDriver driver, int TestData) {
	
		List<String> browserTab = Lists.newArrayList(driver.getWindowHandles());
	    driver.switchTo().window(browserTab.get(TestData));
	
	}
	
	// Method for secroll down
	public static void moveTo(WebDriver driver, String LocatorType, String LocatorValue) {
		
		if(LocatorType.equalsIgnoreCase("xpath")) {
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.xpath(LocatorValue)) );
			
//			
//			WebElement ele = driver.findElement(By.xpath(LocatorValue));		
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);

		}
		
		
	}

	// Method for select from radio
	public static void selectFromRadio(WebDriver driver, String LocatorType, String LocatorValue) {
		
		if(LocatorType.equalsIgnoreCase("xpath")) {
			
//			String m = driver.findElement(By.xpath(LocatorValue)).getText();
			WebElement ele = driver.findElement(By.xpath(LocatorValue));
			
			if(!ele.isSelected()) 
			{
				ele.click();
			}
			
	}
		
		
	}
	
	// Method for move to element and click
	public static void moveAndClick(WebDriver driver, String LocatorType, String LocatorValue) {
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
//			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//			Actions ac = new Actions(driver);
//			ac.moveToElement(driver.findElement(By.xpath(LocatorValue))).doubleClick().build().perform();
		
			WebElement ele = driver.findElement(By.xpath(LocatorValue));
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			js.executeScript("arguments[0].click();", ele);
		
			
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);

		
		
		
		
		
		}
		
	}
	
	
	public static void displyedClick(WebDriver driver, String LocatorType, String LocatorValue) {
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
			WebElement ele = driver.findElement(By.xpath(LocatorValue));
//			System.out.println(ele.getText());
			
			if(ele.isDisplayed()) {
				ele.click();
			}
		}
		
	}
	
	
	public static void selectRadio(WebDriver driver, String LocatorType, String LocatorValue, String TestData) {
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
			
			List<WebElement> ul ;
			String gen;
			ul = driver.findElements(By.xpath(LocatorValue));
			for(int i=0; i<ul.size(); i++) {
				gen = ul.get(i).getText();
				if(gen.equalsIgnoreCase(TestData)) {
					
				}
			}
	
		}
		
	}
	
	
	public static void addNew(WebDriver driver, String LocatorType, String LocatorValue, String TestData) throws Throwable {
		if(LocatorType.equalsIgnoreCase("xpath")) 
		{
			Thread.sleep(3000);
			int person = Integer.parseInt(TestData);
			WebElement plus;
			
			plus = driver.findElement(By.xpath("(//div[contains(@class,'invent-unit-q-change-box')])[1]//button[2]"));
			Thread.sleep(3000);

			for(int i=1; i<person; i++) {
				try {
//					System.out.println();
					plus.click();
					break;
				} catch (Exception e) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", plus);
				}
			}
			
			
			
			
		}
		
	}
	
	// Method for close browser 
	public static void closeBrowser(WebDriver driver) {
		driver.quit();
	}
		
	
	
	
		
}

	 

	
