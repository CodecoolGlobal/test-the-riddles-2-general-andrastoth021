package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;


import java.time.Duration;

public class QuizForm extends BasePage {
    public QuizForm(WebDriver driver) {
        super(driver);
    }

    public void fillQuizTitleField(String title) {
        WebElement quizTitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        quizTitleField.sendKeys(title);
    }

    public void clickOnAddQuestion() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Add Question')]")));
        button.click();
    }

    public void fillQuestionField(String question) {
        WebElement questionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id, 'question')]")));
        questionField.sendKeys(question);
    }

    public void setTimeLimit(int second) {
        WebElement timeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'time')]")));
        timeField.clear();
        timeField.sendKeys(String.valueOf(second));
    }

    public void fillAnswerOptionByInputId(int id, String title) {
        WebElement answerOptionTitleOne = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("answer-" + id)));
        answerOptionTitleOne.sendKeys(title);
    }

    public void checkCorrectByInputId(int id) {
        WebElement correctAnswerCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox-" + id)));
        correctAnswerCheckbox.click();
    }

    public void clickOnAddOption() {
        WebElement addOptionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-zinc-700') and contains(text(), '+ Add option')]")));
        addOptionButton.click();
    }

    public void clickOnSaveQuestionButton() {
        WebElement saveQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save')]")));
        saveQuestionButton.click();

    }

    public void clickOnDeleteQuestionButton() {
        WebElement deleteQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-zinc-950') and contains(text(), 'Delete')]")));
        deleteQuestionButton.click();
    }

    public void clickOnSaveQuizButton() {
        WebElement saveQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save quiz')]")));
        saveQuizButton.click();
    }

    public void clickOnDeleteQuizButton() {
        WebElement deleteQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-zinc-950') and contains(text(), 'Delete quiz')]")));
        deleteQuizButton.click();
    }

    public void handleAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
}
