package de.pardertec.datamodel;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Thiemo on 27.01.2016.
 */
public abstract class BusinessObject implements Comparable<BusinessObject> {
    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_NAME = "name";
    public final UUID id;
    protected String name;

    public BusinessObject(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    protected BusinessObject(UUID uuid, String name) {
        this.id = uuid;
        this.name = name;
    }


    public JSONObject toJson() {
        JSONObject jsonRepresentation = new JSONObject();
        jsonRepresentation.put(JSON_KEY_ID, this.id.toString());
        jsonRepresentation.put(JSON_KEY_NAME, this.name);
        return jsonRepresentation;
    }

    /**
     * @return the Uuid of the object as a String
     */
    public String getId(){
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    @Override
    public int compareTo(BusinessObject o) {
        return this.name.compareTo(o.name);
    }
}
