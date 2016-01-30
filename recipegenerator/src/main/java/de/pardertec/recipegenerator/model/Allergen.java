package de.pardertec.recipegenerator.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class Allergen extends BusinessObject {

    public Allergen(String name) {
        super(name);
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
}
