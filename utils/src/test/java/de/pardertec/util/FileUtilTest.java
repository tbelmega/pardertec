package de.pardertec.util;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 05.03.2016.
 */
public class FileUtilTest {

    File targetFile = new File("test.txt");

    @BeforeMethod
    public void setUp() {
        //File should not exist before the test
        if (targetFile.exists()) targetFile.delete();
    }

    /**
     * Tests that FileUtil.writeToFile() method writes a string into a file.
     *
     * @throws Exception
     */
    @Test
    public void testThatStringCanBeWrittenToFile() throws Exception {
        //arrange
        String testContent = "Eine Seefahrt, die ist lustig\nEine Seefahrt, die ist schön!";

        //act
        FileUtil.writeToFile(targetFile, testContent);

        //assert
        String readContent = FileUtil.readFile(targetFile);
        assertEquals(testContent, readContent);
    }

    @AfterMethod
    public void cleanUp() {
        //Tests should not leave any durable effects behind
        if (targetFile.exists()) targetFile.delete();
    }

}
