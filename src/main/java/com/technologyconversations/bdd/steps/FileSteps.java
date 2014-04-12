package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddVariable;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.Given;
import java.io.File;
import java.io.IOException;

public class FileSteps {

    @Given("File $path exists")
    public void createFile(BddVariable path) throws IOException {
        File file = new File(path.toString());
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    @Given("Directory $path exists")
    public void createDirectory(BddVariable path) {
        new File(path.toString()).mkdirs();
    }

    @Given("File $path does NOT exist")
    public void deleteFile(BddVariable path) {
        new File(path.toString()).delete();
    }

    @Given("Directory $path does NOT exist")
    public void deleteDirectory(BddVariable path) throws IOException {
        FileUtils.deleteDirectory(new File(path.toString()));
    }

}
