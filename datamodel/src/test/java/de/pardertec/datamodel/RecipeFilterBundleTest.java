package de.pardertec.datamodel;

import org.testng.annotations.Test;

import java.util.HashSet;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by Thiemo on 29.04.2016.
 */
public class RecipeFilterBundleTest {

    @Test
    public void testThatBundleWithVeganStatus_restrictsRecipeWithMeat() {
        //arrange
        Ingredient i = new Ingredient("Test Ingredient", new Measure("Gram"), VeganismStatus.CONTAINS_MEAT);
        Recipe r = new Recipe("Test Recipe");
        r.addIngredientWithAmount(i, 500);
        RecipeFilterBundle recipeFilterBundle = new RecipeFilterBundle(VeganismStatus.VEGAN, new HashSet<Allergen>());

        //act
        boolean restricted = recipeFilterBundle.isRestricted(r);

        //assert
        assertTrue(restricted);
    }

    @Test
    public void testThatBundleWithMeatStatus_notRestrictsRecipeWithMeat() {
        //arrange
        Ingredient i = new Ingredient("Test Ingredient", new Measure("Gram"), VeganismStatus.CONTAINS_MEAT);
        Recipe r = new Recipe("Test Recipe");
        r.addIngredientWithAmount(i, 500);
        RecipeFilterBundle recipeFilterBundle = new RecipeFilterBundle(VeganismStatus.CONTAINS_MEAT, new HashSet<Allergen>());

        //act
        boolean restricted = recipeFilterBundle.isRestricted(r);

        //assert
        assertFalse(restricted);
    }


}
