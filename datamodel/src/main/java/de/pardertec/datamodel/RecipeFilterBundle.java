package de.pardertec.datamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thiemo on 26.04.2016.
 */
public class RecipeFilterBundle {
    private final List<Allergen> allergens;
    private VeganismStatus status;

    public RecipeFilterBundle(VeganismStatus status, Collection<Allergen> allergens) {
        this.status = status;
        this.allergens = new LinkedList<>(allergens);
        Collections.sort(this.allergens);
    }

    public RecipeFilterBundle() {
        //by default, use least restrictive properties
        this(VeganismStatus.CONTAINS_MEAT, new LinkedList<Allergen>());
    }

    public boolean isRestricted(Recipe r) {
        boolean restricted = false;
        for (Ingredient i: r.getIngredients().keySet()) {
            if (this.status.restricts(i.getStatus())) {
                restricted = true;
                break;
            }
            if (containsAnyRestrictedAllergens(i)) {
                restricted = true;
                break;
            }
        }
        return restricted;
    }

    private boolean containsAnyRestrictedAllergens(Ingredient i) {
        for (Allergen a: this.allergens) {
            if (i.getAllergensCopy().contains(a)) return true;
        }
        return false;
    }

    public void setStatus(VeganismStatus status) {
        this.status = status;
    }

    public VeganismStatus getStatus() {
        return status;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }
}
