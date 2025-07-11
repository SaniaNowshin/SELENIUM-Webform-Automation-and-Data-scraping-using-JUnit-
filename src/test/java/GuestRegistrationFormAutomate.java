import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


import java.util.List;
import java.util.regex.Pattern;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuestRegistrationFormAutomate {

    private WebDriver driver;

    @BeforeAll
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
    }

    @Test
    public void GuestRegistration() throws InterruptedException {

        //First name, last name, email
        driver.findElement(By.id("first_name")).sendKeys("Sania");
        driver.findElement(By.id("last_name")).sendKeys("Nowshin");
        driver.findElement(By.id("user_email")).sendKeys("sania@gmail.com");

        // Selecting Gender
        List<WebElement> genderChoose = driver.findElements(By.className("radio"));
        genderChoose.get(1).click();

        //user password
        driver.findElement(By.id("user_pass")).sendKeys("helloJava!@#56");

        //Date of Birth
        driver.findElement(By.xpath("//div[@id='date_box_1665628538_field']//input[1]")).click();
        //month
        Select select = new Select(driver.findElement(By.className("flatpickr-monthDropdown-months")));
        select.selectByVisibleText("November");
        //year
        WebElement yearElement = driver.findElement(By.className("cur-year"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        yearElement.sendKeys(currentDate);
        yearElement.click();
        yearElement.sendKeys(Keys.BACK_SPACE);
        yearElement.sendKeys("2000");
        //date
        WebElement dateElement = driver.findElement(By.xpath("//span[text()='28']"));
        dateElement.click();

        // Nationality
        driver.findElement(By.id("input_box_1665629217")).sendKeys("Bangladeshi");

        // Phone Number
        WebElement phoneInput = driver.findElement(By.id("phone_1665627880"));
        Actions actions = new Actions(driver);
        actions.click(phoneInput)
                .sendKeys("233")
                .sendKeys("333")
                .sendKeys("4444")
                .perform();

        // Country
        Select countryDropdown = new Select(driver.findElement(By.id("country_1665629257")));
        countryDropdown.selectByVisibleText("Bangladesh");

        //emergency contact
        WebElement emergencyContact = driver.findElement(By.id("phone_1665627865"));
        Actions action = new Actions(driver);
        action.click(emergencyContact)
                .sendKeys("234")
                .sendKeys("333")
                .sendKeys("4494")
                .perform();


        // Agree to Terms and Conditions (Checkbox)
        WebElement termsCheckbox = driver.findElement(By.id("privacy_policy_1665633140"));
        termsCheckbox.click();

        // Submit the form
        driver.findElement(By.className("ur-submit-button")).click();
        // Step 3: Assert registration success

        WebElement successMessage = driver.findElement(By.id("ur-submit-message-node"));
        String successText = successMessage.getText();
        Assertions.assertTrue(successText.contains("User successfully registered."));
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}

