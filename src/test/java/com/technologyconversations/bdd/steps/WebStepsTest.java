package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.technologyconversations.bdd.steps.util.BddParam;
import com.technologyconversations.bdd.steps.util.BddParamsBean;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.junit.runner.RunWith;

import static org.mockito.Mockito.times;

import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@RunWith(PowerMockRunner.class)
@PrepareForTest({FirefoxDriver.class, WebSteps.class })
@PowerMockIgnore("javax.net.ssl.*")
public class WebStepsTest {

    // TODO Test with all browsers

    private static WebSteps steps;
    private static final BddVariable LINK_ID = new BddVariable("#linkId");
    private final BddVariable selectSelector = new BddVariable("#selectId");
    private final BddVariable textAreaSelector = new BddVariable("#textAreaId");
    private static final String INVISIBLE_ID = "#invisibleId";
    private final BddVariable indexTitle = new BddVariable("BDD Steps Test Index");
    private final BddVariable pageTitle = new BddVariable("BDD Steps Test Page");
    private final BddVariable notSelectedOptionText = new BddVariable("Option 1 Test");
    private final BddVariable selectedOptionText = new BddVariable("Option 2 Test");
    private static String indexUrl, pageUrl;
    private static Dimension dimension;
    private static final String LINK_TEXT = "this is LINK";
    private final BddVariable inputSelector = new BddVariable("#inputId");
    private final BddVariable value = new BddVariable("random value");
    private static final int BROWSER_WIDTH = 789;
    private static final int BROWSER_HEIGHT = 678;

    @BeforeClass
    public static void beforeClass() {
        steps = new WebSteps();
        File indexFile = new File("src/test/resources/index.html");
        File pageFile = new File("src/test/resources/page.html");
        indexUrl = "file:///" + indexFile.getAbsolutePath();
        pageUrl = "file:///" + pageFile.getAbsolutePath();
        dimension = new Dimension(BROWSER_WIDTH, BROWSER_HEIGHT);
    }

    @Before
    public final void before() {
        if (steps.getWebDriver() == null) {
            steps.setWebDriver(new BddVariable("htmlunit"));
        }
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
    public final void setWebDriverShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setWebDriver").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("browser"));
    }

    @Test
    @Ignore("PowerMock RunWith in the class declaration does not play well with JaCoCo. "
            + "More info can be found in https://github.com/jacoco/eclemma/issues/15 .")
    public final void setWebDriverShouldHaveFirefoxAsDefaultBrowser() throws Exception {
        FirefoxDriver mockFirefoxDriver = Mockito.mock(FirefoxDriver.class);
        whenNew(FirefoxDriver.class).withNoArguments().thenReturn(mockFirefoxDriver);
        steps.setWebDriver(null);
        steps.setWebDriver();
        verifyNew(FirefoxDriver.class, times(1)).withNoArguments();
        assertThat(steps.getWebDriver(), is(instanceOf(FirefoxDriver.class)));
    }


    @Test
    public final void setWebDriverShouldSetWebDriver() {
        steps.setWebDriver(new BddVariable("phantomjs"));
        assertThat(steps.getWebDriver(), is(instanceOf(PhantomJSDriver.class)));
    }

