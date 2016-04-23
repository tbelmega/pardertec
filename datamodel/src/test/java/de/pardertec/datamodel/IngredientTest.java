package de.pardertec.datamodel;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static de.pardertec.datamodel.Ingredient.*;
import static de.pardertec.datamodel.VeganismStatus.CONTAINS_MEAT;
import static de.pardertec.datamodel.VeganismStatus.VEGETARIAN;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 30.01.2016.
 */
public class IngredientTest {

    Measure GRAMS = new Measure("Gramm");
    RecipeCollection collection = RecipeCollection.create();

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
        assertEquals(GRAMS.getId(), cheeseAsJson.getString(JSON_KEY_MEASURE));
        assertEquals(VEGETARIAN.getCode(), cheeseAsJson.getInt(JSON_KEY_STATUS));
        assertEquals(lactose.getId(), cheeseAsJson.getJSONArray(JSON_KEY_ALLERGENS).getString(0));

    }

    @Test
    public void testThatJsonRepresentationIsParsedToIngredient() throws Exception {
        //arrange
        Allergen lactose = new Allergen("Laktose");
        Ingredient cheese = new Ingredient("Käse", GRAMS, CONTAINS_MEAT);
        cheese.addAllergen(lactose);
        collection.add(lactose);
        collection.add(GRAMS);

        JSONObject jsonRepresentation = cheese.toJson();

        //act
        Ingredient ingredient = Ingredient.fromJSON(jsonRepresentation, collection);

        //assert
        assertTrue(ingredient.getAllergens().contains(lactose));
        assertEquals("Käse", ingredient.getName());
        assertEquals(GRAMS, ingredient.getMeasure());
        assertEquals(CONTAINS_MEAT, ingredient.getStatus());
    }
}
