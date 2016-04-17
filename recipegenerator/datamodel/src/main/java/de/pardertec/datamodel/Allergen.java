package de.pardertec.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class Allergen extends BusinessObject {

    public Allergen(String name) {
        super(name);
    }

    protected Allergen(UUID uuid, String name) {
        super(uuid, name);
    }



    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Allergen otherAllergen = (Allergen) obj;
        return new EqualsBuilder()
                .append(name, otherAllergen.name)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(19, 39).
                append(name).
                toHashCode();
    }

    public static Allergen fromJSON(JSONObject jsonObject) {
        String name = jsonObject.getString(JSON_KEY_NAME);
        String id = jsonObject.getString(JSON_KEY_ID);
        return new Allergen(UUID.fromString(id), name);
    }

    @Override
    protected int compareInstancesWithSameName(BusinessObject o) {
        return id.compareTo(o.id);
    }


}
