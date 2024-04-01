import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class TestClass {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void testMainTask() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        //Test EN lang
        driver.get("https://www.playtech.ee");
        driver.manage().window().maximize();

        actions.moveByOffset(680, 40).click().perform();
        boolean IsGlobalPresenceShown = false;

        if (driver.getPageSource().contains("Global presence")) IsGlobalPresenceShown = true;

        //Test ET lang
        driver.get("https://www.playtech.ee/et");

        WebElement meistTab = driver.findElement(By.xpath("//a[text()='Meist']"));
        meistTab.click();
        boolean IsMeieAsukohadShown = false;

        if (driver.getPageSource().contains("Meie asukohad")) IsMeieAsukohadShown = true;

        driver.quit();

        //write result to txt file
        try {
            FileWriter writer = new FileWriter("verification_result.txt");
            if (IsGlobalPresenceShown) {
                writer.write("Global presence is shown on the page.");
            } else {
                writer.write("Global presence is not shown on the page.");
            }
            if (IsMeieAsukohadShown) {
                writer.write("\nMeie asukohad is shown on the page.");
            } else {
                writer.write("\nMeie asukohad is not shown on the page.");
            }
            writer.close();

            // Check if the file was created and read its content
            File file = new File("verification_result.txt");
            assertTrue("verification_result.txt was not created.", file.exists());
            String content = new String(Files.readAllBytes(Paths.get("verification_result.txt")));
            System.out.println("Content of verification_result.txt: " + content);
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the verification result to a text file.");
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}