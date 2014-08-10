package com.technologyconversations.bdd.steps.util;

import com.technologyconversations.bdd.steps.CommonSteps;

public class BddVariable {

    private String variableValue;

    public BddVariable(final String value) {
        this.variableValue = CommonSteps.replaceTextWithVariableValues(value);
    }

    @Override
    public final String toString() {
        return variableValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BddVariable that = (BddVariable) o;
        return variableValue.equals(that.variableValue);
    }

    @Override
    public int hashCode() {
        return variableValue.hashCode();
    }
}
