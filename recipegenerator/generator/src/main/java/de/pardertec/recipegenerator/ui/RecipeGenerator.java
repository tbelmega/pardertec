package de.pardertec.recipegenerator.ui;

import de.pardertec.datamodel.RecipeCollection;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;


/**
 * Created by Thiemo on 27.01.2016.
 */
public class RecipeGenerator {


    public static final String HEADLINE_RECIPE_GENERATOR = "PARDERTEC™ RecipeGenerator";

    public static final int RESOLUTION_BASE = 100;
    public static final int GAP_BETWEEN_COMPONENTS = RESOLUTION_BASE / 10;
    public static final int MAIN_WINDOW_WIDTH = 10 * RESOLUTION_BASE;
    public static final int MAIN_WINDOW_HEIGHT = 7 * RESOLUTION_BASE;

    public static final int INSET_SIZE = 2;
    public static final Insets INSETS = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);

    public static final int COLUMN_WIDTH = MAIN_WINDOW_WIDTH / 3;

    public static final Dimension NORTHERN_PANEL_SIZE = new Dimension(MAIN_WINDOW_WIDTH, RESOLUTION_BASE / 2);
    public static final Dimension SOUTHERN_PANEL_SIZE = new Dimension(MAIN_WINDOW_WIDTH, RESOLUTION_BASE / 2);
    public static final Dimension SCROLLER_SIZE = new Dimension(RESOLUTION_BASE / 2, RESOLUTION_BASE * 5);

    private RecipeCollection collection = RecipeCollection.create();

    public final JFrame mainFrame = new JFrame(HEADLINE_RECIPE_GENERATOR);
    private final JPanel contentPane;
    private BorderLayout contentPaneLayout;

    private AllergensEditor allergensEditor;
    private IngredientsEditor ingredientsEditor;
    private RecipesEditor recipesEditor;
    private BottomPanel bottomPanel;
    private AbstractEditor activeEditor;

    public static void main(String[] args) {
        RecipeGenerator main = new RecipeGenerator();
        main.initializeFrame();
        main.bottomPanel.showImportDialog();
    }

    public RecipeGenerator(){
        this.allergensEditor = new AllergensEditor(this);
        this.ingredientsEditor = new IngredientsEditor(this);
        this.recipesEditor = new RecipesEditor(this);

        this.contentPane = createPanelWithCustomBorderLayout();
        this.mainFrame.setContentPane(this.contentPane);
        this.contentPaneLayout = (BorderLayout) contentPane.getLayout();

        initializeContent();
    }

    private void initializeContent() {
        contentPane.add(new ButtonPanel(this, NORTHERN_PANEL_SIZE).getPanel(), BorderLayout.NORTH);
        bottomPanel = new BottomPanel(this, SOUTHERN_PANEL_SIZE);
        contentPane.add(bottomPanel.getPanel(), BorderLayout.SOUTH);
    }

    void initializeFrame() {
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));

        //cause the LayoutManger to set the sizes of the frame and all sub components
        mainFrame.pack();

        mainFrame.setVisible(true);
    }

    void showAllergensEditor() {
        replaceCenterComponent(allergensEditor);
        allergensEditor.updateAllergensList();
    }

    void showIngredientsEditor() {
        replaceCenterComponent(ingredientsEditor);
        ingredientsEditor.updateIngredientsList();
    }

    void showRecipesEditor() {
        replaceCenterComponent(recipesEditor);
        recipesEditor.updateRecipeList();
    }

    private void replaceCenterComponent(AbstractEditor editor) {
        activeEditor = editor;

        Component centerComponent = contentPaneLayout.getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null)
            contentPane.remove(centerComponent);
        contentPane.add(activeEditor.getEditorPanel(), BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.repaint();
    }

    public Class<? extends AbstractEditor>  getActiveEditorClass() {
        return activeEditor.getClass();
    }

    public RecipeCollection getCollection() {
        return collection;
    }

    public void close() {
        this.mainFrame.dispose();
    }

    public void resetRecipes() {
        this.collection = RecipeCollection.create();
    }
}
