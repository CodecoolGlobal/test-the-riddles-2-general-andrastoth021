package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SignUp {
    private Dotenv dotenv = Dotenv.configure().load();
    private String signupUsername = dotenv.get("SIGNUP_USERNAME");
    private String signupEmail = dotenv.get("SIGNUP_EMAIL");
    private String signupPassword = dotenv.get("SIGNUP_PASSWORD");

    private WebDriver driver;
    private WebDriverWait wait;

    public SignUp(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void fillUsernameField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        inputField.sendKeys(signupUsername);
    }

    public void fillEmailField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        inputField.sendKeys(signupEmail);
    }

    public void fillPasswordField() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        inputField.sendKeys(signupPassword);
    }

    public void clickOnSignUp() {
        WebElement button = driver.findElement(By.className("bg-green-800"));
        button.click();

//        List<WebElement> buttons = driver.findElements(By.className("bg-green-800"));
//        for (WebElement button : buttons) {
//            if (button.getText().equalsIgnoreCase("Sign Up")) {
//                button.click();
//                break;
//            }
//        }
    }
}
