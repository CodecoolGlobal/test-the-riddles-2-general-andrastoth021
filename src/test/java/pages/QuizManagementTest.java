package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizManagementTest {
    private WebDriver webDriver;
    private LogIn logIn;
    private QuizForm quizForm;
    private MainPage mainPage;
    private MyQuizzesPage myQuizzesPage;
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

        Thread.sleep(2000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = TEST_DATA_QUIZ_CREATION, numLinesToSkip = 1)
    public void createQuizWithValidInputFields(String quizTitle, String question, int timeLimit,
                                               int idAnswerOption1, String answerOption1, int idAnswerOption2, String answerOption2, int idAnswerOption3, String answerOption3, int idAnswerOption4, String answerOption4,
                                               int correctID) {


        mainPage.clickOnQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizForm.fillQuizTitleField(quizTitle);

        quizForm.clickOnAddQuestion();

        quizForm.fillQuestionField(question);

        quizForm.setTimeLimit(timeLimit);

        quizForm.fillAnswerOptionByInputId(idAnswerOption1, answerOption1);
        quizForm.fillAnswerOptionByInputId(idAnswerOption2, answerOption2);
        quizForm.clickOnAddOption();
        quizForm.fillAnswerOptionByInputId(idAnswerOption3, answerOption3);
        quizForm.clickOnAddOption();
        quizForm.fillAnswerOptionByInputId(idAnswerOption4, answerOption4);

        quizForm.checkCorrectByInputId(correctID);

        quizForm.clickOnSaveQuestionButton();
        quizForm.handleAlert();

        quizForm.clickOnSaveQuizButton();
        quizForm.handleAlert();

        mainPage.clickOnMyQuizzes();
        assertTrue(myQuizzesPage.isQuizPresent(quizTitle));
    }
}
