package Infra.GlobalsqaCom.TransactionsPage;

import Infra.Common.Common;
import jdk.javadoc.doclet.Reporter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionsPage {

    public enum TransactionType{
        CREDIT,
        DEBIT
    }

    @FindBy(css = "button[ng-click='back()']")
    WebElement backButton;

    @FindBy(css = "table.table.table-bordered.table-striped")
    WebElement transactionTable;
    private Common common = null;

    public TransactionsPage(Common commonObj) {

        common = commonObj;
        PageFactory.initElements(common.getDriver(), this);
    }

    public void clickBackButton() {
        try {

            common.getDriverWait().until(ExpectedConditions.elementToBeClickable((backButton))).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void verifyTransactionListIsEmpty(boolean expected) {
        try {

            if(expected){

                if(common.isElementPresent(new By.ByCssSelector("table.table.table-bordered.table-striped > tbody > tr"))){

                    common.Report("Transaction table is not empty", Common.MessageColor.RED);
                }else{
                    common.Report("Transaction table is empty as expected");
                }
            }else{
                if(common.isElementPresent(new By.ByCssSelector("table.table.table-bordered.table-striped > tbody > tr"))){

                    common.Report("Transaction table is not empty as expected");
                }else{
                    common.Report("Transaction table is empty", Common.MessageColor.RED);
                }
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void verifySingleTransactionDetails(Integer amount, TransactionType tType) {
        try {

            String trType = "";

            boolean flgDateFound = false, flgAmountFound = false, flgTypeEquals = false;

            if(tType == TransactionType.CREDIT) {

                trType = "credit";

            }else if(tType == TransactionType.DEBIT){

                trType = "debit";
            }

            WebElement table = common.getDriverWait().until(ExpectedConditions.visibilityOf((transactionTable)));

            List<WebElement> rows = table.findElements(By.cssSelector("tbody > tr"));

            for(WebElement tmpRow : rows){

                List<WebElement> columns = tmpRow.findElements(By.cssSelector("td"));

                if(columns.get(1).getText().trim().equals(amount.toString())) {

                    flgAmountFound = true;

                    if(columns.get(2).getText().trim().toLowerCase().equals(trType)) {

                        flgTypeEquals = true;

                        if(regexDataFormatVerification(columns.get(0).getText())) {

                            flgDateFound = true;
                        }

                        break;

                    }
                }

            }

            if(flgDateFound && flgAmountFound && flgTypeEquals){
                common.Report("Expected transaction of amount \"" +amount+ "\" found successfully");
            }

            if(!flgAmountFound){
                common.Report("Expected transaction of amount \'" +amount+ "\" not found", Common.MessageColor.RED);
            }else{

                if(!flgTypeEquals){
                    common.Report("Expected type \'" +tType+ "\" not equals", Common.MessageColor.RED);
                }
                if(!flgDateFound){
                    common.Report("Data format is wrong", Common.MessageColor.RED);
                }
            }


        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void verifyCollectionTransactionsDetails(String filePath) {
        try {

            InputStream is = new FileInputStream(filePath);

            String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONArray jo = new JSONArray (text);

            for (int i=0; i < jo.length(); i++) {

                JSONObject tmp = jo.getJSONObject(i);

                Iterator<String> keys = tmp.keys();

                while(keys.hasNext()) {
                    String key = keys.next();

                    //System.out.printf("key : %s | value : %s\n", key, tmp.get(key));

                    switch(tmp.get(key).toString()) {
                        case "credit":
                            verifySingleTransactionDetails(Integer.valueOf(key), TransactionType.CREDIT);
                            break;
                        case "debit":
                            verifySingleTransactionDetails(Integer.valueOf(key), TransactionType.DEBIT);
                            break;
                    }

                }
            }

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }
    private boolean regexDataFormatVerification(final String input) {

        final Pattern pattern = Pattern.compile("^[A-Za-z]+\\s\\d\\d,\\s+\\d\\d\\d\\d\\s+(0?[0-9]|1[0-9]|2[0-3]):\\d\\d:\\d\\d\\s+[A-Za-z][A-Za-z]$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);

        final Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}
