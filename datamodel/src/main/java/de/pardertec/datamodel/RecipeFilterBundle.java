package de.pardertec.datamodel;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Thiemo on 26.04.2016.
 */
public class RecipeFilterBundle {
    private final Collection<Allergen> allergens;
    private final VeganismStatus status;

    public RecipeFilterBundle(VeganismStatus status, Collection<Allergen> allergens) {
        this.status = status;
        this.allergens = allergens;
    }

    public RecipeFilterBundle() {
        this.status = VeganismStatus.CONTAINS_MEAT; //least restrictive status
        this.allergens = new HashSet<>();
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
}
