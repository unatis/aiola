package Infra.GlobalsqaCom.CustomerPage;

import Infra.Common.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerPage {

    @FindBy(css = "button[type='submit']")
    WebElement customerLoginButton;

    @FindBy(id = "userSelect")
    WebElement yourNameCombo;
    private Common common = null;

    public CustomerPage(Common commonObj) {

        common = commonObj;
        PageFactory.initElements(common.getDriver(), this);
    }

    public void selectYourNameComboBox(String nameToSelect) {
        try {

            WebElement yourNameComboBox = common.getDriverWait().until(ExpectedConditions.visibilityOf(yourNameCombo));

            Select combo = new Select(yourNameComboBox);

            combo.selectByVisibleText(nameToSelect);

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void clickLoginButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable((customerLoginButton))).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }
}
