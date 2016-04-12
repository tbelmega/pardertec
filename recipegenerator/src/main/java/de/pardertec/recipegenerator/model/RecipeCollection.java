package de.pardertec.recipegenerator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import static de.pardertec.recipegenerator.model.Measure.*;
import static de.pardertec.recipegenerator.model.VeganismStatus.*;


/**
 * Created by Thiemo on 30.01.2016.
 *
 * Singleton pattern.
 */
public class RecipeCollection {

    public static final String JSON_KEY_RECIPES = "recipes";

    private static RecipeCollection instance = new RecipeCollection();
    private Set<Recipe> recipes = new HashSet<>();
    private Set<Ingredient> ingredients = new HashSet<>();
    private Set<Allergen> allergens = new HashSet<>();

    /**
     * Private constructor -> prevent instances.
     */
    private RecipeCollection() {
    }

    public static RecipeCollection getInstance() {
        return instance;
    }

    public void add(Recipe recipe) {
        for (Ingredient ingredient: recipe.getIngredients().keySet()){
            add(ingredient);
        }
        this.recipes.add(recipe);
    }

    public void add(Ingredient ingredient) {
        for (Allergen allergen: ingredient.getAllergens()){
            add(allergen);
        }
        this.ingredients.add(ingredient);
    }

    public void add(Allergen allergen) {
        this.allergens.add(allergen);
    }


    public boolean contains(Recipe myRecipe) {
        return this.recipes.contains(myRecipe);
    }

    /**
     * This method creates a JSON representation of the RecipeCollection, including all recipes/ingredients/allergens
     *
     * @return
     */
    public JSONObject toJson() {
        JSONObject jsonRepresentation = new JSONObject();
        jsonRepresentation.put(JSON_KEY_RECIPES, createRecipesRepresentation());
        jsonRepresentation.put(Recipe.JSON_KEY_INGREDIENTS, createIngredientsRepresentation());
        jsonRepresentation.put(Ingredient.JSON_KEY_ALLERGENS, createAllergensRepresentation());
        return jsonRepresentation;
    }

    private JSONArray createAllergensRepresentation() {
        JSONArray allAllergens = new JSONArray();
        for (Allergen a: this.allergens){
            allAllergens.put(a.toJson());
        }
        return allAllergens;
    }

    private JSONArray createIngredientsRepresentation() {
        JSONArray allIngredients = new JSONArray();
        for (Ingredient i: this.ingredients){
            allIngredients.put(i.toJson());
        }
        return allIngredients;
    }

    private JSONArray createRecipesRepresentation() {
        JSONArray allRecipes = new JSONArray();
        for (Recipe r: this.recipes){
            allRecipes.put(r.toJson());
        }
        return allRecipes;
    }

    /**
     * Creates a recipe as test data. Bad practice for production code don't do this!
     * @return
     */
    public static final Allergen LAKTOSE = new Allergen("Laktose");
    public static Recipe createSpaghettiBologneseRecipe() {

        final Ingredient MEAT = new Ingredient("Hackfleisch, gemischt", GRAMS, CONTAINS_MEAT);

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

    /**
     * @return a new copy of the allergens set
     */
    public Set<Allergen> getAllergensCopy() {
        return new HashSet<>(allergens);
    }

    /**
     * @return a new copy of the ingredients set
     */
    public Set<Ingredient> getIngredientsCopy() {
        return new HashSet<>(ingredients);
    }

    /**
     * @return a new copy of the recipes set
     */
    public Set<Recipe> getRecipesCopy() {
        return new HashSet<>(recipes);
    }

    public void remove(Allergen a) {
        this.allergens.remove(a);
    }

    public void remove(Ingredient i) {
        this.ingredients.remove(i);
    }

    public void remove(Recipe r) {
        this.recipes.remove(r);
    }
}
