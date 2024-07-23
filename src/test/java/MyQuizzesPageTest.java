import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CreateQuiz;
import pages.LogIn;
import pages.mains.MyQuizzesPage;

import java.time.Duration;

class MyQuizzesPageTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private LogIn logIn;

    @BeforeEach
    public void precondition() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        logIn = new LogIn(webDriver);
        logIn.fillUsernameFieldWithQuizMasterCredentials();
        logIn.fillPasswordFieldWithQuizMasterCredentials();
        logIn.clickOnLogIn();

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Quizzes')]")));

        Thread.sleep(2000);

        webDriver.get("http://localhost:3000/quiz/my");
    }

    @Test
    public void testMyQuizzesPageMethods() throws InterruptedException {
        MyQuizzesPage myQuizzesPage = new MyQuizzesPage(webDriver);

        myQuizzesPage.clickOnEditNthQuiz(2);
        myQuizzesPage.handleAlert();

        Thread.sleep(2000);
    }
}