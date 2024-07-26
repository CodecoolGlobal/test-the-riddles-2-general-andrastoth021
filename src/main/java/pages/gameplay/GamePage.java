package pages.gameplay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class GamePage extends BasePage {
    // if this is the only button on the page then the locator could be simplified
    private final By pinkJoinBy = By.xpath("//button[contains(@class, 'bg-pink-500') and contains(text(), 'Join')]");
    // private By answerBy = By.xpath("//*[contains(text(), '" + answer + "')]"); // TODO: find a solution for this By

    public GamePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnPinkJoinButton() {  // color can be removed from method name :)
        WebElement pinkJoinBtn = wait.until(ExpectedConditions.elementToBeClickable(pinkJoinBy));
        pinkJoinBtn.click();
    }

    public void clickOnCorrectAnswer(String answer) {
        By answerLocator = By.xpath("//*[contains(text(), '" + answer + "')]")
        WebElement answerBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator)); 
        answerBtn.click();
    }
}
