package com.technologyconversations.bdd.steps;

import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

public class WebStepsTest {

    // TODO Test with all browsers

    private WebSteps steps;
    private final String linkId = "#linkId";
    private final String inputId = "#inputId";
    private final String selectId = "#selectId";
    private final String invisibleId = "#invisibleId";
    private final String indexTitle = "BDD Steps Test Index";
    private final String pageTitle = "BDD Steps Test Page";
    private final String notSelectedOptionText = "Option 1 Test";
    private final String selectedOptionText = "Option 2 Test";

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

    // open

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

    // clickElement

    @Test
    public void clickElementShouldUseIdAsSelectorIfSelectorStartsWithLetter() {
        steps.checkTitle(indexTitle);
        steps.clickElement(linkId);
        steps.checkTitle(pageTitle);
    }

    // shouldHaveTest

    @Test
    public void shouldHaveTextShouldPassIfElementTextIsTheSame() {
        steps.shouldHaveText(linkId, "This is link");
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveTextShouldFailIfElementTextIsNotTheSame() {
        steps.shouldHaveText(linkId, "This is non-existent text");
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

    // shouldNotHaveText

    @Test
    public void shouldNotHaveTextShouldPassIfElementTextIsNotTheSame() {
        steps.shouldNotHaveText(linkId, "This is non-existent text");
    }

    @Test(expected = AssertionError.class)
    public void shouldNotHaveTextShouldFailIfElementTextIsTheSame() {
        steps.shouldNotHaveText(linkId, "This is link");
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

    // shouldHaveVisible

    @Test
    public void shouldHaveVisibleShouldNotFailIfElementIsVisible() {
        steps.shouldHaveVisible(linkId);
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveVisibleShouldFailIfElementIsHidden() {
        steps.shouldHaveVisible(invisibleId);
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveVisibleShouldFailIfElementIsNotPresent() {
        steps.shouldHaveVisible("#nonExistentId");
    }

    // shouldHaveHidden

    @Test(expected = AssertionError.class)
    public void shouldHaveHiddenShouldFailIfElementIsVisible() {
        steps.shouldHaveHidden(linkId);
    }

    @Test
    public void shouldHaveHiddenShouldNotFailIfElementIsHidden() {
        steps.shouldHaveHidden(invisibleId);
    }

    @Test
    public void shouldHaveHiddenShouldNotFailIfElementIsNotPresent() {
        steps.shouldHaveHidden("#nonExistentId");
    }

    // shouldHavePresent

    @Test
    public void shouldHavePresentShouldNotFailIfElementIsPresent() {
        steps.shouldHavePresent(linkId);
    }

    @Test(expected = AssertionError.class)
    public void shouldHavePresentShouldFailIfElementIsNotPresent() {
        steps.shouldHavePresent("#nonExistentId");
    }

    @Test
    public void shouldHavePresentShouldNotFailIfElementIsNotVisible() {
        steps.shouldHavePresent(invisibleId);
    }

    // selectOption

    @Test
    public void selectOptionShouldSelectDropDownListItem() {
        steps.shouldHaveSelectedOption(selectId, selectedOptionText);
        steps.selectOption(notSelectedOptionText, selectId);
        steps.shouldHaveSelectedOption(selectId, notSelectedOptionText);
    }

}
