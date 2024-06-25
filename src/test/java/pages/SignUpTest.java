package pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {
    private WebDriver webDriver;
    private SignUp signUp;

    @BeforeEach
    public void precondition() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/register");
        signUp = new SignUp(webDriver);
    }

    @Test
    public void signUpValidCredentials() {
        signUp.fillUsernameField();
        signUp.fillEmailField();
        signUp.fillPasswordField();
        signUp.clickOnSignUp();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:3000/login"));

        String currentURL = webDriver.getCurrentUrl();

        assertEquals("http://localhost:3000/login", currentURL);
    }
}