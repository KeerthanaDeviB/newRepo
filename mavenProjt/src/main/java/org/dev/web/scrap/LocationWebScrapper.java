
    
package org.dev.web.scrap;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class LocationWebScrapper {
  private static final Logger LOGGER = Logger.getLogger(LocationWebScrapper.class.getName());
  private String getProjectPath = Paths.get("").toAbsolutePath().toString();
  private static final String BASEURL = "https://www.chick-fil-a.com/Locations";
  private String driverPath = "chromedriver_win32/chromedriver.exe";
  @Test
  public void webScraping() throws IOException {
    System.setProperty("webdriver.chrome.driver", driverPath);
    WebDriver driver = new ChromeDriver();
    driver.get(BASEURL);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    FileWriter writer = null;
    try {
      writer = new FileWriter(getProjectPath + "/OutputFile.csv", false);
      writer.append("Title: \t");
      writer.append(driver.getTitle());
      writer.append("\n\n");        
      writer.append("Location Name");
      writer.append(',');
      writer.append("Address");
      writer.append(',');
      writer.append("City");
      writer.append(',');
      writer.append("State");
      writer.append(',');
      writer.append("Location Details");
      writer.append("\n");
      WebElement iframeSwitch = driver.findElement(By.id("locatoriframe"));
      driver.switchTo().frame(iframeSwitch);
      for (int i = 1; i < 5; i++) {
        driver.findElement(By.xpath("//*[@class='ResultList-moreButton']")).click();
        Thread.sleep(5000);
      }
      List<WebElement> getWholeData = driver.findElements(By.xpath("//*[@class='ResultList-item ResultList-item--ordered js-location-result']"));        
      for(WebElement element:getWholeData) {
        writer.append(element.findElement(By.className("LocationName-geo")).getText());
        writer.append(',');    
        writer.append(element.findElement(By.className("c-address-street-1")).getText());
        writer.append(',');
        writer.append(element.findElement(By.className("c-address-city")).getText());
        writer.append(',');
        writer.append(element.findElement(By.className("c-address-state")).getText());
        writer.append(',');
        writer.append(StringUtils.mid(element.findElement(By.className("Teaser-cta")).getAttribute("onclick"), 13, 89));
        writer.append(',');
        writer.append('\n');
      }
    }
    catch (IOException e) {
      LOGGER.info("IOException occured" + e.toString());
    }
    catch (InterruptedException e) {
      LOGGER.info("InterruptedException occured" + e.toString());
      Thread.currentThread().interrupt();
    }
    writer.flush();
    writer.close();
    driver.close();
  }
}

