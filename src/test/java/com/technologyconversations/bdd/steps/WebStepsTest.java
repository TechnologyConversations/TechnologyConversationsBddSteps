package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebStepsTest {

    // TODO Test with all browsers

    private static WebSteps steps;
    private final String linkId = "#linkId";
    private final String inputId = "#inputId";
    private final String selectId = "#selectId";
    private final String invisibleId = "#invisibleId";
    private final String indexTitle = "BDD Steps Test Index";
    private final String pageTitle = "BDD Steps Test Page";
    private final String notSelectedOptionText = "Option 1 Test";
    private final String selectedOptionText = "Option 2 Test";
    private static String indexUrl, pageUrl;
    private static Dimension dimension;

    @BeforeClass
    public static void beforeClass() {
        steps = new WebSteps();
        steps.setWebDriver("htmlunit");
        File indexFile = new File("src/test/resources/index.html");
        File pageFile = new File("src/test/resources/page.html");
        indexUrl = "file:///" + indexFile.getAbsolutePath();
        pageUrl = "file:///" + pageFile.getAbsolutePath();
        dimension = new Dimension(789, 678);
    }

    @Before
    public void before() {
        if (!(steps.getWebDriver() instanceof HtmlUnitDriver)) {
            steps.setWebDriver("htmlunit");
        }
        steps.setConfigTimeout(0);
        steps.open(indexUrl);
        steps.setParams(null);
        steps.setSize(100, 100);
    }

    // setWebDriver

    @Test
    public void setWebDriverShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setWebDriver").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("browser"));
    }

    @Test
    public void setWebDriverShouldSetWebDriver() {
        steps.setWebDriver("phantomjs");
        assertThat(steps.getWebDriver(), is(instanceOf(PhantomJSDriver.class)));
    }

    @Test
    public void setWebDriverShouldSetWindowWidthAndHeight() {
        String widthHeight = Integer.toString(dimension.getWidth()) + ", " + Integer.toString(dimension.getHeight());
        steps.getParams().put("widthHeight", widthHeight);
        steps.setWebDriver("phantomjs");
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(equalTo(dimension.getWidth())));
        assertThat(actual.getHeight(), is(equalTo(dimension.getHeight())));
    }

    @Test
    public void setWebDriverShouldNotSetWindowWidthAndHeightWhenWeigthHeightParamIsNotSet() {
        steps.setWebDriver("phantomjs");
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(not(equalTo(dimension.getWidth()))));
        assertThat(actual.getHeight(), is(not(equalTo(dimension.getHeight()))));
    }

    // setParams

    @Test
    public void setParamsShouldStoreParams() {
        WebSteps testSteps = new WebSteps();
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            params.put("key" + i, "value" + i);
        }
        testSteps.setParams(params);
        assertThat(testSteps.getParams(), equalTo(params));
    }

    @Test
    public void setParamsShouldBeAnnotatedAsBddParamsBean() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setParams", Map.class).getAnnotation(BddParamsBean.class), is(notNullValue()));
    }

    // BeforeStories

    @Test
    public void beforeStoriesShouldSetTimeoutWithValueFromParamTimeout() {
        assertThat(steps.getConfigTimeout(), is(0));
        steps.getParams().put("timeout", "10");
        steps.beforeStoriesWebSteps();
        assertThat(steps.getConfigTimeout(), is(10));
    }

    @Test
    public void beforeStoriesShouldDoNothingParamTimeoutIsNotInteger() {
        assertThat(steps.getConfigTimeout(), is(0));
        steps.getParams().put("timeout", "ten");
        steps.beforeStoriesWebSteps();
        assertThat(steps.getConfigTimeout(), is(0));
    }

    // configTimeout

    @Test
    public void configTimeoutShouldSetTimeout() {
        Date start;
        int actual;
        steps.setConfigTimeout(0);
        start = new Date();
        try {
            steps.clickElement("#nonExistingElement");
        } catch (ElementNotFound e) { }
        actual = (int) (new Date().getTime() - start.getTime());
        assertThat(actual, is(lessThan(1000)));
        steps.setConfigTimeout(1);
        start = new Date();
        try {
            steps.clickElement("#nonExistingElement");
        } catch (ElementNotFound e) { }
        actual = (int) (new Date().getTime() - start.getTime());
        assertThat(actual, is(greaterThan(1000)));
    }

    @Test
    public void setSizeShouldSetWindowWidthAndHeight() {
        steps.setSize(dimension.getWidth(), dimension.getHeight());
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(equalTo(dimension.getWidth())));
    }

    @Test
    public void configTimeoutShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setConfigTimeout", int.class).getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("timeout"));
    }

    // open

    @Test
    public void openWithUrlShouldRedirectToTheSpecifiedUrl() {
        steps.checkTitle(indexTitle);
        steps.open(pageUrl);
        steps.checkTitle(pageTitle);
    }

    @Test(expected = AssertionError.class)
    public void openShouldFailIfWebUrlPageDoesNotExist() {
        steps.open();
    }

    @Test
    public void openShouldRedirectToTheWebUrlSpecifiedAsParam() {
        steps.checkTitle(indexTitle);
        steps.getParams().put("url", pageUrl);
        steps.open();
        steps.checkTitle(pageTitle);
    }

    @Test
    public void openShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("open").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("url"));
    }

    @Test
    public void openShouldSetDriverToValueOfTheParamWebDriverWhenDriverIsNull() {
        steps.setWebDriver(null);
        steps.getParams().put("browser", "phantomjs");
        steps.open(indexUrl);
        assertThat(steps.getWebDriver(), is(instanceOf(PhantomJSDriver.class)));
    }

    // clickElement

    @Test
    public void clickElementShouldUseIdSelectorIfSelectorItStartsWithSharp() {
        steps.checkTitle(indexTitle);
        steps.clickElement(linkId);
        steps.checkTitle(pageTitle);
    }

    @Test
    public void clickElementShouldUseCssSelectorIfSelectorItStartsWithDot() {
        steps.checkTitle(indexTitle);
        steps.clickElement(".linkClass");
        steps.checkTitle(pageTitle);
    }

    @Test
    public void clickElementShouldUseIdAsSelectorIfSelectorStartsWithLetter() {
        steps.checkTitle(indexTitle);
        steps.clickElement(linkId);
        steps.checkTitle(pageTitle);
    }

    // findElement

    @Test
    public void findElementShouldUseByTextIfSelectorStartsWithTextColon() {
        SelenideElement element = steps.findElement("text:This is div");
//        System.out.println(element.toString());
        assertThat(element.exists(), is(true));
    }

    // shouldHaveText

    @Test
    public void shouldHaveTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveText(linkId, "This is link");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveText(linkId, "This is non-existent text");
    }

    @Test
    public void shouldHaveTextShouldPassIfElementTextContainsSpecifiedText() {
        steps.shouldHaveText(linkId, "This is");
    }

    @Test
    public void shouldHaveTextShouldBeCaseInsensitive() {
        steps.shouldHaveText(linkId, "this is LINK");
    }

    // shouldNotHaveText

    @Test
    public void shouldNotHaveTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveText(linkId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveText(linkId, "This is link");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldFailIfElementContainsSpecifiedText() {
        steps.shouldNotHaveText(linkId, "This is");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldBeCaseInsensitive() {
        steps.shouldNotHaveText(linkId, "this is LINK");
    }

    // shouldHaveExactText

    @Test
    public void shouldHaveExactTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveExactText(linkId, "This is link");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveExactTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveExactText(linkId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveExactTextShouldFailIfElementTextContainsSpecifiedText() {
        steps.shouldHaveExactText(linkId, "This is");
    }

    @Test
    public void shouldHaveExactTextShouldBeCaseInsensitive() {
        steps.shouldHaveExactText(linkId, "this is LINK");
    }

    // shouldNotHaveExactText

    @Test
    public void shouldNotHaveExactTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveExactText(linkId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveExactTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveExactText(linkId, "This is link");
    }

    @Test
    public void shouldNotHaveExactTextShouldPassIfElementContainsSpecifiedText() {
        steps.shouldNotHaveExactText(linkId, "This is");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveExactTextShouldBeCaseInsensitive() {
        steps.shouldNotHaveExactText(linkId, "this is LINK");
    }

    // shouldHaveMatchText

    @Test
    public void shouldHaveMatchTextShouldPassIfThereIsMatch() {
        steps.shouldHaveMatchText(linkId, "This .* link");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveMatchTextShouldFailIfThereIsNoMatch() {
        steps.shouldHaveMatchText(linkId, "This is non-existent text");
    }

    // shouldNotHaveMatchText

    @Test
    public void shouldNotHaveMatchTextShouldPassIfThereIsNoMatch() {
        steps.shouldNotHaveMatchText(linkId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveMatchTextShouldFailIfThereIsMatch() {
        steps.shouldNotHaveMatchText(linkId, "This .* link");
    }

    // shouldHaveValue

    @Test
    public void shouldHaveValueShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveValue(inputId, "This is input");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveValueShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveValue(inputId, "This is non-existent value");
    }

    // shouldNotHaveValue

    @Test
    public void shouldNotHaveValueShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveValue(inputId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveValueShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveValue(inputId, "This is input");
    }

    // setElementValue

    @Test
    public void setElementValueShouldSetValueToInputElement() {
        String value = "this is new value";
        steps.shouldNotHaveValue(inputId, value);
        steps.setElementValue(value, inputId);
        steps.shouldHaveValue(inputId, value);
    }

    @Test
    public void setElementValueShouldClearValueBeforeSettingTheNewOne() {
        String value = "this is new value";
        steps.setElementValue("some random value", inputId);
        steps.setElementValue(value, inputId);
        steps.shouldHaveValue(inputId, value);
    }

    // appendElementValue

    @Test
    public void appendElementValueShouldAppendValueToInputElement() {
        String value1 = "value1";
        String value2 = "value2";
        steps.setElementValue(value1, inputId);
        steps.shouldHaveValue(inputId, value1);
        steps.appendElementValue(value2, inputId);
        steps.shouldHaveValue(inputId, value1 + value2);
    }

    // pressEnter

    @Test
    public void pressEnterShouldSendEnterKeyToTheSpecifiedElement() {
        String value1 = "First line";
        String value2 = "Second line";
        String textAreaId = "#textAreaId";
        steps.setElementValue(value1, textAreaId);
        steps.shouldHaveText(textAreaId, value1);
        steps.pressEnter(textAreaId);
        steps.appendElementValue(value2, textAreaId);
        steps.shouldHaveText(textAreaId, value1 + "\n" + value2);
    }

    // shouldHaveSelectedOption

    @Test
    public void shouldHaveSelectedOptionShouldNotFailIfSelectedOptionMatchesText() {
        steps.shouldHaveSelectedOption(selectId, selectedOptionText);
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveSelectedOptionShouldFailIfSelectedOptionDoesNotMatchText() {
        steps.shouldHaveSelectedOption(selectId, notSelectedOptionText);
    }

    // shouldHaveSelectedOption

    @Test(expected = AssertionError.class)
    public void shouldNotHaveSelectedOptionShouldFailIfSelectedOptionMatchesText() {
        steps.shouldNotHaveSelectedOption(selectId, selectedOptionText);
    }

    // shouldBeVisible

    @Test
    public void shouldBeVisibleShouldNotFailIfElementIsVisible() {
        steps.shouldBeVisible(linkId);
    }

    @Test(expected = AssertionError.class)
    public void shouldBeVisibleShouldFailIfElementIsHidden() {
        steps.shouldBeVisible(invisibleId);
    }

    @Test(expected = AssertionError.class)
    public void shouldBeVisibleShouldFailIfElementIsNotPresent() {
        steps.shouldBeVisible("#nonExistentId");
    }

    // shouldBeHidden

    @Test(expected = AssertionError.class)
    public void shouldBeHiddenShouldFailIfElementIsVisible() {
        steps.shouldBeHidden(linkId);
    }

    @Test
    public void shouldBeHiddenShouldNotFailIfElementIsHidden() {
        steps.shouldBeHidden(invisibleId);
    }

    @Test
    public void shouldBeHiddenShouldNotFailIfElementIsNotPresent() {
        steps.shouldBeHidden("#nonExistentId");
    }

    // shouldBePresent

    @Test
    public void shouldBePresentShouldNotFailIfElementIsPresent() {
        steps.shouldBePresent(linkId);
    }

    @Test(expected = AssertionError.class)
    public void shouldBePresentShouldFailIfElementIsNotPresent() {
        steps.shouldBePresent("#nonExistentId");
    }

    @Test
    public void shouldBePresentShouldNotFailIfElementIsNotVisible() {
        steps.shouldBePresent(invisibleId);
    }

    // shouldBeReadOnly

    @Test
    public void shouldBeReadOnlyShouldNotFailIfElementIsReadOnly() {
        steps.shouldBeReadOnly("#readOnlyId");
    }

    @Test(expected = AssertionError.class)
    public void shouldBeReadOnlyShouldFailIfElementIsNotReadOnly() {
        steps.shouldBeReadOnly(linkId);
    }

    // shouldBeEmpty

    @Test
    public void shouldBeEmptyShouldNotFailIfElementIsEmpty() {
        steps.shouldBeEmpty("#emptyId");
    }

    @Test(expected = AssertionError.class)
    public void shouldBeEmptyShouldFailIfElementIsNotEmpty() {
        steps.shouldBeEmpty(linkId);
    }

    // shouldBeEnabled

    @Test
    public void shouldBeEnabledShouldNotFailIfElementIsEnabled() {
        steps.shouldBeEnabled(linkId);
    }

    @Test(expected = AssertionError.class)
    public void shouldBeEnabledShouldFailIfElementIsDisabled() {
        steps.shouldBeEnabled("#disabledId");
    }

    // shouldBeDisabled

    @Test
    public void shouldBeDisabledShouldNotFailIfElementIsDisabled() {
        steps.shouldBeDisabled("#disabledId");
    }

    @Test(expected = AssertionError.class)
    public void shouldBeDisabledShouldFailIfElementIsEnabled() {
        steps.shouldBeDisabled(linkId);
    }

    // selectOption

    @Test
    public void selectOptionShouldSelectDropDownListItem() {
        steps.shouldHaveSelectedOption(selectId, selectedOptionText);
        steps.selectOption(notSelectedOptionText, selectId);
        steps.shouldHaveSelectedOption(selectId, notSelectedOptionText);
    }

}
