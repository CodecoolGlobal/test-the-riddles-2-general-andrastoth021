package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CreateQuiz {
    private WebDriver driver;
    private WebDriverWait wait;

    public CreateQuiz(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickOnQuizzesMenu() {
        WebElement quizzesMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Quizzes')]")));
        quizzesMenu.click();
    }

    public void clickOnAddQuizButton() {
        WebElement addQuizButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'bg-green-400') and contains(text(), 'Add Quiz')]")));
        addQuizButton.click();
    }

    public void enterQuizTitle(String title) {
        WebElement quizTitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        quizTitleField.sendKeys(title);
    }

    public void clickOnAddQuestion() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Add Question')]")));
        button.click();
    }

    public void enterQuestion(String question) {
        WebElement questionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id, 'question')]")));
        questionField.sendKeys(question);
    }

    public void fillAnswerByInputId(int id, String inputValue) {
        WebElement answerInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("answer-" + id)));
        answerInputField.sendKeys(inputValue);
    }

    public void clickOnAddOptionButton() {
        WebElement addOptionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-zinc-700') and contains(text(), '+ Add option')]")));
        addOptionButton.click();
    }

    public void clickOnSaveQuestionButton() {
        WebElement saveQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save')]")));
        saveQuestionButton.click();
    }

    public void clickOnMyQuizzesInNavigation() {
        WebElement quizzesMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'My Quizzes')]")));
        quizzesMenu.click();
    }

    public int getMyQuizAmount() {
        WebElement myQuizzes = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        return myQuizzes.findElements(By.xpath("./*")).size() - 1;
    }

    public WebElement getQuizElementByPosition(int position) {
        WebElement parentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'grow pt-16')]")));
        return parentElement.findElements(By.xpath("./*")).get(position - 1);
    }

    public List<WebElement> getChildrenOfWebElement(WebElement webElement) {
        return webElement.findElements(By.xpath("./*"));
    }

    public void clickOnDeleteQuizButton(List<WebElement> elements) {
        for (WebElement e : elements) {
            if (e.getText().equals("Delete")) {
                e.click();
                return;
            }
        }
    }

    public void clickOnSaveQuizButton() {
        WebElement saveQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save quiz')]")));
        Actions actions = new Actions(driver);
        actions.moveToElement(saveQuizButton).click().perform();
    }

    public void handleAlert() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void clickOnMyQuizzes() {
        WebElement myQuizzesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[1]/nav/div/div[1]/ul/li[3]/a/span")));
        Actions actions = new Actions(driver);
        actions.moveToElement(myQuizzesButton).click().perform();
    }

    public boolean isQuizPresent(String title) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + title + "')]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOnEditButton() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/div/button[2]")));
        Actions actions = new Actions(driver);
        actions.moveToElement(editButton).click().perform();
    }

    public void addQuestionButton() {
        WebElement addQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/button")));
        Actions actions = new Actions(driver);
        actions.moveToElement(addQuestionButton).click().perform();
    }

    public void enterQuestionTitle(String title) {
        WebElement questionTitleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("-1question")));
        questionTitleField.sendKeys(title);
    }


    public void enterQuestionTime(String seconds) {
        WebElement questionTimeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'time')]")));
        questionTimeField.clear(); // Törli a mező jelenlegi tartalmát
        questionTimeField.sendKeys(seconds); // másodpercek
    }

    public void enterAnswerOptionTitleOne(String title) {
        WebElement answerOptionTitleOne = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("answer-1")));
        answerOptionTitleOne.sendKeys(title);
    }
    public void enterAnswerOptionTitleTwo(String title) {
        WebElement answerOptionTitleTwo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("answer-2")));
        answerOptionTitleTwo.sendKeys(title);
    }

    public void selectCorrectAnswer(int index) {
        WebElement correctAnswerCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox-" + index)));
        correctAnswerCheckbox.click();
    }

    public void clickValidQuestionButton() {
        WebElement validQuestionButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/div/button")));
        Actions actions = new Actions(driver);
        actions.moveToElement(validQuestionButton).click().perform();
    }

    public String getQuestionTime() {
        WebElement questionTimeField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'time')]")));
        return questionTimeField.getAttribute("value");
    }

    public boolean canBeClickedFirstQuizEditButton() {
        clickOnMyQuizzes();
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button[2]")));
        editButton.click();
        WebElement saveQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save quiz')]")));
        return saveQuizButton.getAccessibleName().equals("Save quiz");
    }

    public boolean hasBeenChangedFirstQuizTitle(String newTitle) {
        clickOnMyQuizzes();
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[1]/button[2]")));
        editButton.click();
        WebElement titleField = wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("name"))));
        titleField.clear();
        titleField.sendKeys(newTitle);
        clickOnSaveQuizButton();
        handleAlert();
        clickOnMyQuizzes();
        return isQuizPresent(newTitle);
    }

    public void canEditQuizWithEmptyTitle() {
        clickOnMyQuizzes();
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/div/button[2]")));
        editButton.click();
        WebElement titleField = wait.until((ExpectedConditions.visibilityOfElementLocated(By.id("name"))));
        titleField.clear();
        clickOnSaveQuizButton();
        handleAlert();
        clickOnMyQuizzes();
    }

}
