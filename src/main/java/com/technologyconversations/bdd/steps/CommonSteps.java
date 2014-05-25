package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddDescription;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.jbehave.core.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.TreeMap;

public class CommonSteps {

    public static final ThreadLocal<TreeMap<String, String>> VARIABLE_MAP = new ThreadLocal<>();

    protected static TreeMap<String, String> getVariableMap() {
        if (VARIABLE_MAP.get() == null) {
            VARIABLE_MAP.set(new TreeMap<String, String>());
        }
        return VARIABLE_MAP.get();
    }
    protected static void setVariableMap(final TreeMap<String, String> value) {
        VARIABLE_MAP.set(value);
    }
    @BddDescription("Adds variable with the specified key. "
            + "Variables can be referenced using @KEY format. "
            + "For example, if variable username has value my_user, "
            + "text 'Then Web element username should have text @username' would be transformed to: "
            + "'Then Web element username should have text my_user'.")
    @Given("variable $key has value $value")
    public static void addVariable(final String key, final String value) {
        getVariableMap().put(key, value);
    }
    public static String getVariable(final String key) {
        return getVariableMap().get(key);
    }

    @Then("variable $key has value $value")
    public final void checkVariable(final String key, final String value) {
        assertThat(getVariable(key), is(equalTo(value)));
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
    public final BddVariable createBddVariable(final String value) {
        return new BddVariable(value);
    }

    @AfterStories
    public final void afterStoriesCommonSteps() {
        VARIABLE_MAP.remove();
    }

}
