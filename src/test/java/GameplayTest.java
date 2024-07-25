import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.authentication.LogInPage;
import pages.gameplay.LobbyPage;
import pages.quiz.GameListPage;
import pages.quiz.MyQuizzesPage;
import pages.gameplay.GamePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameplayTest {
    private WebDriver driverQuizMaster;
    private WebDriver driverPlayer;

    @BeforeEach
    public void precondition() throws InterruptedException {
        driverQuizMaster = new FirefoxDriver();
        driverPlayer = new FirefoxDriver();

        driverQuizMaster.get("http://localhost:3000/login");
        driverPlayer.get("http://localhost:3000/login");

        LogInPage logInPage = new LogInPage(driverQuizMaster);
        logInPage.fillUsernameFieldWithQuizMasterCredentials();
        logInPage.fillPasswordFieldWithQuizMasterCredentials();
        logInPage.clickOnLogIn();

        logInPage = new LogInPage(driverPlayer);
        logInPage.fillUsernameFieldWithUserCredentials();
        logInPage.fillPasswordFieldWithUserCredentials();
        logInPage.clickOnLogIn();

        driverQuizMaster.get("http://localhost:3000/quiz/my");
        driverPlayer.get("http://localhost:3000/gamelist");
    }

    @Test
    public void testGamePlayWithAOneQuestionQuiz() throws InterruptedException {
        // TODO: as merge is complete, and LobbyPage is present, remove the comment and the test will run.
        MyQuizzesPage myQuizzesPage = new MyQuizzesPage(driverQuizMaster);
        myQuizzesPage.clickOnPlayNthQuiz(1);
        Thread.sleep(1000);

        LobbyPage lobbyPage = new LobbyPage(driverQuizMaster);
        lobbyPage.clickOnCreateGameLobby();
        Thread.sleep(2000);

        driverPlayer.get("http://localhost:3000/gamelist");
        GameListPage gameListPage = new GameListPage(driverPlayer);
        gameListPage.clickOnJoinNthQuiz(1);
        Thread.sleep(1000);

        GamePage gamePage = new GamePage(driverPlayer);
        gamePage.clickOnPinkJoinButton();
        Thread.sleep(1000);

        lobbyPage.clickOnStartGameButton();
        Thread.sleep(1000);

        gamePage.clickOnCorrectAnswer("Yes");
        Thread.sleep(1000);

        lobbyPage.clickOnResultInGame();
        Thread.sleep(1000);
        lobbyPage.clickOnNextInGame();
        Thread.sleep(1000);

        Thread.sleep(1000);
        assertTrue(lobbyPage.validateSuccessfulEndOfGame());
    }

    @AfterEach
    public void postcondition() throws InterruptedException {
        Thread.sleep(1000);
        driverQuizMaster.quit();
        driverPlayer.quit();
    }
}