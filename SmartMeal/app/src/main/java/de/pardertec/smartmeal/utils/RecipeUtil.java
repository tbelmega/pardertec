package de.pardertec.smartmeal.utils;

import android.app.Application;

import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.smartmeal.SmartMealApplication;

/**
 * Created by Thiemo on 24.04.2016.
 */
public class RecipeUtil {

    public static Recipe loadRecipe(String id, Application application) {
        RecipeCollection collection = ((SmartMealApplication)application).getRecipeCollection();
        return collection.getRecipe(id);
    }

}
