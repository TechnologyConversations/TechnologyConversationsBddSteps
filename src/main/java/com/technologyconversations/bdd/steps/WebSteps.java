package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.opera.core.systems.OperaDriver;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class WebSteps {

    public static void main(final String[] args) throws Exception {
        WebSteps steps = new WebSteps();
        steps.open("/login");
    }

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
            WebDriverRunner.setWebDriver(new HtmlUnitDriver());
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

    @Given("Web address $url is opened")
    public void open(String url) {
        Selenide.open(url);
    }

    @BddAnnotation(description = "Verifies that the title of the current page is as expected.")
    @Then("Web page title is $title")
    public void checkTitle(String title) {
        Assert.assertEquals(title, title());
    }

    @BddAnnotation(description = "Clicks the element. Any CSS selector can be used to locate the element. Most commonly used selectors are:\n" +
            ".myClass: Matches any element with class equal to 'myClass'\n" +
            "#myId: Matches any element with ID equal to 'myId'\n" +
            "For more information on CSS selectors please consult http://www.w3.org/TR/CSS21/selector.html.")
    @When("Web user clicks the element $selector")
    public void click(String selector) {
        if (Character.isLetter(selector.charAt(0))) {
            selector = "#" + selector;
        }
        $(selector).click();
    }

}
