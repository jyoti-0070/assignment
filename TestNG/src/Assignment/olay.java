package Assignment;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.tecacet.jflat.excel.JxlExcelReader;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.Assert;

public class olay {

	WebDriver driver;

	@BeforeTest
	public void SetDriver() throws IOException, BiffException{
		
		
				
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\JRR\\Downloads\\chromedriver_win32\\chromedriver.exe");	     
		driver = new ChromeDriver();// Object is created - Chrome browser is opened
		driver.manage().window().maximize();
	}

	@Test(priority=1)
	public void Profile_Creation() throws IOException, ParseException, BiffException
	{
		driver.get("https://www.olay.co.uk/en-gb");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Json File
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("C:\\Users\\Public\\json_input_olay.json"));
		JSONObject jsonObject = (JSONObject)obj;

		for(int i=1; i<=4; ++i)
		{
			String email = (String)jsonObject.get("Email" +i);
			String password = (String)jsonObject.get("Password" +i);
			String dy = (String)jsonObject.get("Day" +i);
			String mnth = (String)jsonObject.get("Month" +i);
			String yr = (String)jsonObject.get("Year" +i);
			registration_uk(email, password, dy, mnth, yr);  
		}
		
		//Excel File		
		File exceFile = new File("C:\\Users\\Public\\excel_olay1.xls");
		Workbook excelWorkBook = Workbook.getWorkbook(exceFile);
		Sheet excelSheet = excelWorkBook.getSheet(1);
		int columns = excelSheet.getColumns();
		int rows = excelSheet.getRows();
		String f_name=null, l_name=null, email=null, pwd=null;
		for (int r = 1; r < 2; r++)
		{			
				 f_name = excelSheet.getCell(0, r).getContents();
				 l_name = excelSheet.getCell(1, r).getContents();
				 email = excelSheet.getCell(2, r).getContents();
				 pwd = excelSheet.getCell(3, r).getContents();	
				 registration_germany(f_name, l_name, email, pwd );
		}
	}	

	@Test(priority=2)
	public void sign_in()
	{
		//UK English
		driver.get("https://www.olay.co.uk/en-gb");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		signin_call();
		//Germany
		driver.get("https://www.olaz.de/de-de");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		signin_call();
		//Spain
		driver.get("https://www.olay.es/es-es");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		signin_call();
	}

	public void signin_call()
	{
		WebElement signin = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_pnlCRMHeaderLink\"]/div/a[1]"));
		signin.click();
		WebElement email_id = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_username\"]"));
		email_id.sendKeys("jrr461@gmail.com");
		WebElement pwd = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_password\"]"));
		pwd.sendKeys("jrr461gmail");
		//WebElement profile = driver.findElement(By.className("pc_btn").className("pc_btn button logincta"));
		//profile.click();

		////*[@id="phdesktopbody_0_Container"]/div[4]
	}

	public void registration_uk(String email, String password, String dy, String mnth, String yr)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement register = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_pnlCRMHeaderLink\"]/div/a[2]"));
		register.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement email_id = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[emails][0][address]\"]"));
		email_id.sendKeys(email);
		WebElement pwd = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[password][password]\"]"));
		pwd.sendKeys(password);
		WebElement pwd_cnfrm = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[password][confirm]\"]"));
		pwd_cnfrm.sendKeys(password);
		Select day = new Select(driver.findElement(By.id("phdesktopbody_0_grs_consumer[birthdate][day]")));
		day.selectByVisibleText(dy);			
		Select month = new Select(driver.findElement(By.id("phdesktopbody_0_grs_consumer[birthdate][month]")));
		month.selectByVisibleText(mnth);			
		Select year = new Select(driver.findElement(By.id("phdesktopbody_0_grs_consumer[birthdate][year]")));
		year.selectByVisibleText(yr);
		js.executeScript("window.scrollBy(0,1000)");
		WebElement profile = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_submit\"]"));
		profile.click();		
		WebElement signout = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_LogOffLink\"]"));
		signout.click();
		WebElement signout_confirm = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_anchrContinue\"]"));
		signout_confirm.click();
	}

	public void registration_germany(String f_name, String l_name, String email, String pwd1)
	{

		driver.get("https://www.olaz.de/de-de");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement register = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_pnlCRMHeaderLink\"]/div/a[2]"));
		register.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		WebElement gender = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_imgmale\"]"));
		gender.click();
		js.executeScript("window.scrollBy(0,400)");
		WebElement first_name = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_consumer[firstname]\"]"));
		first_name.sendKeys(f_name);
		WebElement last_name = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_consumer[lastname]\"]"));
		last_name.sendKeys(l_name);		
		WebElement email_id = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[emails][0][address]\"]"));
		email_id.sendKeys(email);
		WebElement pwd = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[password][password]\"]"));
		pwd.sendKeys(pwd1);
		WebElement pwd_cnfrm = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[password][confirm]\"]"));
		pwd_cnfrm.sendKeys(pwd1);
		Select day = new Select(driver.findElement(By.id("phdesktopbody_0_grs_consumer[birthdate][day]")));
		day.selectByVisibleText("1");			
		Select month = new Select(driver.findElement(By.id("phdesktopbody_0_grs_consumer[birthdate][month]")));
		month.selectByVisibleText("2");			
		Select year = new Select(driver.findElement(By.id("phdesktopbody_0_grs_consumer[birthdate][year]")));
		year.selectByVisibleText("2002");
		js.executeScript("window.scrollBy(0,600)");

		Select country = new Select(driver.findElement(By.id("//*[@id=\"phdesktopbody_0_labelgrs_account[addresses][0][country]\"]")));
		country.selectByVisibleText("Deutschland");			
		WebElement h_no = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_labelgrs_account[addresses][0][line1]\"]"));
		h_no.sendKeys("1427");
		WebElement postal = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_grs_account[addresses][0][postalarea]\"]"));
		postal.sendKeys("758001");
		WebElement location = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_labelgrs_account[addresses][0][city]\"]"));
		location.sendKeys("Milwaukee");

		WebElement profile = driver.findElement(By.xpath("//*[@id=\"phdesktopbody_0_submit\"]"));
		profile.click();		
		WebElement signout = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_LogOffLink\"]"));
		signout.click();
		WebElement signout_confirm = driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_anchrContinue\"]"));
		signout_confirm.click();
	}
	@AfterTest
	public void closedriver(){
		driver.close();
	}
}
