package pages.mains;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class MainPage extends BasePage {
    private final By gamesBy = By.xpath("//span[contains(text(),'Games')]");
    private final By quizzesBy = By.xpath("//span[contains(text(),'Quizzes')]");
    private final By myQuizzesBy = By.xpath("//span[contains(text(),'My Quizzes')]");
    private final By logoutBy = By.xpath("//span[contains(text(),'Quizzes')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public GamesPage clickOnGames() {
        driver.findElement(gamesBy).click();
        return new GamesPage(driver);
    }

    public QuizzesPage clickOnQuizzes() {
        driver.findElement(quizzesBy).click();
        return new QuizzesPage(driver);
    }

    public MyQuizzesPage clickOnMyQuizzes() {
        driver.findElement(myQuizzesBy).click();
        return new MyQuizzesPage(driver);
    }

    public void clickOnLogout() {
        driver.findElement(logoutBy).click();
        // return new LogInPage(driver);
    }
}
