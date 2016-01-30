package de.pardertec.recipegenerator.model;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static de.pardertec.recipegenerator.model.Ingredient.*;
import static de.pardertec.recipegenerator.model.Measure.GRAMS;
import static de.pardertec.recipegenerator.model.VeganismStatus.VEGETARIAN;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 30.01.2016.
 */
public class IngredientTest {

    @Test
    public void testThatIngredientIsRepresentedAsJson() throws Exception {
        //arrange
        Allergen lactose = new Allergen("Laktose");
        Ingredient cheese = new Ingredient("Käse", GRAMS, VEGETARIAN);
        cheese.addAllergen(lactose);


        //act
        JSONObject cheeseAsJson = cheese.toJson();

        //assert
        assertTrue(cheeseAsJson.has(JSON_KEY_ID));
        assertEquals("Käse", cheeseAsJson.getString(JSON_KEY_NAME));
        assertEquals(GRAMS.toString(), cheeseAsJson.getString(JSON_KEY_MEASURE));
        assertEquals(VEGETARIAN.toString(), cheeseAsJson.getString(JSON_KEY_STATUS));
        assertEquals(lactose.getId(), cheeseAsJson.getJSONArray(JSON_KEY_ALLERGENS).getString(0));

    }
}
