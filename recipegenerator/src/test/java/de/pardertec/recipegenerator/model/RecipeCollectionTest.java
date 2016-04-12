package de.pardertec.recipegenerator.model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static de.pardertec.recipegenerator.model.Ingredient.JSON_KEY_ALLERGENS;
import static de.pardertec.recipegenerator.model.Measure.GRAMS;
import static de.pardertec.recipegenerator.model.Recipe.JSON_KEY_INGREDIENTS;
import static de.pardertec.recipegenerator.model.RecipeCollection.JSON_KEY_RECIPES;
import static de.pardertec.recipegenerator.model.RecipeCollection.createSpaghettiBologneseRecipe;
import static de.pardertec.recipegenerator.model.VeganismStatus.CONTAINS_MEAT;
import static de.pardertec.util.JSONUtil.assertEqualJSONContent;
import static de.pardertec.util.JSONUtil.assertJSONArrayContainsValue;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class RecipeCollectionTest {

    public static final Ingredient MEAT = new Ingredient("Hackfleisch, gemischt", GRAMS, CONTAINS_MEAT);
    public static final Recipe SPAGHETTI_BOLOGNESE = createSpaghettiBologneseRecipe();
    static RecipeCollection allRecipes;

    @BeforeTest
    public void getRecipeCollection(){
        allRecipes =  RecipeCollection.getInstance();
        allRecipes.add(SPAGHETTI_BOLOGNESE);
    }

    @Test
    public void testThatRecipeCollectionContainsAddedRecipe() throws Exception {
        //arrange

        //act

        //assert
        assertTrue(allRecipes.contains(SPAGHETTI_BOLOGNESE));
    }

    @Test
    public void testThatRecipeCollectionDoesNotContainNewRecipe() throws Exception {
        //arrange
        Recipe newRecipe = new Recipe("foo");

        //assert
        assertFalse(allRecipes.contains(newRecipe));
    }

    @Test
    public void testThatRecipeCollectionContainsEqualRecipe() throws Exception {
        //arrange

        //act
        Recipe equalRecipe = createSpaghettiBologneseRecipe();

        //assert
        assertTrue(allRecipes.contains(equalRecipe));
    }

    @Test
    public void testThatRecipeCollectionHasRecipesAndIngredientsAndAllergens() throws Exception {
        //arrange

        //act
        JSONObject recipeCollection = allRecipes.toJson();

        //assert
        assertTrue(recipeCollection.has(JSON_KEY_RECIPES));
        assertTrue(recipeCollection.has(JSON_KEY_INGREDIENTS));
        assertTrue(recipeCollection.has(JSON_KEY_ALLERGENS));

        System.out.println(recipeCollection.toString(4));
    }

    @Test
    public void testThatRecipeCollectionContainsSpaghettiRecipe() throws Exception {
        //arrange
        JSONObject recipeCollection = allRecipes.toJson();

        //act
        JSONArray recipes = recipeCollection.getJSONArray(JSON_KEY_RECIPES);

        //assert
        assertEqualJSONContent(SPAGHETTI_BOLOGNESE.toJson(), recipes.getJSONObject(0));
    }

    @Test
    public void testThatRecipeCollectionContainsMeat() throws Exception {
        //arrange
        JSONObject recipeCollection = allRecipes.toJson();

        //act
        JSONArray ingredients = recipeCollection.getJSONArray(JSON_KEY_INGREDIENTS);

        //assert
        assertJSONArrayContainsValue(ingredients, MEAT.toJson());
    }

    @Test
    public void testThatRecipeCollectionContainsLactose() throws Exception {
        //arrange
        JSONObject recipeCollection = allRecipes.toJson();

        //act
        JSONArray allergens = recipeCollection.getJSONArray(JSON_KEY_ALLERGENS);

        //assert
        assertEqualJSONContent(RecipeCollection.LAKTOSE.toJson(), allergens.getJSONObject(0));
    }


}
