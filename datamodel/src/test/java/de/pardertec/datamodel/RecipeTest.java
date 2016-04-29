package de.pardertec.datamodel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 30.01.2016.
 */
public class RecipeTest {

    RecipeCollection collection = RecipeCollection.create();

    Measure GRAMS = new Measure("Gramm");
    public static final Allergen LACTOSE = new Allergen("Laktose");
    public static final String RECIPE_NAME = "Purer Käse";
    public static final String RECIPE_TEXT = "Heute gibt es Käse. Für 2 Personen.";
    public static final String STEP_1_TEXT = "Step 1 Text";
    public static final String STEP_2_TEXT = "Step 2 Text";
    public static final int NUMBER_OF_SERVINGS = 2;
    public static final int AMOUNT_OF_CHEESE = 1000;
    public static final int DURATION_IN_MINUTES = 30;
    private Ingredient cheese = new Ingredient("Käse", GRAMS, VeganismStatus.VEGETARIAN);
    private Recipe recipe;

    @BeforeMethod
    public void createRecipe() {
        cheese.addAllergen(LACTOSE);

        recipe = new Recipe(RECIPE_NAME);
        recipe.addIngredientWithAmount(cheese, AMOUNT_OF_CHEESE);
        recipe.setServings(NUMBER_OF_SERVINGS);
        recipe.setDuration(DURATION_IN_MINUTES);
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setText(RECIPE_TEXT);
    }

    @Test
    public void testThatJsonRepresentationHasAllSetAttributes() throws Exception {
        //act
        JSONObject recipeAsJson = recipe.toJson();

        //assert
        assertTrue(recipeAsJson.has(BusinessObject.JSON_KEY_ID));
        assertEquals(RECIPE_NAME, recipeAsJson.getString(BusinessObject.JSON_KEY_NAME));
        assertEquals(RECIPE_TEXT, recipeAsJson.getString(Recipe.JSON_KEY_TEXT));
        assertEquals(NUMBER_OF_SERVINGS, recipeAsJson.getInt(Recipe.JSON_KEY_SERVINGS));
        assertEquals(DURATION_IN_MINUTES, recipeAsJson.getInt(Recipe.JSON_KEY_DURATION));
        assertEquals(Difficulty.EASY.toString(), recipeAsJson.getString(Recipe.JSON_KEY_DIFFICULTY));
    }

    @Test
    public void testThatJsonRepresentationHasIngredients() throws Exception {
        //act
        JSONObject recipeAsJson = recipe.toJson();

        //assert
        JSONObject firstIngredient = recipeAsJson.getJSONArray(Recipe.JSON_KEY_INGREDIENTS).getJSONObject(0);
        assertEquals(AMOUNT_OF_CHEESE, firstIngredient.getInt(Recipe.JSON_KEY_AMOUNT));
        assertEquals(cheese.getId(), firstIngredient.getString(Recipe.JSON_KEY_INGREDIENT));
    }

    @Test
    public void testThatJsonRepresentationHasSteps() throws Exception {
        //arrange
        RecipeStep s1 = new RecipeStep(STEP_1_TEXT);
        RecipeStep s2 = new RecipeStep(STEP_2_TEXT);
        recipe.addStep(0, s2);
        recipe.addStep(0, s1);

        //act
        JSONObject recipeAsJson = recipe.toJson();

        //assert
        JSONArray steps = recipeAsJson.getJSONArray(Recipe.JSON_KEY_STEPS);
        JSONObject step1 = steps.getJSONObject(0);
        JSONObject step2 = steps.getJSONObject(1);
        assertEquals(STEP_1_TEXT, step1.getString(Recipe.JSON_KEY_TEXT));
        assertEquals(STEP_2_TEXT, step2.getString(Recipe.JSON_KEY_TEXT));
    }

    @Test
    public void testThatRecipeIsParsedFromJsonRepresentation() throws Exception {
        //arrange
        RecipeStep s1 = new RecipeStep(STEP_1_TEXT);
        RecipeStep s2 = new RecipeStep(STEP_2_TEXT);
        recipe.addStep(0, s2);
        recipe.addStep(0, s1);

        JSONObject jsonRepresentation = recipe.toJson();

        //act
        Recipe recipe = Recipe.fromJSON(jsonRepresentation, collection);

        //assert
        assertEquals(RECIPE_NAME, recipe.getName());
        assertEquals(RECIPE_TEXT, recipe.getText());
        assertEquals(NUMBER_OF_SERVINGS, (int)recipe.getServings());
        assertEquals(DURATION_IN_MINUTES, (int)recipe.getDuration());
        assertEquals(Difficulty.EASY, recipe.getDifficulty());
        assertEquals(s1, recipe.getStep(0));
        assertEquals(s2, recipe.getStep(1));
    }

    @Test
    public void testAddStepsToEmptyRecipe() throws Exception {
        //arrange


        //act
        recipe.addStep(STEP_1_TEXT);
        recipe.addStep(STEP_2_TEXT);

        //assert
        assertEquals(STEP_1_TEXT, recipe.getStep(0).getText());
        assertEquals(STEP_2_TEXT, recipe.getStep(1).getText());
    }

    @Test
    public void testInsertStepIntoExistingRecipe() throws Exception {
        //arrange
        recipe.addStep(STEP_2_TEXT);

        //act
        recipe.insertStep(0, STEP_1_TEXT);

        //assert
        assertEquals(STEP_1_TEXT, recipe.getStep(0).getText());
        assertEquals(STEP_2_TEXT, recipe.getStep(1).getText());
    }

    @Test
    public void testRemoveStep() throws Exception {
        //arrange
        recipe.addStep(STEP_1_TEXT);
        recipe.addStep(STEP_2_TEXT);

        //act
        recipe.removeStep(0);

        //assert
        assertEquals(STEP_2_TEXT, recipe.getStep(0).getText());
        assertEquals(1, recipe.getStepCount());
    }
    
    @Test
    public void testGetStepsCopy() throws Exception {
        //arrange
        RecipeStep s1 = new RecipeStep(STEP_1_TEXT);
        RecipeStep s2 = new RecipeStep(STEP_2_TEXT);
        recipe.addStep(s2);
        recipe.addStep(0, s1);

    
        //act
        List<RecipeStep> steps = recipe.getStepsCopy();
    
        //assert
        assertEquals(s1, steps.get(0));
    }

}
