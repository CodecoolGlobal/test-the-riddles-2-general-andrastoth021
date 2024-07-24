package pages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateQuizTestRefactor {
    private WebDriver webDriver;
//    private WebDriver webDriverOfUser;
//    private SignUp signUp;
    private LogIn logIn;
    private QuizForm quizForm;
    private CreateQuiz createQuiz;
    private WebDriverWait wait;
//    private GameManager gameManager;

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

        createQuiz = new CreateQuiz(webDriver);
        quizForm = new QuizForm(webDriver);

        Thread.sleep(2000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/QuizTestDataOneQuestion.csv", numLinesToSkip = 1)
    public void createQuizWithValidInputFields(String quizTitle, String question, int timeLimit,
                                               int idAnswerOption1, String answerOption1, int idAnswerOption2, String answerOption2, int idAnswerOption3, String answerOption3, int idAnswerOption4, String answerOption4,
                                               int correctID) {

        createQuiz.clickOnQuizzesMenu();
        createQuiz.clickOnAddQuizButton();
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

        createQuiz.clickOnMyQuizzes();

        assertTrue(createQuiz.isQuizPresent(quizTitle));
    }
}
