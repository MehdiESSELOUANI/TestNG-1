package example;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.google.common.io.Files;

import Utility.*; 


public class TestNGCases {

	static WebDriver driver; // Selenium control driver
    private static String baseUrl; // baseUrl of Website Guru99

	@DataProvider(name = "Excel")
	public Object[][] testData() throws Exception {
		return Util.getDataFromExcel(Util.FILE_PATH, Util.SHEET_NAME,
				Util.TABLE_NAME);
	}
    
	@DataProvider(name = "Object")
	public Object[][] testData2() {

		Object[][] data = new Object[4][2];

		// 1st row
		data[0][0] = Util.USER_NAME;
		data[0][1] = Util.PASSWD;
		//2nd row
		data[1][0] = "invalid";
		data[1][1] = "valid";
		//3rd row
		data[2][0] = "valid";
		data[2][1] = "invalid";
		//4th row
		data[3][0] = "invalid";
		data[3][1] = "invalid";
		return data;
	}
	
	
    @BeforeMethod
    public void setUp() throws Exception {
    	
    	System.setProperty("webdriver.chrome.driver", Util.CHROM_PATH);
		driver = new ChromeDriver();
    	baseUrl = Util.BASE_URL;
		driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(baseUrl + "/V4/");
	
    }

  
    // Test Data : Data-provider => EXCEL
    
    @Test(dataProvider = "Excel")
    public void testLogin(String username, String password) throws Exception {

	String actualTitle;
	String actualBoxtitle;
	String MangerID;
	    
	    // Enter username
	    driver.findElement(By.name("uid")).clear();
	    driver.findElement(By.name("uid")).sendKeys(username);

	    // Enter Password
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys(password);

	    // Click Login
	    driver.findElement(By.name("btnLogin")).click();
       
	    try{ 
	    
	       	Alert alt = driver.switchTo().alert();
			actualBoxtitle = alt.getText(); // get content of the Alter Message
			alt.accept();
			
			Assert.assertEquals(actualBoxtitle,Util.EXPECT_ERROR);
			
		}    
	    catch (NoAlertPresentException Ex){ 
	    	actualTitle = driver.getTitle();
	    	MangerID = driver.findElement(By.tagName("tbody")).getText();
	    	
	    	// Extract the dynamic text mngrXXXX on page		
			String[] parts = MangerID.split(Util.PATTERN);
			String dynamicText = parts[1];
			
			// Check that the dynamic text is of pattern mngrXXXX
			
			// remain stores the "XXXX" in pattern mngrXXXX
			String remain = dynamicText.substring(dynamicText.length() - 4);
		
			
	    	Assert.assertEquals(actualTitle,Util.EXPECT_TITLE);
	    	
	    	// Take a screen shot
	    	takeScreenshot("ManagerID1");
	    	
			// First 4 characters must be "mngr"
	    	Assert.assertEquals(dynamicText.substring(1, 5),Util.FIRST_PATTERN);
	    	
			// Check remain string must be numbers;
	    	Assert.assertTrue(remain.matches(Util.SECOND_PATTERN));
			
        } 
	    
	    
	    
	   
	    } 
       
    
// Test Data : Data-provider => Object
    
    @Test(dataProvider = "Object")
    public void testLogin2(String username, String password) throws Exception {

	String actualTitle;
	String actualBoxtitle;
	String MangerID;
	    
	    // Enter username
	    driver.findElement(By.name("uid")).clear();
	    driver.findElement(By.name("uid")).sendKeys(username);

	    // Enter Password
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys(password);

	    // Click Login
	    driver.findElement(By.name("btnLogin")).click();
       
	    try{ 
	    
	       	Alert alt = driver.switchTo().alert();
			actualBoxtitle = alt.getText(); // get content of the Alter Message
			alt.accept();
			
			Assert.assertEquals(actualBoxtitle,Util.EXPECT_ERROR);
			
		}    
	    catch (NoAlertPresentException Ex){ 
	    	actualTitle = driver.getTitle();
	    	MangerID = driver.findElement(By.tagName("tbody")).getText();
	    	
	    	// Extract the dynamic text mngrXXXX on page		
			String[] parts = MangerID.split(Util.PATTERN);
			String dynamicText = parts[1];
			
			// Check that the dynamic text is of pattern mngrXXXX
			
			// remain stores the "XXXX" in pattern mngrXXXX
			String remain = dynamicText.substring(dynamicText.length() - 4);
		
			
	    	Assert.assertEquals(actualTitle,Util.EXPECT_TITLE);
	    	
	    	// Take a screen shot
	    	takeScreenshot("ManagerID2");
	    	
			// First 4 characters must be "mngr"
	    	Assert.assertEquals(dynamicText.substring(1, 5),Util.FIRST_PATTERN);
	    	
			// Check remain string must be numbers;
	    	Assert.assertTrue(remain.matches(Util.SECOND_PATTERN));
			
	    	
        } 
	    
	    
	    
	   
	    } 
    @AfterMethod
    public void quitBrowser() {
    	driver.close();
    }
    
		
    
    public static void takeScreenshot(String fileName) throws IOException{
		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// now copy the screenshot to desired location using copyFile //method
		Files.copy(src, 
				new File(Util.ScreenShoot_PATH + fileName +".png"));

	}
    
    
	    }
	
	  




