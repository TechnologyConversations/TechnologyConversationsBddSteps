package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.technologyconversations.bdd.steps.util.BddParam;
import com.technologyconversations.bdd.steps.util.BddParamsBean;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebStepsTest {

    // TODO Test with all browsers

    private static WebSteps steps;
    private final String linkId = "#linkId";
    private final BddVariable selectSelector = new BddVariable("#selectId");
    private final BddVariable textAreaSelector = new BddVariable("#textAreaId");
    private final String invisibleId = "#invisibleId";
    private final String indexTitle = "BDD Steps Test Index";
    private final String pageTitle = "BDD Steps Test Page";
    private final BddVariable notSelectedOptionText = new BddVariable("Option 1 Test");
    private final BddVariable selectedOptionText = new BddVariable("Option 2 Test");
    private static String indexUrl, pageUrl;
    private static Dimension dimension;
    private final String linkText = "this is LINK";
    private final BddVariable inputSelector = new BddVariable("#inputId");
    private final BddVariable value = new BddVariable("random value");

    @BeforeClass
    public static void beforeClass() {
        steps = new WebSteps();
        steps.setWebDriver(new BddVariable("htmlunit"));
        File indexFile = new File("src/test/resources/index.html");
        File pageFile = new File("src/test/resources/page.html");
        indexUrl = "file:///" + indexFile.getAbsolutePath();
        pageUrl = "file:///" + pageFile.getAbsolutePath();
        dimension = new Dimension(789, 678);
    }

    @Before
    public void before() {
        if (!(steps.getWebDriver() instanceof HtmlUnitDriver)) {
            steps.setWebDriver(new BddVariable("htmlunit"));
        }
        steps.setConfigTimeout(new BddVariable("0"));
        steps.open(new BddVariable(indexUrl));
        steps.setParams(null);
        steps.setSize(new BddVariable("100"), new BddVariable("100"));
        CommonSteps.setVariableMap(null);
    }

    // setWebDriver

    @Test
    public void setWebDriverShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setWebDriver").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("browser"));
    }

    @Test
    public void setWebDriverShouldSetWebDriver() {
        steps.setWebDriver(new BddVariable("phantomjs"));
        assertThat(steps.getWebDriver(), is(instanceOf(PhantomJSDriver.class)));
    }

    @Test
    public void setWebDriverShouldSetWindowWidthAndHeight() {
        String widthHeight = Integer.toString(dimension.getWidth()) + ", " + Integer.toString(dimension.getHeight());
        steps.getParams().put("widthHeight", widthHeight);
        steps.setWebDriver(new BddVariable("phantomjs"));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(equalTo(dimension.getWidth())));
        assertThat(actual.getHeight(), is(equalTo(dimension.getHeight())));
    }

    @Test
    public void setWebDriverShouldNotSetWindowWidthAndHeightWhenWeigthHeightParamIsNotSet() {
        steps.setWebDriver(new BddVariable("phantomjs"));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(not(equalTo(dimension.getWidth()))));
        assertThat(actual.getHeight(), is(not(equalTo(dimension.getHeight()))));
    }

    @Test
    public void setWebDriverShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setWebDriver", BddVariable.class), is(notNullValue()));
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
        steps.setConfigTimeout(new BddVariable("0"));
        start = new Date();
        try {
            steps.clickElement(new BddVariable("#nonExistingElement"));
        } catch (ElementNotFound e) {
            // Do nothing
        }
        actual = (int) (new Date().getTime() - start.getTime());
        assertThat(actual, is(lessThan(1000)));
        steps.setConfigTimeout(new BddVariable("1"));
        start = new Date();
        try {
            steps.clickElement(new BddVariable("#nonExistingElement"));
        } catch (ElementNotFound e) {
            // Do nothing
        }
        actual = (int) (new Date().getTime() - start.getTime());
        assertThat(actual, is(greaterThan(1000)));
    }

    @Test
    public void setConfigTimeoutShouldBeSetToZeroIfValueCannotBeParsedToInteger() {
        steps.setConfigTimeout(new BddVariable("X"));
        assertThat(steps.getConfigTimeout(), is(0));
    }

    @Test
    public void configTimeoutShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setConfigTimeout", BddVariable.class).getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("timeout"));
    }

    @Test
    public void setConfigTimeoutShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setConfigTimeout", BddVariable.class), is(notNullValue()));
    }

    // setSize

    @Test
    public void setSizeWithWidthAndHeightShouldHaveTheBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setSize", BddVariable.class, BddVariable.class).getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("widthHeight"));
    }

    @Test
    public void setSizeWithWidthAndHeightShouldSetWindowWidthAndHeight() {
        String width = Integer.toString(dimension.getWidth());
        String height = Integer.toString(dimension.getHeight());
        steps.setSize(new BddVariable(width), new BddVariable(height));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(dimension)));
    }

    @Test
    public void setSizeWithWidthAndHeightShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setSize", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    @Test
    public void setSizeShouldSetWidthAndHeightUsingParams() {
        Dimension expected = new Dimension(453, 643);
        steps.getParams().put("widthHeight", "453, 643");
        steps.setSize();
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void setSizeShouldDoNothingIfParamWidthHeightIsNotSet() {
        Dimension expected = steps.getWebDriver().manage().window().getSize();
        steps.getParams().put("widthHeight", "");
        steps.setSize();
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(expected)));
    }

    // open

    @Test
    public void openWithUrlShouldRedirectToTheSpecifiedUrl() {
        steps.checkTitle(new BddVariable(indexTitle));
        steps.open(new BddVariable(pageUrl));
        steps.checkTitle(new BddVariable(pageTitle));
    }

    @Test(expected = AssertionError.class)
    public void openShouldFailIfWebUrlPageDoesNotExist() {
        steps.open();
    }

    @Test
    public void openShouldRedirectToTheWebUrlSpecifiedAsParam() {
        steps.checkTitle(new BddVariable(indexTitle));
        steps.getParams().put("url", pageUrl);
        steps.open();
        steps.checkTitle(new BddVariable(pageTitle));
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
        steps.open(new BddVariable(indexUrl));
        assertThat(steps.getWebDriver(), is(instanceOf(PhantomJSDriver.class)));
    }

    @Test
    public void openShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("open", BddVariable.class), is(notNullValue()));
    }


    // clickElement

    @Test
    public void clickElementShouldUseIdSelectorIfSelectorItStartsWithSharp() {
        steps.checkTitle(new BddVariable(indexTitle));
        steps.clickElement(new BddVariable(linkId));
        steps.checkTitle(new BddVariable(pageTitle));
    }

    @Test
    public void clickElementShouldUseCssSelectorIfSelectorItStartsWithDot() {
        steps.checkTitle(new BddVariable(indexTitle));
        steps.clickElement(new BddVariable(".linkClass"));
        steps.checkTitle(new BddVariable(pageTitle));
    }

    @Test
    public void clickElementShouldUseIdAsSelectorIfSelectorStartsWithLetter() {
        steps.checkTitle(new BddVariable(indexTitle));
        steps.clickElement(new BddVariable(linkId));
        steps.checkTitle(new BddVariable(pageTitle));
    }

    @Test
    public void clickElementShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("clickElement", BddVariable.class), is(notNullValue()));
    }

    // findElement

    @Test
    public void findElementShouldUseByTextIfSelectorStartsWithTextColon() {
        SelenideElement element = steps.findElement(new BddVariable("text:This is div"));
        assertThat(element.exists(), is(true));
    }

    @Test
    public void findElementShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("findElement", BddVariable.class), is(notNullValue()));
    }


    // shouldHaveText

    @Test
    public void shouldHaveTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveText(new BddVariable(linkId), new BddVariable("This is non-existent text"));
    }

    @Test
    public void shouldHaveTextShouldPassIfElementTextContainsSpecifiedText() {
        steps.shouldHaveText(new BddVariable(linkId), new BddVariable("This is"));
    }

    @Test
    public void shouldHaveTextShouldBeCaseInsensitive() {
        steps.shouldHaveText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test
    public void shouldHaveTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldHaveText", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldNotHaveText

    @Test
    public void shouldNotHaveTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveText(new BddVariable(linkId), new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldFailIfElementContainsSpecifiedText() {
        steps.shouldNotHaveText(new BddVariable(linkId), new BddVariable("This is"));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldBeCaseInsensitive() {
        steps.shouldNotHaveText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test
    public void shouldNotHaveTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldNotHaveText", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldHaveExactText

    @Test
    public void shouldHaveExactTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveExactText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveExactTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveExactText(new BddVariable(linkId), new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveExactTextShouldFailIfElementTextContainsSpecifiedText() {
        steps.shouldHaveExactText(new BddVariable(linkId), new BddVariable("This is"));
    }

    @Test
    public void shouldHaveExactTextShouldBeCaseInsensitive() {
        steps.shouldHaveExactText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test
    public void shouldHaveExactTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldHaveExactText", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldNotHaveExactText

    @Test
    public void shouldNotHaveExactTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveExactText(new BddVariable(linkId), new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveExactTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveExactText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test
    public void shouldNotHaveExactTextShouldPassIfElementContainsSpecifiedText() {
        steps.shouldNotHaveExactText(new BddVariable(linkId), new BddVariable("This is"));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveExactTextShouldBeCaseInsensitive() {
        steps.shouldNotHaveExactText(new BddVariable(linkId), new BddVariable(linkText));
    }

    @Test
    public void shouldNotHaveExactTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldNotHaveExactText", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldHaveMatchText

    @Test
    public void shouldHaveMatchTextShouldPassIfThereIsMatch() {
        steps.shouldHaveMatchText(new BddVariable(linkId), new BddVariable("This .* link"));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveMatchTextShouldFailIfThereIsNoMatch() {
        steps.shouldHaveMatchText(new BddVariable(linkId), new BddVariable("This is non-existent text"));
    }

    @Test
    public void shouldHaveMatchTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldHaveMatchText", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldNotHaveMatchText

    @Test
    public void shouldNotHaveMatchTextShouldPassIfThereIsNoMatch() {
        steps.shouldNotHaveMatchText(new BddVariable(linkId), new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveMatchTextShouldFailIfThereIsMatch() {
        steps.shouldNotHaveMatchText(new BddVariable(linkId), new BddVariable("This .* link"));
    }

    @Test
    public void shouldNotHaveMatchTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldNotHaveMatchText", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldHaveValue

    @Test
    public void shouldHaveValueShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveValue(inputSelector, new BddVariable("This is input"));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveValueShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveValue(inputSelector, new BddVariable("This is non-existent value"));
    }

    @Test
    public void shouldHaveValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldHaveValue", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldNotHaveValue

    @Test
    public void shouldNotHaveValueShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveValue(inputSelector, new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveValueShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveValue(inputSelector, new BddVariable("This is input"));
    }

    @Test
    public void shouldNotHaveValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldNotHaveValue", BddVariable.class, BddVariable.class), is(notNullValue()));
    }


    // setElementValue

    @Test
    public void setElementValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setElementValue", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public void setElementValueShouldUseWhenAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setElementValue", BddVariable.class, BddVariable.class);
        Annotation annotation = method.getAnnotation(When.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public void setElementValueShouldSetValueToInputElement() {
        String value = "this is new value";
        steps.shouldNotHaveValue(inputSelector, new BddVariable(value));
        steps.setElementValue(new BddVariable(value), inputSelector);
        steps.shouldHaveValue(inputSelector, new BddVariable(value));
    }

    @Test
    public void setElementValueShouldClearValueBeforeSettingTheNewOne() {
        steps.setElementValue(new BddVariable("some random value"), inputSelector);
        steps.setElementValue(value, inputSelector);
        steps.shouldHaveValue(inputSelector, value);
    }

    @Test
    public void setElementValueShouldAddVariable() {
        steps.setElementValue(value, inputSelector);
        assertThat(CommonSteps.getVariableMap(), hasEntry(inputSelector.toString(), value.toString()));
    }

    @Test
    public void setElementValueShouldSetValueToTextAreaElements() {
        steps.shouldNotHaveValue(textAreaSelector, value);
        steps.setElementValue(value, textAreaSelector);
        assertThat(steps.findElement(textAreaSelector).text(), is(equalTo(value.toString() + "x")));
    }

    // appendElementValue

    @Test
    public void appendElementValueShouldAppendValueToInputElement() {
        String value1 = "value1";
        String value2 = "value2";
        steps.setElementValue(new BddVariable(value1), inputSelector);
        steps.shouldHaveValue(inputSelector, new BddVariable(value1));
        steps.appendElementValue(new BddVariable(value2), inputSelector);
        steps.shouldHaveValue(inputSelector, new BddVariable(value1 + value2));
    }

    @Test
    public void appendElementValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("appendElementValue", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    @Test
    public void appendElementValueShouldAddVariable() {
        steps.appendElementValue(value, inputSelector);
        assertThat(CommonSteps.getVariableMap(), hasEntry(inputSelector.toString(), value.toString()));
    }

    // pressEnter

    @Test
    public void pressEnterShouldSendEnterKeyToTheSpecifiedElement() {
        String value1 = "First line";
        String value2 = "Second line";
        steps.setElementValue(new BddVariable(value1), textAreaSelector);
        steps.shouldHaveText(textAreaSelector, new BddVariable(value1));
        steps.pressEnter(textAreaSelector);
        steps.appendElementValue(new BddVariable(value2), textAreaSelector);
        steps.shouldHaveText(textAreaSelector, new BddVariable(value1 + "\n" + value2));
    }

    @Test
    public void pressEnterShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("pressEnter", BddVariable.class), is(notNullValue()));
    }

    // shouldHaveSelectedOption

    @Test
    public void shouldHaveSelectedOptionShouldNotFailIfSelectedOptionMatchesText() {
        steps.shouldHaveSelectedOption(selectSelector, selectedOptionText);
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveSelectedOptionShouldFailIfSelectedOptionDoesNotMatchText() {
        steps.shouldHaveSelectedOption(selectSelector, notSelectedOptionText);
    }

    @Test
    public void shouldHaveSelectedOptionShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldHaveSelectedOption", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldHaveSelectedOption

    @Test(expected = AssertionError.class)
    public void shouldNotHaveSelectedOptionShouldFailIfSelectedOptionMatchesText() {
        steps.shouldNotHaveSelectedOption(selectSelector, selectedOptionText);
    }

    @Test
    public void shouldNotHaveSelectedOptionShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldNotHaveSelectedOption", BddVariable.class, BddVariable.class), is(notNullValue()));
    }

    // shouldBeVisible

    @Test
    public void shouldBeVisibleShouldNotFailIfElementIsVisible() {
        steps.shouldBeVisible(new BddVariable(linkId));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeVisibleShouldFailIfElementIsHidden() {
        steps.shouldBeVisible(new BddVariable(invisibleId));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeVisibleShouldFailIfElementIsNotPresent() {
        steps.shouldBeVisible(new BddVariable("#nonExistentId"));
    }

    @Test
    public void shouldBeVisibleShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeVisible", BddVariable.class), is(notNullValue()));
    }

    // shouldBeHidden

    @Test(expected = AssertionError.class)
    public void shouldBeHiddenShouldFailIfElementIsVisible() {
        steps.shouldBeHidden(new BddVariable(linkId));
    }

    @Test
    public void shouldBeHiddenShouldNotFailIfElementIsHidden() {
        steps.shouldBeHidden(new BddVariable(invisibleId));
    }

    @Test
    public void shouldBeHiddenShouldNotFailIfElementIsNotPresent() {
        steps.shouldBeHidden(new BddVariable("#nonExistentId"));
    }

    @Test
    public void shouldBeHiddenShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeHidden", BddVariable.class), is(notNullValue()));
    }

    // shouldBePresent

    @Test
    public void shouldBePresentShouldNotFailIfElementIsPresent() {
        steps.shouldBePresent(new BddVariable(linkId));
    }

    @Test(expected = AssertionError.class)
    public void shouldBePresentShouldFailIfElementIsNotPresent() {
        steps.shouldBePresent(new BddVariable("#nonExistentId"));
    }

    @Test
    public void shouldBePresentShouldNotFailIfElementIsNotVisible() {
        steps.shouldBePresent(new BddVariable(invisibleId));
    }

    @Test
    public void shouldBePresentShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBePresent", BddVariable.class), is(notNullValue()));
    }


    // shouldBeReadOnly

    @Test
    public void shouldBeReadOnlyShouldNotFailIfElementIsReadOnly() {
        steps.shouldBeReadOnly(new BddVariable("#readOnlyId"));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeReadOnlyShouldFailIfElementIsNotReadOnly() {
        steps.shouldBeReadOnly(new BddVariable(linkId));
    }

    @Test
    public void shouldBeReadOnlyShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeReadOnly", BddVariable.class), is(notNullValue()));
    }

    // shouldBeEmpty

    @Test
    public void shouldBeEmptyShouldNotFailIfElementIsEmpty() {
        steps.shouldBeEmpty(new BddVariable("#emptyId"));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeEmptyShouldFailIfElementIsNotEmpty() {
        steps.shouldBeEmpty(new BddVariable(linkId));
    }

    @Test
    public void shouldBeEmptyShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeEmpty", BddVariable.class), is(notNullValue()));
    }

    // shouldBeEnabled

    @Test
    public void shouldBeEnabledShouldNotFailIfElementIsEnabled() {
        steps.shouldBeEnabled(new BddVariable(linkId));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeEnabledShouldFailIfElementIsDisabled() {
        steps.shouldBeEnabled(new BddVariable("#disabledId"));
    }

    @Test
    public void shouldBeEnabledShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeEnabled", BddVariable.class), is(notNullValue()));
    }

    // shouldBeDisabled

    @Test
    public void shouldBeDisabledShouldNotFailIfElementIsDisabled() {
        steps.shouldBeDisabled(new BddVariable("#disabledId"));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeDisabledShouldFailIfElementIsEnabled() {
        steps.shouldBeDisabled(new BddVariable(linkId));
    }

    @Test
    public void shouldBeDisabledShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeDisabled", BddVariable.class), is(notNullValue()));
    }

    // selectOption

    @Test
    public void selectOptionShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method actual = WebSteps.class.getMethod("selectOption", BddVariable.class, BddVariable.class);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void selectOptionShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Object actual = WebSteps.class.getMethod("selectOption", BddVariable.class, BddVariable.class).getAnnotation(When.class);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void selectOptionShouldSelectDropDownListItem() {
        steps.shouldHaveSelectedOption(selectSelector, selectedOptionText);
        steps.selectOption(notSelectedOptionText, selectSelector);
        steps.shouldHaveSelectedOption(selectSelector, notSelectedOptionText);
    }

    @Test
    public void selectOptionShouldAddVariable() {
        steps.selectOption(selectedOptionText, selectSelector);
        assertThat(CommonSteps.getVariableMap(), hasEntry(selectSelector.toString(), selectedOptionText.toString()));
        System.out.println(CommonSteps.getVariableMap().values());
    }

    // clearValue

    @Test
    public void clearValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("clearValue", BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public void clearValueShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Annotation annotation = WebSteps.class.getMethod("clearValue", BddVariable.class).getAnnotation(When.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public void clearValueShouldClearTheValue() {
        steps.clearValue(inputSelector);
        String actual = steps.findElement(inputSelector).val();
        assertThat(actual, isEmptyString());
    }

    // refresh

    @Test
    public void refreshShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Annotation annotation = WebSteps.class.getMethod("refresh").getAnnotation(Given.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public void refreshShouldReloadThePage() {
        steps.setElementValue(value, inputSelector);
        steps.refresh();
        String actualValue = steps.findElement(inputSelector).val();
        assertThat(actualValue, is(not(value.toString())));
    }

}
