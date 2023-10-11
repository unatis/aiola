package Suites;

import Infra.Common.Common;
import Infra.Common.Config;
import Infra.GlobalsqaCom.AccountPage.AccountPage;
import Infra.GlobalsqaCom.CustomerPage.CustomerPage;
import Infra.GlobalsqaCom.LoginPage.LoginPage;
import Infra.GlobalsqaCom.TransactionsPage.TransactionsPage;
import org.junit.jupiter.api.*;


public class Sanity {
    private static Common common = null;

    //To run test - mvn clean test -Dtest=Suites.Sanity
    @BeforeAll
    public static void setup(){

        common = new Common();
    }

    @BeforeEach
    public void init(){

        common.LaunchBrowser(Common.Browser.FIREFOX);
    }

    @Test
    void transactionsHistoryListPositive(){

        common.NavigateTo(Config.getProperty("start.url"));

        LoginPage loginPage = new Infra.GlobalsqaCom.LoginPage.LoginPage(common);

        loginPage.clickCustomerLoginButton();

        CustomerPage customerPage = new Infra.GlobalsqaCom.CustomerPage.CustomerPage(common);

        customerPage.selectYourNameComboBox("Harry Potter");

        customerPage.clickLoginButton();

        AccountPage accountPage = new Infra.GlobalsqaCom.AccountPage.AccountPage(common);

        accountPage.clickTransactionsButton();

        TransactionsPage transactionsPage = new Infra.GlobalsqaCom.TransactionsPage.TransactionsPage(common);

        transactionsPage.verifyTransactionListIsEmpty(true);//function can work as positive and negative way by its expected parameter

        transactionsPage.clickBackButton();

        accountPage.clickDepositButton();

        accountPage.setAmountTextBox(200);

        accountPage.clickSubmitDepositButton();

        accountPage.verifyTransactionMessage(true);

        accountPage.clickWithdrawButton();

        accountPage.verifyTransactionMessage(false);

        common.Await(2);//need to wait reconnection elements to the DOM for stable test working

        accountPage.setAmountTextBox(100);

        accountPage.clickSubmitWithdrawButton();

        accountPage.verifyTransactionMessage(true);

        common.Await(5);//Need to wait the server side data update for stable test working

        accountPage.clickTransactionsButton();

        transactionsPage.verifyTransactionListIsEmpty(false);

        //single row verification
        transactionsPage.verifySingleTransactionDetails(200, TransactionsPage.TransactionType.CREDIT);

        transactionsPage.verifySingleTransactionDetails(100, TransactionsPage.TransactionType.DEBIT);

        //data driven verification example
        transactionsPage.verifyCollectionTransactionsDetails(System.getProperty("user.dir") + "\\src\\test\\java\\DDT\\expectedTableDataCollection.json");

    }

    @AfterEach
    public void teardown() {

        common.CloseBrowser();
    }

    @AfterAll
    public static void cleanup() {

    }
}
