package pages.mains;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import pages.QuizForm;

public class MyQuizzesPage extends BasePage {
    private final By addQuizBy = By.xpath("//button[contains(@class, 'bg-green-400') and contains(text(), 'Add Quiz')]");
    private final By deleteBy = By.xpath(".//button[contains(@class, 'bg-red-400') and contains(text(), 'Delete')]");
    private final By editBy = By.xpath(".//button[contains(@class, 'bg-yellow-400') and contains(text(), 'Edit')]");
    private final By playBy = By.xpath(".//button[contains(@class, 'bg-green-400') and contains(text(), 'Play')]");

    public MyQuizzesPage(WebDriver driver) {
        super(driver);
    }

    public QuizForm clickOnAddQuiz() {
        WebElement addQuizButton = wait.until(ExpectedConditions.visibilityOfElementLocated(addQuizBy));
        addQuizButton.click();
        return new QuizForm(driver);
    }

    private WebElement getNthQuiz(int n) {
        WebElement myQuizzes = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        return myQuizzes.findElements(By.tagName("div")).get(n - 1);
    }

    public void clickOnDeleteNthQuiz(int n) {
        WebElement deleteButton = getNthQuiz(n).findElement(deleteBy);
        deleteButton.click();
    }

    public QuizForm clickOnEditNthQuiz(int n) {
        WebElement editButton = getNthQuiz(n).findElement(editBy);
        editButton.click();
        return new QuizForm(driver);
    }

    public void clickOnPlayNthQuiz(int n) {
        WebElement playButton = getNthQuiz(n).findElement(playBy);
        playButton.click();
    }

    public boolean isQuizPresent(String title) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + title + "')]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void handleAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
}
