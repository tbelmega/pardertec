package de.pardertec.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Thiemo on 27.01.2016.
 */
public class Measure extends BusinessObject {


    public Measure(String name) {
        super(name);
    }

    protected Measure(UUID uuid, String name) {
        super(uuid, name);
    }

    @Override
    protected int compareInstancesWithSameName(BusinessObject o) {
        return 0; //Measures with the same name are considered equal
    }

    public static Measure fromJSON(JSONObject jsonObject) {
        String name = jsonObject.getString(JSON_KEY_NAME);
        String id = jsonObject.getString(JSON_KEY_ID);
        return new Measure(UUID.fromString(id), name);
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Measure otherMeasure = (Measure) obj;
        return new EqualsBuilder()
                .append(name, otherMeasure.name)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(23, 39).
                append(name).
                toHashCode();
    }

}
