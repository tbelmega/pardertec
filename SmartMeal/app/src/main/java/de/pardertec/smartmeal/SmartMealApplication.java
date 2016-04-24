package de.pardertec.smartmeal;

import android.app.Application;

import java.io.IOException;
import java.io.InputStream;

import de.pardertec.datamodel.RecipeCollection;
import de.pardertec.util.FileUtil;


/**
 * Created by Thiemo on 22.04.2016.
 */
public class SmartMealApplication extends Application {
    public static final String TAG = "SmartMeal" ;

    public static final String EXTRA_RECIPE_ID = "RecipeID";


    private RecipeCollection theCollection = RecipeCollection.create();

    @Override
    public void onCreate() {
        super.onCreate();
        importRecipes();
    }

    public void importRecipes() {
        String fileContent = importFile("recipe_collection.json");
        theCollection.importJSON(fileContent);
    }

    private String importFile(String fileName) {
        try {
            InputStream input = getAssets().open("recipe_collection.json");
            return FileUtil.readFile(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RecipeCollection getRecipeCollection() {
        return theCollection;
    }

}
