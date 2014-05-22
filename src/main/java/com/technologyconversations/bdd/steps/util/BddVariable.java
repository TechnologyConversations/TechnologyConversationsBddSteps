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

}
