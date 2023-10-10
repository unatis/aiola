package Infra.GlobalsqaCom.AccountPage;

import Infra.Common.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountPage {

    @FindBy(css = "button[ng-click='transactions()']")
    WebElement transactionsButton;

    @FindBy(css = "button[ng-click='deposit()']")
    WebElement depositButton;

    @FindBy(css = "input[ng-model='amount']")
    WebElement amountToBeDepositedTextBox;

    @FindBy(css = "button[type='submit']")
    WebElement submitButton;

    @FindBy(css = "button[ng-click='withdrawl()']")
    WebElement withdrawButton;

    private Common common = null;

    public AccountPage(Common commonObj) {

        common = commonObj;
        PageFactory.initElements(common.getDriver(), this);
    }

    public void clickTransactionsButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable(transactionsButton)).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void clickDepositButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable(depositButton)).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void setAmountTextBox(Integer amount) {
        try {

            WebElement element = common.getDriverWait().until(ExpectedConditions.visibilityOf(amountToBeDepositedTextBox));

            element.clear();
            element.sendKeys(amount.toString());

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void clickSubmitDepositButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable(submitButton)).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void clickWithdrawButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable(withdrawButton)).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void clickSubmitWithdrawButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable(submitButton)).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void verifyTransactionMessage(boolean expected) {
        try {

            if(expected){

                if(common.isElementPresent(new By.ByCssSelector("span[ng-show='message']"))){

                    common.Report("Message is visible as expected");

                }else{

                    common.Report("Message is invisible", Common.MessageColor.RED);
                }
            }else{
                if(common.isElementPresent(new By.ByCssSelector("span[ng-show='message']"))){

                    common.Report("Message is invisible as expected");

                }else{
                    common.Report("Message is visible", Common.MessageColor.RED);
                }
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }
}
