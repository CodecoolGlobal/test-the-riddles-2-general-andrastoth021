package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogIn {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_USER_USERNAME");
    private final String password = dotenv.get("REPTILE_USER_PASSWORD");

    private WebDriver driver;
    private WebDriverWait wait;

    public LogIn(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void fillUsernameField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        inputField.sendKeys(username);
    }

    public void fillPasswordField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        inputField.sendKeys(password);
    }

    public void clickOnLogIn() {
        WebElement button = driver.findElement(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'LOGIN')]"));
        button.click();
    }
}
