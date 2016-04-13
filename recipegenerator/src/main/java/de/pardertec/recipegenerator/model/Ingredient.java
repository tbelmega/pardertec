package de.pardertec.recipegenerator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class Ingredient extends BusinessObject {

    public static final String JSON_KEY_MEASURE = "measure";
    public static final String JSON_KEY_STATUS = "status";
    public static final String JSON_KEY_ALLERGENS = "allergens";
    private VeganismStatus status;
    private Measure measure;

    private Set<Allergen> allergens = new HashSet<>();

    public Ingredient(String name, Measure measure, VeganismStatus status) {
        super(name);
        this.measure = measure;
        this.status = status;
    }

    protected Ingredient(UUID uuid, String name, Measure measure, VeganismStatus veganismStatus) {
        super(uuid, name);
        this.measure = measure;
        this.status = veganismStatus;
    }



    public void addAllergen(Allergen allergen){
        if (allergen != null) this.allergens.add(allergen);
    }

    public void removeAllergen(Allergen allergen){
        this.allergens.remove(allergen);
    }

    public Set<Allergen> getAllergens(){
        return new HashSet<>(this.allergens);
    }

    public VeganismStatus getStatus() {
        return status;
    }

    public void setStatus(VeganismStatus status) {
        this.status = status;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Ingredient otherIngredient = (Ingredient) obj;
        return new EqualsBuilder()
                .append(name, otherIngredient.name)
                .append(status, otherIngredient.status)
                .append(measure, otherIngredient.measure)
                .append(allergens, otherIngredient.getAllergens())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(21, 35).
                append(name).
                append(status).
                append(measure).
                append(allergens).
                toHashCode();
    }

    public JSONObject toJson() {
        JSONObject jsonRepresentation = super.toJson();
        jsonRepresentation.put(JSON_KEY_MEASURE, this.measure.toString());
        jsonRepresentation.put(JSON_KEY_STATUS, this.status.toString());
        jsonRepresentation.put(JSON_KEY_ALLERGENS, createAllergensRepresentation());

        return jsonRepresentation;
    }

    private JSONArray createAllergensRepresentation() {
        JSONArray allergens = new JSONArray();
        for (Allergen a: this.allergens){
            allergens.put(a.getId());
        }
        return allergens;
    }

    public static Ingredient fromJSON(JSONObject jsonObject) {
        String name = jsonObject.getString(JSON_KEY_NAME);
        String id = jsonObject.getString(JSON_KEY_ID);
        String measure = jsonObject.getString(JSON_KEY_MEASURE);
        String status = jsonObject.getString(JSON_KEY_STATUS);
        JSONArray allergens = jsonObject.getJSONArray(JSON_KEY_ALLERGENS);

        Ingredient i = new Ingredient(UUID.fromString(id),
                name,
                Measure.getEnum(measure),
                VeganismStatus.getEnum(status));

        for (int j = 0; j < allergens.length(); j++) {
            String allergenId = allergens.getString(j);
            Allergen a = RecipeCollection.getAllergen(UUID.fromString(allergenId));
            i.addAllergen(a);
        }
        return i;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.measure + " ";
    }
}
