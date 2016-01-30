package de.pardertec.recipegenerator.model;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static de.pardertec.recipegenerator.model.Allergen.*;

/**
 * Created by Thiemo on 30.01.2016.
 */
public class AllergenTest {

    @Test
    public void testThatAllergenIsRepresentedAsJson() throws Exception {
        //arrange
        Allergen lactose = new Allergen("Laktose");


        //act
        JSONObject allergenAsJson = lactose.toJson();

        //assert
        assertTrue(allergenAsJson.has(JSON_KEY_ID));
        assertEquals("Laktose", allergenAsJson.getString(JSON_KEY_NAME));

    }
}
