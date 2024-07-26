import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LogInPage;
import pages.quiz.MainPage;
import pages.quiz.MyQuizzesPage;
import pages.quiz.QuizFormPage;
import pages.quiz.QuizzesPage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// test cases should be isolated
// test data should be cleaned up after every test case
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuizManagementTest extends BaseTest {
    private LogInPage logInPage;
    private QuizFormPage quizFormPage;
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
        driverQuizMaster = new FirefoxDriver();
        driverQuizMaster.get("http://localhost:3000/login");
        wait = new WebDriverWait(driverQuizMaster, Duration.ofSeconds(20));

        logInPage = new LogInPage(driverQuizMaster);
        logInPage.fillFieldById(username, "user-name");
        logInPage.fillFieldById(password, "password");
        logInPage.clickOnButton("LOGIN");
        // loginPage.successfulLogin();

        // waiting should not happen in the test class under POM
        wait = new WebDriverWait(driverQuizMaster, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Quizzes')]")));

        quizFormPage = new QuizFormPage(driverQuizMaster);
        mainPage = new MainPage(driverQuizMaster);
        myQuizzesPage = new MyQuizzesPage(driverQuizMaster);
        quizzesPage = new QuizzesPage(driverQuizMaster);

        Thread.sleep(2000);  // why is this needed?
    }


    @ParameterizedTest
    @CsvFileSource(resources = TEST_DATA_QUIZ_CREATION, numLinesToSkip = 1)
    @Order(1)
    public void createQuizWithValidInputFields(String quizTitle, String question, int timeLimit,
                                               int idAnswerOption1, String answerOption1, int idAnswerOption2, String answerOption2, int idAnswerOption3, String answerOption3, int idAnswerOption4, String answerOption4,
                                               int correctID) {

        mainPage.clickOnQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.createAndSaveQuiz(quizTitle, question, timeLimit, idAnswerOption1,answerOption1, idAnswerOption2, answerOption2, idAnswerOption3, answerOption3, idAnswerOption4, answerOption4, correctID);

        driverQuizMaster.get("http://localhost:3000/quiz/my");  // navigating should happen in page classes
        assertTrue(myQuizzesPage.isQuizPresent(quizTitle));
    }

    @Test
    @Order(2)
    public void testEditingExistingQuiz() {
        mainPage.clickOnMyQuizzes();

        myQuizzesPage.clickOnEditNthQuiz(1);
        String newQuizTitle = "Edited Quiz Title";
        quizFormPage.fillQuizTitleField(newQuizTitle);

        int questionNumber = 1;
        quizFormPage.clickOnNthQuestion(questionNumber);

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

        quizFormPage.editAndSaveQuiz(newQuizTitle, newQuestion, newTimeLimit, newIdAnswerOption1, newAnswerOption1, newIdAnswerOption2, newAnswerOption2, newIdAnswerOption3, newAnswerOption3, newIdAnswerOption4, newAnswerOption4, newCorrectID);

        driverQuizMaster.get("http://localhost:3000/quiz/my");
        assertTrue(myQuizzesPage.isQuizPresent(newQuizTitle), "The edited quiz title should be present.");
    }

    @Test
    @Order(3)
    public void testAddingNewQuizOnlyWithATitle() {
        mainPage.clickOnMyQuizzes();
        myQuizzesPage.clickOnAddQuiz();
        quizFormPage.fillQuizTitleField("Quiz only with a title");
        quizFormPage.clickOnSaveQuizButton();
        quizFormPage.handleAlert();
        driverQuizMaster.get("http://localhost:3000/quiz/my");
        assertTrue(myQuizzesPage.isQuizPresent("Quiz only with a title"));
    }

    @Test
    @Order(4)
    public void testDeletingExistingQuiz() {

        mainPage.clickOnMyQuizzes();

        String nthQuizTitle = myQuizzesPage.getQuizTitle(1);

        myQuizzesPage.clickOnDeleteNthQuiz(1);
        myQuizzesPage.handleAlert();

        driverQuizMaster.get("http://localhost:3000/quiz/my");
        assertFalse(myQuizzesPage.isQuizPresent(nthQuizTitle), "The quiz title should not be present after deletion.");
    }

    @AfterEach
    public void postCondition() {
        driverQuizMaster.quit();
    }
}
