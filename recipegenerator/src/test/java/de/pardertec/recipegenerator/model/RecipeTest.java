package de.pardertec.recipegenerator.model;

import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static de.pardertec.recipegenerator.model.BusinessObject.JSON_KEY_ID;
import static de.pardertec.recipegenerator.model.BusinessObject.JSON_KEY_NAME;
import static de.pardertec.recipegenerator.model.Measure.GRAMS;
import static de.pardertec.recipegenerator.model.Recipe.*;
import static de.pardertec.recipegenerator.model.VeganismStatus.VEGETARIAN;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 30.01.2016.
 */
public class RecipeTest {

    public static final Allergen LACTOSE = new Allergen("Laktose");
    private Ingredient cheese;
    private Recipe recipe;

    @BeforeTest
    public void createRecipe(){
        cheese = new Ingredient("Käse", GRAMS, VEGETARIAN);
        cheese.addAllergen(LACTOSE);

        recipe = new Recipe("Purer Käse");
        recipe.setIngredientWithAmount(cheese,1000);
        recipe.setServings(2);
        recipe.setText("Heute gibt es Käse. Für 2 Personen.");
    }

    @Test
    public void testThatJsonRepresentationHasTextAndServings() throws Exception {
        //act
        JSONObject recipeAsJson = recipe.toJson();

        //assert
        assertTrue(recipeAsJson.has(JSON_KEY_ID));
        assertEquals("Purer Käse", recipeAsJson.getString(JSON_KEY_NAME));
        assertEquals("Heute gibt es Käse. Für 2 Personen.", recipeAsJson.getString(JSON_KEY_TEXT));
        assertEquals(2, recipeAsJson.getInt(JSON_KEY_SERVINGS));
    }

    @Test
    public void testThatJsonRepresentationHasIngredients() throws Exception {
        //act
        JSONObject recipeAsJson = recipe.toJson();

        //assert
        JSONObject firstIngredient = recipeAsJson.getJSONArray(JSON_KEY_INGREDIENTS).getJSONObject(0);
        assertEquals(1000,firstIngredient.getInt(JSON_KEY_AMOUNT));
        assertEquals(cheese.getId(),firstIngredient.getString(JSON_KEY_INGREDIENT));
    }

}
