package driverFactory;

import org.openqa.selenium.WebDriver;

import commonfunctions.*;
import utiles.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;
	public static String inputFilePath  = "D:\\Prime Batch\\Mavan_MMT\\FileInput\\DataEngine.xlsx";
	public static String outputFilePath  = "D:\\Prime Batch\\Mavan_MMT\\FileOutput\\Res.xlsx";

	
	public static void startTest() throws Throwable {
		
		
		String Modulestatus = "";
		ExcelFileUtil xl = new ExcelFileUtil(inputFilePath);
		
		for(int i=1; i<=xl.getRowCount("MasterTestCases"); i++) 
		{
//			String Modulestatus = "";
			if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y")) {
				
				String TCModule = xl.getCellData("MasterTestCases", i, 1);
				
				for(int j=1; j<=xl.getRowCount(TCModule); j++) {
					String Description = xl.getCellData(TCModule, j, 0);
					String ObjectType = xl.getCellData(TCModule, j, 1);
					String LocatorType = xl.getCellData(TCModule, j, 2);
					String LocatorValue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);
					
					try {
						
						if(ObjectType.equalsIgnoreCase("startBrowser")) {
							driver = CommonFunction.startBrowser();
		
						}
						
						else if (ObjectType.equalsIgnoreCase("openUrl")) {
							CommonFunction.openUrl(driver);
							
						}
						else if (ObjectType.equalsIgnoreCase("validateTitle")) {
							CommonFunction.validateTitle(driver, TestData);
							
						}
						else if (ObjectType.equalsIgnoreCase("waitForElement")) {
							CommonFunction.waitForElement(driver, LocatorType, LocatorValue, TestData);
						}
						
						else if (ObjectType.equalsIgnoreCase("clickAction")) {
							CommonFunction.clickAction(driver, LocatorType, LocatorValue);

						}
						else if (ObjectType.equalsIgnoreCase("typeAction")) {
							CommonFunction.typeAction(driver, LocatorType, LocatorValue, TestData);

						}
						else if (ObjectType.equalsIgnoreCase("selectFromList")) {
							CommonFunction.selectFromList(driver, LocatorType, LocatorValue, TestData);

						}
						else if (ObjectType.equalsIgnoreCase("selectDate")) {
							CommonFunction.selectDate(driver, LocatorType, LocatorValue, TestData);

						}
						
						else if (ObjectType.equalsIgnoreCase("switchTab")) {
							CommonFunction.switchTab(driver, 1);

						}
						else if (ObjectType.equalsIgnoreCase("moveTo")) {
							CommonFunction.moveTo(driver, LocatorType, LocatorValue);

						}
						
						else if (ObjectType.equalsIgnoreCase("selectFromRadio")) {
							CommonFunction.selectFromRadio(driver, LocatorType, LocatorValue);

						}
						else if (ObjectType.equalsIgnoreCase("closeBrowser")) {
							CommonFunction.closeBrowser(driver);

						}
						else if (ObjectType.equalsIgnoreCase("moveAndClick")) {
							CommonFunction.moveAndClick(driver, LocatorType, LocatorValue);

						}
						else if (ObjectType.equalsIgnoreCase("displyedClick")) {
							CommonFunction.displyedClick(driver, LocatorType, LocatorValue);

						}
						else if (ObjectType.equalsIgnoreCase("selectRadio")) {
							CommonFunction.selectRadio(driver, LocatorType, LocatorValue, TestData);
						}
						else if (ObjectType.equalsIgnoreCase("addNew")) {
							CommonFunction.addNew(driver, LocatorType, LocatorValue, TestData);
						}
						
						else {
							System.out.println("method missing");
							xl.setCellData(TCModule, j, 5, "fail", outputFilePath);
							Modulestatus="false";
							
						}
						
						xl.setCellData(TCModule, j, 5, "pass", outputFilePath);
						Modulestatus="true";
						
//						if(Modulestatus.equalsIgnoreCase("true")) {
//							xl.setCellData(TCModule, j, 5, "pass", outputFilePath);
//						}
						
						
						
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.err.println("catch part" +" "+ Description);
						xl.setCellData(TCModule, j, 5, "Fail", outputFilePath);
						Modulestatus="false";
						break;
						
					}	
				}
				
				System.out.println(Modulestatus+"new ststs");
				if(Modulestatus.equalsIgnoreCase("true")) {
					xl.setCellData("MasterTestCases", i, 3, "pass", outputFilePath);
				}
				if(Modulestatus.equalsIgnoreCase("false")) {
					xl.setCellData("MasterTestCases", i, 3, "fail", outputFilePath);
				}
				
				
				
				
			}
			
			else {
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputFilePath);
			}
			
			
		}
		
	
	}	
}
