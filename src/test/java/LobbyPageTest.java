import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.gameplay.LobbyPage;
import pages.authentication.LogInPage;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LobbyPageTest extends BaseTest {
    private LogInPage logInPage;
    private LobbyPage lobbyPage;
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String usernameOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String passwordOfQuizMaster = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");

    @BeforeEach
    public void precondition() {
        driverQuizMaster = new FirefoxDriver();
        driverQuizMaster.get("http://localhost:3000/login");

        logInPage = new LogInPage(driverQuizMaster);
        logInPage.fillFieldById(usernameOfQuizMaster, "user-name");
        logInPage.fillFieldById(passwordOfQuizMaster, "password");
        logInPage.clickOnButton("LOGIN");

        driverQuizMaster.get("http://localhost:3000/quiz/all");
        lobbyPage = new LobbyPage(driverQuizMaster);
        logInPage.clickOnButton("Play");
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
        WebDriverWait wait = new WebDriverWait(driverQuizMaster, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, driverQuizMaster.getCurrentUrl());
    }

    @AfterEach
    public void postcondition() {
        driverQuizMaster.quit();
    }
}
