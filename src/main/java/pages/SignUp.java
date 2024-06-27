package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUp {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String email = dotenv.get("REPTILE_QUIZMASTER_EMAIL");
    private final String password = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");

    private final String usernameOfUser = dotenv.get("REPTILE_USER_USERNAME");
    private final String emailOfUser = dotenv.get("REPTILE_USER_EMAIL");
    private final String passwordOfUser = dotenv.get("REPTILE_USER_PASSWORD");

    private WebDriver driver;
    private WebDriverWait wait;

    public SignUp(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void fillUsernameField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        inputField.sendKeys(username);
    }

    public void fillEmailField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        inputField.sendKeys(email);
    }

    public void fillPasswordField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        inputField.sendKeys(password);
    }

    public void fillUsernameFieldWithUserCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        inputField.sendKeys(usernameOfUser);
    }

    public void fillEmailFieldWithUserCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        inputField.sendKeys(emailOfUser);
    }

    public void fillPasswordFieldWithUserCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        inputField.sendKeys(passwordOfUser);
    }

    public void clickOnSignUp() {
        WebElement button = driver.findElement(By.xpath("//button[contains(@class, 'mr-4 mt-6 text-white w-full font-bold p-4 bg-green-800') and contains(text(), 'SIGN UP')]"));
        button.click();
    }
}
