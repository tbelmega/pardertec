package de.pardertec.datamodel;

/**
 * Created by Thiemo on 27.01.2016.
 */
public enum VeganismStatus { CONTAINS_MEAT("Enthält Fleisch", 100), VEGETARIAN("Vegetarisch", 200), VEGAN("Vegan" , 300);

    private final String stringRepresentation;
    private final int code;

    VeganismStatus(String stringRepresentation, int code) {
        this.stringRepresentation = stringRepresentation;
        this.code = code;
    }

    public String toString(){
        return this.stringRepresentation;
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