    @Test
    public final void setWebDriverShouldSetWindowWidthAndHeight() {
        String widthHeight = Integer.toString(dimension.getWidth()) + ", " + Integer.toString(dimension.getHeight());
        steps.getParams().put("widthHeight", widthHeight);
        steps.setWebDriver(new BddVariable("phantomjs"));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(equalTo(dimension.getWidth())));
        assertThat(actual.getHeight(), is(equalTo(dimension.getHeight())));
    }

    @Test
    public final void setWebDriverShouldNotSetWindowWidthAndHeightWhenWeigthHeightParamIsNotSet() {
        steps.setWebDriver(new BddVariable("phantomjs"));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual.getWidth(), is(not(equalTo(dimension.getWidth()))));
        assertThat(actual.getHeight(), is(not(equalTo(dimension.getHeight()))));
    }

    @Test
    public final void setWebDriverShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setWebDriver", BddVariable.class), is(notNullValue()));
    }

    @Test
    public final void setWebDriverShouldAcceptNullAsDriver() {
        steps.setWebDriver(null);
    }

    // setParams

    @Test
    public final void setParamsShouldStoreParams() {
        WebSteps testSteps = new WebSteps();
        Map<String, String> params = new HashMap<>();
        final int times = 5;
        for (int i = 0; i < times; i++) {
            params.put("key" + i, "value" + i);
        }
        testSteps.setParams(params);
        assertThat(testSteps.getParams(), equalTo(params));
    }

    @Test
    public final void setParamsShouldBeAnnotatedAsBddParamsBean() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setParams", Map.class);
        Annotation annotation = method.getAnnotation(BddParamsBean.class);
        assertThat(annotation, is(notNullValue()));
    }

    // BeforeStories

    @Test
    public final void beforeScenarioShouldSetTimeoutWithValueFromParamTimeout() {
        final int timeout = 10;
        assertThat(steps.getConfigTimeout(), is(0));
        steps.getParams().put("timeout", Integer.toString(timeout));
        steps.beforeScenarioWebSteps();
        assertThat(steps.getConfigTimeout(), is(timeout));
    }

    @Test
    public final void beforeScenarioShouldDoNothingParamTimeoutIsNotInteger() {
        assertThat(steps.getConfigTimeout(), is(0));
        steps.getParams().put("timeout", "ten");
        steps.beforeScenarioWebSteps();
        assertThat(steps.getConfigTimeout(), is(0));
    }

    // configTimeout

    //CHECKSTYLE:OFF
    @SuppressWarnings("PMD.EmptyCatchBlock")
    @Test
    public final void configTimeoutShouldSetTimeout() {
        int timeout = 1;
        int timeoutMilliseconds = timeout * WebSteps.MILLISECONDS_IN_SECOND;
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
        assertThat(actual, is(lessThan(timeoutMilliseconds + 500)));
        steps.setConfigTimeout(new BddVariable(Integer.toString(timeout)));
        start = new Date();
        try {
            steps.clickElement(new BddVariable("#nonExistingElement"));
        } catch (ElementNotFound e) {
            // Do nothing
        }
        actual = (int) (new Date().getTime() - start.getTime());
        assertThat(actual, is(greaterThan(timeoutMilliseconds)));
    }
    //CHECKSTYLE:ON

    @Test
    public final void setConfigTimeoutShouldBeSetToZeroIfValueCannotBeParsedToInteger() {
        steps.setConfigTimeout(new BddVariable("X"));
        assertThat(steps.getConfigTimeout(), is(0));
    }

    @Test
    public final void configTimeoutShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setConfigTimeout", BddVariable.class);
        BddParam bddParam = method.getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("timeout"));
    }

    @Test
    public final void setConfigTimeoutShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setConfigTimeout", BddVariable.class), is(notNullValue()));
    }

    // setSize

    @Test
    public final void setSizeWithWidthAndHeightShouldHaveTheBddParamAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setSize", BddVariable.class, BddVariable.class);
        BddParam bddParam = method.getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("widthHeight"));
    }

    @Test
    public final void setSizeWithWidthAndHeightShouldSetWindowWidthAndHeight() {
        String width = Integer.toString(dimension.getWidth());
        String height = Integer.toString(dimension.getHeight());
        steps.setSize(new BddVariable(width), new BddVariable(height));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(dimension)));
    }

    @Test
    public final void setSizeWithWidthAndHeightShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setSize", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void setSizeShouldNotThrowException() {
        Dimension expected = steps.getWebDriver().manage().window().getSize();
        steps.setSize(new BddVariable("123"), new BddVariable("xxx"));
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public final void setSizeShouldSetWidthAndHeightUsingParams() {
        final int width = 453;
        final int height = 643;
        Dimension expected = new Dimension(width, height);
        steps.getParams().put("widthHeight", String.format("%s, %s", width, height));
        steps.setSize();
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public final void setSizeShouldDoNothingIfParamWidthHeightIsNotSet() {
        Dimension expected = steps.getWebDriver().manage().window().getSize();
        steps.getParams().put("widthHeight", "");
        steps.setSize();
        Dimension actual = steps.getWebDriver().manage().window().getSize();
        assertThat(actual, is(equalTo(expected)));
    }

    // open

    @Test
    public final void openWithUrlShouldRedirectToTheSpecifiedUrl() {
        steps.checkTitle(indexTitle);
        steps.open(new BddVariable(pageUrl));
        steps.checkTitle(pageTitle);
    }

    @Test(expected = AssertionError.class)
    public final void openShouldFailIfWebUrlPageDoesNotExist() {
        steps.open();
    }

    @Test
    public final void openShouldRedirectToTheWebUrlSpecifiedAsParam() {
        steps.checkTitle(indexTitle);
        steps.getParams().put("url", pageUrl);
        steps.open();
        steps.checkTitle(pageTitle);
    }

    @Test
    public final void openShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("open").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("url"));
    }

    @Test
    public final void openShouldSetDriverToValueOfTheParamWebDriverWhenDriverIsNull() {
        steps.setWebDriver(null);
        steps.getParams().put("browser", "phantomjs");
        steps.open(new BddVariable(indexUrl));
        assertThat(steps.getWebDriver(), is(instanceOf(PhantomJSDriver.class)));
    }

    @Test
    public final void openShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("open", BddVariable.class), is(notNullValue()));
    }


    // clickElement

    @Test
    public final void clickElementShouldUseIdSelectorIfSelectorItStartsWithSharp() {
        steps.checkTitle(indexTitle);
        steps.clickElement(LINK_ID);
        steps.checkTitle(pageTitle);
    }

    @Test
    public final void clickElementShouldUseCssSelectorIfSelectorItStartsWithDot() {
        steps.checkTitle(indexTitle);
        steps.clickElement(new BddVariable(".linkClass"));
        steps.checkTitle(pageTitle);
    }

    @Test
    public final void clickElementShouldUseIdAsSelectorIfSelectorStartsWithLetter() {
        steps.checkTitle(indexTitle);
        steps.clickElement(LINK_ID);
        steps.checkTitle(pageTitle);
    }

    @Test
    public final void clickElementShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("clickElement", BddVariable.class), is(notNullValue()));
    }

    // findElement

    @Test
    public final void findElementShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("findElement", BddVariable.class), is(notNullValue()));
    }

    @Test
    public final void findElementShouldUseCssSelectors() {
        SelenideElement element = steps.findElement(LINK_ID);
        assertThat(element.exists(), is(true));
    }

    @Test
    public final void findElementShouldAssumeThatSelectorIsByIdIfNoneIsSpecified() {
        SelenideElement element = steps.findElement(new BddVariable("linkId"));
        assertThat(element.exists(), is(true));
    }

    @Test
    public final void findElementShouldUseByTextIfSelectorStartsWithTextColon() {
        SelenideElement element = steps.findElement(new BddVariable("text:This is div"));
        assertThat(element.exists(), is(true));
    }

    // shouldHaveText

    @Test
    public final void shouldHaveTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test(expected = AssertionError.class)
    public final void shouldHaveTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveText(LINK_ID, new BddVariable("This is non-existent text"));
    }

    @Test
    public final void shouldHaveTextShouldPassIfElementTextContainsSpecifiedText() {
        steps.shouldHaveText(LINK_ID, new BddVariable("This is"));
    }

    @Test
    public final void shouldHaveTextShouldBeCaseInsensitive() {
        steps.shouldHaveText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test
    public final void shouldHaveTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldHaveText", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldNotHaveText

    @Test
    public final void shouldNotHaveTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveText(LINK_ID, new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveTextShouldFailIfElementContainsSpecifiedText() {
        steps.shouldNotHaveText(LINK_ID, new BddVariable("This is"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveTextShouldBeCaseInsensitive() {
        steps.shouldNotHaveText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test
    public final void shouldNotHaveTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldNotHaveText", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldHaveExactText

    @Test
    public final void shouldHaveExactTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveExactText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test(expected = AssertionError.class)
    public final void shouldHaveExactTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveExactText(LINK_ID, new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldHaveExactTextShouldFailIfElementTextContainsSpecifiedText() {
        steps.shouldHaveExactText(LINK_ID, new BddVariable("This is"));
    }

    @Test
    public final void shouldHaveExactTextShouldBeCaseInsensitive() {
        steps.shouldHaveExactText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test
    public final void shouldHaveExactTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldHaveExactText", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldNotHaveExactText

    @Test
    public final void shouldNotHaveExactTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveExactText(LINK_ID, new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveExactTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveExactText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test
    public final void shouldNotHaveExactTextShouldPassIfElementContainsSpecifiedText() {
        steps.shouldNotHaveExactText(LINK_ID, new BddVariable("This is"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveExactTextShouldBeCaseInsensitive() {
        steps.shouldNotHaveExactText(LINK_ID, new BddVariable(LINK_TEXT));
    }

    @Test
    public final void shouldNotHaveExactTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldNotHaveExactText", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldHaveMatchText

    @Test
    public final void shouldHaveMatchTextShouldPassIfThereIsMatch() {
        steps.shouldHaveMatchText(LINK_ID, new BddVariable("This .* link"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldHaveMatchTextShouldFailIfThereIsNoMatch() {
        steps.shouldHaveMatchText(LINK_ID, new BddVariable("This is non-existent text"));
    }

    @Test
    public final void shouldHaveMatchTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldHaveMatchText", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldNotHaveMatchText

    @Test
    public final void shouldNotHaveMatchTextShouldPassIfThereIsNoMatch() {
        steps.shouldNotHaveMatchText(LINK_ID, new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveMatchTextShouldFailIfThereIsMatch() {
        steps.shouldNotHaveMatchText(LINK_ID, new BddVariable("This .* link"));
    }

    @Test
    public final void shouldNotHaveMatchTextShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldNotHaveMatchText", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldHaveValue

    @Test
    public final void shouldHaveValueShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveValue(inputSelector, new BddVariable("This is input"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldHaveValueShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveValue(inputSelector, new BddVariable("This is non-existent value"));
    }

    @Test
    public final void shouldHaveValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldHaveValue", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldNotHaveValue

    @Test
    public final void shouldNotHaveValueShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveValue(inputSelector, new BddVariable("This is non-existent text"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveValueShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveValue(inputSelector, new BddVariable("This is input"));
    }

    @Test
    public final void shouldNotHaveValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldNotHaveValue", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }


    // setElementValue

    @Test
    public final void setElementValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setElementValue", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void setElementValueShouldUseWhenAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("setElementValue", BddVariable.class, BddVariable.class);
        Annotation annotation = method.getAnnotation(When.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public final void setElementValueShouldSetValueToInputElement() {
        String newValue = "this is new value";
        steps.shouldNotHaveValue(inputSelector, new BddVariable(newValue));
        steps.setElementValue(new BddVariable(newValue), inputSelector);
        steps.shouldHaveValue(inputSelector, new BddVariable(newValue));
    }

    @Test
    public final void setElementValueShouldClearValueBeforeSettingTheNewOne() {
        steps.setElementValue(new BddVariable("some random value"), inputSelector);
        steps.setElementValue(value, inputSelector);
        steps.shouldHaveValue(inputSelector, value);
    }

    @Test
    public final void setElementValueShouldAddVariable() {
        steps.setElementValue(value, inputSelector);
        assertThat(CommonSteps.getVariableMap(), hasEntry(inputSelector.toString(), value.toString()));
    }

    @Test
    public final void setElementValueShouldSetValueToTextAreaElements() {
        steps.shouldNotHaveValue(textAreaSelector, value);
        steps.setElementValue(value, textAreaSelector);
        assertThat(steps.findElement(textAreaSelector).text(), is(equalTo(value.toString())));
    }

    // appendElementValue

    @Test
    public final void appendElementValueShouldAppendValueToInputElement() {
        String value1 = "value1";
        String value2 = "value2";
        steps.setElementValue(new BddVariable(value1), inputSelector);
        steps.shouldHaveValue(inputSelector, new BddVariable(value1));
        steps.appendElementValue(new BddVariable(value2), inputSelector);
        steps.shouldHaveValue(inputSelector, new BddVariable(value1 + value2));
    }

    @Test
    public final void appendElementValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("appendElementValue", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void appendElementValueShouldAddVariable() {
        steps.appendElementValue(value, inputSelector);
        assertThat(CommonSteps.getVariableMap(), hasEntry(inputSelector.toString(), value.toString()));
    }

    // pressEnter

    @Test
    public final void pressEnterShouldSendEnterKeyToTheSpecifiedElement() {
        String value1 = "First line";
        String value2 = "Second line";
        steps.setElementValue(new BddVariable(value1), textAreaSelector);
        steps.shouldHaveText(textAreaSelector, new BddVariable(value1));
        steps.pressEnter(textAreaSelector);
        steps.appendElementValue(new BddVariable(value2), textAreaSelector);
        steps.shouldHaveText(textAreaSelector, new BddVariable(value1 + "\n" + value2));
    }

    @Test
    public final void pressEnterShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("pressEnter", BddVariable.class), is(notNullValue()));
    }

    // shouldHaveSelectedOption

    @Test
    public final void shouldHaveSelectedOptionShouldNotFailIfSelectedOptionMatchesText() {
        steps.shouldHaveSelectedOption(selectSelector, selectedOptionText);
    }

    @Test(expected = AssertionError.class)
    public final void shouldHaveSelectedOptionShouldFailIfSelectedOptionDoesNotMatchText() {
        steps.shouldHaveSelectedOption(selectSelector, notSelectedOptionText);
    }

    @Test
    public final void shouldHaveSelectedOptionShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldHaveSelectedOption", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldHaveSelectedOption

    @Test(expected = AssertionError.class)
    public final void shouldNotHaveSelectedOptionShouldFailIfSelectedOptionMatchesText() {
        steps.shouldNotHaveSelectedOption(selectSelector, selectedOptionText);
    }

    @Test
    public final void shouldNotHaveSelectedOptionShouldNotFailIfSelectedOptionMatchesText() {
        steps.shouldNotHaveSelectedOption(selectSelector, new BddVariable("thisDoesNotExist"));
    }

    @Test
    public final void shouldNotHaveSelectedOptionShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("shouldNotHaveSelectedOption", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    // shouldBeVisible

    @Test
    public final void shouldBeVisibleShouldNotFailIfElementIsVisible() {
        steps.shouldBeVisible(LINK_ID);
    }

    @Test(expected = AssertionError.class)
    public final void shouldBeVisibleShouldFailIfElementIsHidden() {
        steps.shouldBeVisible(new BddVariable(INVISIBLE_ID));
    }

    @Test(expected = AssertionError.class)
    public final void shouldBeVisibleShouldFailIfElementIsNotPresent() {
        steps.shouldBeVisible(new BddVariable("#nonExistentId"));
    }

    @Test
    public final void shouldBeVisibleShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeVisible", BddVariable.class), is(notNullValue()));
    }

    // shouldBeHidden

    @Test(expected = AssertionError.class)
    public final void shouldBeHiddenShouldFailIfElementIsVisible() {
        steps.shouldBeHidden(LINK_ID);
    }

    @Test
    public final void shouldBeHiddenShouldNotFailIfElementIsHidden() {
        steps.shouldBeHidden(new BddVariable(INVISIBLE_ID));
    }

    @Test
    public final void shouldBeHiddenShouldNotFailIfElementIsNotPresent() {
        steps.shouldBeHidden(new BddVariable("#nonExistentId"));
    }

    @Test
    public final void shouldBeHiddenShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeHidden", BddVariable.class), is(notNullValue()));
    }

    // shouldBePresent

    @Test
    public final void shouldBePresentShouldNotFailIfElementIsPresent() {
        steps.shouldBePresent(LINK_ID);
    }

    @Test(expected = AssertionError.class)
    public final void shouldBePresentShouldFailIfElementIsNotPresent() {
        steps.shouldBePresent(new BddVariable("#nonExistentId"));
    }

    @Test
    public final void shouldBePresentShouldNotFailIfElementIsNotVisible() {
        steps.shouldBePresent(new BddVariable(INVISIBLE_ID));
    }

    @Test
    public final void shouldBePresentShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBePresent", BddVariable.class), is(notNullValue()));
    }


    // shouldBeReadOnly

    @Test
    public final void shouldBeReadOnlyShouldNotFailIfElementIsReadOnly() {
        steps.shouldBeReadOnly(new BddVariable("#readOnlyId"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldBeReadOnlyShouldFailIfElementIsNotReadOnly() {
        steps.shouldBeReadOnly(LINK_ID);
    }

    @Test
    public final void shouldBeReadOnlyShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeReadOnly", BddVariable.class), is(notNullValue()));
    }

    // shouldBeEmpty

    @Test
    public final void shouldBeEmptyShouldNotFailIfElementIsEmpty() {
        steps.shouldBeEmpty(new BddVariable("#emptyId"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldBeEmptyShouldFailIfElementIsNotEmpty() {
        steps.shouldBeEmpty(LINK_ID);
    }

    @Test
    public final void shouldBeEmptyShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeEmpty", BddVariable.class), is(notNullValue()));
    }

    // shouldBeEnabled

    @Test
    public final void shouldBeEnabledShouldNotFailIfElementIsEnabled() {
        steps.shouldBeEnabled(LINK_ID);
    }

    @Test(expected = AssertionError.class)
    public final void shouldBeEnabledShouldFailIfElementIsDisabled() {
        steps.shouldBeEnabled(new BddVariable("#disabledId"));
    }

    @Test
    public final void shouldBeEnabledShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeEnabled", BddVariable.class), is(notNullValue()));
    }

    // shouldBeDisabled

    @Test
    public final void shouldBeDisabledShouldNotFailIfElementIsDisabled() {
        steps.shouldBeDisabled(new BddVariable("#disabledId"));
    }

    @Test(expected = AssertionError.class)
    public final void shouldBeDisabledShouldFailIfElementIsEnabled() {
        steps.shouldBeDisabled(LINK_ID);
    }

    @Test
    public final void shouldBeDisabledShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("shouldBeDisabled", BddVariable.class), is(notNullValue()));
    }

    // selectOption

    @Test
    public final void selectOptionShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method actual = WebSteps.class.getMethod("selectOption", BddVariable.class, BddVariable.class);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public final void selectOptionShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("selectOption", BddVariable.class, BddVariable.class);
        Object actual = method.getAnnotation(When.class);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public final void selectOptionShouldSelectDropDownListItem() {
        steps.shouldHaveSelectedOption(selectSelector, selectedOptionText);
        steps.selectOption(notSelectedOptionText, selectSelector);
        steps.shouldHaveSelectedOption(selectSelector, notSelectedOptionText);
    }

    @Test
    public final void selectOptionShouldAddVariable() {
        steps.selectOption(selectedOptionText, selectSelector);
        assertThat(CommonSteps.getVariableMap(), hasEntry(selectSelector.toString(), selectedOptionText.toString()));
        System.out.println(CommonSteps.getVariableMap().values());
    }

    // clearValue

    @Test
    public final void clearValueShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("clearValue", BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void clearValueShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Annotation annotation = WebSteps.class.getMethod("clearValue", BddVariable.class).getAnnotation(When.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public final void clearValueShouldClearTheValue() {
        steps.clearValue(inputSelector);
        String actual = steps.findElement(inputSelector).val();
        assertThat(actual, isEmptyString());
    }

    // refresh

    @Test
    public final void refreshShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Annotation annotation = WebSteps.class.getMethod("refresh").getAnnotation(Given.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public final void refreshShouldReloadThePage() {
        steps.setElementValue(value, inputSelector);
        steps.refresh();
        String actualValue = steps.findElement(inputSelector).val();
        assertThat(actualValue, is(not(value.toString())));
    }

    // dragAndDrop
    // TODO Test the actual functionality

    @Test
    public final void dragAndDropShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("dragAndDrop", BddVariable.class, BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void dragAndDropShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("dragAndDrop", BddVariable.class, BddVariable.class);
        Annotation annotation = method.getAnnotation(When.class);
        assertThat(annotation, is(notNullValue()));
    }

    // checkTitle

    @Test
    public final void checkTitleShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("checkTitle", BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void checkTitleShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Annotation annotation = WebSteps.class.getMethod("checkTitle", BddVariable.class).getAnnotation(Then.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test(expected = AssertionError.class)
    public final void checkTitleShouldFailWhenThereIsNoExactMatch() {
        steps.checkTitle(pageTitle);
    }

    @Test
    public final void checkTitleShouldNotFailWhenThereIsExactMatch() {
        steps.checkTitle(indexTitle);
    }

    // checkTitleContains

    @Test
    public final void checkTitleContainsShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("checkTitleContains", BddVariable.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public final void checkTitleContainsShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Method method = WebSteps.class.getMethod("checkTitleContains", BddVariable.class);
        Annotation annotation = method.getAnnotation(Then.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test(expected = AssertionError.class)
    public final void checkTitleContainsShouldFailWhenThereIsNoExactMatch() {
        steps.checkTitleContains(new BddVariable("Page"));
    }

    @Test
    public final void checkTitleContainsShouldNotFailWhenThereIsExactMatch() {
        steps.checkTitleContains(new BddVariable("Index"));
    }

    // afterStoriesWebSteps

    @Test
    public final void afterStoriesWebStepsShouldCloseWebDriver() {
        steps.afterStoriesWebSteps();
        assertThat(steps.getWebDriver(), is(nullValue()));
    }

}
