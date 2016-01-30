package de.pardertec.recipegenerator.model;

/**
 * Created by Thiemo on 27.01.2016.
 */
public enum Measure {
    GRAMS("Gramm"), MILLILITERS("Milliliter"), PIECES("Stück");

    private final String stringRepresentation;

    Measure(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public String toString(){
        return this.stringRepresentation;
    }
}
