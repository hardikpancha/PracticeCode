package tutorialsninja.register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RF_009 {
	
	@Test
	public void verifyRegistringAccountUsingExistingEmail() {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		driver.findElement(By.name("firstname")).sendKeys("Hardik");
		driver.findElement(By.name("lastname")).sendKeys("Pancha");
		driver.findElement(By.name("email")).sendKeys("hardikpancha@gmail.com");
		driver.findElement(By.name("telephone")).sendKeys("5162378374");
		driver.findElement(By.name("password")).sendKeys("12345");
		driver.findElement(By.name("confirm")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value=0]")).click();
		driver.findElement(By.xpath("//input[@name='agree'][@value=1]")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessage = "Warning: E-Mail Address is already registered!";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(), expectedWarningMessage);
		
		driver.quit();
	}

}
