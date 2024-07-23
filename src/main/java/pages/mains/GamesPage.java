package pages.mains;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GamesPage {
    private WebDriver driver;
    private final WebDriverWait wait;
    private final By joinBy = By.xpath(".//button[contains(@class, 'bg-green-400') and contains(text(), 'Join')]");

    public GamesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement getNthQuiz(int n) {
        WebElement myQuizzes = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        return myQuizzes.findElements(By.tagName("div")).get(n - 1);
    }

    public void clickOnJoinNthQuiz(int n) {
        WebElement joinButton = getNthQuiz(n).findElement(joinBy);
        joinButton.click();
    }
}
