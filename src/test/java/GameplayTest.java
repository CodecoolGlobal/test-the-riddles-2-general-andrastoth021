import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.authentication.LogIn;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameplayTest {
    private WebDriver driverQuizMaster;
    private WebDriver driverPlayer;

    @BeforeEach
    public void precondition() throws InterruptedException {
        driverQuizMaster = new FirefoxDriver();
        driverPlayer = new FirefoxDriver();

        driverQuizMaster.get("http://localhost:3000/login");
        driverPlayer.get("http://localhost:3000/login");

        LogIn logInPage = new LogIn(driverQuizMaster);
        logInPage.fillUsernameFieldWithQuizMasterCredentials();
        logInPage.fillPasswordFieldWithQuizMasterCredentials();
        logInPage.clickOnLogIn();

        logInPage = new LogIn(driverPlayer);
        logInPage.fillUsernameFieldWithQuizMasterCredentials();
        logInPage.fillPasswordFieldWithQuizMasterCredentials();
        logInPage.clickOnLogIn();

        driverQuizMaster.get("http://localhost:3000/quiz/my");
        driverPlayer.get("http://localhost:3000/gamelist");
    }

    @Test
    public void startGameAsQuizmasterAndPlayAsUser() throws InterruptedException {
        // TODO: as merge is complete, and LobbyPage is present, remove the comment and the test will run.
        /*MyQuizzesPage myQuizzesPage = new MyQuizzesPage(driverQuizMaster);
        myQuizzesPage.clickOnPlayNthQuiz(1);

        LobbyPage lobbyPage = new LobbyPage(driverQuizMaster);
        lobbyPage.clickOnCreateGameLobby();

        GamesPage gamesPage = new GamesPage(driverPlayer);
        gamesPage.clickOnJoinNthQuiz(1);

        GamePage gamePage = new GamePage(driverPlayer);
        Thread.sleep(1000);
        gamePage.clickOnGreenJoinButton();
        Thread.sleep(1000);
        gamePage.clickOnPinkJoinButton();

        lobbyPage.clickOnStartGameButton();

        gamePage.clickOnCorrectAnswer("Yes");

        Thread.sleep(1000);
        lobbyPage.clickOnResultInGame();
        lobbyPage.clickOnNextInGame();

        Thread.sleep(1000);
        String messageText = lobbyPage.getFinalMessageOfGame();
        assertEquals("Congratulations!", messageText);*/
    }

    /*@AfterEach
    public void postcondition() {
        driverQuizMaster.quit();
        driverPlayer.quit();
    }*/
}