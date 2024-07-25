import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LogInPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInPageTest {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_USER_USERNAME");
    private final String password = dotenv.get("REPTILE_USER_PASSWORD");
    private final String usernameOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String passwordOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");
    private WebDriver webDriver;
    private LogInPage logInPage;

    @BeforeEach
    public void precondition() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        logInPage = new LogInPage(webDriver);
    }

    @Test
    public void logInValidCredentials() {
        logInPage.fillFieldById(username, "user-name");
        logInPage.fillFieldById(password, "password");
        logInPage.clickOnButton("LOGIN");

        String expectedURL = "http://localhost:3000/";
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }
}