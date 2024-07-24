import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LogIn;
import pages.mains.MyQuizzesPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyQuizzesPageTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private LogIn logIn;
    private MyQuizzesPage myQuizzesPage;

    @BeforeEach
    public void precondition() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        logIn = new LogIn(webDriver);
        logIn.fillUsernameFieldWithQuizMasterCredentials();
        logIn.fillPasswordFieldWithQuizMasterCredentials();
        logIn.clickOnLogIn();

        webDriver.get("http://localhost:3000/quiz/my");
        myQuizzesPage = new MyQuizzesPage(webDriver);
    }

    @Test
    public void testAddingNewQuiz() throws InterruptedException {
        // TODO
    }

    @Test
    public void testEditingExistingQuiz() throws InterruptedException {
        // TODO
    }

    @Test
    public void testDeletingExistingQuiz() throws InterruptedException {
        myQuizzesPage.clickOnDeleteNthQuiz(1);
        myQuizzesPage.handleAlert();
    }

    @AfterEach
    public void postcondition() {
        webDriver.quit();
    }
}