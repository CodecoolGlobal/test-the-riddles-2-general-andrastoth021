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
    public void precondition() {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Bejelentkezés
        // TODO: getting rid of comments :')
        logIn = new LogIn(webDriver);
        logIn.fillUsernameFieldWithQuizMasterCredentials();
        logIn.fillPasswordFieldWithQuizMasterCredentials();
        logIn.clickOnLogIn();

        // Várakozás a főoldal betöltésére
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));  // there is a wait already, stored in a field
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Quizzes')]")));

        // Probléma volt. Alkalmazzuk a Thread.sleep() hívást ideiglenesen
        try {
            Thread.sleep(2000); // Várakozás 5 másodpercig
        } catch (InterruptedException e) {  // TODO: no need for a try-catch block here
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

    // I recommend using private methods rather than a nested class
    @Nested
    class TimeForAnswersTests {
        private void createAndSaveQuiz(String quizTitle, String questionTitle, String questionTime) {
            createQuiz.clickOnQuizzesMenu();  // these calls could be in createQuiz() under another method?
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

    @Test
    public void checkIfLobbyCreationWorks() {
        createQuiz.createLobby();

        String expectedPrefix = "http://localhost:3000/game/lobby/";

        wait.until(ExpectedConditions.urlMatches("^" + Pattern.quote(expectedPrefix) + ".*"));

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.startsWith(expectedPrefix));
    }

    @Test
    public void startGameAsQuizmasterAndPlayAsUser() {
        webDriverOfUser = new FirefoxDriver();
        webDriverOfUser.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriverOfUser, Duration.ofSeconds(20));

        /*signUp = new SignUp(webDriverOfUser);
        signUp.fillUsernameFieldWithUserCredentials();
        signUp.fillEmailFieldWithUserCredentials();
        signUp.fillPasswordFieldWithUserCredentials();
        signUp.clickOnSignUp();*/
        LogIn logInUser = new LogIn(webDriverOfUser);
        logInUser.fillUsernameFieldWithUserCredentials();
        logInUser.fillPasswordFieldWithUserCredentials();
        logInUser.clickOnLogIn();

        gameManager = new GameManager(webDriverOfUser);

        // Quizmaster starts a game by going to MyQuizzes and clicking Play
        createQuiz.clickOnMyQuizzesInNavigation();
        createQuiz.clickOnPlayButton();

        // Quizmaster creates a lobby
        createQuiz.clickOnCreateLobbyButton();

        // User joins the lobby
        gameManager.clickOnGamesMenu();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameManager.clickOnGreenJoinButton();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameManager.clickOnPinkJoinButton();

        // Quizmaster starts the game
        createQuiz.clickOnStartGameButton();

        // User clicks YES
        gameManager.clickOnCorrectAnswer();

        // Quizmaster click RESULTS (bg-pink-500) and then NEXT (pink500)
        createQuiz.clickOnResultInGame();
        createQuiz.clickOnNextInGame();

        // TODO: no assertion in test
    }

    @AfterEach
    public void closeDriver() {
        webDriver.quit();
    }
}
