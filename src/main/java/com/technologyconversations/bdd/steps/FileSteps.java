package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddVariable;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.Given;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileSteps {

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
        boolean actual = new File(path.toString()).delete();
        assertThat(actual, is(true));
    }

    @Given("Directory $path does NOT exist")
    public void deleteDirectory(BddVariable path) throws IOException {
        FileUtils.deleteDirectory(new File(path.toString()));
    }

}
