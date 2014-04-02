package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddVariable;
import org.jbehave.core.annotations.AsParameterConverter;

import java.util.TreeMap;
import java.util.logging.Logger;

public class CommonSteps {

    public Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    private static TreeMap<String, String> variableMap;
    protected static TreeMap<String, String> getVariableMap() {
        if (variableMap == null) {
            variableMap = new TreeMap<>();
        }
        return variableMap;
    }
    protected void setVariableMap(final TreeMap<String, String> value) {
        variableMap = value;
    }
    public void addVariable(String key, String value) {
        getVariableMap().put(key, value);
    }
    public String getVariable(String key) {
        return getVariableMap().get(key);
    }

    public static String replaceTextWithVariableValues(final String text) {
        if (text != null) {
            String output = text;
            if (text.contains("@")) {
                for (String key : getVariableMap().descendingKeySet()) {
                    output = output.replace("@" + key, getVariableMap().get(key));
                }
            }
            return output;
        } else {
            return null;
        }
    }

    @AsParameterConverter
    public BddVariable createBddVariable(String value){
        return new BddVariable(value);
    }

}
