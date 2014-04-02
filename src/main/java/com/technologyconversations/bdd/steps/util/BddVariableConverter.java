package com.technologyconversations.bdd.steps.util;

import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import java.lang.reflect.Type;

public class BddVariableConverter implements ParameterConverter {

    public boolean accept(Type type) {
        return (type instanceof Class<?> &&
                BddVariable.class.isAssignableFrom((Class<?>) type));
    }

    public Object convertValue(String value, Type type) {
        return new BddVariable(value);
    }

}
