package com.technologyconversations.bdd.steps;

import com.technologyconversations.bdd.steps.util.BddParam;
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
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileStepsTest {

    private FileSteps steps;
    private BddVariable filePath;
    private BddVariable newFilePath;
    private BddVariable directoryPath;

    @Before
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    public final void beforeFileStepsTest() {
        steps = new FileSteps();
        File tmp = new File("tmp");
        tmp.mkdir();
        filePath = new BddVariable("tmp/myFile.test");
        newFilePath = new BddVariable("tmp/newDir/myFile.test_new");
        directoryPath = new BddVariable("tmp/myDirectory");
    }

    @After
    public final void afterFileStepsTest() throws IOException {
        FileUtils.deleteDirectory(new File("tmp"));
    }

    // setTimeoutSeconds

    @Test
    public final void setTimeoutSecondsShouldUseBddVariable() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("setTimeoutSeconds", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void setTimeoutSecondsShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("setTimeoutSeconds", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void setTimeoutSecondsShouldHaveBddParamAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("setTimeoutSeconds", BddVariable.class).getAnnotation(BddParam.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void setTimeoutSecondsShouldSetTimeout() {
        final int timeout = 5;
        long timeoutMilliseconds = timeout * FileSteps.MILLISECONDS_IN_SECOND;
        steps.setTimeoutSeconds(new BddVariable(Integer.toString(timeout)));
        assertThat(steps.getTimeout(), is(timeoutMilliseconds));
    }

    // getTimeout

    @Test
    public final void getTimeoutShouldReturnDefaultValue() {
        assertThat(steps.getTimeout(), is(FileSteps.DEFAULT_TIMEOUT));
    }

    // createFile

    @Test
    public final void createFileShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createFile", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void createFileShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createFile", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void createFileShouldCreateFile() throws IOException {
        steps.createFile(filePath);
        File actual = new File(filePath.toString());
        assertThat(actual.exists(), is(true));
    }

    @Test
    public final void createFileShouldCreateParentDirectories() throws IOException {
        String actualPath = "tmp" + File.separator + "non_existing_dir" + File.separator + "myFile.test";
        steps.createFile(new BddVariable(actualPath));
        File actual = new File(actualPath);
        assertThat(actual.exists(), is(true));
    }

    // createDirectory

    @Test
    public final void createDirectoryShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createDirectory", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void createDirectoryShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("createDirectory", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void createDirectoryShouldCreateDirectory() throws IOException {
        steps.createDirectory(directoryPath);
        File actual = new File(directoryPath.toString());
        assertThat(actual.exists(), is(true));
    }

    @Test
    public final void createDirectoryShouldCreateParentDirectories() throws IOException {
        String actualPath = "tmp" + File.separator + "non_existing_dir" + File.separator + "another_non_existing_dir";
        steps.createDirectory(new BddVariable(actualPath));
        File actual = new File(actualPath);
        assertThat(actual.exists(), is(true));
    }

    // deleteFile

    @Test
    public final void deleteFileShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteFile", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void deleteFileShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteFile", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    public final void deleteFileShouldDeleteFile() throws IOException {
        File actual = new File(filePath.toString());
        actual.createNewFile();
        steps.deleteFile(filePath);
        assertThat(actual.exists(), is(false));
    }

    // deleteDirectory

    @Test
    public final void deleteDirectoryShouldUseBddVariablePathAsArgument() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteDirectory", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void deleteDirectoryShouldHaveGivenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("deleteDirectory", BddVariable.class).getAnnotation(Given.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    public final void deleteDirectoryShouldDeleteDirectory() throws IOException {
        File actual = new File(directoryPath.toString());
        actual.mkdir();
        steps.deleteDirectory(directoryPath);
        assertThat(actual.exists(), is(false));
    }

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    public final void deleteDirectoryShouldDeleteChildDirectories() throws IOException {
        String path = directoryPath.toString() + File.separator + "child_dir" + File.separator + "another_child_dir";
        new File(path).mkdirs();
        steps.deleteDirectory(directoryPath);
        File actual = new File(directoryPath.toString());
        assertThat(actual.exists(), is(false));
    }

    // copyFile

    @Test
    public final void copyFileShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("copyFile", BddVariable.class, BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void copyFileShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Method method = FileSteps.class.getMethod("copyFile", BddVariable.class, BddVariable.class);
        Object actual = method.getAnnotation(When.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void copyFileShouldCopyFile() throws IOException {
        File from = new File(filePath.toString());
        FileUtils.touch(from);
        File to = new File(newFilePath.toString());
        steps.copyFile(new BddVariable(from.getPath()), new BddVariable(to.getPath()));
        assertThat(to.exists(), is(true));
    }

    @Test
    public final void copyFileShouldNotOverwriteExistingFile() throws IOException {
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
    public final void fileExistsShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileExists", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void fileExistsShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileExists", BddVariable.class).getAnnotation(Then.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void fileExistsShouldHaveAliasAnnotationForDirectory() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileExists", BddVariable.class).getAnnotation(Alias.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test(expected = AssertionError.class)
    public final void fileExistsShouldThrowExceptionIfFileDoesNotExist() throws IOException, InterruptedException {
        steps.setTimeout(0);
        steps.fileExists(newFilePath);
    }

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    public final void fileExistsShouldNotThrowExceptionIfFileExists() throws IOException, InterruptedException {
        new File(filePath.toString()).createNewFile();
        steps.fileExists(filePath);
    }

    // fileDoesNotExist

    @Test
    public final void fileDoesNotExistShouldUseBddVariablesAsArguments() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileDoesNotExist", BddVariable.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void fileDoesNotExistShouldHaveWhenAnnotation() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileDoesNotExist", BddVariable.class).getAnnotation(Then.class);
        assertThat(actual, is(not(nullValue())));
    }

    @Test
    public final void fileDoesNotExistShouldHaveAliasAnnotationForDirectory() throws NoSuchMethodException {
        Object actual = FileSteps.class.getMethod("fileDoesNotExist", BddVariable.class).getAnnotation(Alias.class);
        assertThat(actual, is(not(nullValue())));
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    @Test(expected = AssertionError.class)
    public final void fileDoesNotExistShouldThrowExceptionIfFileExists() throws IOException, InterruptedException {
        steps.setTimeout(0);
        new File(filePath.toString()).createNewFile();
        steps.fileDoesNotExist(filePath);
    }

    @Test
    public final void fileDoesNotExistShouldNotThrowExceptionIfFileDoesNotExist()
            throws IOException, InterruptedException {
        steps.fileDoesNotExist(newFilePath);
    }

}
