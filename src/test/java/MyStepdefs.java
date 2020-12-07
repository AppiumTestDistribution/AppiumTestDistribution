import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import static com.appium.manager.AppiumDriverManager.getDriver;


public class MyStepdefs {
    RemoteWebElement edit;

    @When("I click on {int} number")
    public void iClickOnNumber(int arg0) {
        edit = (RemoteWebElement) getDriver().findElementByClassName("Edit");
    }

    @Then("It should finnish")
    public void itShouldFinnish() {
        Assert.assertNotNull(edit);
    }
}
