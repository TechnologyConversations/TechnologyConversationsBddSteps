package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

public class WebStepsTest {

    private WebSteps steps;
    private final String linkId = "#linkId";
    private final String inputId = "#inputId";

    @Before
    public void before() {
        if (steps == null) {
            steps = new WebSteps();
            steps.setDriver("htmlunit");
        }
        File file = new File("src/test/resources/index.html");
        steps.configTimeout(0);
        steps.open("file:///" + file.getAbsolutePath());
    }

    @Test
    public void configTimeoutShouldSetTimeout() {
        Date start;
        steps.configTimeout(0);
        start = new Date();
        try {
            steps.clickElement("#nonExistingElement");
        } catch (ElementNotFound e) { }
        Assert.assertTrue((new Date().getTime() - start.getTime()) < 1000);
        steps.configTimeout(1);
        start = new Date();
        try {
            steps.clickElement("#nonExistingElement");
        } catch (ElementNotFound e) { }
        Assert.assertTrue((new Date().getTime() - start.getTime()) >= 1000);
    }

    @Test
    public void openShouldRetrieveSpecifiedUrlUsingIdSelector() {
        steps.clickElement(linkId);
        steps.checkTitle("BDD Steps Test Page");
    }

    @Test
    public void openShouldRetrieveSpecifiedUrlUsingCssSelector() {
        steps.clickElement(".linkClass");
        steps.checkTitle("BDD Steps Test Page");
    }

    @Test
    public void openShouldUseIdAsSelectorIfSelectorStartsWithLetter() {
        steps.clickElement("linkId");
        steps.checkTitle("BDD Steps Test Page");
    }

    @Test
    public void shouldHaveTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveText(linkId, "This is link");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveText(linkId, "This is non-existent text");
    }

    @Test
    public void shouldHaveValueShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveValue(inputId, "This is input");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveValueShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveValue(inputId, "This is non-existent value");
    }

    @Test
    public void shouldNotHaveTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveText(linkId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveText(linkId, "This is link");
    }

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

}
