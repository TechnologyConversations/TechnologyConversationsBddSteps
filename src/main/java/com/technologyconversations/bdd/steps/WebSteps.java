package com.technologyconversations.bdd.steps;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.codeborne.selenide.*;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.opera.core.systems.OperaDriver;
import com.technologyconversations.bdd.steps.util.*;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class WebSteps {

    protected static final int MILLISECONDS_IN_SECOND = 1000;
    protected static final int SLEEP_AFTER_CLICK = 100;

    // TODO Add methods that use selector with index
    // TODO Add methods that use findAll selector ($$)
    // TODO Add uploadFromClasspath
    // TODO Add options
    // TODO Add focused
    // TODO Add selected

    public final Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    private static final String SELECTORS_INFO = "\nAny CSS selector can be used to locate the element. "
            + "Most commonly used selectors are:\n"
            + ".myClass: Matches any element with class equal to 'myClass'\n"
            + "#myId: Matches any element with ID equal to 'myId'\n"
            + "For more information on CSS selectors please consult http://www.w3.org/TR/CSS21/selector.html.\n"
            + "Additional selectors are:\n"
            + "text:my text: Matches any element with text equal to 'my text'";
    private static final String VALUE_TO_VARIABLE_INFO = "\nValue will be saved as variable. "
            + "It can be retrieved using selector as the key.";
    private static final String CASE_INSENSITIVE = "\nVerification is NOT case sensitive.";

    private Map<String, String> params;
    @BddParamsBean()
    public final void setParams(final Map<String, String> value) {
        params = value;
    }
    public final Map<String, String> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    private String baseUrl;
    public final String getBaseUrl() {
        if (baseUrl == null) {
            if (getParams().containsKey("url")) {
                baseUrl = getParams().get("url");
            } else {
                baseUrl = "";
            }
        }
        return baseUrl;
    }

    /*
    Supported drivers are: firefox (default), chrome (the fastest, recommended), htmlunit (headless browser),
    ie, opera (slow and unstable, not recommended), phantomjs (headless browser).
    Same effect can be accomplished with System parameter (i.e. -Dbrowser=chrome)
    TODO Test
    */
    private WebDriver webDriver;

    public final void setWebDriver(final BddVariable driver) {
        if (driver == null || driver.toString().isEmpty()) {
            webDriver = null;
        } else {
            switch (driver.toString().toLowerCase()) {
                case "firefox":
                    webDriver = new FirefoxDriver();
                    break;
                case "chrome":
                    webDriver = new ChromeDriver();
                    break;
                case "htmlunit":
                    HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
                    htmlUnitDriver.setJavascriptEnabled(true);
                    webDriver = htmlUnitDriver;
                    break;
                case "ie":
                    webDriver = new InternetExplorerDriver();
                    break;
                case "opera":
                    webDriver = new OperaDriver();
                    break;
                case "phantomjs":
                    webDriver = new PhantomJSDriver();
                    break;
                default:
                    throw new RuntimeException(driver + " driver is currently not supported");
            }
            WebDriverRunner.setWebDriver(webDriver);
            setSize();
        }
    }

    @BddParam(value = "browser", description = "Supported drivers are: firefox (default), "
            + "chrome (the fastest, recommended), htmlunit (headless browser), ie, "
            + "opera (slow and unstable, not recommended), phantomjs (headless browser)."
            , options = {@BddOptionParam(text = "Mozilla Firefox", value = "firefox", isSelected = true),
                         @BddOptionParam(text = "Google Chrome", value = "chrome"),
                         @BddOptionParam(text = "HTML Unit", value = "htmlunit"),
                         @BddOptionParam(text = "Internet Explorer", value = "ie"),
                         @BddOptionParam(text = "Opera", value = "opera"),
                         @BddOptionParam(text = "Phantom JS", value = "phantomjs") })
    public final void setWebDriver() {
        if (getWebDriver() == null) {
            String browser = "firefox";
            if (getParams().containsKey("browser")) {
                browser = getParams().get("browser");
            }
            setWebDriver(new BddVariable(browser));
        }
    }
    protected final WebDriver getWebDriver() {
        return webDriver;
    }

    // Given

    private boolean urlHasBeenOpened = false;
    @BddDescription("Opens specified address.")
    @Given("Web address $url is opened")
    public final void open(final BddVariable url) {
        setWebDriver();
        String urlString = url.toString();
        if (!urlString.toLowerCase().startsWith("http")) {
            urlString = getBaseUrl() + urlString;
        }
        Selenide.open(urlString);
        urlHasBeenOpened = true;
    }

    @BddDescription("Opens address specified by webUrl parameter.")
    @BddParam(value = "url", description = "Web address used with the 'Given Web home page is opened' step.")
    @Given("Web home page is opened")
    public final void open() {
        assertThat(getParams(), hasKey("url"));
        setWebDriver();
        Selenide.open(getParams().get("url"));
        urlHasBeenOpened = true;
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD",
            justification = "The only way to set the timeout is to assign new value to the static variable")
    @BddDescription("Sets timeout used when operating with elements. Default value is 4 seconds.")
    @BddParam(value = "timeout", description = "Sets timeout used when operating with elements."
            + " Default value is 4 seconds.")
    @Given("Web timeout is $seconds seconds")
    public final void setConfigTimeout(final BddVariable seconds) {
        try {
            Configuration.timeout = Integer.parseInt(seconds.toString()) * MILLISECONDS_IN_SECOND;
        } catch (NumberFormatException e) {
            getLogger().info("Could not parse " + seconds + " as integer");
        }
    }
    protected final int getConfigTimeout() {
        Long value = Configuration.timeout / MILLISECONDS_IN_SECOND;
        return value.intValue();
    }

    @BddDescription("Sets browser window size")
    @BddParam(value = "widthHeight", description = "Sets window width and height."
            + " Values should be separated by comma (i.e. 1024, 768)")
    @Given("Web window size is $width width and $height height")
    public final void setSize(final BddVariable width, final BddVariable height) {
        try {
            int widthFormatted = Integer.parseInt(width.toString());
            int heightFormatted = Integer.parseInt(height.toString());
            getWebDriver().manage().window().setSize(new Dimension(widthFormatted, heightFormatted));
        } catch (NumberFormatException e) {
            getLogger().info("Could not parse " + width + " or " + height + " as integer");
        }
    }
    protected final void setSize() {
        if (getParams().containsKey("widthHeight")) {
            String widthHeight = getParams().get("widthHeight");
            if (!widthHeight.isEmpty()) {
                String[] widthHeightArray = widthHeight.split(",");
                assertThat("widthHeight must contain two numbers separated by comma.", widthHeightArray.length, is(2));
                BddVariable width = new BddVariable(widthHeightArray[0].trim());
                BddVariable height = new BddVariable(widthHeightArray[1].trim());
                setSize(width, height);
            }
        }
    }

    @Given("Web page is refreshed")
    public final void refresh() {
        Selenide.refresh();
    }

    // When

    @BddDescription("Clicks the element." + SELECTORS_INFO)
    @When("Web user clicks the element $selector")
    public final void clickElement(final BddVariable selector) {
        SelenideElement element = findElement(selector);
        element.scrollTo();
        // TODO Figure out a better way
        try {
            Thread.sleep(SLEEP_AFTER_CLICK);
        } catch (InterruptedException e) {
            getLogger().fine(e.getMessage());
        }
        element.click();
    }

    @BddDescription("Clears the text field and sets the specified value." + SELECTORS_INFO + VALUE_TO_VARIABLE_INFO)
    @When("Web user sets value $value to the element $selector")
    public final void setElementValue(final BddVariable value, final BddVariable selector) {
        SelenideElement element = findElement(selector);
        element.scrollTo();
        element.setValue(value.toString());
        CommonSteps.addVariable(selector.toString(), value.toString());
    }

    @BddDescription("Appends the specified value." + SELECTORS_INFO + VALUE_TO_VARIABLE_INFO)
    @When("Web user appends value $value to the element $selector")
    public final void appendElementValue(final BddVariable value, final BddVariable selector) {
        SelenideElement element = findElement(selector);
        element.scrollTo();
        element.append(value.toString());
        CommonSteps.addVariable(selector.toString(), value.toString());
    }

    @BddDescription("Presses enter key on a specified element" + SELECTORS_INFO)
    @When("Web user presses the enter key in the element $selector")
    public final void pressEnter(final BddVariable selector) {
        SelenideElement element = findElement(selector);
        element.scrollTo();
        element.pressEnter();
    }

    @BddDescription("Select an option from dropdown list" + SELECTORS_INFO + VALUE_TO_VARIABLE_INFO)
    @When("Web user selects $text from the dropdown list $selector")
    public final void selectOption(final BddVariable text, final BddVariable selector) {
        SelenideElement element = findElement(selector);
        element.scrollTo();
        element.selectOption(text.toString());
        CommonSteps.addVariable(selector.toString(), text.toString());
    }

    @When("Web user clears the element $selector")
    public final void clearValue(final BddVariable selector) {
        SelenideElement element = findElement(selector);
        element.scrollTo();
        element.clear();
    }

    @When("Web user drags the element $fromSelector to the $toSelector")
    public final void dragAndDrop(final BddVariable fromSelector, final BddVariable toSelector) {
        SelenideElement from = findElement(fromSelector);
        SelenideElement to = findElement(toSelector);
        Selenide.actions().dragAndDrop(from, to).perform();
    }

    // Then

    @BddDescription("Verifies that the title of the current page is the same as specified text.")
    @Then("Web page title should have exact text $text")
    public final void checkTitle(final BddVariable text) {
        assertThat(title(), equalTo(text.toString()));
    }

    @BddDescription("Verifies that the title of the current page contains the specified text.")
    @Then("Web page title should have text $text")
    public final void checkTitleContains(final BddVariable text) {
        assertThat(title(), containsString(text.toString()));
    }

    @BddDescription("Verifies that the element text contains the specified text."
            + CASE_INSENSITIVE + SELECTORS_INFO)
    @Then("Web element $selector should have text $text")
    public final void shouldHaveText(final BddVariable selector, final BddVariable text) {
        findElement(selector).shouldHave(text(text.toString()));
    }

    @BddDescription("Verifies that the element text does NOT contain the specified text."
            + CASE_INSENSITIVE + SELECTORS_INFO)
    @Then("Web element $selector should NOT have text $text")
    public final void shouldNotHaveText(final BddVariable selector, final BddVariable text) {
        findElement(selector).shouldNotHave(text(text.toString()));
    }

    @BddDescription("Verifies that the element text matches the specified regular expression."
            + " For example, 'Hello, .*, how are you!' uses '.*' to match any text." + SELECTORS_INFO)
    @Then("Web element $selector should have matching text $regEx")
    public final void shouldHaveMatchText(final BddVariable selector, final BddVariable regEx) {
        findElement(selector).shouldHave(matchText(regEx.toString()));
    }

    @BddDescription("Verifies that the element text does NOT match the specified regular expression." + SELECTORS_INFO)
    @Then("Web element $selector should NOT have matching text $regEx")
    public final void shouldNotHaveMatchText(final BddVariable selector, final BddVariable regEx) {
        findElement(selector).shouldNotHave(matchText(regEx.toString()));
    }

    @BddDescription("Verifies that the element text is exactly the same as the specified text."
            + CASE_INSENSITIVE + SELECTORS_INFO)
    @Then("Web element $selector should have exact text $text")
    public final void shouldHaveExactText(final BddVariable selector, final BddVariable text) {
        findElement(selector).shouldHave(exactText(text.toString()));
    }

    @BddDescription("Verifies that the element text is NOT exactly the same as the specified text."
            + CASE_INSENSITIVE + SELECTORS_INFO)
    @Then("Web element $selector should NOT have exact text $text")
    public final void shouldNotHaveExactText(final BddVariable selector, final BddVariable text) {
        findElement(selector).shouldNotHave(exactText(text.toString()));
    }

    @BddDescription("Verifies that the element value is the same as specified." + SELECTORS_INFO)
    @Then("Web element $selector should have value $value")
    public final void shouldHaveValue(final BddVariable selector, final BddVariable value) {
        findElement(selector).shouldHave(value(value.toString()));
    }

    @BddDescription("Verifies that the element value is NOT the same as specified." + SELECTORS_INFO)
    @Then("Web element $selector should NOT have value $value")
    public final void shouldNotHaveValue(final BddVariable selector, final BddVariable value) {
        findElement(selector).shouldNotHave(value(value.toString()));
    }

    @BddDescription("Verifies that the text of the selected dropdown list element is the same as text")
    @Then("Web dropdown list $selector has $text selected")
    public final void shouldHaveSelectedOption(final BddVariable selector, final BddVariable text) {
        findElement(selector).getSelectedOption().shouldHave(text(text.toString()));
    }

    @BddDescription("Verifies that the text of the selected dropdown list element is NOT the same as text")
    @Then("Web dropdown list $selector does NOT have $text selected")
    public final void shouldNotHaveSelectedOption(final BddVariable selector, final BddVariable text) {
        findElement(selector).getSelectedOption().shouldNotHave(text(text.toString()));
    }

    @BddDescription("Verifies that the element is visible (appears) and present (exists)")
    @Then("Web element $selector is visible")
    @Alias("Web element $selector appears")
    public final void shouldBeVisible(final BddVariable selector) {
        findElement(selector).shouldBe(visible);
    }

    @BddDescription("Verifies that the element is hidden (not visible, disappeared)")
    @Then("Web element $selector is hidden")
    @Aliases(values = {
            "Web element $selector disappeared",
            "Web element $selector is NOT visible"
    })
    public final void shouldBeHidden(final BddVariable selector) {
        findElement(selector).shouldBe(hidden);
    }

    @BddDescription("Verifies that the element is present (exists)")
    @Then("Web element $selector is present")
    @Alias("Web element $selector exists")
    public final void shouldBePresent(final BddVariable selector) {
        findElement(selector).shouldBe(present);
    }

    @BddDescription("Verifies that the element is read-only")
    @Then("Web element $selector is read-only")
    public final void shouldBeReadOnly(final BddVariable selector) {
        findElement(selector).shouldBe(readonly);
    }

    @BddDescription("Verifies that the element text (or value in case of input) is empty.")
    @Then("Web element $selector is empty")
    public final void shouldBeEmpty(final BddVariable selector) {
        findElement(selector).shouldBe(empty);
    }

    @BddDescription("Verifies that the element is enabled")
    @Then("Web element $selector is enabled")
    public final void shouldBeEnabled(final BddVariable selector) {
        findElement(selector).shouldBe(enabled);
    }

    @BddDescription("Verifies that the element is disabled")
    @Then("Web element $selector is disabled")
    public final void shouldBeDisabled(final BddVariable selector) {
        findElement(selector).shouldBe(disabled);
    }

    // Common methods

    public final SelenideElement findElement(final BddVariable selector) {
        openIfNotAlreadyOpened();
        String formattedSelector = selector.toString();
        String byTextPrefix = "text:";
        SelenideElement element;
        if (formattedSelector.startsWith(byTextPrefix)) {
            element = $(Selectors.byText(formattedSelector.substring(byTextPrefix.length())));
        } else {
            if (Character.isLetter(formattedSelector.charAt(0))) {
                formattedSelector = "#" + formattedSelector;
            }
            element = $(formattedSelector);
        }
        return element;
    }

    private void openIfNotAlreadyOpened() {
        if (!urlHasBeenOpened) {
            open();
        }
    }

    @BeforeScenario
    public final void beforeScenarioWebSteps() {
        if (getParams().containsKey("timeout")) {
            String timeout = getParams().get("timeout");
            setConfigTimeout(new BddVariable(timeout));
        }
    }

    @AfterStories
    public final void afterStoriesWebSteps() {
        WebDriverRunner.closeWebDriver();
        webDriver = null;
    }

    @AsParameterConverter
    public final BddVariable createBddVariable(final String value) {
        return new BddVariable(value);
    }

}
