package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class IngredientsEditor {


    public static final String BTN_NEW = "Neu";
    public static final String BTN_DELETE = "Löschen";

    //Main panel (ingredient list)
    private JPanel ingredientsListPanel;
    private JList<Ingredient> mainList = new JList(new DefaultListModel<>());
    private Button btnNew = new Button(BTN_NEW);
    private Button btnDelete = new Button(BTN_DELETE);
    private JPanel btnPanel = new JPanel();
    private JPanel editorPanel;
    private DefaultListModel<Ingredient> ingredientListModel = new DefaultListModel<>();

    //Right panel (ingredient details)
    private JPanel ingredientDetailPanel;
    private JComboBox veganismBox = new JComboBox(VeganismStatus.values());
    private JComboBox measureBox = new JComboBox(Measure.values());
    private JList<Allergen> allergensList = new JList(new DefaultListModel<>());

    public IngredientsEditor() {
        editorPanel = createEditorPanel();
    }

    private JPanel createEditorPanel() {

        //Main panel (ingredient list)
        ingredientsListPanel = createPanelWithCustomBorderLayout();
        ingredientsListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        //Center list for ingredients
        mainList.setMinimumSize(LIST_SIZE);
        mainList.setPreferredSize(LIST_SIZE);
        mainList.addMouseListener(new IngredientListDoubleClickListener());
        createScrollbar(mainList);
        ingredientsListPanel.add(mainList, BorderLayout.CENTER);

        //Button "New"
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        btnNew.addActionListener(new AddIngredientAction());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        btnDelete.addActionListener(new DeleteIngredientAction());
        btnPanel.add(btnDelete);

        ingredientsListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateModel();

        //Right panel (ingredient details)
        ingredientDetailPanel = new JPanel(new GridBagLayout());
        ingredientDetailPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        //Selection drop down vegan
        GridBagConstraints veganBoxConstraints = new GridBagConstraints();
        veganBoxConstraints.gridx = 0;
        veganBoxConstraints.gridy = 0;
        veganBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(veganismBox, veganBoxConstraints);

        //Selection drop down measure
        GridBagConstraints measureBoxConstraints = new GridBagConstraints();
        measureBoxConstraints.gridx = 0;
        measureBoxConstraints.gridy = 1;
        measureBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(measureBox, measureBoxConstraints);

        //Allergens list
        allergensList.setPreferredSize(LIST_SIZE);
        GridBagConstraints allergensListConstraints = new GridBagConstraints();
        allergensListConstraints.gridx = 0;
        allergensListConstraints.gridy = 2;
        allergensListConstraints.fill = GridBagConstraints.BOTH;
        ingredientDetailPanel.add(allergensList, allergensListConstraints);

        //Add created elements to Editor
        JPanel recipesEditor = createPanelWithCustomBorderLayout();
        recipesEditor.add(ingredientsListPanel, BorderLayout.CENTER);
        recipesEditor.add(ingredientDetailPanel, BorderLayout.EAST);
        return recipesEditor;
    }


    private void updateModel() {
        ingredientListModel = new DefaultListModel<>();
        for (Ingredient i : RecipeCollection.getInstance().getIngredientsCopy()) {
            ingredientListModel.addElement(i);
        }
        mainList.setModel(ingredientListModel);
    }


    public Component getEditorPanel() {
        return editorPanel;
    }

    private void createScrollbar(JList list) {
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(SCROLLER_SIZE);
    }

    private class DeleteIngredientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Ingredient i = mainList.getSelectedValue();
            RecipeCollection.getInstance().remove(i);
            IngredientsEditor.this.updateModel();
        }
    }

    private class AddIngredientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String s = (String) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    "Bezeichnung eingeben",
                    "Neue Zutat",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            if ((s != null) && (s.length() > 0)) {
                RecipeCollection.getInstance().add(new Ingredient(s, Measure.GRAMS, VeganismStatus.CONTAINS_MEAT));
            }

            IngredientsEditor.this.updateModel();
        }
    }

    private class IngredientListDoubleClickListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            JList<Ingredient> list = (JList) evt.getSource();

            IngredientsEditor.this.displayIngredient(list.getSelectedValue());
        }
    }

    private void displayIngredient(Ingredient ingredient) {
        veganismBox.setSelectedItem(ingredient.getStatus());
        measureBox.setSelectedItem(ingredient.getMeasure());

        DefaultListModel<Allergen> allergens = new DefaultListModel<>();
        for (Allergen a : ingredient.getAllergens()) {
            allergens.addElement(a);
        }
        allergensList.setModel(allergens);
    }
}
