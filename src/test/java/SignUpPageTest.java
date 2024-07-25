import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.SignUpPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpPageTest extends BaseTest {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String username = dotenv.get("REPTILE_QUIZMASTER_USERNAME");
    private final String email = dotenv.get("REPTILE_QUIZMASTER_EMAIL");
    private final String password = dotenv.get("REPTILE_QUIZMASTER_PASSWORD");
    private SignUpPage signUpPage;

    @BeforeEach
    public void precondition() {
        driverQuizMaster = new FirefoxDriver();
        driverQuizMaster.get("http://localhost:3000/register");
        signUpPage = new SignUpPage(driverQuizMaster);
    }

    @Test
    public void signUpValidCredentials() {
        signUpPage.fillFieldById(username, "user-name");
        signUpPage.fillFieldById(password, "password");
        signUpPage.fillFieldById(email, "email");
        signUpPage.clickOnButton("SIGN UP");

        String expectedURL = "http://localhost:3000/login";
        WebDriverWait wait = new WebDriverWait(driverQuizMaster, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedURL));

        assertEquals(expectedURL, driverQuizMaster.getCurrentUrl());
    }
}