package pages.authentication;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.authentication.AuthForm;

// TODO only the methods from AuthForm to be used in the future
// TODO below methods remains till all codes refactored where currently used

public class LogIn extends AuthForm {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_USER_USERNAME");
    private final String password = dotenv.get("REPTILE_USER_PASSWORD");
    private final String usernameOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String passwordOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");

//    private WebDriver driver;
//    private WebDriverWait wait;

    public LogIn(WebDriver driver) {
        super(driver);
    }

    public void fillUsernameFieldWithUserCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        inputField.sendKeys(username);
    }

    public void fillPasswordFieldWithUserCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        inputField.sendKeys(password);
    }

    public void fillUsernameFieldWithQuizMasterCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        inputField.sendKeys(usernameOfQuizMaster);
    }

    public void fillPasswordFieldWithQuizMasterCredentials() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        inputField.sendKeys(passwordOfQuizMaster);
    }

    public void clickOnLogIn() {
        WebElement button = driver.findElement(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'LOGIN')]"));
        button.click();
    }
}
