package com.technologyconversations.bdd.steps;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class WebStepsTest {

    private WebSteps steps;

    @Before
    public void before() {
        if (steps == null) {
            steps = new WebSteps();
            steps.setDriver("htmlunit");
        }
        File file = new File("src/test/resources/index.html");
        steps.open("file:///" + file.getAbsolutePath());
    }

    @Test
    public void openShouldRetrieveSpecifiedUrlUsingIdSelector() {
        steps.click("#linkId");
        steps.checkTitle("BDD Steps Test Page");
    }

    @Test
    public void openShouldRetrieveSpecifiedUrlUsingCssSelector() {
        steps.click(".linkClass");
        steps.checkTitle("BDD Steps Test Page");
    }

    @Test
    public void openShouldUseIdAsSelectorIfSelectorStartsWithLetter() {
        steps.click("linkId");
        steps.checkTitle("BDD Steps Test Page");
    }

}
