package de.pardertec.recipegenerator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;


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

    public static void importJSON(String s) {

    }
}
