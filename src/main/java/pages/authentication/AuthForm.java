package pages.authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public abstract class AuthForm extends BasePage {
    public AuthForm(WebDriver driver) {
        super(driver);
    }

    public void fillFieldById(String input, WebElement fieldElement) {  // accepting a WebElement for compatibility with PageFactory
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldElement));
        inputField.sendKeys(input);
    }

    public void clickOnButton(String buttonText) {  // should accept a WebElement as well
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String xpath = String.format("//button[text()='%s']", buttonText);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        button.click();
    }
}
