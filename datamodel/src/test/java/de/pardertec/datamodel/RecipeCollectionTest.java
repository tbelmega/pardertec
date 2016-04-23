package de.pardertec.datamodel;


import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static de.pardertec.datamodel.Ingredient.JSON_KEY_ALLERGENS;
import static de.pardertec.datamodel.RecipeCollection.JSON_KEY_RECIPES;
import static de.pardertec.datamodel.VeganismStatus.*;
import static de.pardertec.util.JSONUtil.assertEqualJSONContent;
import static de.pardertec.util.JSONUtil.assertJSONArrayContainsValue;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class RecipeCollectionTest {

    RecipeCollection collection = RecipeCollection.create();



    public static final Measure MILLILITERS = new Measure("Milliliter");
    public static final Measure PIECES = new Measure("Stück");
    public static final Measure GRAMS = new Measure("Gramm");
    public static final Allergen LAKTOSE = new Allergen("Laktose");
    public static final Ingredient MEAT = new Ingredient("Hackfleisch, gemischt", GRAMS, CONTAINS_MEAT);
    public static Recipe spaghettiBolognese;

    @BeforeTest
    public void getRecipeCollection(){
        spaghettiBolognese = createSpaghettiBologneseRecipe();
        collection.add(spaghettiBolognese);
    }


    @Test
    public void testThatRecipeCollectionContainsAddedRecipe() throws Exception {
        //arrange

        //act

        //assert
        assertTrue(collection.contains(spaghettiBolognese));
    }

    @Test
    public void testThatRecipeCollectionDoesNotContainNewRecipe() throws Exception {
        //arrange
        Recipe newRecipe = new Recipe("foo");

        //assert
        assertFalse(collection.contains(newRecipe));
    }

    @Test
    public void testThatRecipeCollectionContainsEqualRecipe() throws Exception {
        //arrange

        //act
        Recipe equalRecipe = createSpaghettiBologneseRecipe();

        //assert
        assertTrue(collection.contains(equalRecipe));
    }

    @Test
    public void testThatRecipeCollectionHasRecipesAndIngredientsAndAllergens() throws Exception {
        //arrange

        //act
        JSONObject recipeCollection = collection.toJson();

        //assert
        assertTrue(recipeCollection.has(JSON_KEY_RECIPES));
        assertTrue(recipeCollection.has(Recipe.JSON_KEY_INGREDIENTS));
        assertTrue(recipeCollection.has(JSON_KEY_ALLERGENS));

        System.out.println(recipeCollection.toString(4));
    }

    @Test
    public void testThatRecipeCollectionContainsSpaghettiRecipe() throws Exception {
        //arrange
        JSONObject recipeCollection = collection.toJson();

        //act
        JSONArray recipes = recipeCollection.getJSONArray(JSON_KEY_RECIPES);

        //assert
        assertEqualJSONContent(spaghettiBolognese.toJson(), recipes.getJSONObject(0));
    }

    @Test
    public void testThatRecipeCollectionContainsMeat() throws Exception {
        //arrange
        JSONObject recipeCollection = collection.toJson();

        //act
        JSONArray ingredients = recipeCollection.getJSONArray(Recipe.JSON_KEY_INGREDIENTS);

        //assert
        assertJSONArrayContainsValue(ingredients, MEAT.toJson());
    }

    @Test
    public void testThatRecipeCollectionContainsLactose() throws Exception {
        //arrange
        JSONObject recipeCollection = collection.toJson();

        //act
        JSONArray allergens = recipeCollection.getJSONArray(JSON_KEY_ALLERGENS);

        //assert
        assertEqualJSONContent(LAKTOSE.toJson(), allergens.getJSONObject(0));
    }



    public static Recipe createSpaghettiBologneseRecipe() {

        Ingredient MEAT = new Ingredient("Hackfleisch, gemischt", GRAMS, CONTAINS_MEAT);
        Ingredient oil = new Ingredient("Sonnenblumenöl", MILLILITERS, VEGAN);
        Ingredient onions = new Ingredient("Zwiebeln", PIECES, VEGAN);
        Ingredient tomatoPaste = new Ingredient("Tomatenmark", GRAMS, VEGAN);
        Ingredient sugar = new Ingredient("Zucker", GRAMS, VEGAN);
        Ingredient salt = new Ingredient("Salz", GRAMS, VEGAN);
        Ingredient pepper = new Ingredient("Pfeffer", GRAMS, VEGAN);
        Ingredient garlicClove = new Ingredient("Knochblauchzehe", PIECES, VEGAN);
        Ingredient sievedTomatos = new Ingredient("Passierte Tomaten", GRAMS, VEGAN);
        Ingredient spaghetti = new Ingredient("Spaghetti", GRAMS, VEGETARIAN);
        Ingredient parmesan = new Ingredient("Parmesankäse", GRAMS, VEGETARIAN);
        parmesan.addAllergen(LAKTOSE);

        Recipe myRecipe = new Recipe("Spaghetti Bolognese");

        myRecipe.setIngredientWithAmount(MEAT, 500);
        myRecipe.setIngredientWithAmount(oil, 20);
        myRecipe.setIngredientWithAmount(onions, 0);
        myRecipe.setIngredientWithAmount(tomatoPaste, 50);
        myRecipe.setIngredientWithAmount(sugar, 50);
        myRecipe.setIngredientWithAmount(salt, 50);
        myRecipe.setIngredientWithAmount(pepper, 50);
        myRecipe.setIngredientWithAmount(garlicClove, 2);
        myRecipe.setIngredientWithAmount(sievedTomatos, 500);
        myRecipe.setIngredientWithAmount(spaghetti, 500);
        myRecipe.setIngredientWithAmount(parmesan, 100);

        myRecipe.setServings(4);
        myRecipe.setText("foo");
        return myRecipe;
    }


}
