package de.pardertec.datamodel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


/**
 * Created by Thiemo on 30.01.2016.
 * <p>
 * Singleton pattern.
 */
public class RecipeCollection {

    public static final String JSON_KEY_RECIPES = "recipes";

    private static RecipeCollection instance = new RecipeCollection();
    private Map<UUID, Recipe> recipes = new HashMap<>();
    private Map<UUID, Ingredient> ingredients = new HashMap<>();
    private Map<UUID, Allergen> allergens = new HashMap<>();

    /**
     * Private constructor -> prevent instances.
     */
    private RecipeCollection() {
    }

    private static RecipeCollection getInstance() {
        return instance;
    }

    public static void add(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients().keySet()) {
            add(ingredient);
        }
        getInstance().recipes.put(recipe.id, recipe);
    }

    public static void add(Ingredient ingredient) {
        for (Allergen allergen : ingredient.getAllergens()) {
            add(allergen);
        }
        getInstance().ingredients.put(ingredient.id, ingredient);
    }

    public static void add(Allergen allergen) {
        getInstance().allergens.put(allergen.id, allergen);
    }


    public static boolean contains(Recipe myRecipe) {
        return getInstance().recipes.values().contains(myRecipe);
    }

    /**
     * This method creates a JSON representation of the RecipeCollection, including all recipes/ingredients/allergens
     */
    public static JSONObject toJson() {
        JSONObject jsonRepresentation = new JSONObject();
        jsonRepresentation.put(JSON_KEY_RECIPES, createRecipesRepresentation());
        jsonRepresentation.put(Recipe.JSON_KEY_INGREDIENTS, createIngredientsRepresentation());
        jsonRepresentation.put(Ingredient.JSON_KEY_ALLERGENS, createAllergensRepresentation());
        return jsonRepresentation;
    }

    private static JSONArray createAllergensRepresentation() {
        JSONArray allAllergens = new JSONArray();
        for (Allergen a : getInstance().allergens.values()) {
            allAllergens.put(a.toJson());
        }
        return allAllergens;
    }

    private static JSONArray createIngredientsRepresentation() {
        JSONArray allIngredients = new JSONArray();
        for (Ingredient i : getInstance().ingredients.values()) {
            allIngredients.put(i.toJson());
        }
        return allIngredients;
    }

    private static JSONArray createRecipesRepresentation() {
        JSONArray allRecipes = new JSONArray();
        for (Recipe r : getInstance().recipes.values()) {
            allRecipes.put(r.toJson());
        }
        return allRecipes;
    }

    /**
     * @return a new copy of the allergens set
     */
    public static SortedSet<Allergen> getAllergensCopy() {
        return new TreeSet<>(getInstance().allergens.values());
    }

    /**
     * @return a new copy of the ingredients set
     */
    public static SortedSet<Ingredient> getIngredientsCopy() {
        return new TreeSet<>(getInstance().ingredients.values());
    }

    /**
     * @return a new copy of the recipes set
     */
    public SortedSet<Recipe> getRecipesCopy() {
        return new TreeSet<>(recipes.values());
    }

    public static void remove(Allergen a) {
        getInstance().allergens.remove(a.id);
    }

    public static void remove(Ingredient i) {
        getInstance().ingredients.remove(i.id);
    }

    public static void remove(Recipe r) {
        getInstance().recipes.remove(r.id);
    }

    public static void importJSON(String s) {
        JSONObject document = new JSONObject(s);
        JSONArray recipes = document.getJSONArray(JSON_KEY_RECIPES);
        JSONArray ingredients = document.getJSONArray(Recipe.JSON_KEY_INGREDIENTS);
        JSONArray allergens = document.getJSONArray(Ingredient.JSON_KEY_ALLERGENS);

        for (int i = 0; i < allergens.length(); i++) {
            importAllergen(allergens.getJSONObject(i));
        }
        for (int i = 0; i < ingredients.length(); i++) {
            importIngredient(ingredients.getJSONObject(i));
        }
        for (int i = 0; i < recipes.length(); i++) {
            importRecipe(recipes.getJSONObject(i));
        }
    }

    private static void importRecipe(JSONObject jsonObject) {
        Recipe r = Recipe.fromJSON(jsonObject);
        getInstance().recipes.put(r.id, r);
    }

    private static void importIngredient(JSONObject jsonObject) {
        Ingredient ingredient = Ingredient.fromJSON(jsonObject);
        getInstance().ingredients.put(ingredient.id, ingredient);
    }

    private static void importAllergen(JSONObject jsonObject) {
        Allergen allergen = Allergen.fromJSON(jsonObject);
        getInstance().allergens.put(allergen.id, allergen);
    }

    public static Allergen getAllergen(UUID uuid) {
        return getInstance().allergens.get(uuid);
    }

    public static Ingredient getIngredient(UUID uuid) {
        return getInstance().ingredients.get(uuid);
    }

    public static boolean isEmpty() {
        return getInstance().allergens.isEmpty() &&
                getInstance().ingredients.isEmpty() &&
                getInstance().recipes.isEmpty();
    }

    public static void clear() {
        instance = new RecipeCollection();
    }
}
