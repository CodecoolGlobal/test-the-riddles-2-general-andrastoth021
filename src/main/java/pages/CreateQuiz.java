package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public void clickOnSaveQuizButton() {
        WebElement saveQuizButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bg-green-800') and contains(text(), 'Save quiz')]")));
        Actions actions = new Actions(driver);
        actions.moveToElement(saveQuizButton).click().perform();
    }

    public void handleAlert() {
        // Várakozás 2 másodpercig a save gomb megnyomása után
        try {
            Thread.sleep(2000);
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
}
