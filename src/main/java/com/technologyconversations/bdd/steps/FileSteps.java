package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddVariable;
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
        File directory = new File(path.toString());
        directory.mkdirs();
    }

}
