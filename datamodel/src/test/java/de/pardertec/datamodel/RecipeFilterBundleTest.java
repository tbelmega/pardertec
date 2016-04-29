package de.pardertec.datamodel;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

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
    public void testThatBundleWithVeganStatus_notRestrictsRecipeWithoutMeat() {
        //arrange
        Ingredient i = new Ingredient("Test Ingredient", new Measure("Gram"), VeganismStatus.VEGAN);
        Recipe r = new Recipe("Test Recipe");
        r.addIngredientWithAmount(i, 500);
        RecipeFilterBundle recipeFilterBundle = new RecipeFilterBundle(VeganismStatus.VEGAN, new HashSet<Allergen>());

        //act
        boolean restricted = recipeFilterBundle.isRestricted(r);

        //assert
        assertFalse(restricted);
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

    @Test
    public void testThatBundleWithoutAllergens_notRestrictsAllergen() {
        //arrange
        Allergen a = new Allergen("Test allergen");
        Ingredient i = new Ingredient("Test ingredient", new Measure("Gram"), VeganismStatus.CONTAINS_MEAT);
        i.addAllergen(a);
        Recipe r = new Recipe("Test recipe");
        r.addIngredientWithAmount(i, 500);
        RecipeFilterBundle recipeFilterBundle = new RecipeFilterBundle(VeganismStatus.CONTAINS_MEAT, new HashSet<Allergen>());

        //act
        boolean restricted = recipeFilterBundle.isRestricted(r);

        //assert
        assertFalse(restricted);
    }

    @Test
    public void testThatBundleWithAllergen_restrictsIngredientsWithThisAllergen() {
        //arrange
        Allergen a = new Allergen("Test allergen");
        Ingredient i = new Ingredient("Test ingredient", new Measure("Gram"), VeganismStatus.CONTAINS_MEAT);
        i.addAllergen(a);
        Recipe r = new Recipe("Test recipe");
        r.addIngredientWithAmount(i, 500);
        Set<Allergen> restrictedAllergens = new HashSet<>();
        restrictedAllergens.add(a);
        RecipeFilterBundle recipeFilterBundle = new RecipeFilterBundle(VeganismStatus.CONTAINS_MEAT, restrictedAllergens);

        //act
        boolean restricted = recipeFilterBundle.isRestricted(r);

        //assert
        assertTrue(restricted);
    }


}
