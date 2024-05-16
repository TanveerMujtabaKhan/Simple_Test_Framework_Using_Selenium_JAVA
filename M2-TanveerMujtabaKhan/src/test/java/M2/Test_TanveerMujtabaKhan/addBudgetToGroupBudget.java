package M2.Test_TanveerMujtabaKhan;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import base.RequisiteBase;
import pages.sampleBoardPage;
import pages.loginPage;

import java.util.Random;
@Feature("Add Buget to different Groups")
public class addBudgetToGroupBudget extends RequisiteBase {
sampleBoardPage home = new sampleBoardPage();
loginPage login = new loginPage();

@Test(description = "Add Budget to Group Budget")
@Story("Add Buget to Group Budget")
@Description("Add Budget Name, set status to 'Done' , add forumula and Data under Group Budget")
@Step
public void addBugetDetailsToBudgetGroup() {
	Random random = new Random();
	int randomIndex = random.nextInt(100);
	String budgetName = "budget item "+randomIndex;
	String budgetGroupName = "Group Budget";
	String groupTitle = "Group Title";
	login.loginUser(credentials("email"), credentials("password"));
	// user can add group name against any budgetGroup Name
	home.addGroupName(groupTitle, budgetGroupName, budgetName);
	// user can change any status using this method
	home.setStatusOfBudget(budgetName, "Done");
	// This Method also print all the dates available
	home.selectDateFromDatePicker(budgetName);
	home.addFormula(budgetName, "X1+X2+X3");
	
	}
}
