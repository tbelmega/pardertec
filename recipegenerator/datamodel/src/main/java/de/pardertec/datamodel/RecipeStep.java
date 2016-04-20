package de.pardertec.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class RecipeStep {

    private final String text;

    public RecipeStep(String stepText) {
        this.text = stepText;
    }

    public String getText() {
        return text;
    }

    public JSONObject toJson() {
        JSONObject o = new JSONObject();
        o.put(Recipe.JSON_KEY_TEXT, this.text);
        return o;
    }

    public static RecipeStep fromJson(JSONObject recipeStepJsonRepresentation) {
        String text = recipeStepJsonRepresentation.getString(Recipe.JSON_KEY_TEXT);
        return new RecipeStep(text);
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        RecipeStep otherStep = (RecipeStep) obj;
        return new EqualsBuilder()
                .append(text, otherStep.text)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(text).
                toHashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
