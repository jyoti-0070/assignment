package Assignment;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
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
import org.testng.Assert;


public class demoqa {

	WebDriver driver;

	@BeforeTest
	public void SetDriver(){
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\JRR\\Downloads\\chromedriver_win32\\chromedriver.exe");	     
		driver = new ChromeDriver();// Object is created - Chrome browser is opened
		driver.manage().window().maximize();
	}

	@Test(priority=1)
	public void selectable()
	{
		driver.get("https://demoqa.com/selectable/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		for(int j=1; j<=7; ++j)
		{
			WebElement element = driver.findElement(By.xpath("//*[@id=\"selectable\"]/li[" +j+ "]"));
			element.click();
			String name= element.getText();
			System.out.println(name);
		}
	}		

	@Test(priority=2)
	public void contact_form() throws AWTException
	{
		driver.get("https://demoqa.com/html-contact-form/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String parent_window=driver.getWindowHandle();

		WebElement element1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/form/input[1]"));
		element1.sendKeys("Jyoti");

		WebElement element2 = driver.findElement(By.xpath("//*[@id=\"lname\"]"));
		element2.sendKeys("Ram");

		WebElement element3 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/form/input[3]"));
		element3.sendKeys("India");

		WebElement element4 = driver.findElement(By.xpath("//*[@id=\"subject\"]"));
		element4.sendKeys("COVID-19");

		WebElement google1 = driver.findElement(By.linkText("Google Link"));
		String url1 = google1.getAttribute("href");

		WebElement google2 = driver.findElement(By.partialLinkText("is here"));
		String url2 = google2.getAttribute("href");

		WebElement element5 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/form/input[4]"));
		element5.click();

		for (int i=0; i<=1; ++i)
		{
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_T);
			rob.keyRelease(KeyEvent.VK_CONTROL);
			rob.keyRelease(KeyEvent.VK_T);		
			ArrayList<String> tabs1 = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window((String) tabs1.get(i));
		}

		driver.get(url1);		
		driver.switchTo().window(parent_window);		
		driver.get(url2);		
	}

	@Test(priority=3)
	public void drag_drop()
	{
		driver.get("https://demoqa.com/droppable/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement From=driver.findElement(By.xpath("//*[@id=\"draggable\"]/p"));	
		WebElement To=driver.findElement(By.xpath("//*[@id=\"droppable\"]"));					
		Actions act=new Actions(driver);					
		act.dragAndDrop(From, To).build().perform();
		WebElement Final=driver.findElement(By.xpath("//*[@id=\"droppable\"]"));	
		String output = Final.getText();
		System.out.println(output);    
		Assert.assertEquals(output, "Dropped!");
	}

	@Test(priority=4)
	public void DOB()
	{
		driver.get("https://demoqa.com/datepicker/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.id("datepicker")).click();



		WebElement btnNext = driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/div/a[2]"));

		WebElement lblYear = driver.findElement(By.className("ui-datepicker-year"));

		WebElement lblMonth = driver.findElement(By.className("ui-datepicker-month"));
		String yr=lblYear.getText();
		String mn=lblMonth.getText();

		String year="2000"; String month="May"; String day="9";       	    
		try{
			while (year != lblYear.getText() )
			{
				if (Integer.parseInt(year) < Integer.parseInt(yr))
				{
					WebElement btnPrevious = driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/div/a[1]/span"));
					btnPrevious.click();
					System.out.println (lblYear.getText());

				}
				else
				{
					btnNext.click();
				}
			}
			while ( lblMonth.getText() != "January")
			{
				WebElement btnPrevious = driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/div/a[1]/span"));
				btnPrevious.click();
				System.out.println (mn);
			}

			while (month != lblMonth.getText())
			{
				btnNext.click();
				System.out.println (mn);
			}
		}
		catch(StaleElementReferenceException e){
			System.out.println("Exception");
		}

		List<WebElement> allDates=driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td"));
		for(WebElement ele:allDates)
		{

			String date=ele.getText();

			if(date.equals(day))
			{
				ele.click();
				break;
			}

		}  

		String op = driver.findElement(By.id("datepicker")).getText();
		System.out.println("DOB is:- " + op);

	}

	@Test(priority=5)
	public void select_menu()
	{
		driver.get("https://demoqa.com/selectmenu/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement speed = driver.findElement(By.xpath("//*[@id=\"speed-button\"]/span[1]"));
		speed.click();
		WebElement speed_select = driver.findElement(By.xpath("//*[@id=\"speed-menu\"]/li[4]"));
		speed_select.click();

		WebElement files = driver.findElement(By.xpath("//*[@id=\"files-button\"]/span[1]"));
		files.click();
		WebElement files_select = driver.findElement(By.xpath("//*[@id=\"files-menu\"]/li[2]"));
		files_select.click();

		WebElement number = driver.findElement(By.xpath("//*[@id=\"number-button\"]/span[1]"));
		number.click();
		WebElement number_select = driver.findElement(By.xpath("//*[@id=\"number-menu\"]/li[4]"));
		number_select.click();

		WebElement title = driver.findElement(By.xpath("//*[@id=\"salutation-button\"]/span[1]"));
		title.click();
		WebElement title_select = driver.findElement(By.xpath("//*[@id=\"salutation-menu\"]/li[3]"));
		title_select.click();

		Assert.assertEquals((driver.findElement(By.id("speed-button"))).getText(), "Fast");
		Assert.assertEquals((driver.findElement(By.id("files-button"))).getText(), "jQuery.js");
		Assert.assertEquals((driver.findElement(By.id("number-button"))).getText(), "4");
		Assert.assertEquals((driver.findElement(By.id("salutation-button"))).getText(), "Mrs.");
	}

	@Test(priority=6)
	public void Rental_Car()
	{
		driver.get("https://demoqa.com/controlgroup/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Rental Car1
		WebElement d1 = driver.findElement(By.xpath("//*[@id=\"car-type-button\"]/span[1]"));
		d1.click();
		WebElement d1_select = driver.findElement(By.xpath("//*[@id=\"car-type-menu\"]/li[4]"));
		d1_select.click();		
		WebElement rd1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[1]/div/label[1]"));
		rd1.click();		
		WebElement ch1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[1]/div/label[3]"));
		ch1.click();
		WebElement no1 = driver.findElement(By.xpath("//*[@id=\"horizontal-spinner\"]"));
		no1.clear();
		no1.sendKeys("5");

		WebElement book1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[1]/div/button"));
		book1.click();



		//Rental Car2
		WebElement d2 = driver.findElement(By.xpath("//*[@id=\"ui-id-8-button\"]/span[1]"));
		d2.click();
		WebElement d2_select = driver.findElement(By.xpath("//*[@id=\"ui-id-8-menu\"]/li[6]"));
		d2_select.click();		
		WebElement rd2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[2]/div/label[2]"));
		rd2.click();		
		WebElement ch2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[2]/div/label[3]"));
		ch2.click();
		WebElement no2 = driver.findElement(By.xpath("//*[@id=\"vertical-spinner\"]"));
		no2.clear();
		no2.sendKeys("2");

		WebElement book2 = driver.findElement(By.xpath("//*[@id=\"book\"]"));
		book2.click();

		
		//Assert Rental1
		Assert.assertEquals((driver.findElement(By.xpath("//*[@id=\"car-type-button\"]"))).getText(), "SUV");
		Assert.assertEquals((driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[1]/div/label[1]"))).getText(), "Standard");
		Assert.assertEquals((driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[1]/div/label[3]"))).getText(), "Insurance");
			
		//Assert Rental2
		Assert.assertEquals((driver.findElement(By.xpath("//*[@id=\"ui-id-8-button\"]"))).getText(), "Truck");
		Assert.assertEquals((driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[2]/div/label[2]"))).getText(), "Automatic");
		Assert.assertEquals((driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/fieldset[2]/div/label[3]"))).getText(), "Insurance");
			
	}

	@AfterTest
	public void closedriver(){
		driver.close();
	}
}
