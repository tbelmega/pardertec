package de.pardertec.datamodel;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Thiemo on 27.01.2016.
 */
public enum VeganismStatus {
    CONTAINS_MEAT("contains.meat", 100),
    VEGETARIAN("vegetarian", 200),
    VEGAN("vegan", 300);

    private final String key;
    private final int code;

    VeganismStatus(String stringRepresentation, int code) {
        this.key = stringRepresentation;
        this.code = code;
    }

    public String toString(){
        return this.key;
    }

    /**
     * Used for I18n.
     * Pass in a bundle with translations of the keys.
     * The method returns the translated name of the enum value.
     * @param bundle
     * @return A translated representation of the enum value.
     */
    public String toString(ResourceBundle bundle) {
        return bundle.getString(this.key);
    }

    /**
     * Used for I18n.
     * Pass in a bundle with translations of the keys.
     * @param bundle
     * @return An array with the translations of all possible enum values.
     */
    public static String[] values(ResourceBundle bundle) {
        List<String> values = new LinkedList<>();
        for (VeganismStatus s: VeganismStatus.values()) {
            values.add(s.toString(bundle));
        }
        return values.toArray(new String[values.size()]);
    }

    public int getCode(){
        return code;
    }

    public static VeganismStatus getEnum(int code) {
        for (VeganismStatus status : VeganismStatus.values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("No enum value for code " + code);
    }
}
