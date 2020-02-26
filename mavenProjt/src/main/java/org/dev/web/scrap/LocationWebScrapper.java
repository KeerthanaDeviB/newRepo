package org.dev.web.scrap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LocationWebScrapper {

	Path currentRelativePath = Paths.get("");
	String getprojtPath = currentRelativePath.toAbsolutePath().toString();

	public WebDriver driver;

	public String baseUrl = "https://www.chick-fil-a.com/Locations";
	String driverPath = "C:\\Users\\ke291856\\Downloads\\chromedriver_win32/chromedriver.exe";

	String getElement = "";

	@Test
	public void webScraping() throws IOException {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();		
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		String siteTitle = driver.getTitle();

		String iterateLocation[] ;
		
		WebElement iframeSwitch = driver.findElement(By.id("locatoriframe"));
		driver.switchTo().frame(iframeSwitch);
		System.out.println("Switched");
		
		WebElement getWholeData = driver.findElement(By.xpath("//*[@id='js-resultList']"));		
		
		WebElement location = driver.findElement(By.className("Teaser-title"));
		WebElement address = driver.findElement(By.className("Teaser-address"));		


		FileWriter writer = new FileWriter(getprojtPath + "/OutputFile.csv", false);
		writer.append("Title: ");
		writer.append(siteTitle);
		writer.append("\n\n");		
		writer.append("Location Name");
		writer.append(',');
		writer.append("City");
		writer.append(',');
		writer.append("Address");
		writer.append(',');
		writer.append("Location Link");		
		writer.append("\n");
		
		writer.append(location.getText());
		writer.append(',');	
		
		writer.append(',');
		writer.append(address.getText());
		writer.append(',');
		
		writer.append(',');
		
		writer.append('\n');
		writer.flush();
		writer.close();

		driver.close();
	}

}