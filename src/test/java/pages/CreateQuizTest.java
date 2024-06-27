package pages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateQuizTest {
    private WebDriver webDriver;
    private LogIn logIn;
    private CreateQuiz createQuiz;
    private WebDriverWait wait;

    @BeforeEach
    public void precondition() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

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
        //webDriver.quit();
    }

    @Test
    public void testIfEditButtonWorksByClicking() {
        createQuiz.clickOnFirstQuizEditButton();
    }

    @Test
    public void testEditFirstQuizWithAnExistingQuiz() {
        createQuiz.changeFirstQuizTitle("Edited Quiz Title");
    }

    @Test
    public void testEditFirstQuizDeletingTheTitle() {
        createQuiz.canEditQuizWithEmptyTitle();
        WebElement titleField = wait.until((ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'grow flex')]"))));

        assertFalse(titleField.getText().isEmpty());
    }

    @Test
    public void createQuizWithOneQuestionWhichHasMultipleAnswerOptions() {
        createQuiz.clickOnQuizzesMenu();
        createQuiz.clickOnAddQuizButton();
        createQuiz.enterQuizTitle("Test Quiz");

        // Add question
        createQuiz.clickOnAddQuestion();

        // Enter Question
        createQuiz.enterQuestion("Test Question");

        // 2 answer
        createQuiz.fillAnswerByInputId(1, "#1 Test Answer");
        createQuiz.fillAnswerByInputId(2, "#2 Test Answer");

        // Add other answer options and fill them out
        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(3, "#3 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(4, "#4 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(5, "#5 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(6, "#6 Test Answer");

        // Click on both save buttons (first save the question and after that the quiz)

        createQuiz.clickOnSaveQuestionButton();
        createQuiz.handleAlert();
        createQuiz.clickOnSaveQuizButton();
        createQuiz.handleAlert();

        String expectedURL = "http://localhost:3000/quiz/all";
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));
        
        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }

    @Test
    public void deleteTopOfListExistingQuiz() {
        createQuiz.clickOnMyQuizzesInNavigation();
        int originalQuizAmount = createQuiz.getMyQuizAmount();

        WebElement firstQuiz = createQuiz.getQuizElementByPosition(1); // First Existing Quiz
        List<WebElement> childElement = createQuiz.getChildrenOfWebElement(firstQuiz);
        createQuiz.clickOnDeleteQuizButton(childElement);
        createQuiz.handleAlert();

        // Wait an extra second before comparing original and new amount
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int newQuizAmount = createQuiz.getMyQuizAmount();
        assertEquals(originalQuizAmount - 1, newQuizAmount);
    }

    @AfterEach
    public void closeDriver() {
        // webDriver.quit();
    }
}
