package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.SelenideElement;
import com.technologyconversations.bdd.steps.util.BddParam;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WebSteps2Test {

    private static WebSteps steps;
    private static final String HOME_PAGE = "http://technologyconversations.com";
    private BddVariable browserVariable = new BddVariable("phantomjs");
    private WebSteps mockedSteps;
    private WebDriver mockedWebDriver;

    @BeforeClass
    public static void beforeWebStepsTestClass() {
        steps = new WebSteps();
    }

    @AfterClass
    public static void afterWebStepsTestClass() {
        steps.afterStoriesWebSteps();
    }

    @Before
    public void beforeWebStepsTest() {
        mockedWebDriver = Mockito.mock(WebDriver.class);
        mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doReturn(mockedWebDriver).when(mockedSteps).getWebDriver();
    }

    // getRemoteDriverUrl

    @Test
    public void getRemoteDriverUrlShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("getRemoteDriverUrl").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("remoteDriverUrl"));
    }

    @Test
    public void getRemoteDriverUrlShouldReturnNullWhenParamDoesNotExist() {
        steps.getParams().clear();
        assertThat(steps.getRemoteDriverUrl(), is(nullValue()));
    }

    @Test
    public void getRemoteDriverUrlShouldReturnParameterValue() {
        String url = "http://www.technologyconversations.com";
        steps.getParams().put("remoteDriverUrl", url);
        assertThat(steps.getRemoteDriverUrl(), is(equalTo(url)));
    }

    // getRemoteDriverVersion

    @Test
    public void getRemoteDriverVersionShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("getRemoteDriverVersion").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("remoteDriverVersion"));
    }

    @Test
    public void getRemoteDriverVersionShouldReturnNullWhenParamDoesNotExist() {
        steps.getParams().clear();
        assertThat(steps.getRemoteDriverVersion(), is(nullValue()));
    }

    @Test
    public void getRemoteDriverVersionShouldReturnParameterValue() {
        String version = "1.2.3";
        steps.getParams().put("remoteDriverVersion", version);
        assertThat(steps.getRemoteDriverVersion(), is(equalTo(version)));
    }

    // getRemoteDriverPlatform

    @Test
    public void getRemoteDriverPlatformShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("getRemoteDriverPlatform").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("remoteDriverPlatform"));
    }

    @Test
    public void getRemoteDriverPlatformShouldReturnNullWhenParamDoesNotExist() {
        steps.getParams().clear();
        assertThat(steps.getRemoteDriverPlatform(), is(nullValue()));
    }

    @Test
    public void getRemoteDriverPlatformShouldReturnParameterValue() {
        String platform = "Linux";
        steps.getParams().put("remoteDriverPlatform", platform);
        assertThat(steps.getRemoteDriverPlatform(), is(equalTo(platform)));
    }

    // getDesiredCapabilities

    @Test
    public void getDesiredCapabilitiesShouldReturnDesiredCapabilities() {
        assertThat(steps.getDesiredCapabilities(), is(instanceOf(DesiredCapabilities.class)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserAndroid() {
        steps.getParams().put("browser", "android");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.ANDROID)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserChrome() {
        steps.getParams().put("browser", "chrome");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.CHROME)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserFirefox() {
        steps.getParams().put("browser", "firefox");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.FIREFOX)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserHtmlUnit() {
        steps.getParams().put("browser", "htmlunit");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.HTMLUNIT)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserIe() {
        steps.getParams().put("browser", "ie");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.IE)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserIPad() {
        steps.getParams().put("browser", "ipad");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.IPAD)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserIPhone() {
        steps.getParams().put("browser", "iphone");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.IPHONE)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserOpera() {
        steps.getParams().put("browser", "opera");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.OPERA)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserPhantomJS() {
        steps.getParams().put("browser", "phantomjs");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.PHANTOMJS)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserSafari() {
        steps.getParams().put("browser", "safari");
        DesiredCapabilities capability = steps.getDesiredCapabilities();
        assertThat(capability.getBrowserName(), is(equalTo(BrowserType.SAFARI)));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetPlatform() {
        String expected = "linux";
        steps.getParams().put("remoteDriverPlatform", expected);
        String actual = steps.getDesiredCapabilities().getCapability(CapabilityType.PLATFORM).toString();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getDesiredCapabilitiesShouldNotSetPlatformWhenParameterDoesNotExist() {
        steps.getParams().clear();
        Platform actual = (Platform) steps.getDesiredCapabilities().getCapability(CapabilityType.PLATFORM);
        assertThat(actual, is(Platform.ANY));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetVersion() {
        String expected = "linux";
        steps.getParams().put("remoteDriverVersion", expected);
        String actual = steps.getDesiredCapabilities().getCapability(CapabilityType.VERSION).toString();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getDesiredCapabilitiesShouldNotSetVersionWhenParameterDoesNotExist() {
        steps.getParams().clear();
        String actual = (String) steps.getDesiredCapabilities().getCapability(CapabilityType.VERSION);
        assertThat(actual, isEmptyString());
    }

    // beforeScenarioWebSteps

    @Test
    public void beforeScenarioWebStepsShouldCallSetConfigTimeoutWhenParameterExists() {
        String timeout = "123";
        BddVariable variable = new BddVariable(timeout);
        WebSteps spiedSteps = Mockito.spy(new WebSteps());
        Mockito.doNothing().when(spiedSteps).setConfigTimeout(Mockito.any(BddVariable.class));
        spiedSteps.getParams().put("timeout", timeout);
        spiedSteps.beforeScenarioWebSteps();
        Mockito.verify(spiedSteps).setConfigTimeout(variable);
    }

    @Test
    public void beforeScenarioWebStepsShouldNotCallSetConfigTimeoutWhenParameterDoesNotExist() {
        String timeout = "123";
        BddVariable variable = new BddVariable(timeout);
        WebSteps spiedSteps = Mockito.spy(new WebSteps());
        Mockito.doNothing().when(spiedSteps).setConfigTimeout(Mockito.any(BddVariable.class));
        spiedSteps.getParams().clear();
        spiedSteps.beforeScenarioWebSteps();
        Mockito.verify(spiedSteps, Mockito.never()).setConfigTimeout(variable);
    }

    // getUrl

    @Test
    public void getUrlShouldReturnUrlWhenItStartsWithHttp() {
        assertThat(steps.getUrl(HOME_PAGE), is(HOME_PAGE));
    }

    @Test
    public void getUrlShouldBePrefixedWithBaseUrlWhenItDoesNotStartWithHttp() {
        String url = "/some/page";
        steps.getParams().put("url", HOME_PAGE);
        assertThat(steps.getUrl(url), is(HOME_PAGE + url));
    }

    @Test
    public void getUrlShouldRemoveDuplicatedSlashFromBaseUrl() {
        String url = "/some/page";
        steps.getParams().put("url", HOME_PAGE + "/");
        assertThat(steps.getUrl(url), is(HOME_PAGE + url));
    }

    @Test
    public void getUrlShouldBePrefixedWithBaseUrlWhenItDoesNotStartWithHttpAndItDoesNotStartWithSlash() {
        String url = "some/page";
        steps.getParams().put("url", HOME_PAGE + "/");
        assertThat(steps.getUrl(url), is(HOME_PAGE + "/" + url));
    }

    // switchToFrame

    @Test
    public void switchToFrameShouldSwitchToNewFrame() {
        BddVariable id = new BddVariable("myId");
        WebDriver.TargetLocator locator = Mockito.mock(WebDriver.TargetLocator.class);
        Mockito.doCallRealMethod().when(mockedSteps).switchToFrame(id);
        Mockito.doReturn(locator).when(mockedWebDriver).switchTo();
        mockedSteps.switchToFrame(id);
        Mockito.verify(locator).frame(id.toString());
    }

    @Test
    public void switchToFrameShouldCallSwitchToDefaultContent() {
        BddVariable id = new BddVariable("myId");
        WebDriver.TargetLocator locator = Mockito.mock(WebDriver.TargetLocator.class);
        Mockito.doCallRealMethod().when(mockedSteps).switchToFrame(id);
        Mockito.doReturn(locator).when(mockedWebDriver).switchTo();
        mockedSteps.switchToFrame(id);
        Mockito.verify(mockedSteps).switchToDefaultContent();
    }

    // switchToDefaultContent

    @Test
    public void switchToDefaultContentShouldSwitchToDefaultContent() {
        WebDriver.TargetLocator locator = Mockito.mock(WebDriver.TargetLocator.class);
        Mockito.doCallRealMethod().when(mockedSteps).switchToDefaultContent();
        Mockito.doReturn(locator).when(mockedWebDriver).switchTo();
        mockedSteps.switchToDefaultContent();
        Mockito.verify(locator).defaultContent();
    }

    // openIfNotAlreadyOpened

    @Test
    public void openIfNotAlreadyOpenedShouldCallOpen() {
        Mockito.doCallRealMethod().when(mockedSteps).openIfNotAlreadyOpened();
        mockedSteps.openIfNotAlreadyOpened();
        Mockito.verify(mockedSteps).open();
    }

    @Test
    public void openIfNotAlreadyOpenedShouldNotCallOpen() {
        Mockito.doCallRealMethod().when(mockedSteps).openIfNotAlreadyOpened();
        Mockito.doCallRealMethod().when(mockedSteps).setUrlHasBeenOpened(Mockito.anyBoolean());
        mockedSteps.setUrlHasBeenOpened(true);
        mockedSteps.openIfNotAlreadyOpened();
        Mockito.verify(mockedSteps, Mockito.never()).open();
    }

    // createBddVariable

    @Test
    public void createBddVariableShouldReturnBddVariable() {
        String value = "myValue";
        BddVariable expected = new BddVariable(value);
        BddVariable actual = steps.createBddVariable(value);
        assertThat(actual, is(equalTo(expected)));
    }

    // setWebDriver(BddVariable)

    @Test
    public final void setWebDriverShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setWebDriver").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("browser"));
    }

    @Test
    public final void setWebDriverShouldSetWebDriver() {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setWebDriverRunner();
    }

    @Test
    public final void setWebDriverShouldInvokeGetRemoteDriverWhenRemoteDriverUrlIsSet() throws Exception {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("URL").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).getRemoteDriver();
    }

    @Test
    public final void setWebDriverShouldInvokeSetWebDriverRunnerWhenRemoteDriverUrlIsSet() throws Exception {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("URL").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setWebDriverRunner();
    }

    @Test
    public final void setWebDriverShouldInvokeSetSizeWhenRemoteDriverUrlIsSet() throws Exception {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("URL").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setSize();
    }

    @Test
    public final void setWebDriverShouldNotInvokeGetRemoteDriverWhenRemoteDriverUrlIsNull() throws Exception {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn(null).when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps, Mockito.never()).getRemoteDriver();
    }

    @Test
    public final void setWebDriverShouldNotInvokeGetRemoteDriverWhenRemoteDriverUrlIsEmpty() throws Exception {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps, Mockito.never()).getRemoteDriver();
    }

    @Test
    public final void setWebDriverShouldInvokeSetSize() {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setSize();
    }

    @Test
    public final void setWebDriverShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        assertThat(WebSteps.class.getMethod("setWebDriver", BddVariable.class), is(notNullValue()));
    }

    @Test
    public final void setWebDriverShouldAcceptNullAsDriver() {
        steps.setWebDriver(null);
    }

    // setWebDriver()

    @Test
    public final void setWebDriverShouldCallSetWebDriver() {
        String driverString = "MY_DRIVER";
        BddVariable driver = new BddVariable(driverString);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver();
        Mockito.doReturn(null).when(mockedSteps).getWebDriver();
        Mockito.doReturn(driverString).when(mockedSteps).getBrowserParam();
        mockedSteps.setWebDriver();
        Mockito.verify(mockedSteps).setWebDriver(driver);
    }

    @Test
    public final void setWebDriverShouldNotCallSetWebDriverIfAlreadySet() {
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver();
        mockedSteps.setWebDriver();
        Mockito.verify(mockedSteps, Mockito.never()).setWebDriver(Mockito.any(BddVariable.class));
    }

    // clickElement

    @Test
    public final void clickElementShouldCallScrollTo() {
        BddVariable selector = new BddVariable("ID");
        SelenideElement element = Mockito.mock(SelenideElement.class);
        Mockito.doCallRealMethod().when(mockedSteps).clickElement(selector);
        Mockito.doReturn(element).when(mockedSteps).findElement(selector);
        mockedSteps.clickElement(selector);
        Mockito.verify(element).scrollTo();
    }

    @Test
    public final void clickElementShouldCallClick() {
        BddVariable selector = new BddVariable("ID");
        SelenideElement element = Mockito.mock(SelenideElement.class);
        Mockito.doCallRealMethod().when(mockedSteps).clickElement(selector);
        Mockito.doReturn(element).when(mockedSteps).findElement(selector);
        mockedSteps.clickElement(selector);
        Mockito.verify(element).click();
    }

    @Test
    public final void clickElementShouldCallSleep() {
        BddVariable selector = new BddVariable("ID");
        SelenideElement element = Mockito.mock(SelenideElement.class);
        Mockito.doCallRealMethod().when(mockedSteps).clickElement(selector);
        Mockito.doReturn(element).when(mockedSteps).findElement(selector);
        mockedSteps.clickElement(selector);
        Mockito.verify(mockedSteps).sleep(WebSteps.SLEEP_AFTER_CLICK);
    }

    // setElementTextToVariable

    @Test
    public final void setElementTextToVariableShouldSetElementTextToVariable() {
        BddVariable selector = new BddVariable("ID");
        BddVariable variableName = new BddVariable("myVariable");
        String expectedText = "THIS IS SOME TEXT";
        SelenideElement element = Mockito.mock(SelenideElement.class);
        Mockito.doReturn(expectedText).when(element).getText();
        Mockito.doReturn(element).when(mockedSteps).findElement(selector);
        Mockito.doCallRealMethod().when(mockedSteps).setElementTextToVariable(selector, variableName);
        mockedSteps.setElementTextToVariable(selector, variableName);
        assertThat(CommonSteps.getVariable(variableName.toString()), is(equalTo(expectedText)));
    }

}
