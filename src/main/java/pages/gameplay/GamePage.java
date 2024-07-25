package pages.gameplay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class GamePage extends BasePage {
    private final By pinkJoinBy = By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Join')]");
    // private By answerBy = By.xpath("//*[contains(text(), '" + answer + "')]"); // TODO: find a solution for this By

    public GamePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnPinkJoinButton() {
        WebElement pinkJoinBtn = wait.until(ExpectedConditions.elementToBeClickable(pinkJoinBy));
        pinkJoinBtn.click();
    }

    public void clickOnCorrectAnswer(String answer) {
        WebElement answerBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + answer + "')]")));
        answerBtn.click();
    }
}
