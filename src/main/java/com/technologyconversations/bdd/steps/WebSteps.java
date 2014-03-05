package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.opera.core.systems.OperaDriver;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class WebSteps {

    // TODO Add methods that use selector with index
    // TODO Add methods that use findAll selector ($$)
    // TODO Add uploadFromClasspath

    private final String selectorsInfo = "\nAny CSS selector can be used to locate the element. Most commonly used selectors are:\n" +
            ".myClass: Matches any element with class equal to 'myClass'\n" +
            "#myId: Matches any element with ID equal to 'myId'\n" +
            "For more information on CSS selectors please consult http://www.w3.org/TR/CSS21/selector.html.";

    /* Supported drivers are:
            firefox (default)
            chrome (the fastest. Recommended. Needs additional chromedriver binary installation.)
            htmlunit (headless browser)
            ie
            opera (slow and unstable, not recommended)
            phantomjs (newborn headless browser)
        Same effect can be accomplished with System parameter (i.e. -Dbrowser=chrome)
        TODO Test
    */
    public void setDriver(String driver) {
        if ("firefox".equals(driver)) {
            WebDriverRunner.setWebDriver(new FirefoxDriver());
        } else if ("chrome".equals(driver)) {
            WebDriverRunner.setWebDriver(new ChromeDriver());
        } else if ("htmlunit".equals(driver)) {
            WebDriverRunner.setWebDriver(new HtmlUnitDriver(true));
        } else if ("ie".equals(driver)) {
            WebDriverRunner.setWebDriver(new InternetExplorerDriver());
        } else if ("opera".equals(driver)) {
            WebDriverRunner.setWebDriver(new OperaDriver());
        } else if ("phantomjs".equals(driver)) {
            WebDriverRunner.setWebDriver(new PhantomJSDriver());
        } else {
            throw new RuntimeException(driver + " driver is currently not supported");
        }
    }

    // Given

    @BddAnnotation(description = "Opens specified address.")
    @Given("Web address $url is opened")
    public void open(String url) {
        Selenide.open(url);
    }

    @BddAnnotation(description = "Sets timeout used when operating with elements.")
    @Given("Web timeout is $seconds seconds")
    public void configTimeout(int seconds) {
        Configuration.timeout = seconds * 1000;
    }

    // When

    @BddAnnotation(description = "Clicks the element." + selectorsInfo)
    @When("Web user clicks the element $selector")
    public void clickElement(String selector) {
        findElement(selector).click();
    }

    @BddAnnotation(description = "Clears the text field and sets the specified value." + selectorsInfo)
    @When("Web user sets value $value to the element $selector")
    public void setElementValue(String value, String selector) {
        findElement(selector).setValue(value);
    }

    @BddAnnotation(description = "Appends the specified value." + selectorsInfo)
    @When("Web user appends value $value to the element $selector")
    public void appendElementValue(String value, String selector) {
        findElement(selector).append(value);
    }

    @BddAnnotation(description = "Presses enter key on a specified element" + selectorsInfo)
    @When("Web user presses the enter key in the element $selector")
    public void pressEnter(String selector) {
        findElement(selector).pressEnter();
    }

    @BddAnnotation(description = "Select an option from dropdown list" + selectorsInfo)
    @When("Web user selects $text from the dropdown list $selector")
    public void selectOption(String text, String selector) {
        findElement(selector).selectOption(text);
    }

    // Then

    @BddAnnotation(description = "Verifies that the title of the current page is as expected.")
    @Then("Web page title is $title")
    public void checkTitle(String title) {
        Assert.assertEquals(title, title());
    }

    @BddAnnotation(description = "Verifies that the element text is the same as specified." + selectorsInfo)
         @Then("Web element $selector should have text $text")
         public void shouldHaveText(String selector, String text) {
        findElement(selector).shouldHave(text(text));
    }

    @BddAnnotation(description = "Verifies that the element value is the same as specified." + selectorsInfo)
    @Then("Web element $selector should have value $value")
    public void shouldHaveValue(String selector, String value) {
        findElement(selector).shouldHave(value(value));
    }

    @BddAnnotation(description = "Verifies that the element text is NOT the same as specified." + selectorsInfo)
    @Then("Web element $selector should NOT have text $text")
    public void shouldNotHaveText(String selector, String text) {
        findElement(selector).shouldNotHave(text(text));
    }

    @BddAnnotation(description = "Verifies that the element value is NOT the same as specified." + selectorsInfo)
    @Then("Web element $selector should NOT have value $value")
    public void shouldNotHaveValue(String selector, String value) {
        findElement(selector).shouldNotHave(value(value));
    }

    @BddAnnotation(description = "Verifies that the text of the selected dropdown list element is the same as text")
    @Then("Web dropdown list $selector has $text selected")
    public void shouldHaveSelectedOption(String selector, String text) {
        findElement(selector).getSelectedOption().shouldHave(text(text));
    }

    @BddAnnotation(description = "Verifies that the text of the selected dropdown list element is NOT the same as text")
    @Then("Web dropdown list $selector does NOT have $text selected")
    public void shouldNotHaveSelectedOption(String selector, String text) {
        findElement(selector).getSelectedOption().shouldNotHave(text(text));
    }

    @BddAnnotation(description = "Verifies that the element is visible (appears) and present (exists)")
    @Then("Web element $selector is visible")
    public void shouldHaveVisible(String selector) {
        findElement(selector).shouldHave(visible);
    }

    @BddAnnotation(description = "Verifies that the element is hidden (not visible, disappeared)")
    @Then("Web element $selector is visible")
    public void shouldHaveHidden(String selector) {
        findElement(selector).shouldHave(hidden);
    }

    @BddAnnotation(description = "Verifies that the element is present (exists)")
    @Then("Web element $selector is present")
    public void shouldHavePresent(String selector) {
        findElement(selector).shouldHave(present);
    }

    // Common methods

    private SelenideElement findElement(String selector) {
        if (Character.isLetter(selector.charAt(0))) {
            selector = "#" + selector;
        }
        return $(selector);
    }

}
