package com.technologyconversations.bdd.steps;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;

public class CommonStepsTest {

    private CommonSteps steps;

    @Before
    public void beforeCommonStepsTest() {
        steps = new CommonSteps();
        steps.setVariableMap(null);
    }

    @Test
    public void variableMapShouldHaveSetterAndGetter() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        steps.setVariableMap(map);
        assertThat(CommonSteps.getVariableMap().size(), is(2));
        assertThat(CommonSteps.getVariableMap(), hasKey("key1"));
    }

    @Test
    public void addVariableShouldAddVariableToTheMap() {
        String key = "myKey";
        String value = "my value";
        steps.addVariable(key, value);
        assertThat(steps.getVariable(key), is(equalTo(value)));
    }

    @Test
    public void addVariableShouldReturnNullIfTheKeyDoesNotExist() {
        assertThat(steps.getVariable("myKey"), is(nullValue()));
    }

    @Test
    public void replaceTextWithVariableValuesShouldReturnUnchangedTextWhenThereIsNoMatch() {
        String text = "Lorem ipsum dolor sit amet";
        String actual = CommonSteps.replaceTextWithVariableValues(text);
        assertThat(actual, is(equalTo(text)));
    }

    @Test
    public void replaceTextWithVariableValuesShouldReturnReplacedTextWhenThereIsExactMatch() {
        String key = "myKey";
        String value = "my value";
        String text = "@myKey";
        steps.addVariable(key, value);
        String actual = CommonSteps.replaceTextWithVariableValues(text);
        assertThat(actual, is(equalTo(value)));
    }

    @Test
    public void replaceTextWithVariableValuesShouldReturnReplacedTextWhenThereIsPartialMatch() {
        String key = "myKey";
        String anotherKey = "anotherKey";
        String value = "my value";
        String anotherValue = "my another value";
        String text = "This is @myKey and this is @anotherKey!";
        steps.addVariable(key, value);
        steps.addVariable(anotherKey, anotherValue);
        String actual = CommonSteps.replaceTextWithVariableValues(text);
        assertThat(actual, is(equalTo("This is my value and this is my another value!")));
    }

    @Test
    public void replaceTextWithVariableValuesShouldReturnReplacedTextRespectingNumbers() {
        String key1 = "myKey1";
        String key11 = "myKey11";
        String value1 = "one";
        String value11 = "eleven";
        String text = "This is @myKey1 and this is @myKey11!";
        steps.addVariable(key1, value1);
        steps.addVariable(key11, value11);
        String actual = CommonSteps.replaceTextWithVariableValues(text);
        assertThat(actual, is(equalTo("This is one and this is eleven!")));
    }

    @Test
    public void replaceTextWithVariableValuesShouldReturnNullIfTextIsNull() {
        String actual = CommonSteps.replaceTextWithVariableValues(null);
        assertThat(actual, is(nullValue()));
    }


}
