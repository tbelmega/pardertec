package de.pardertec.recipegenerator.model;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by Thiemo on 13.04.2016.
 */
public class MeasureTest {

    @Test
    public void testGetEnum() throws Exception {
        //arrange

        //act
        for (Measure m : Measure.values()) {
            Measure result = Measure.getEnum(m.toString());

            //assert
            assertEquals(m, result);
        }
    }
}
