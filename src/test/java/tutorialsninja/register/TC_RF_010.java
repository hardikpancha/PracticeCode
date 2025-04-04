package tutorialsninja.register;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.CommonUtils;

public class TC_RF_010 {

	@Test
	public void verifyRegisteringAccountUsingInvalidEmail() throws InterruptedException, IOException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo");

		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();

		driver.findElement(By.id("input-firstname")).sendKeys("Hardik");
		driver.findElement(By.id("input-lastname")).sendKeys("Pancha");
		driver.findElement(By.id("input-email")).sendKeys("hpancha");
		driver.findElement(By.id("input-telephone")).sendKeys("1234567890");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		Thread.sleep(2000);
		//Scenario 1: hpancha
		File srcScreenshot1 = driver.findElement(By.xpath("//form[@class='form-horizontal']"))
				.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcScreenshot1, new File(System.getProperty("user.dir") + "//Screenshots//sc1Actual.png"));

		Thread.sleep(2000);
		Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir")+"//Screenshots//sc1Actual.png",System.getProperty("user.dir")+"//Screenshots//sc1Expected.png"));
		
		//Scenario 2: hpancha@
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys("hpancha@");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		Thread.sleep(2000);
		File srcScreenshot2 = driver.findElement(By.xpath("//form[@class='form-horizontal']"))
				.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcScreenshot2, new File(System.getProperty("user.dir") + "//Screenshots//sc2Actual.png"));
		
		Thread.sleep(2000);
		Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir")+"//Screenshots//sc2Actual.png",System.getProperty("user.dir")+"//Screenshots//sc2Expected.png"));
		
		//Scenario 3: hpancha@gmail
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys("hpancha@gmail");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessage = "E-Mail Address does not appear to be valid!";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='text-danger'][text()='E-Mail Address does not appear to be valid!']")).getText(), expectedWarningMessage);
		
		//Scenario 4: hpancha@gmail.
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys("hpancha@gmail.");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		Thread.sleep(2000);
		File srcScreenshot3 = driver.findElement(By.xpath("//form[@class='form-horizontal']"))
				.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcScreenshot3, new File(System.getProperty("user.dir") + "//Screenshots//sc3Actual.png"));
		
		Thread.sleep(2000);
		Assert.assertFalse(CommonUtils.compareTwoScreenshots(System.getProperty("user.dir")+"//Screenshots//sc3Actual.png",System.getProperty("user.dir")+"//Screenshots//sc3Expected.png"));
		
		driver.quit();

	}

}
