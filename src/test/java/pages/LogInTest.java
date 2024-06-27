package pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInTest {
    private WebDriver webDriver;
    private LogIn logIn;

    @BeforeEach
    public void precondition() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        logIn = new LogIn(webDriver);
    }

    @Test
    public void logInValidCredentials() {
        logIn.fillUsernameFieldWithUserCredentials();
        logIn.fillPasswordFieldWithUserCredentials();
        logIn.clickOnLogIn();

        String expectedURL = "http://localhost:3000/";
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }
}