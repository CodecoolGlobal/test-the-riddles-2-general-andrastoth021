import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LogIn;
import pages.mains.MainPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainPageTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private LogIn logIn;
    private MainPage mainPage;

    @BeforeEach
    public void precondition() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        logIn = new LogIn(webDriver);
        logIn.fillUsernameFieldWithQuizMasterCredentials();
        logIn.fillPasswordFieldWithQuizMasterCredentials();
        logIn.clickOnLogIn();

        webDriver.get("http://localhost:3000/");
        mainPage = new MainPage(webDriver);
    }

    @Test
    public void testNavigatingToGamesPage() {
        mainPage.clickOnGames();

        String expectedURL = "http://localhost:3000/gamelist";
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }

    @Test
    public void testNavigatingToQuizzesPage() {
        mainPage.clickOnQuizzes();

        String expectedURL = "http://localhost:3000/quiz/all";
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }

    @Test
    public void testNavigatingToMyQuizzesPage() {
        mainPage.clickOnMyQuizzes();

        String expectedURL = "http://localhost:3000/quiz/my";
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }

    @AfterEach
    public void postcondition() {
        webDriver.quit();
    }
}