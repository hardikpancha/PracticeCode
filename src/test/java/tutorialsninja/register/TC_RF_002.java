package tutorialsninja.register;

import java.time.Duration;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RF_002 {

	@Test
	public void verifyConfirmationEmail() {

		WebDriver driver = new SafariDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://www.amazon.com");
		
		driver.findElement(By.xpath("//*[@id=\"redir-stay-at-www\"]/span[2]/img")).click();
		driver.findElement(By.xpath("//span[text()='Hello, sign in']")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Need help?')]")));
		element.click();
		
		driver.findElement(By.id("auth-fpp-link-bottom")).click();
		
		driver.findElement(By.id("ap_email")).click();
		driver.findElement(By.id("ap_email")).sendKeys("hardikpancha22@yahoo.in");
		driver.findElement(By.xpath("//input[@id='continue']")).click();
		
		String email = "hardikpancha22@yahoo.in";
		String appPasscode = "wjmd tejz kuld bfdd";
		String link1 = null;
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Your Yahoo email and App password
		String host = "imap.mail.yahoo.com";
		String username = email; // Replace with your Yahoo email
		String password = appPasscode; // Replace with your Yahoo app password
		String port = "993";
		String expectedSubject = "amazon.com: Password recovery";
        String expectedFromEmail = "\"amazon.com\" <account-update@amazon.com>";
        String expectedBodyContent = "Someone is attempting to reset the password of your account.";

		// Set up the properties for the IMAP connection
		Properties properties = new Properties();
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);
		properties.put("mail.imap.ssl.enable", "true");

		// Create a session with the given properties
		Session session = Session.getDefaultInstance(properties);

		try {
			// Connect to the email server using IMAP
			Store store = session.getStore("imap");
			store.connect(host,username, password);

			// Open the inbox folder in read-only mode
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			// Search for unread emails
            FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = inbox.search(unseenFlagTerm);

         // Loop through unread emails
            for (Message message : messages) {
                // Get the subject of the email
                String subject = message.getSubject();

                // Get the sender's email address
                Address[] fromAddresses = message.getFrom();
                String from = fromAddresses != null ? fromAddresses[0].toString() : "Unknown";

                // Get the body of the email (handles both text and HTML emails)
                String body = getTextFromMessage(message);
                
                Assert.assertEquals(message.getSubject(),expectedSubject);
                Assert.assertEquals(message.getFrom()[0].toString(), expectedFromEmail);
                String actualEmailBody = getTextFromMessage(message);
                Assert.assertTrue(actualEmailBody.contains(expectedBodyContent));

                // Print out the details
                System.out.println("Subject: " + subject);
                System.out.println("From: " + from);
                System.out.println("Body: " + body);
                System.out.println("-------------------------------");
                
                String[] ar = body.split("into a browser to view.<br><br>");
                String linkpart = ar[1];
                
                String[] ar1 = linkpart.split("</p>");
                
                link1 = ar1[0].trim();
                System.out.println("Link in Email: "+link1);
                
            }

			// Close the inbox folder and the store
			inbox.close(false);
			store.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		driver.navigate().to(link1);
		Assert.assertTrue(driver.findElement(By.name("customerResponseDenyButton")).isDisplayed());
		if (driver.findElement(By.name("customerResponseDenyButton")).isDisplayed()) {
			System.out.println("Link in the email has taken to the correct page");
			Assert.assertTrue(driver.findElement(By.name("customerResponseDenyButton")).isDisplayed());
		}else {
			
			System.out.println("Link in the email has not taken to the correct page");
		}
			
		driver.quit();
	}

	// Helper method to extract the body text from the email
	private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("text/html")) {      // changing to html
            result = message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
            result = (String) message.getContent();
        } else if (message.getContent() instanceof Multipart) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart part = multipart.getBodyPart(i);
                if (part.isMimeType("text/html")) {   // changing to html
                    result = part.getContent().toString();
                    break;
                } else if (part.isMimeType("text/html")) {
                    result = part.getContent().toString();
                }
            }
        }
        return result;

	}

}
