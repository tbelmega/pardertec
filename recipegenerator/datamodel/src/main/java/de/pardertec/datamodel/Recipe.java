package de.pardertec.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class Recipe extends BusinessObject  {

    public static final String JSON_KEY_SERVINGS = "servings";
    public static final String JSON_KEY_TEXT = "text";
    public static final String JSON_KEY_INGREDIENTS = "ingredients";
    public static final String JSON_KEY_INGREDIENT = "ingredient";
    public static final String JSON_KEY_AMOUNT = "amount";

    private Map<Ingredient, Integer> ingredients = new HashMap<>();
    private int servings;
    private String text;

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

    public JSONObject toJson(){
        JSONObject jsonRepresentation = super.toJson();
        jsonRepresentation.put(JSON_KEY_TEXT, this.text);
        jsonRepresentation.put(JSON_KEY_SERVINGS, this.servings);
        jsonRepresentation.put(JSON_KEY_INGREDIENTS, createIngredientsRepresentation());
        return jsonRepresentation;
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

    public static Recipe fromJSON(JSONObject jsonObject) {
        String name = jsonObject.getString(JSON_KEY_NAME);
        String id = jsonObject.getString(JSON_KEY_ID);
        String text = jsonObject.getString(JSON_KEY_TEXT);
        int servings = jsonObject.getInt(JSON_KEY_SERVINGS);
        JSONArray ingredients = jsonObject.getJSONArray(JSON_KEY_INGREDIENTS);

        Recipe r = new Recipe(UUID.fromString(id),
                name,
                text,
                servings);

        for (int j = 0; j < ingredients.length(); j++) {
            JSONObject ingredient = ingredients.getJSONObject(j);

            String ingredientId = ingredient.getString(JSON_KEY_INGREDIENT);
            int amount = 0;
            if (ingredient.has(JSON_KEY_AMOUNT)) amount = ingredient.getInt(JSON_KEY_AMOUNT);

            Ingredient i = RecipeCollection.getIngredient(UUID.fromString(ingredientId));
            r.setIngredientWithAmount(i, amount);
        }

        return r;
    }

    @Override
    protected int compareInstancesWithSameName(BusinessObject o) {
        return id.compareTo(o.id);
    }
}
