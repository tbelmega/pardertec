package de.pardertec.smartmeal;

import android.app.Application;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.pardertec.datamodel.Recipe;
import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.datamodel.RecipeFilterBundle;
import de.pardertec.util.FileUtil;


/**
 * Created by Thiemo on 22.04.2016.
 */
public class SmartMealApplication extends Application {

    //name of the recipe collection json file in the /assets dir
    private static final String RECIPE_COLLECTION_ASSET = "recipe_collection.json";
    public static final String TAG = "SmartMeal" ;

    //extra names
    public static final String EXTRA_RECIPE_ID = "RecipeID";

    //Singleton object
    private static SmartMealApplication instance;

    //Fields
    private RecipeCollection theCollection = RecipeCollection.create();
    private RecipeFilterBundle filterBundle = new RecipeFilterBundle();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        importRecipes();
    }

    public void importRecipes() {
        String fileContent = importFile(RECIPE_COLLECTION_ASSET);
        theCollection.importJSON(fileContent);
    }

    private String importFile(String fileName) {
        try {
            InputStream input = getAssets().open(fileName);
            return FileUtil.readFile(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SmartMealApplication getInstance() {
        return instance;
    }

    public static RecipeFilterBundle getFilterBundle() {
        return getInstance().filterBundle;
    }

    public static void setFilterBundle(RecipeFilterBundle filterBundle) {
        getInstance().filterBundle = filterBundle;
    }

    public RecipeCollection getRecipeCollection() {
        return theCollection;
    }

    public static List<Recipe> getFilteredRecipes(String query) {
        return getInstance().getFilteredRecipesFromInstance(query);
    }

    private List<Recipe> getFilteredRecipesFromInstance(String query) {
        return theCollection.getFilteredRecipes(filterBundle, query);
    }

}
