package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddDescription;
import com.technologyconversations.bdd.steps.util.BddParam;
import com.technologyconversations.bdd.steps.util.BddVariable;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileSteps {

    public Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    // Given

    private long timeout = 4000;
    @BddParam(value = "timeout", description = "Sets timeout used when operating with elements. Default value is 4 seconds.")
    @Given("Web timeout is $seconds seconds")
    public void setTimeoutSeconds(BddVariable seconds) {
        try {
            timeout = Integer.parseInt(seconds.toString()) * 1000;
        } catch (NumberFormatException e) {
            getLogger().info("Could not parse " + seconds + " as integer");
        }
    }
    protected void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    protected long getTimeout() {
        return timeout;
    }

    @Given("File $path exists")
    public void createFile(BddVariable path) throws IOException {
        File file = new File(path.toString());
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    @Given("Directory $path exists")
    public void createDirectory(BddVariable path) {
        boolean actual = new File(path.toString()).mkdirs();
        assertThat(actual, is(true));
    }

    @Given("File $path does NOT exist")
    public void deleteFile(BddVariable path) {
        new File(path.toString()).delete();
    }

    @Given("Directory $path does NOT exist")
    public void deleteDirectory(BddVariable path) throws IOException {
        FileUtils.deleteDirectory(new File(path.toString()));
    }

    // When

    @When("File is copied from $from to $to")
    public void copyFile(BddVariable from, BddVariable to) throws IOException {
        File toFile = new File(to.toString());
        if (!toFile.exists()) {
            FileUtils.copyFile(new File(from.toString()), toFile);
        }
    }

    // Then

    @Then("File $path exists")
    @Alias("Directory $path exists")
    public void fileExists(BddVariable path) throws InterruptedException {
        validateFile(path.toString(), true);
    }

    @Then("File $path does NOT exist")
    @Alias("Directory $path does NOT exist")
    public void fileDoesNotExist(BddVariable path) throws InterruptedException {
        validateFile(path.toString(), false);
    }

    private void validateFile(String path, boolean shouldExist) throws InterruptedException {
        File file = new File(path);
        Date date = new Date();
        boolean valid = (file.exists() == shouldExist);
        while(!valid && (new Date().getTime() - date.getTime()) < this.getTimeout()) {
            if (file.exists() == shouldExist) {
                valid = true;
                break;
            }
            Thread.sleep(500);
        }
        assertThat("File " + path + " exists should be " + shouldExist + ".", valid, is(true));
    }

    // General

    @AsParameterConverter
    public BddVariable createBddVariable(String value){
        return new BddVariable(value);
    }

}
