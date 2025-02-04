package pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class QuizzesPage extends BasePage {
    private final By addQuizBy = By.xpath("//button[contains(@class, 'bg-green-400') and contains(text(), 'Add Quiz')]");
    private final By playBy = By.xpath(".//button[contains(@class, 'bg-green-400') and contains(text(), 'Play')]");

    public QuizzesPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnAddQuiz() {
        WebElement addQuizButton = wait.until(ExpectedConditions.visibilityOfElementLocated(addQuizBy));
        addQuizButton.click();
    }

    private WebElement getNthQuiz(int n) {
        WebElement myQuizzes = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        return myQuizzes.findElements(By.tagName("div")).get(n - 1);
    }

    public void clickOnPlayNthQuiz(int n) {
        WebElement playButton = getNthQuiz(n).findElement(playBy);
        playButton.click();
    }
}
