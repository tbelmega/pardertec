package de.pardertec.recipegenerator.model;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static de.pardertec.recipegenerator.model.RecipeCollection.createSpaghettiBologneseRecipe;

/**
 * Created by Thiemo on 05.03.2016.
 */
public class DataExportTest {


    @Test
    public void testThat() throws Exception {
        //arrange
        //@Kira: RecipeCollection is a Singleton -> there can be only one instance of this class
        RecipeCollection allRecipes = RecipeCollection.getInstance();
        allRecipes.add(createSpaghettiBologneseRecipe());

        JSONObject recipeCollection = allRecipes.toJson();

        //toString(4) is the "pretty print" of the JSONObject -> every nested element is indented by 4 spaces
        String recipeCollectionAsString = recipeCollection.toString(4);

        //act
        //what this test call prints, is the content that should be exported to a file
        System.out.println(recipeCollectionAsString);

        //assert
    }

}
