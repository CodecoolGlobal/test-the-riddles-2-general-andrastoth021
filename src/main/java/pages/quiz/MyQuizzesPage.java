package pages.quiz;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class MyQuizzesPage extends BasePage {
    private final By addQuizBy = By.xpath("//button[contains(@class, 'bg-green-400') and contains(text(), 'Add Quiz')]");
    private final By deleteBy = By.xpath(".//button[contains(@class, 'bg-red-400') and contains(text(), 'Delete')]");
    private final By editBy = By.xpath(".//button[contains(@class, 'bg-yellow-400') and contains(text(), 'Edit')]");
    private final By playBy = By.xpath(".//button[contains(@class, 'bg-green-400') and contains(text(), 'Play')]");

    public MyQuizzesPage(WebDriver driver) {
        super(driver);
    }

    public QuizFormPage clickOnAddQuiz() {
        WebElement addQuizButton = wait.until(ExpectedConditions.visibilityOfElementLocated(addQuizBy));
        addQuizButton.click();
        return new QuizFormPage(driver);
    }

    private WebElement getNthQuiz(int n) {
        WebElement myQuizzes = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        return myQuizzes.findElements(By.tagName("div")).get(n - 1);
    }

    public String getQuizTitle(int n) {
        WebElement myQuizzes = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        WebElement quizElement = myQuizzes.findElements(By.tagName("div")).get(n - 1);
        return quizElement.findElement(By.tagName("span")).getText();
    }

    public void clickOnDeleteNthQuiz(int n) {
        WebElement deleteButton = getNthQuiz(n).findElement(deleteBy);
        deleteButton.click();
    }

    public QuizFormPage clickOnEditNthQuiz(int n) {
        WebElement quizElement = getNthQuiz(n);
        System.out.println("Found quiz element: " + quizElement);
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(quizElement.findElement(editBy)));
        System.out.println("Found edit button: " + editButton);
        editButton.click();
        return new QuizFormPage(driver);
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
