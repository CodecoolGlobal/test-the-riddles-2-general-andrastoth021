package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.SignUp;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String email = dotenv.get("REPTILE_QUIZMASTER_EMAIL");
    private final String password = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");
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
        signUp.fillFieldById(username, "user-name");
        signUp.fillFieldById(password, "password");
        signUp.fillFieldById(email, "email");
        signUp.clickOnButton("SIGN UP");

        String expectedURL = "http://localhost:3000/login";
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }
}