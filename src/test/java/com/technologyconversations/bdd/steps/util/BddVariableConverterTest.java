package com.technologyconversations.bdd.steps.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.lang.reflect.Type;

public class BddVariableConverterTest {

    private BddVariableConverter converter;

    @Before
    public void beforeBddVariableConverterTest() {
        converter = new BddVariableConverter();
    }

    // convertValue

    @Test
    public void convertValueShouldReturnBddVariable() {
        Type type = Mockito.mock(Type.class);
        assertThat(converter.convertValue("value", type), is(instanceOf(BddVariable.class)));
    }

    @Test
    public void convertValueShouldSetValueToBddVariable() {
        String value = "value";
        Type type = Mockito.mock(Type.class);
        BddVariable bddVariable = (BddVariable) converter.convertValue(value, type);
        assertThat(bddVariable.toString(), is(equalTo(value)));
    }

    // accept

    @Test
    public void acceptShouldReturnTrueWhenTypeIsBddVariable() {
        boolean actual = converter.accept(BddVariable.class);
        assertThat(actual, is(true));
    }

    @Test
    public void acceptShouldReturnFalseWhenTypeIsNotBddVariable() {
        boolean actual = converter.accept(String.class);
        assertThat(actual, is(false));
    }

}
