package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddVariable;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
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
    private BddVariable newFilePath;
    private BddVariable directoryPath;

    @Before
    public void beforeFileStepsTest() {
        steps = new FileSteps();
        File tmp = new File("tmp");
        tmp.mkdir();
        filePath = new BddVariable("tmp/myFile.test");
        newFilePath = new BddVariable("tmp/newDir/myFile.test_new");
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

    // deleteFile

    @Test
    public void deleteFileShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteFile", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void deleteFileShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteFile", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void deleteFileShouldDeleteFile() throws IOException {
        File actual = new File(filePath.toString());
        actual.createNewFile();
        steps.deleteFile(filePath);
        assertThat(actual.exists(), is(false));
    }

    // deleteDirectory

    @Test
    public void deleteDirectoryShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteDirectory", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void deleteDirectoryShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteDirectory", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void deleteDirectoryShouldDeleteDirectory() throws IOException {
        File actual = new File(directoryPath.toString());
        actual.mkdir();
        steps.deleteDirectory(directoryPath);
        assertThat(actual.exists(), is(false));
    }

    @Test
    public void deleteDirectoryShouldDeleteChildDirectories() throws IOException {
        String path = directoryPath.toString() + File.separator + "child_dir" + File.separator + "another_child_dir";
        new File(path).mkdirs();
        steps.deleteDirectory(directoryPath);
        File actual = new File(directoryPath.toString());
        assertThat(actual.exists(), is(false));
    }

    // copyFile

    @Test
    public void copyFileShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("copyFile", BddVariable.class, BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void copyFileShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("copyFile", BddVariable.class, BddVariable.class).getAnnotation(When.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void copyFileShouldCopyFile() throws IOException {
        File from = new File(filePath.toString());
        FileUtils.touch(from);
        File to = new File(newFilePath.toString());
        steps.copyFile(new BddVariable(from.getPath()), new BddVariable(to.getPath()));
        assertThat(to.exists(), is(true));
    }

    @Test
    public void copyFileShouldNotOverwriteExistingFile() throws IOException {
        String expected = "expected content";
        File from = new File(filePath.toString());
        FileUtils.writeStringToFile(from, "some content");
        File to = new File(newFilePath.toString());
        FileUtils.writeStringToFile(to, expected);

        steps.copyFile(new BddVariable(from.getPath()), new BddVariable(to.getPath()));
        String actual = FileUtils.readFileToString(to);
        assertThat(actual, is(equalTo(expected)));
    }

    // fileExists

    @Test
    public void fileExistsShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileExists", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void fileExistsShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileExists", BddVariable.class).getAnnotation(Then.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public void fileExistsShouldHaveAliasAnnotationForDirectory() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileExists", BddVariable.class).getAnnotation(Alias.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test(expected = AssertionError.class)
    public void fileExistsShouldThrowExceptionIfFileDoesNotExist() throws IOException {
        steps.fileExists(newFilePath);
    }

    @Test
    public void fileExistsShouldNotThrowExceptionIfFileExists() throws IOException {
        new File(filePath.toString()).createNewFile();
        steps.fileExists(filePath);
    }

}
