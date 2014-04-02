package com.technologyconversations.bdd.steps.util;

import com.technologyconversations.bdd.steps.CommonSteps;

public class BddVariable {

    private String value;

    public BddVariable(String value) {
        this.value = CommonSteps.replaceTextWithVariableValues(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
