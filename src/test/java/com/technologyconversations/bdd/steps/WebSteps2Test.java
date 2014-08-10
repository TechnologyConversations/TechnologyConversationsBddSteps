package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddParam;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WebSteps2Test {

    private static WebSteps steps;
    private static final String HOME_PAGE = "http://technologyconversations.com";
    private BddVariable browserVariable = new BddVariable("phantomjs");

    @BeforeClass
    public static void beforeWebStepsTest() {
        steps = new WebSteps();
    }

    @AfterClass
    public static void afterWebStepsTestClass() {
        steps.afterStoriesWebSteps();
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
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.android())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserChrome() {
        steps.getParams().put("browser", "chrome");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.chrome())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserFirefox() {
        steps.getParams().put("browser", "firefox");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.firefox())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserHtmlUnit() {
        steps.getParams().put("browser", "htmlunit");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.htmlUnit())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserIe() {
        steps.getParams().put("browser", "ie");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.internetExplorer())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserIPad() {
        steps.getParams().put("browser", "ipad");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.ipad())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserIPhone() {
        steps.getParams().put("browser", "iphone");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.iphone())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserOpera() {
        steps.getParams().put("browser", "opera");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.opera())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserPhantomJS() {
        steps.getParams().put("browser", "phantomjs");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.phantomjs())));
    }

    @Test
    public void getDesiredCapabilitiesShouldSetBrowserSafari() {
        steps.getParams().put("browser", "safari");
        DesiredCapabilities capability = (DesiredCapabilities) steps
                .getDesiredCapabilities().getCapability(CapabilityType.BROWSER_NAME);
        assertThat(capability, is(equalTo(DesiredCapabilities.safari())));
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
        Object actual = steps.getDesiredCapabilities().getCapability(CapabilityType.PLATFORM);
        assertThat(actual, is(nullValue()));
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
        Object actual = steps.getDesiredCapabilities().getCapability(CapabilityType.VERSION);
        assertThat(actual, is(nullValue()));
    }

    // beforeScenarioWebSteps

    @Test
    public void beforeScenarioWebStepsShouldCallSetConfigTimeoutWhenParameterExists() {
        String timeout = "123";
        BddVariable variable = new BddVariable(timeout);
        WebSteps mockedSteps = Mockito.spy(new WebSteps());
        Mockito.doNothing().when(mockedSteps).setConfigTimeout(Mockito.any(BddVariable.class));
        mockedSteps.getParams().put("timeout", timeout);
        mockedSteps.beforeScenarioWebSteps();
        Mockito.verify(mockedSteps).setConfigTimeout(variable);
    }

    @Test
    public void beforeScenarioWebStepsShouldNotCallSetConfigTimeoutWhenParameterDoesNotExist() {
        String timeout = "123";
        BddVariable variable = new BddVariable(timeout);
        WebSteps mockedSteps = Mockito.spy(new WebSteps());
        Mockito.doNothing().when(mockedSteps).setConfigTimeout(Mockito.any(BddVariable.class));
        mockedSteps.getParams().clear();
        mockedSteps.beforeScenarioWebSteps();
        Mockito.verify(mockedSteps, Mockito.never()).setConfigTimeout(variable);
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

    // openIfNotAlreadyOpened

    @Test
    public void openIfNotAlreadyOpenedShouldCallOpen() {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).openIfNotAlreadyOpened();
        mockedSteps.openIfNotAlreadyOpened();
        Mockito.verify(mockedSteps).open();
    }

    @Test
    public void openIfNotAlreadyOpenedShouldNotCallOpen() {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
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

    // setWebDriver

    @Test
    public final void setWebDriverShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        BddParam bddParam = WebSteps.class.getMethod("setWebDriver").getAnnotation(BddParam.class);
        assertThat(bddParam.value(), is("browser"));
    }

    @Test
    public final void setWebDriverShouldSetWebDriver() {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setWebDriverRunner();
    }

    @Test
    public final void setWebDriverShouldInvokeGetRemoteDriverWhenRemoteDriverUrlIsSet() throws Exception {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("URL").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).getRemoteDriver();
    }

    @Test
    public final void setWebDriverShouldInvokeSetWebDriverRunnerWhenRemoteDriverUrlIsSet() throws Exception {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("URL").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setWebDriverRunner();
    }

    @Test
    public final void setWebDriverShouldInvokeSetSizeWhenRemoteDriverUrlIsSet() throws Exception {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("URL").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps).setSize();
    }

    @Test
    public final void setWebDriverShouldNotInvokeGetRemoteDriverWhenRemoteDriverUrlIsNull() throws Exception {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn(null).when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps, Mockito.never()).getRemoteDriver();
    }

    @Test
    public final void setWebDriverShouldNotInvokeGetRemoteDriverWhenRemoteDriverUrlIsEmpty() throws Exception {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
        Mockito.doCallRealMethod().when(mockedSteps).setWebDriver(Mockito.any(BddVariable.class));
        Mockito.doReturn("").when(mockedSteps).getRemoteDriverUrl();
        mockedSteps.setWebDriver(browserVariable);
        Mockito.verify(mockedSteps, Mockito.never()).getRemoteDriver();
    }

    @Test
    public final void setWebDriverShouldInvokeSetSize() {
        WebSteps mockedSteps = Mockito.mock(WebSteps.class);
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

}
