package de.pardertec.recipegenerator.ui;

import org.testng.annotations.BeforeMethod;

import javax.swing.*;
import java.util.Locale;

/**
 * Created by Thiemo on 19.04.2016.
 */
public class AbstractRecipeGeneratorTest {

    protected RecipeGenerator app;
    protected JFrame mainFrame;

    @BeforeMethod
    public void setUp() {
        app = new RecipeGenerator(Locale.GERMANY);
        app.initializeFrame();
        mainFrame = app.mainFrame;
    }
}
