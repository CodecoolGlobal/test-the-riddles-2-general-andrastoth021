import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.lobby.LobbyPage;
import pages.authentication.LogIn;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LobbyPageTest {
    private LogIn logIn;
    private LobbyPage lobbyPage;
    private WebDriver webDriver;
    private WebDriverWait wait;
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String usernameOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String passwordOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");

    @BeforeEach
    public void precondition() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:3000/login");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        logIn = new LogIn(webDriver);
        logIn.fillFieldById(usernameOfQuizMaster, "user-name");
        logIn.fillFieldById(passwordOfQuizMaster, "password");
        logIn.clickOnButton("LOGIN");

        webDriver.get("http://localhost:3000/quiz/all");
        lobbyPage = new LobbyPage(webDriver);
        logIn.clickOnButton("Play");
    }

    @Test
    public void testLobbyPage(){
        lobbyPage.clickOnCreateGameLobby();
        lobbyPage.clickOnStartGameButton();
        lobbyPage.clickOnResultInGame();
        lobbyPage.clickOnNextInGame();
        lobbyPage.clickOnResultInGame();
        lobbyPage.clickOnNextInGame();

        String expectedURL = "http://localhost:3000/result";
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, webDriver.getCurrentUrl());
    }

}
