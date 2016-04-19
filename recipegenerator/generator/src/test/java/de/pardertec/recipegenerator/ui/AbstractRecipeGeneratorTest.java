package de.pardertec.recipegenerator.ui;

import org.testng.annotations.BeforeMethod;

import javax.swing.*;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class AbstractRecipeGeneratorTest {

    protected RecipeGenerator recipeGenerator;
    protected JFrame mainFrame;

    @BeforeMethod
    public void setUp() {
        recipeGenerator = new RecipeGenerator();
        recipeGenerator.initializeFrame();
        mainFrame = recipeGenerator.mainFrame;

    }
}
