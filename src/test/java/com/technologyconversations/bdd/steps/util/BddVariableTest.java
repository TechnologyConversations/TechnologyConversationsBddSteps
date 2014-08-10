package com.technologyconversations.bdd.steps.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BddVariableTest {

    private BddVariable bddVariable;
    private String value = "myValue";

    @Before
    public void beforeBddVariableTest() {
        bddVariable = new BddVariable(value);
    }

    // constructor

    @Test
    public void constructorShouldAssignVariableValue() {
        assertThat(bddVariable.toString(), is(equalTo(value)));
    }

    // equals

    @Test
    public void equalsReturnTrueWhenParameterIsTheSameObject() {
        boolean actual = bddVariable.equals(bddVariable);
        assertThat(actual, is(true));
    }

    @Test
    public void equalsReturnFalseWhenParameterIsNull() {
        boolean actual = bddVariable.equals(null);
        assertThat(actual, is(false));
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "EC_UNRELATED_TYPES")
    @Test
    public void equalsReturnFalseWhenParameterIsDifferentClass() {
        boolean actual = bddVariable.equals("different class");
        assertThat(actual, is(false));
    }

    @Test
    public void equalsReturnTrueWhenVariableValueIsTheSame() {
        boolean actual = bddVariable.equals(new BddVariable(value));
        assertThat(actual, is(true));
    }

    @Test
    public void equalsReturnFalseWhenVariableValueIsNotTheSame() {
        boolean actual = bddVariable.equals(new BddVariable("some other value"));
        assertThat(actual, is(false));
    }

    // hashCode

    @Test
    public void hashCodeShouldReturnVariableValueHashCode() {
        assertThat(bddVariable.hashCode(), is(equalTo(value.hashCode())));
    }

}
