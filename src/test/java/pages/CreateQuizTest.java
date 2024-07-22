package pages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateQuizTest {  // TODO: separate quiz creation tests from game tests & general refactoring
    private WebDriver webDriver;
    private WebDriver webDriverOfUser;
    private SignUp signUp;
    private LogIn logIn;
    private CreateQuiz createQuiz;
    private WebDriverWait wait;
    private GameManager gameManager;

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

        createQuiz.handleAlert();

        createQuiz.clickOnMyQuizzes();

        assertTrue(createQuiz.isQuizPresent(quizTitle));

        webDriver.quit();
    }

    // TODO: I recommend using private methods rather than a nested class
    @Nested
    class TimeForAnswersTests {
        private void createAndSaveQuiz(String quizTitle, String questionTitle, String questionTime) {
            createQuiz.clickOnQuizzesMenu();  // TODO: these calls could be in createQuiz() under another method?
            createQuiz.clickOnAddQuizButton();
            createQuiz.enterQuizTitle(quizTitle);
            createQuiz.clickOnSaveQuizButton();
            createQuiz.handleAlert();
            createQuiz.clickOnMyQuizzes();
            createQuiz.clickOnEditButtonByQuizTitle(quizTitle);
            createQuiz.addQuestionButton();
            createQuiz.enterQuestionTitle(questionTitle);
            createQuiz.enterQuestionTime(questionTime);
            createQuiz.enterAnswerOptionTitleOne("this will be good");
            createQuiz.enterAnswerOptionTitleTwo("might be better");
            createQuiz.selectCorrectAnswer(1);
            createQuiz.clickOnSaveQuestionButton();
            createQuiz.handleAlert();
            createQuiz.clickOnSaveQuizButton();
            createQuiz.handleAlert();
            createQuiz.clickOnMyQuizzes();
            createQuiz.clickOnEditButtonByQuizTitle(quizTitle);
            createQuiz.clickValidQuestionButton();
        }

        @Test
        public void setTimeForAnswersInQuizQuestion_ValidTime() {
            String timeForGivingAnswer = "11";
            createAndSaveQuiz("Test Quiz for testing time set", "testing time change in answer", timeForGivingAnswer);
            String actualTimeLimit = createQuiz.getQuestionTime();
            assertEquals(timeForGivingAnswer, actualTimeLimit);
        }

        @Test
        public void setTimeForAnswersInQuizQuestion_ZeroTime() {
            String timeForGivingAnswer = "0";
            createAndSaveQuiz("Test Quiz for testing zero time", "testing zero time", timeForGivingAnswer);
            String actualTimeLimit = createQuiz.getQuestionTime();
            assertEquals(timeForGivingAnswer, actualTimeLimit);
        }

        @Test
        public void setTimeForAnswersInQuizQuestion_InvalidTime() {
            String timeForGivingAnswer = "a";
            createAndSaveQuiz("Test Quiz for testing invalid time", "testing invalid time", timeForGivingAnswer);
            String actualTimeLimit = createQuiz.getQuestionTime();
            assertEquals(timeForGivingAnswer, actualTimeLimit);
        }
    }

    @Test
    public void testIfEditButtonWorksByClicking() {
        assertTrue(createQuiz.canBeClickedFirstQuizEditButton());
    }

    @Test
    public void testEditFirstQuizWithAnExistingQuiz() {
        assertTrue(createQuiz.hasBeenChangedFirstQuizTitle("Edited Quiz Title"));
    }

    @Test
    public void testEditFirstQuizDeletingTheTitle() {
        createQuiz.canEditQuizWithEmptyTitle();
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Quiz']")));

        List<WebElement> precedingDivs = addButton.findElements(By.xpath("./preceding-sibling::div"));

        boolean foundEmptySpan = false;

        for (WebElement div : precedingDivs) {
            List<WebElement> spanElements = div.findElements(By.xpath(".//span[@class='grow flex align-middle text-lg pl-2 items-center']"));

            for (WebElement span : spanElements) {
                String innerHTML = span.getAttribute("innerHTML").trim();
                if (innerHTML.isEmpty()) {
                    foundEmptySpan = true;
                    break;
                }
            }

            if (foundEmptySpan) {
                break;
            }
        }

        assertTrue(foundEmptySpan);
    }

    @Test
    public void createQuizWithOneQuestionWhichHasMultipleAnswerOptions() {
        createQuiz.clickOnQuizzesMenu();
        createQuiz.clickOnAddQuizButton();
        createQuiz.enterQuizTitle("Test Quiz");

        createQuiz.clickOnAddQuestion();

        createQuiz.enterQuestion("Test Question");

        createQuiz.fillAnswerByInputId(1, "#1 Test Answer");
        createQuiz.fillAnswerByInputId(2, "#2 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(3, "#3 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(4, "#4 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(5, "#5 Test Answer");

        createQuiz.clickOnAddOptionButton();
        createQuiz.fillAnswerByInputId(6, "#6 Test Answer");

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
    public void deleteTopOfListExistingQuiz() throws InterruptedException {
        createQuiz.clickOnMyQuizzesInNavigation();
        int originalQuizAmount = createQuiz.getMyQuizAmount();

        WebElement firstQuiz = createQuiz.getQuizElementByPosition(1);
        List<WebElement> childElement = createQuiz.getChildrenOfWebElement(firstQuiz);
        createQuiz.clickOnDeleteQuizButton(childElement);
        createQuiz.handleAlert();

        Thread.sleep(1000);

        int newQuizAmount = createQuiz.getMyQuizAmount();
        assertEquals(originalQuizAmount - 1, newQuizAmount);
    }

    @Test
    public void checkIfLobbyCreationWorks() {
        createQuiz.createLobby();

        String expectedPrefix = "http://localhost:3000/game/lobby/";

        wait.until(ExpectedConditions.urlMatches("^" + Pattern.quote(expectedPrefix) + ".*"));

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.startsWith(expectedPrefix));
    }

    @Test
    public void startGameAsQuizmasterAndPlayAsUser() throws InterruptedException {
        webDriverOfUser = new FirefoxDriver();
        webDriverOfUser.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriverOfUser, Duration.ofSeconds(20));

        LogIn logInUser = new LogIn(webDriverOfUser);
        logInUser.fillUsernameFieldWithUserCredentials();
        logInUser.fillPasswordFieldWithUserCredentials();
        logInUser.clickOnLogIn();

        gameManager = new GameManager(webDriverOfUser);

        createQuiz.clickOnMyQuizzesInNavigation();
        createQuiz.clickOnPlayButton();

        createQuiz.clickOnCreateLobbyButton();

        gameManager.clickOnGamesMenu();

        Thread.sleep(1000);
        gameManager.clickOnGreenJoinButton();

        Thread.sleep(1000);
        gameManager.clickOnPinkJoinButton();

        createQuiz.clickOnStartGameButton();

        gameManager.clickOnCorrectAnswer();

        createQuiz.clickOnResultInGame();
        createQuiz.clickOnNextInGame();

        // TODO: no assertion in test
    }

    @AfterEach
    public void closeDriver() {
        webDriver.quit();
    }
}
