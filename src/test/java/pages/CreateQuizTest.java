package pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateQuizTest {
    private WebDriver webDriver;
    private LogIn logIn;
    private CreateQuiz createQuiz;

    @BeforeEach
    public void precondition() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");

        // Bejelentkezés
        logIn = new LogIn(webDriver);
        logIn.fillUsernameField();
        logIn.fillPasswordField();
        logIn.clickOnLogIn();

        // Várakozás a főoldal betöltésére
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Quizzes')]")));

        // Probléma volt. Alkalmazzuk a Thread.sleep() hívást ideiglenesen
        try {
            Thread.sleep(2000); // Várakozás 5 másodpercig
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Navigálás a Quizzes oldalra
        createQuiz = new CreateQuiz(webDriver);
        webDriver.get("http://localhost:3000");
    }

    @Test
    public void createQuizWithValidInputFields() {
        createQuiz.clickOnQuizzesMenu();
        createQuiz.clickOnAddQuizButton();
        String quizTitle = "Test Quiz";
        createQuiz.enterQuizTitle(quizTitle);
        createQuiz.clickOnSaveQuizButton();

        // Probléma volt. Kezeljük a felugró ablakot 2 másodperc várakozással. Így elmenti névvel.
        createQuiz.handleAlert();

        // Kattintás a "My Quizzes" gombra
        createQuiz.clickOnMyQuizzes();

        // Ellenőrizzük, hogy az új quiz létrejött-e. A tesztek során volt, hogy név nélkül mentette.
        assertTrue(createQuiz.isQuizPresent(quizTitle));

        webDriver.quit();
    }

    @Test
    public void setTimeForAnswersInQuizQuestion() {
        int timeForGivingAnswer = 11;
        createQuiz.clickOnQuizzesMenu();
        createQuiz.clickOnAddQuizButton();
        String quizTitle = "Test Quiz for testing time set";
        createQuiz.enterQuizTitle(quizTitle);
        createQuiz.clickOnSaveQuizButton();
        createQuiz.handleAlert();
        createQuiz.clickOnMyQuizzes();
        createQuiz.clickOnEditButton();
        createQuiz.addQuestionButton();
        createQuiz.enterQuestionTitle("testing time change in answer");
        createQuiz.enterQuestionTime(timeForGivingAnswer);
        createQuiz.enterAnswerOptionTitleOne("this will be good");
        createQuiz.enterAnswerOptionTitleTwo("might be better");
        createQuiz.selectCorrectAnswer(1);
        createQuiz.clickOnSaveQuestionButton();
        createQuiz.handleAlert();
        createQuiz.clickOnSaveQuizButton();
        createQuiz.handleAlert();
        createQuiz.clickOnMyQuizzes();
        createQuiz.clickOnEditButton();
        createQuiz.clickValidQuestionButton();
        int actualTimeLimit = createQuiz.getQuestionTime();
        assertEquals(timeForGivingAnswer, actualTimeLimit);
        webDriver.quit();
    }
}
