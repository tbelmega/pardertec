package de.pardertec.datamodel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


/**
 * Created by Thiemo on 30.01.2016.
 */
public class RecipeCollection {

    public static final String JSON_KEY_RECIPES = "recipes";

    private Map<UUID, Recipe> recipes = new HashMap<>();
    private Map<UUID, Ingredient> ingredients = new HashMap<>();
    private Map<UUID, Allergen> allergens = new HashMap<>();


    private RecipeCollection() {
    }

    public static RecipeCollection create() {
        return new RecipeCollection();
    }


    public void add(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients().keySet()) {
            add(ingredient);
        }
        recipes.put(recipe.id, recipe);
    }

    public void add(Ingredient ingredient) {
        for (Allergen allergen : ingredient.getAllergens()) {
            add(allergen);
        }
        ingredients.put(ingredient.id, ingredient);
    }

    public  void add(Allergen allergen) {
        allergens.put(allergen.id, allergen);
    }


    public boolean contains(Recipe myRecipe) {
        return recipes.values().contains(myRecipe);
    }

    public boolean contains(Ingredient myIngredient) {
        return ingredients.values().contains(myIngredient);
    }

    public  boolean contains(Allergen myAllergen) {
        return allergens.values().contains(myAllergen);
    }

    /**
     * This method creates a JSON representation of the RecipeCollection, including all recipes/ingredients/allergens
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
        for (Allergen a : allergens.values()) {
            allAllergens.put(a.toJson());
        }
        return allAllergens;
    }

    private  JSONArray createIngredientsRepresentation() {
        JSONArray allIngredients = new JSONArray();
        for (Ingredient i : ingredients.values()) {
            allIngredients.put(i.toJson());
        }
        return allIngredients;
    }

    private JSONArray createRecipesRepresentation() {
        JSONArray allRecipes = new JSONArray();
        for (Recipe r : recipes.values()) {
            allRecipes.put(r.toJson());
        }
        return allRecipes;
    }

    /**
     * @return a new copy of the allergens set
     */
    public SortedSet<Allergen> getAllergensCopy() {
        return new TreeSet<>(allergens.values());
    }

    /**
     * @return a new copy of the ingredients set
     */
    public SortedSet<Ingredient> getIngredientsCopy() {
        return new TreeSet<>(ingredients.values());
    }

    /**
     * @return a new copy of the recipes set
     */
    public SortedSet<Recipe> getRecipesCopy() {
        return new TreeSet<>(recipes.values());
    }

    public void remove(Allergen a) {
        allergens.remove(a.id);
    }

    public void remove(Ingredient i) {
        ingredients.remove(i.id);
    }

    public void remove(Recipe r) {
        recipes.remove(r.id);
    }

    public void remove(BusinessObject b) {
        if (b instanceof Recipe) remove((Recipe) b);
        if (b instanceof Allergen) remove((Allergen) b);
        if (b instanceof Ingredient) remove((Ingredient) b);
    }

    public void importJSON(String s) {
        JSONObject document = new JSONObject(s);
        importRecipes(document);
    }

    private void importRecipes(JSONObject document) {
        importIngredients(document);

        JSONArray recipes = document.getJSONArray(JSON_KEY_RECIPES);
        for (int i = 0; i < recipes.length(); i++) {
            importRecipe(recipes.getJSONObject(i));
        }
    }

    private void importRecipe(JSONObject jsonObject) {
        Recipe r = Recipe.fromJSON(jsonObject, this);
        recipes.put(r.id, r);
    }

    private void importIngredients(JSONObject document) {
        JSONArray ingredients = document.getJSONArray(Recipe.JSON_KEY_INGREDIENTS);


        importAllergens(document);

        for (int i = 0; i < ingredients.length(); i++) {
            importIngredient(ingredients.getJSONObject(i));
        }
    }

    private void importIngredient(JSONObject jsonObject) {
        Ingredient ingredient = Ingredient.fromJSON(jsonObject, this);
        ingredients.put(ingredient.id, ingredient);
    }

    private void importAllergens(JSONObject document) {
        JSONArray allergens = document.getJSONArray(Ingredient.JSON_KEY_ALLERGENS);

        for (int i = 0; i < allergens.length(); i++) {
            importAllergen(allergens.getJSONObject(i));
        }
    }

    private void importAllergen(JSONObject jsonObject) {
        Allergen allergen = Allergen.fromJSON(jsonObject);
        allergens.put(allergen.id, allergen);
    }

    public Allergen getAllergen(UUID uuid) {
        return allergens.get(uuid);
    }

    public Ingredient getIngredient(UUID uuid) {
        return ingredients.get(uuid);
    }

    public boolean isEmpty() {
        return allergens.isEmpty() &&
                ingredients.isEmpty() &&
                recipes.isEmpty();
    }

}
