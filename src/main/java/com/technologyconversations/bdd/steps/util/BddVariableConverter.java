package com.technologyconversations.bdd.steps.util;

import org.jbehave.core.steps.ParameterConverters.ParameterConverter;
import java.lang.reflect.Type;


public class BddVariableConverter implements ParameterConverter {

    public final boolean accept(final Type type) {
        return BddVariable.class.isAssignableFrom((Class<?>) type);
    }

    public final Object convertValue(final String value, final Type type) {
        return new BddVariable(value);
    }

}
