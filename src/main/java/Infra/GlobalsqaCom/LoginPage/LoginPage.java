package Infra.GlobalsqaCom.LoginPage;

import Infra.Common.Common;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage {

    @FindBy(css = "button[ng-click='customer()']")
    WebElement customerLoginButton;

    private Common common = null;

    public LoginPage(Common commonObj) {
        common = commonObj;
        PageFactory.initElements(common.getDriver(), this);
    }

    public void clickCustomerLoginButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable(customerLoginButton)).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }
}
