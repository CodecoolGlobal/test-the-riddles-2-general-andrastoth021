import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LogInPage;
import pages.quiz.MainPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainPageTest extends BaseTest {
    private WebDriverWait wait;
    private LogInPage logInPage;
    private MainPage mainPage;

    @BeforeEach
    public void precondition() {
        driverQuizMaster = new FirefoxDriver();
        driverQuizMaster.get("http://localhost:3000/login");
        wait = new WebDriverWait(driverQuizMaster, Duration.ofSeconds(20));

        logInPage = new LogInPage(driverQuizMaster);
        logInPage.fillUsernameFieldWithQuizMasterCredentials();
        logInPage.fillPasswordFieldWithQuizMasterCredentials();
        logInPage.clickOnLogIn();

        driverQuizMaster.get("http://localhost:3000/");
        mainPage = new MainPage(driverQuizMaster);
    }

    @Test
    public void testNavigatingToGamesPage() {
        mainPage.clickOnGames();

        String expectedURL = "http://localhost:3000/gamelist";
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, driverQuizMaster.getCurrentUrl());
    }

    @Test
    public void testNavigatingToQuizzesPage() {
        mainPage.clickOnQuizzes();

        String expectedURL = "http://localhost:3000/quiz/all";
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, driverQuizMaster.getCurrentUrl());
    }

    @Test
    public void testNavigatingToMyQuizzesPage() {
        mainPage.clickOnMyQuizzes();

        String expectedURL = "http://localhost:3000/quiz/my";
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, driverQuizMaster.getCurrentUrl());
    }

    @AfterEach
    public void postcondition() {
        driverQuizMaster.quit();
    }
}