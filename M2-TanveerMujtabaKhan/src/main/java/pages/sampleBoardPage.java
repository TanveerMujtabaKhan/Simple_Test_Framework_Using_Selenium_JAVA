package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import base.RequisiteBase;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class sampleBoardPage extends RequisiteBase {
    private By budgetNameInputLocator(String budgetGroup) {
        return By.xpath("//div[contains(@class, 'resize-group-header-wrapper') and .//h4[text()='" + budgetGroup + "']]/following-sibling::div//input[@class='add-pulse-input seperated-footer' and @placeholder='+ Add budget']");
    }

    private By statusCell(String budgetName) {
        return By.xpath("//div[contains(@class, 'grid-floating-cells-row-component') and .//*[contains(text(), '" + budgetName + "')]]/following-sibling::div[contains(@class, 'grid-cells-row-component-wrapper')]/div[contains(@class, 'col-identifier-status')]");
    }

    private By selectStatus(String status) {
        return By.xpath("//div[@class='ds-text-component']/span[text()='" + status + "']");
    }

    private By collapsedArrow(String budgetGroup) {
        return By.xpath("//span[descendant::h4[text()='" + budgetGroup + "']]/preceding-sibling::span/div[contains(@class, 'collapsable-icon-button-collapsed')]");
    }

    private By expandedArrow(String budgetGroup) {
        return By.xpath("//span[descendant::h4[text()='" + budgetGroup + "']]/preceding-sibling::span/div[contains(@class, 'collapsable-icon-button')]");
    }

    private By datePickerCel(String budgetName) {
        return By.xpath("//div[contains(@class, 'grid-floating-cells-row-component') and .//*[contains(text(), '" + budgetName + "')]]/following-sibling::div/div/following-sibling::div[contains(@class, 'col-identifier-date4')]/div/div/div[contains(@class, 'date-cell-component ')]");
    }

    private By formulaCell(String budgetName) {
        return By.xpath("//div[contains(@class, 'grid-floating-cells-row-component') and .//*[contains(text(), '" + budgetName + "')]]/following-sibling::div[contains(@class, 'grid-cells-row-component-wrapper')]/div[contains(@class, 'col-identifier-formula')]");
    }

    private final By addFormula = By.className("CodeMirror-code");
    private final By updateFormula = By.cssSelector("button.update-formula-button");


    /****************************************
     *@Summary: The method addGroupName performs an action to add a budget name to a specified budget group by locating the necessary elements using explicit waits and user interactions with the web page.
     *****************************************/
    @Step("add Group Name under specific Budget Group")
    public void addGroupName(String budgetGroupTitle, String budgetGroup, String budgetName) {
        Actions actions = new Actions(getDriver());
        explicitPresenceOfElement(budgetNameInputLocator(budgetGroupTitle), 30);
        collapseBudgetList(budgetGroupTitle);
        WebElement groupGrid = explicitPresenceOfElement(budgetNameInputLocator(budgetGroup), 30);
        groupGrid.sendKeys(budgetName);
        actions.sendKeys(Keys.ENTER).perform();
    }

    /****************************************
     * @Summary The method setStatusOfBudget sets the status of a specified budget by locating the relevant elements on the web page
     * using explicit waits and then using user interactions to click on the status cell and select the desired status from a dropdown menu.
     *****************************************/
    @Step("Set Status of Budget by given Status")
    public void setStatusOfBudget(String budgetName, String statusName) {
        Actions actions = new Actions(getDriver());
        explicitPresenceOfElement(statusCell(budgetName), 30);
        getDriver().findElement(statusCell(budgetName)).click();
        explicitPresenceOfElement(selectStatus(statusName), 30);
        WebElement statusElement = getDriver().findElement(selectStatus(statusName));
        actions.moveToElement(statusElement).click().perform();
    }

    /****************************************
     * @Summary The method collapseBudgetList collapses the list of budgets by locating the expand/collapse arrows associated with the specified budget name on the web page.
     * If the arrows are found, and the budget list is expanded, it clicks on the first expand arrow to collapse the budget list.
     * *****************************************/
    public void collapseBudgetList(String budgetName) {
        List<WebElement> expander = getDriver().findElements(expandedArrow(budgetName));
        List<WebElement> collapse = getDriver().findElements(collapsedArrow(budgetName));
        if ((collapse.isEmpty()) && (!expander.isEmpty())) {
            expander.get(0).click();
        }
    }

    /****************************************
     @Summary The method selectDateFromDatePicker interacts with a date picker element associated with the specified budget name on the web page.
     It clicks on the date picker, retrieves and prints all available dates in the current month, filters the dates to find those in the current month,
     selects a random date from the filtered list, and then clicks on that date in the date picker to set it as the selected date.
      * *****************************************/
    @Step("Select Random Date from Date Picker")
    public void selectDateFromDatePicker(String budgetName) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver.get();
        WebElement datePickerElement = explicitPresenceOfElement(datePickerCel(budgetName), 15);
        jsExecutor.executeScript("arguments[0].click();", datePickerElement);
        List<WebElement> dateElements = getDriver().findElements(By.cssSelector(".CalendarDay__button"));
        for (WebElement dateElement : dateElements) {
            String dateLabel = dateElement.getAttribute("aria-label");
            System.out.println("Date available " + dateLabel);
        }
        List<WebElement> datesInCurrentMonth = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        String currentMonth = currentDate.getMonth().name().toLowerCase(); // Convert to lowercase
        currentMonth = currentMonth.substring(0, 1).toUpperCase() + currentMonth.substring(1); // Capitalize first letter
        for (WebElement dateElement : dateElements) {
            String dateLabel = dateElement.getAttribute("aria-label");
            if (dateLabel.contains(currentMonth)) {
                datesInCurrentMonth.add(dateElement);
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(datesInCurrentMonth.size());
        String desiredDateLabel = datesInCurrentMonth.get(randomIndex).getAttribute("aria-label");
        getDriver().findElement(By.xpath("//button[@aria-label='" + desiredDateLabel + "']")).click();
    }

    /****************************************
     @Summary The method addFormula adds a formula to the specified budget by locating the formula cell on the web page and interacting with the CodeMirror editor.
     It clicks on the formula cell, clears any existing formula, enters the new formula, and then clicks on the "Update Formula" button to save the changes.
     *****************************************/
    @Step("Add Formula in text box")
    public void addFormula(String budgetName, String formula) {
        Actions actions = new Actions(getDriver());
        explicitPresenceOfElement(formulaCell(budgetName), 30);
        getDriver().findElement(formulaCell(budgetName)).click();
        WebElement codeMirror = explicitPresenceOfElement(addFormula, 30);
        actions.moveToElement(codeMirror).click().keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform();
        actions.sendKeys(formula).perform();
        getDriver().findElement(updateFormula).click();
    }
}


