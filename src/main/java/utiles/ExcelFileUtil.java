package utiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.util.HSSFColor.GREEN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	
	Workbook wb;
	
	//constructor for reading excel path
	public ExcelFileUtil(String excelpath) throws Throwable 
	{
		FileInputStream fi = new FileInputStream(excelpath);
		wb = WorkbookFactory.create(fi);
	}
	
	
	//Counting rows
	public int getRowCount(String sheetName) 
	{
		return wb.getSheet(sheetName).getLastRowNum();
	}
	
	// Reading cell data (int&  String both)
	public String getCellData(String sheetName, int row, int column) 
	{
	String data = "";
	
			if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
				int celldata = (int)wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
				data = String.valueOf(celldata);
				System.out.println(data);
			}
			else {
				data = wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
			}
		return data;
	
	}
	
	//sending data to cell
	public void setCellData(String sheetName, int row, int column, String status, String writeexcel) throws Throwable
	{
		// getting sheet
		Sheet ws = wb.getSheet(sheetName);
		// getting row
		Row rowNum = ws.getRow(row);
		// creating cell
		Cell cell = rowNum.createCell(column);
		//sending data to cell
		cell.setCellValue(status);
		
		if(status.equalsIgnoreCase("Pass")) 
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
			
		}
		else if (status.equalsIgnoreCase("Fail")) 
		{
			CellStyle style = wb.createCellStyle();
			
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);	
		}
		else if (status.equalsIgnoreCase("Blocked")) 
		{
			CellStyle style = wb.createCellStyle();
			
			Font font = wb.createFont();
			font.setColor(IndexedColors.SKY_BLUE.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		
		FileOutputStream fo = new FileOutputStream(writeexcel);
		wb.write(fo);
		
	}

	
	
	
	
	
	
//	public static void main(String[] args) throws Throwable {
//		
//		ExcelFileUtil xl = new ExcelFileUtil("D:\\Prime Batch\\Mavan_MMT\\FileInput\\DataEngine.xlsx");
//		
//		int rc = xl.getRowCount("FlightTicketBooking");
//		System.out.println(rc);
//		
//		for(int i=1; i<=rc; i++) {
//			
//			String username = xl.getCellData("FlightTicketBooking", i, 0);
//			String password = xl.getCellData("FlightTicketBooking", i, 1);
//			System.out.println(username +"   "+password);
//			xl.setCellData("FlightTicketBooking", i, 5, "Pass", "D:\\Prime Batch\\Mavan_MMT\\FileOutput\\Res.xlsx");
//			xl.setCellData("MasterTestCases", i, 3, "Fail", "D:\\Prime Batch\\Mavan_MMT\\FileOutput\\Res.xlsx");
//			xl.setCellData("MasterTestCases", i, 3, "Blocked", "D:\\Prime Batch\\Mavan_MMT\\FileOutput\\Res.xlsx");
//		}
		

//	}

}
