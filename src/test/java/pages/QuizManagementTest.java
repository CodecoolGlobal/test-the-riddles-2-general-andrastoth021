package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LogIn;
import pages.mains.MainPage;
import pages.mains.MyQuizzesPage;
import pages.mains.QuizzesPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizManagementTest {
    private WebDriver webDriver;
    private LogIn logIn;
    private QuizForm quizForm;
    private MainPage mainPage;
    private MyQuizzesPage myQuizzesPage;
    private QuizzesPage quizzesPage;
    private WebDriverWait wait;
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_USER_USERNAME");
    private final String password = dotenv.get("REPTILE_USER_PASSWORD");
    private static final String TEST_DATA_QUIZ_CREATION = "/QuizTestDataOneQuestion.csv";

    @BeforeEach
    public void precondition() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        logIn = new LogIn(webDriver);
        logIn.fillFieldById(username, "user-name");
        logIn.fillFieldById(password, "password");
        logIn.clickOnButton("LOGIN");

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Quizzes')]")));

        quizForm = new QuizForm(webDriver);
        mainPage = new MainPage(webDriver);
        myQuizzesPage = new MyQuizzesPage(webDriver);
        quizzesPage = new QuizzesPage(webDriver);

        Thread.sleep(2000);
    }


    @ParameterizedTest
    @CsvFileSource(resources = TEST_DATA_QUIZ_CREATION, numLinesToSkip = 1)
    public void createQuizWithValidInputFields(String quizTitle, String question, int timeLimit,
                                               int idAnswerOption1, String answerOption1, int idAnswerOption2, String answerOption2, int idAnswerOption3, String answerOption3, int idAnswerOption4, String answerOption4,
                                               int correctID) {

        mainPage.clickOnQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizForm.createAndSaveQuiz(quizTitle, question, timeLimit, idAnswerOption1,answerOption1, idAnswerOption2, answerOption2, idAnswerOption3, answerOption3, idAnswerOption4, answerOption4, correctID);

        mainPage.clickOnMyQuizzes();
        assertTrue(myQuizzesPage.isQuizPresent(quizTitle));
    }

    @Test
    public void testEditingExistingQuiz() throws InterruptedException {
        mainPage.clickOnMyQuizzes();

        myQuizzesPage.clickOnEditNthQuiz(1);
        String newQuizTitle = "Edited Quiz Title";
        quizForm.fillQuizTitleField(newQuizTitle);

        int questionNumber = 1;
        quizForm.clickOnNthQuestion(questionNumber);

        String newQuestion = "Edited Question?";
        int newTimeLimit = 30;
        int newIdAnswerOption1 = 1;
        String newAnswerOption1 = "Edited Answer 1";
        int newIdAnswerOption2 = 2;
        String newAnswerOption2 = "Edited Answer 2";
        int newIdAnswerOption3 = 3;
        String newAnswerOption3 = "Edited Answer 3";
        int newIdAnswerOption4 = 4;
        String newAnswerOption4 = "Edited Answer 4";
        int newCorrectID = 1;

        quizForm.editAndSaveQuiz(newQuizTitle, newQuestion, newTimeLimit, newIdAnswerOption1, newAnswerOption1, newIdAnswerOption2, newAnswerOption2, newIdAnswerOption3, newAnswerOption3, newIdAnswerOption4, newAnswerOption4, newCorrectID);

        mainPage.clickOnMyQuizzes();
        assertTrue(myQuizzesPage.isQuizPresent(newQuizTitle), "The edited quiz title should be present.");
    }

    @Test
    public void testAddingNewQuizOnlyWithATitle() throws InterruptedException {
        mainPage.clickOnQuizzes();
        quizzesPage.clickOnAddQuiz();
        quizForm.fillQuizTitleField("Test");
        quizForm.clickOnSaveQuizButton();
        quizForm.handleAlert();
        mainPage.clickOnMyQuizzes();
        assertTrue(myQuizzesPage.isQuizPresent("Test"));
    }

    @Test
    public void testDeletingExistingQuiz() throws InterruptedException {

        mainPage.clickOnMyQuizzes();

        String nthQuizTitle = myQuizzesPage.getQuizTitle(1);

        myQuizzesPage.clickOnDeleteNthQuiz(1);
        myQuizzesPage.handleAlert();

        mainPage.clickOnMyQuizzes();
        assertFalse(myQuizzesPage.isQuizPresent(nthQuizTitle), "The quiz title should not be present after deletion.");
    }

    @AfterEach
    public void postCondition() {
        webDriver.quit();
    }
}
