import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DSETableScraperTest {

    private WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void scrapeDSEPriceTable() throws IOException {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.shares-table tr")));

        WebElement table = driver.findElement(By.cssSelector("table.shares-table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        FileWriter writer = new FileWriter("dse_share_prices.txt");

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector("th, td"));
            for (WebElement cell : cells) {
                String text = cell.getText().trim().replaceAll("\\s+", " ");
                writer.write(text + " | ");
                System.out.print(text + " | ");
            }
            writer.write("\n");
            System.out.println();
        }

        writer.close();

        System.out.println("\n\u2705 Data written to dse_share_prices.txt\n");

        FileReader fr = new FileReader("dse_share_prices.txt");
        StringBuilder line = new StringBuilder();
        int ch;
        while ((ch = fr.read()) != -1) {
            if (ch == '\n') {
                System.out.println(line);
                line.setLength(0);
            } else if (ch != '\r') {
                line.append((char) ch);
            }
        }
        if (line.length() > 0) {
            System.out.println(line);
        }
        fr.close();
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
