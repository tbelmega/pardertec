package de.pardertec.recipegenerator.model;

/**
 * Created by Thiemo on 27.01.2016.
 */
public enum VeganismStatus { CONTAINS_MEAT("Enthält Fleisch"), VEGETARIAN("Vegetarisch"), VEGAN("Vegan");

    private final String stringRepresentation;

    VeganismStatus(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public String toString(){
        return this.stringRepresentation;
    }
}
