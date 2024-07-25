package pages.lobby;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import pages.authentication.AuthForm;

public class LobbyPage extends BasePage {

    private final By createGameLobby = By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Create game lobby')]");
    private final By startGame = By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Start')]");
    private final By resultInGame = By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Results')]");
    private final By nextInGame = By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Next')]");
    public LobbyPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnCreateGameLobby() {
        WebElement createGameLobbyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(createGameLobby));
        createGameLobbyButton.click();
    }

    public void clickOnStartGameButton() {
        WebElement createStartGameButton = wait.until(ExpectedConditions.visibilityOfElementLocated(startGame));
        createStartGameButton.click();
    }

    public void clickOnResultInGame() {
        WebElement createResultInGameButton = wait.until(ExpectedConditions.visibilityOfElementLocated(resultInGame));
        createResultInGameButton.click();
    }

    public void clickOnNextInGame() {
        WebElement createNextInGameButton = wait.until(ExpectedConditions.visibilityOfElementLocated(nextInGame));
        createNextInGameButton.click();
    }

}
