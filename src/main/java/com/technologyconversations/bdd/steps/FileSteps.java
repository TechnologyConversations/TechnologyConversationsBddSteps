package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddParam;
import com.technologyconversations.bdd.steps.util.BddVariable;
import edu.umd.cs.findbugs.annotations.*;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.*;
import org.jbehave.core.annotations.When;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileSteps {

    protected static final long DEFAULT_TIMEOUT = 4000;
    protected static final long MILLISECONDS_IN_SECOND = 1000;
    private static final int SLEEP_BETWEEN_VALIDATIONS = 500;

    public final Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    // Given

    private long timeout = DEFAULT_TIMEOUT;
    @BddParam(value = "timeout", description = "Sets timeout used when operating with elements."
            + " Default value is 4 seconds.")
    @Given("File timeout is $seconds seconds")
    public final void setTimeoutSeconds(final BddVariable seconds) {
        try {
            timeout = Integer.parseInt(seconds.toString()) * MILLISECONDS_IN_SECOND;
        } catch (NumberFormatException e) {
            getLogger().info("Could not parse " + seconds + " as integer");
        }
    }
    protected final void setTimeout(final long value) {
        this.timeout = value;
    }
    protected final long getTimeout() {
        return timeout;
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    @Given("File $path exists")
    public final void createFile(final BddVariable path) throws IOException {
        File file = new File(path.toString());
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    @Given("Directory $path exists")
    public final void createDirectory(final BddVariable path) {
        File file = new File(path.toString());
        file.mkdirs();
        assertThat(file.exists(), is(true));
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    @Given("File $path does NOT exist")
    public final void deleteFile(final BddVariable path) {
        new File(path.toString()).delete();
    }

    @Given("Directory $path does NOT exist")
    public final void deleteDirectory(final BddVariable path) throws IOException {
        FileUtils.deleteDirectory(new File(path.toString()));
    }

    // When

    @When("File is copied from $from to $to")
    public final void copyFile(final BddVariable from, final BddVariable to) throws IOException {
        File toFile = new File(to.toString());
        if (!toFile.exists()) {
            FileUtils.copyFile(new File(from.toString()), toFile);
        }
    }

    // Then

    @Then("File $path exists")
    @Alias("Directory $path exists")
    public final void fileExists(final BddVariable path) throws InterruptedException {
        validateFile(path.toString(), true);
    }

    @Then("File $path does NOT exist")
    @Alias("Directory $path does NOT exist")
    public final void fileDoesNotExist(final BddVariable path) throws InterruptedException {
        validateFile(path.toString(), false);
    }

    private void validateFile(final String path, final boolean shouldExist) throws InterruptedException {
        File file = new File(path);
        Date date = new Date();
        boolean valid = (file.exists() == shouldExist);
        while (!valid && (new Date().getTime() - date.getTime()) < this.getTimeout()) {
            if (file.exists() == shouldExist) {
                valid = true;
                break;
            }
            Thread.sleep(SLEEP_BETWEEN_VALIDATIONS);
        }
        assertThat("File " + path + " exists should be " + shouldExist + ".", valid, is(true));
    }

    // General

    @AsParameterConverter
    public final BddVariable createBddVariable(final String value) {
        return new BddVariable(value);
    }

}
