package de.pardertec.datamodel;

/**
 * Created by Thiemo on 19.04.2016.
 */
public enum Difficulty {
    UNKNOWN (0, "Unbekannt"), EASY (10, "Leicht"), MEDIUM (20, "Mittel"), HARD (30, "Schwer");

    private final String stringRepresentation;
    private final int numberRepresentation;

    Difficulty(int number, String string) {
        this.stringRepresentation = string;
        this.numberRepresentation = number;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public static Difficulty getEnum(String s) {
        for (Difficulty d : Difficulty.values()) {
            if (d.toString().equalsIgnoreCase(s)) return d;
        }
        throw new IllegalArgumentException("No enum value for String " + s);
    }
}
