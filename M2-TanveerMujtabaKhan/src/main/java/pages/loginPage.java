package pages;

import base.RequisiteBase;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class loginPage extends RequisiteBase {
    private By email = By.id("user_email");
    private By password = By.id("user_password");
    private By loginButton = By.cssSelector("button.next-button");

    /****************************************
     * @Summary - login the user with user credentials
     *****************************************/
    @Step("Login with user details")
    public void loginUser(String userName, String userPassword) {
        getDriver().findElement(email).sendKeys("tester@company.com");
        getDriver().findElement(password).sendKeys("Pass@4321$");
        getDriver().findElement(loginButton).click();

    }
}
