package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GameManager {
    private WebDriver driver;
    private WebDriverWait wait;

    public GameManager(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickOnGamesMenu() {
        WebElement quizzesMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Games')]")));
        quizzesMenu.click();
    }

    public void clickOnGreenJoinButton() {
        WebElement greenJoin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-400') and contains(text(), 'Join')]")));
        greenJoin.click();
    }

    public void clickOnPinkJoinButton() {
        WebElement pinkJoin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Join')]")));
        pinkJoin.click();
    }

    public void clickOnCorrectAnswer(String answerText) {
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + answerText + "')]")));
        answer.click();
    }
}
