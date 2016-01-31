package de.pardertec.recipegenerator.ui;

import javax.swing.*;
import java.awt.*;

import static de.pardertec.recipegenerator.ui.UiUtil.*;


/**
 * Created by Thiemo on 27.01.2016.
 */
public class GeneratorMainFrame {


    public static final String HEADLINE_RECIPE_GENERATOR = "PARDERTEC™ RecipeGenerator";

    public static final int RESOLUTION_BASE = 100;
    public static final int GAP_BETWEEN_COMPONENTS = RESOLUTION_BASE / 10;
    public static final int MAIN_WINDOW_WIDTH = 10 * RESOLUTION_BASE;
    public static final int MAIN_WINDOW_HEIGHT = 7 * RESOLUTION_BASE;

    public static final Dimension NORTHERN_PANEL_SIZE = new Dimension(MAIN_WINDOW_WIDTH, RESOLUTION_BASE / 2);
    public static final Dimension SOUTHERN_PANEL_SIZE = new Dimension(MAIN_WINDOW_WIDTH, RESOLUTION_BASE / 2);
    public static final Dimension WESTERN_PANEL_SIZE = new Dimension((int) (RESOLUTION_BASE * 1.5), RESOLUTION_BASE * 6);
    public static final Dimension SINGLE_COLUMN_SIZE = new Dimension(MAIN_WINDOW_WIDTH / 3, RESOLUTION_BASE * 6);
    public static final Dimension LIST_SIZE = new Dimension(MAIN_WINDOW_WIDTH / 3, RESOLUTION_BASE * 5);

    public static final Dimension BUTTON_DIMENSION = new Dimension(RESOLUTION_BASE * 3, RESOLUTION_BASE);

    public final JFrame mainFrame = new JFrame(HEADLINE_RECIPE_GENERATOR);
    private final JPanel contentPane;
    private BorderLayout contentPaneLayout;
    private AllergensEditor allergensEditor = new AllergensEditor();
    private IngredientsEditor ingredientsEditor = new IngredientsEditor();
    private RecipesEditor recipesEditor = new RecipesEditor();

    public static void main(String[] args) {
            new GeneratorMainFrame().initializeFrame();
    }

    public GeneratorMainFrame(){
        this.contentPane = createPanelWithCustomBorderLayout();
        this.mainFrame.setContentPane(this.contentPane);
        this.contentPaneLayout = (BorderLayout) contentPane.getLayout();

        initializeContent();
    }

    private void initializeContent() {
        contentPane.add(createTopPanel(), BorderLayout.NORTH);
        contentPane.add(new ButtonPanel(this, WESTERN_PANEL_SIZE).getPanel(), BorderLayout.WEST);
        contentPane.add(new BottomPanel(mainFrame, SOUTHERN_PANEL_SIZE).getPanel(), BorderLayout.SOUTH);
    }

    private static Component createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(NORTHERN_PANEL_SIZE);
        return topPanel;
    }

    private void initializeFrame() {
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));

        //cause the LayoutManger to set the sizes of the frame and all sub components
        mainFrame.pack();

        mainFrame.setVisible(true);
    }

    public void showAllergensEditor() {
        replaceCenterComponent(allergensEditor.getPanel());
    }

    public void showIngredientsEditor() {
        replaceCenterComponent(ingredientsEditor.getPanel());
    }

    public void showRecipesEditor() {
        replaceCenterComponent(recipesEditor.getPanel());
    }

    private void replaceCenterComponent(Component newComponent) {
        Component centerComponent = contentPaneLayout.getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null)
            contentPane.remove(centerComponent);
        contentPane.add(newComponent, BorderLayout.CENTER);
        mainFrame.pack();
    }
}
