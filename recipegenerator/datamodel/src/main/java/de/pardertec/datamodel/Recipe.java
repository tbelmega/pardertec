package de.pardertec.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class Recipe extends BusinessObject  {

    public static final String JSON_KEY_DIFFICULTY = "difficulty";
    public static final String JSON_KEY_DURATION = "duration";
    public static final String JSON_KEY_SERVINGS = "servings";
    public static final String JSON_KEY_TEXT = "text";
    public static final String JSON_KEY_INGREDIENTS = "ingredients";
    public static final String JSON_KEY_STEPS = "steps";
    public static final String JSON_KEY_INGREDIENT = "ingredient";
    public static final String JSON_KEY_AMOUNT = "amount";

    private Map<Ingredient, Integer> ingredients = new HashMap<>();
    private int servings;
    private String text;
    private List<RecipeStep> steps = new LinkedList<>();
    private int duration = 0;
    private Difficulty difficulty = Difficulty.UNKNOWN;

    public Recipe(String name) {
        super(name);
    }

    public Recipe(UUID uuid, String name, String text, int servings) {
        super(uuid, name);
        this.text = text;
        this.servings = servings;
    }

    public void setIngredientWithAmount(Ingredient ingredient, int amount){
        this.ingredients.put(ingredient, amount);
    }

    public void removeIngredient(Ingredient ingredient){
        this.ingredients.remove(ingredient);
    }

    public Map<Ingredient, Integer> getIngredients(){
        return new HashMap<>(this.ingredients);
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getServings() {
        return servings;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public RecipeStep getStep(int numberOfStep) {
        return this.steps.get(numberOfStep);
    }

    public void insertStep(int position, String stepText) {
        this.steps.add(position, new RecipeStep(stepText));
    }

    public int getStepCount() {
        return this.steps.size();
    }

    public void removeStep(int i) {
        this.steps.remove(i);
    }

    public void addStep(RecipeStep step) {
        this.steps.add(step);
    }

    public void addStep(int position, RecipeStep step) {
        this.steps.add(position, step);
    }

    public List<RecipeStep> getStepsCopy() {
        return new LinkedList<>(this.steps);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getDuration() {
        return this.duration;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }


    public JSONObject toJson(){
        JSONObject jsonRepresentation = super.toJson();
        jsonRepresentation.put(JSON_KEY_TEXT, this.text);
        jsonRepresentation.put(JSON_KEY_SERVINGS, this.servings);
        jsonRepresentation.put(JSON_KEY_DURATION, this.duration);
        jsonRepresentation.put(JSON_KEY_DIFFICULTY, this.difficulty.toString());
        jsonRepresentation.put(JSON_KEY_INGREDIENTS, createIngredientsRepresentation());
        jsonRepresentation.put(JSON_KEY_STEPS, createStepsRepresentation());
        return jsonRepresentation;
    }

    private JSONArray createStepsRepresentation() {
        JSONArray steps = new JSONArray();
        for (int i = 0; i < this.steps.size(); i++) {
            RecipeStep s = this.steps.get(i);
            steps.put(i, s.toJson());
        }
        return steps;
    }

    private JSONArray createIngredientsRepresentation() {
        JSONArray ingredients = new JSONArray();
        for (Ingredient i: this.ingredients.keySet()){
            JSONObject ingredientWithAmount = new JSONObject();
            ingredientWithAmount.put(JSON_KEY_INGREDIENT, i.getId());
            ingredientWithAmount.put(JSON_KEY_AMOUNT, 0);
            ingredientWithAmount.put(JSON_KEY_AMOUNT, this.ingredients.get(i));
            ingredients.put(ingredientWithAmount);
        }
        return ingredients;
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Recipe otherRecipe = (Recipe) obj;
        return new EqualsBuilder()
                .append(name, otherRecipe.name)
                .append(text, otherRecipe.text)
                .append(servings, otherRecipe.servings)
                .append(ingredients, otherRecipe.ingredients)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(name).
                append(text).
                append(servings).
                append(ingredients).
                toHashCode();
    }

    public static Recipe fromJSON(JSONObject recipeRepresentation) {
        Recipe r = createRecipeFromRequiredValues(recipeRepresentation);
        setOptionalValues(recipeRepresentation, r);
        setIngredients(recipeRepresentation, r);
        setSteps(recipeRepresentation, r);
        return r;
    }

    private static void setSteps(JSONObject recipe, Recipe r) {
        JSONArray steps = recipe.getJSONArray(JSON_KEY_STEPS);
        for (int i = 0; i < steps.length(); i++) {
            r.addStep(i, RecipeStep.fromJson(steps.getJSONObject(i)));
        }
    }

    private static void setIngredients(JSONObject recipe, Recipe r) {
        JSONArray ingredients = recipe.getJSONArray(JSON_KEY_INGREDIENTS);
        for (int j = 0; j < ingredients.length(); j++) {
            JSONObject ingredient = ingredients.getJSONObject(j);

            String ingredientId = ingredient.getString(JSON_KEY_INGREDIENT);
            int amount = 0;
            if (ingredient.has(JSON_KEY_AMOUNT)) amount = ingredient.getInt(JSON_KEY_AMOUNT);

            Ingredient i = RecipeCollection.getIngredient(UUID.fromString(ingredientId));
            r.setIngredientWithAmount(i, amount);
        }
    }

    private static void setOptionalValues(JSONObject recipe, Recipe r) {
        int duration = recipe.getInt(JSON_KEY_DURATION);
        r.setDuration(duration);
        Difficulty difficulty = Difficulty.getEnum(recipe.getString(JSON_KEY_DIFFICULTY));
        r.setDifficulty(difficulty);
    }

    private static Recipe createRecipeFromRequiredValues(JSONObject recipe) {
        String name = recipe.getString(JSON_KEY_NAME);
        String id = recipe.getString(JSON_KEY_ID);
        String text = recipe.getString(JSON_KEY_TEXT);
        int servings = recipe.getInt(JSON_KEY_SERVINGS);

        return new Recipe(UUID.fromString(id),
                name,
                text,
                servings);
    }

    @Override
    protected int compareInstancesWithSameName(BusinessObject o) {
        return id.compareTo(o.id);
    }

    public void addStep(String stepText) {
        this.steps.add(new RecipeStep(stepText));
    }
    public void addStep(int position, String stepText) {
        this.steps.add(position, new RecipeStep(stepText));
    }

}
