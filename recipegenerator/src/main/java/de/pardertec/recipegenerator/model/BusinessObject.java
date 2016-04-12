package de.pardertec.recipegenerator.model;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Thiemo on 27.01.2016.
 */
public abstract class BusinessObject {
    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_NAME = "name";
    public final UUID id;
    protected String name;

    public BusinessObject(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
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
}
