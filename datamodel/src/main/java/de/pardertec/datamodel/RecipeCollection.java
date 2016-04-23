package de.pardertec.datamodel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


/**
 * Created by Thiemo on 30.01.2016.
 */
public class RecipeCollection {

    public static final String JSON_KEY_RECIPES = "recipes";
    public static final String JSON_KEY_MEASURES = "measures";

    private Map<UUID, Recipe> recipes = new HashMap<>();
    private Map<UUID, Ingredient> ingredients = new HashMap<>();
    private Map<UUID, Allergen> allergens = new HashMap<>();
    private Map<UUID, Measure> measures = new HashMap<>();


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
        measures.put(ingredient.getMeasure().id, ingredient.getMeasure());
        ingredients.put(ingredient.id, ingredient);
    }

    public  void add(Allergen allergen) {
        allergens.put(allergen.id, allergen);
    }

    public  void add(Measure measure) {
        measures.put(measure.id, measure);
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

    public boolean contains(Measure myMeasure) { return measures.containsValue(myMeasure); }

    /**
     * This method creates a JSON representation of the RecipeCollection, including all recipes/ingredients/allergens/meaasures
     */
    public JSONObject toJson() {
        JSONObject jsonRepresentation = new JSONObject();
        jsonRepresentation.put(JSON_KEY_RECIPES, createRepresentation(recipes.values()));
        jsonRepresentation.put(Recipe.JSON_KEY_INGREDIENTS, createRepresentation(ingredients.values()));
        jsonRepresentation.put(Ingredient.JSON_KEY_ALLERGENS, createRepresentation(allergens.values()));
        jsonRepresentation.put(JSON_KEY_MEASURES, createRepresentation(measures.values()));
        return jsonRepresentation;
    }

    private JSONArray createRepresentation(Collection<? extends BusinessObject> values) {
        JSONArray jsonArray = new JSONArray();
        for (BusinessObject b : values) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

    /**
     * @return a new copy of the allergens set
     */
    public SortedSet<Allergen> getAllergensCopy() {
        return new TreeSet<>(allergens.values());
    }

    public SortedSet<Measure> getMeasuresCopy() {
        return new TreeSet<>(measures.values());
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


    public void remove(BusinessObject b) {
        if (b instanceof Recipe) recipes.remove(((Recipe) b).id);
        if (b instanceof Allergen) allergens.remove(((Allergen) b).id);
        if (b instanceof Measure) measures.remove(((Measure) b).id);
        if (b instanceof Ingredient) ingredients.remove(((Ingredient) b).id);
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

        importMeasures(document);
        importAllergens(document);

        for (int i = 0; i < ingredients.length(); i++) {
            importIngredient(ingredients.getJSONObject(i));
        }
    }

    private void importMeasures(JSONObject document) {
        JSONArray measures = document.getJSONArray(JSON_KEY_MEASURES);

        for (int i = 0; i < measures.length(); i++) {
            importMeasure(measures.getJSONObject(i));
        }
    }

    private void importMeasure(JSONObject jsonObject) {
        Measure measure = Measure.fromJSON(jsonObject);
        measures.put(measure.id, measure);
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
                measures.isEmpty() &&
                recipes.isEmpty();
    }

    public Measure getMeasure(UUID uuid) {
        return measures.get(uuid);
    }
}
