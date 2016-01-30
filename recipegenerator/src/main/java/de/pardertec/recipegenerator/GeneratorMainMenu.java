package de.pardertec.recipegenerator;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Thiemo on 27.01.2016.
 */
public class GeneratorMainMenu {


    public static final String HEADLINE_RECIPE_GENERATOR = "RecipeGenerator";

    public static final int RESOLUTION_BASE = 100;
    public static final int MAIN_WINDOW_WIDTH = 10 * RESOLUTION_BASE;
    public static final int MAIN_WINDOW_HEIGHT = 7 * RESOLUTION_BASE;
    public static final int NORTHERN_PANEL_HEIGHT = 1 * RESOLUTION_BASE;

    private static final Dimension BUTTON_DIMENSION = new Dimension(RESOLUTION_BASE * 3, RESOLUTION_BASE);
    public static final String CAPTION_MANAGE_RECIPES = "Rezepte verwalten";
    public static final String CAPTION_MANAGE_INGREDIENTS = "Zutaten verwalten";
    public static final String CAPTION_MANAGE_ALLERGENS = "Allergene verwalten";

    public static void main(String[] args)
        {
            JPanel panel = createMainPanel();
            createFrame(panel);
        }

    private static void createFrame(JPanel panel) {
        JFrame generator = new JFrame(HEADLINE_RECIPE_GENERATOR);
        generator.setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        generator.add(panel);
        generator.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        generator.setVisible(true);
    }

    private static JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel northernPanel = new JPanel();
        northernPanel.setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, NORTHERN_PANEL_HEIGHT));
        panel.add(northernPanel, BorderLayout.NORTH);

        JList<String> list = createList();
        panel.add(list, BorderLayout.EAST);


        JPanel westernPanel = createWesternPanel();
        panel.add(westernPanel, BorderLayout.WEST);
        return panel;
    }

    private static JList<String> createList() {
        JList<String> list = new JList<>();
        DefaultListModel listModel = new DefaultListModel<>();
        list.setModel(listModel);

        listModel.addElement("foo");
        listModel.addElement("bar");
        listModel.addElement("...");
        return list;
    }

    private static JPanel createWesternPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

        JButton buttonRecipes = new JButton(CAPTION_MANAGE_RECIPES);
        buttonRecipes.setPreferredSize(BUTTON_DIMENSION);
        leftPanel.add(buttonRecipes);

        JButton buttonIngredients = new JButton(CAPTION_MANAGE_INGREDIENTS);
        buttonIngredients.setPreferredSize(BUTTON_DIMENSION);
        leftPanel.add(buttonIngredients);


        JButton buttonAllergens = new JButton(CAPTION_MANAGE_ALLERGENS);
        buttonAllergens.setPreferredSize(BUTTON_DIMENSION);
        leftPanel.add(buttonAllergens);
        return leftPanel;
    }


}
