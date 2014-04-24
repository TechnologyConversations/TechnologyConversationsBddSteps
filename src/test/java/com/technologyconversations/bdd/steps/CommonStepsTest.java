package com.technologyconversations.bdd.steps;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jbehave.core.annotations.Then;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.TreeMap;

public class CommonStepsTest {

    private CommonSteps steps;
    private final String VARIABLE_KEY = "myVariable";
    private final String VARIABLE_VALUE = "my variable value";

    @Before
    public void beforeCommonStepsTest() {
        steps = new CommonSteps();
        CommonSteps.setVariableMap(null);
        CommonSteps.addVariable(VARIABLE_KEY, VARIABLE_VALUE);
    }

    @Test
    public void variableMapShouldHaveSetterAndGetter() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        CommonSteps.setVariableMap(map);
        assertThat(CommonSteps.getVariableMap().size(), is(2));
        assertThat(CommonSteps.getVariableMap(), hasKey("key1"));
    }

    @Test
    public void addVariableShouldAddVariableToTheMap() {
        String key = "myKey";
        String value = "my value";
        CommonSteps.addVariable(key, value);
        assertThat(CommonSteps.getVariable(key), is(equalTo(value)));
    }

    @Test
    public void addVariableShouldReturnNullIfTheKeyDoesNotExist() {
        assertThat(CommonSteps.getVariable("myKey"), is(nullValue()));
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
        CommonSteps.addVariable(key, value);
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
        CommonSteps.addVariable(key, value);
        CommonSteps.addVariable(anotherKey, anotherValue);
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
        CommonSteps.addVariable(key1, value1);
        CommonSteps.addVariable(key11, value11);
        String actual = CommonSteps.replaceTextWithVariableValues(text);
        assertThat(actual, is(equalTo("This is one and this is eleven!")));
    }

    @Test
    public void replaceTextWithVariableValuesShouldReturnNullIfTextIsNull() {
        String actual = CommonSteps.replaceTextWithVariableValues(null);
        assertThat(actual, is(nullValue()));
    }

    // checkVariable

    @Test
    public void checkVariableShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Method method = CommonSteps.class.getMethod("checkVariable", String.class, String.class);
        assertThat(method, is(notNullValue()));
    }

    @Test
    public void checkVariableShouldHaveThenAnnotation() throws NoSuchMethodException {
        Annotation annotation = CommonSteps.class.getMethod("checkVariable", String.class, String.class).getAnnotation(Then.class);
        assertThat(annotation, is(notNullValue()));
    }

    @Test
    public void checkVariableShouldNotFailWhenVariableHasTheSameValueAsSpecified() {
        steps.checkVariable(VARIABLE_KEY, VARIABLE_VALUE);
    }

    @Test(expected = AssertionError.class)
    public void checkVariableShouldFailWhenVariableHasDifferentValueThanSpecified() {
        steps.checkVariable(VARIABLE_KEY, "my different value");
    }

}
