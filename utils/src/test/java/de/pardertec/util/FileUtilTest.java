package de.pardertec.util;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 13.04.2016.
 */
public class FileUtilTest {

    File f;

    @Test
    public void testThatTextWritesIntoFile() throws Exception {
        //arrange
        f = new File("test_file_" + System.currentTimeMillis());
        assert !f.exists();
        String s = "Hello, world!";

        //act
        FileUtil.writeTextFile(f, s);

        //assert
        String result = FileUtil.readFile(f);
        assertEquals(s, result);
    }

    @AfterMethod
    public void clean() {
        f.delete();
    }


}
