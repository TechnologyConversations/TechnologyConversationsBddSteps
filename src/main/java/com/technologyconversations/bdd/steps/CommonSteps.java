package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddDescription;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.Given;

import java.util.TreeMap;

public class CommonSteps {

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
    @BddDescription("Adds variable with the specified key. " +
            "Variables can be referenced using @KEY format. " +
            "For example, if variable username has value my_user, " +
            "text 'Then Web element username should have text @username' would be transformed to: " +
            "'Then Web element username should have text my_user'.")
    @Given("variable $key has value $value")
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
