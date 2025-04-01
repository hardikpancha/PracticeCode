package tutorialsninja.register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.CommonUtils;

public class TC_RF_006 {
	
	@Test
	public void verifyRegisteringAccountBySayingNoToNewsletter() {
	
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		driver.findElement(By.name("firstname")).sendKeys("Hardik");
		driver.findElement(By.name("lastname")).sendKeys("Pancha");
		driver.findElement(By.name("email")).sendKeys(CommonUtils.generateBrandNewEmail());
		driver.findElement(By.name("telephone")).sendKeys("5162378374");
		driver.findElement(By.name("password")).sendKeys("12345");
		driver.findElement(By.name("confirm")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value=0]")).click();
		driver.findElement(By.xpath("//input[@name='agree'][@value=1]")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		driver.findElement(By.linkText("Continue")).click();
		driver.findElement(By.linkText("Subscribe / unsubscribe to newsletter")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Newsletter']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='newsletter'][@value='0']")).isSelected());
		
		driver.quit();
	
	}

}
