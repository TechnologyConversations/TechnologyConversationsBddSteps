package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddVariable;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.Given;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileStepsTest {

    private FileSteps steps;
    private BddVariable filePath;
    private BddVariable directoryPath;

    @Before
    public void beforeFileStepsTest() {
        steps = new FileSteps();
        File tmp = new File("tmp");
        tmp.mkdir();
        filePath = new BddVariable("tmp/myFile.test");
        directoryPath = new BddVariable("tmp/myDirectory");
    }

    @After
    public void afterFileStepsTest() throws IOException {
        FileUtils.deleteDirectory(new File("tmp"));
    }

    // createFile

    @Test
    public void createFileShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createFile", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void createFileShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createFile", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void createFileShouldCreateFile() throws IOException {
        steps.createFile(filePath);
        File actual = new File(filePath.toString());
        assertThat(actual.exists(), is(true));
    }

    @Test
    public void createFileShouldCreateParentDirectories() throws IOException {
        String actualPath = "tmp" + File.separator + "non_existing_dir" + File.separator + "myFile.test";
        steps.createFile(new BddVariable(actualPath));
        File actual = new File(actualPath);
        assertThat(actual.exists(), is(true));
    }

    // createDirectory

    @Test
    public void createDirectoryShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createDirectory", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void createDirectoryShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createDirectory", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void createDirectoryShouldCreateDirectory() throws IOException {
        steps.createDirectory(directoryPath);
        File actual = new File(directoryPath.toString());
        assertThat(actual.exists(), is(true));
    }

    @Test
    public void createDirectoryShouldCreateParentDirectories() throws IOException {
        String actualPath = "tmp" + File.separator + "non_existing_dir" + File.separator + "another_non_existing_dir";
        steps.createDirectory(new BddVariable(actualPath));
        File actual = new File(actualPath);
        assertThat(actual.exists(), is(true));
    }

}
