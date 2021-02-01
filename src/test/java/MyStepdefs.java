import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import static com.appium.manager.AppiumDriverManager.getDriver;


public class MyStepdefs {
    RemoteWebElement edit;

    @When("I click on {int} number")
    public void iClickOnNumber(int arg0) throws InterruptedException {
        System.out.println("sleep for " + arg0 + " sec");
        Thread.sleep(arg0 * 1000);
        System.out.println("Passed");
    }

    @Given("I accept the tip screen")
    public void i_accept_the_tip_screen() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("I accept the tip screen step executed ");
    }
}
