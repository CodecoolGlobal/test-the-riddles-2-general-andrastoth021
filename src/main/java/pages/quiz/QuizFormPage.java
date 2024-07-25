package pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Alert;
import pages.BasePage;

public class QuizFormPage extends BasePage {
    public QuizFormPage(WebDriver driver) {
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

    public void clickOnNthQuestion(int n) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='" + n + "']")));
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

    public void clickOnSaveQuizButton() {
        WebElement saveQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save quiz')]")));
        saveQuizButton.click();
    }

    public void handleAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void createAndSaveQuiz(String quizTitle, String question, int timeLimit, int idAnswerOption1, String answerOption1, int idAnswerOption2, String answerOption2, int idAnswerOption3, String answerOption3, int idAnswerOption4, String answerOption4, int correctID) {
        fillQuizTitleField(quizTitle);

        clickOnAddQuestion();

        fillQuestionField(question);

        setTimeLimit(timeLimit);

        fillAnswerOptionByInputId(idAnswerOption1, answerOption1);
        fillAnswerOptionByInputId(idAnswerOption2, answerOption2);
        clickOnAddOption();
        fillAnswerOptionByInputId(idAnswerOption3, answerOption3);
        clickOnAddOption();
        fillAnswerOptionByInputId(idAnswerOption4, answerOption4);

        checkCorrectByInputId(correctID);

        clickOnSaveQuestionButton();
        handleAlert();

        clickOnSaveQuizButton();
        handleAlert();
    }

    public void editAndSaveQuiz(String newQuizTitle, String newQuestion, int newTimeLimit, int newIdAnswerOption1, String newAnswerOption1, int newIdAnswerOption2, String newAnswerOption2, int newIdAnswerOption3, String newAnswerOption3, int newIdAnswerOption4, String newAnswerOption4, int newCorrectID) {
        fillQuestionField(newQuestion);

        setTimeLimit(newTimeLimit);

        fillAnswerOptionByInputId(newIdAnswerOption1, newAnswerOption1);
        fillAnswerOptionByInputId(newIdAnswerOption2, newAnswerOption2);
        fillAnswerOptionByInputId(newIdAnswerOption3, newAnswerOption3);
        fillAnswerOptionByInputId(newIdAnswerOption4, newAnswerOption4);

        checkCorrectByInputId(newCorrectID);

        clickOnSaveQuestionButton();
        handleAlert();

        clickOnSaveQuizButton();
        handleAlert();
    }

}
