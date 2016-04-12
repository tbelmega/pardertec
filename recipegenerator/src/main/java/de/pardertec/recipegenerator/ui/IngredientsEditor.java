package de.pardertec.recipegenerator.ui;

import de.pardertec.recipegenerator.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import static de.pardertec.recipegenerator.ui.GeneratorMainFrame.*;
import static de.pardertec.recipegenerator.ui.UiUtil.createPanelWithCustomBorderLayout;

/**
 * Created by Thiemo on 31.01.2016.
 */
public class IngredientsEditor extends AbstactEditor {


    //Main panel (ingredient list)
    private JPanel ingredientsListPanel;
    private JList<Ingredient> ingredientsList = new JList(new DefaultListModel<>());
    private Button btnNew = new Button(BTN_NEW);
    private Button btnDelete = new Button(BTN_DELETE);
    private JPanel btnPanel = new JPanel();
    private JPanel editorPanel;

    //Right panel (ingredient details)
    private JPanel ingredientDetailPanel;
    private JComboBox<VeganismStatus> veganismBox = new JComboBox<>(VeganismStatus.values());
    private JComboBox<Measure> measureBox = new JComboBox<>(Measure.values());
    private JList<Allergen> allergensList = new JList(new DefaultListModel<>());

    public IngredientsEditor() {
        createEditorPanel();
    }

    private void createEditorPanel() {
        //Main panel (ingredient list)
        ingredientsListPanel = createPanelWithCustomBorderLayout();
        ingredientsListPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        //Center list for ingredients
        ingredientsList.setMinimumSize(LIST_SIZE);
        ingredientsList.setPreferredSize(LIST_SIZE);
        ingredientsList.addMouseListener(new IngredientListClickListener());
        selectFirstEntry(ingredientsList);
        createScrollbar(ingredientsList);
        ingredientsListPanel.add(ingredientsList, BorderLayout.CENTER);

        //Button "New"
        btnNew.setMaximumSize(BUTTON_DIMENSION);
        btnNew.addActionListener(new AddIngredientAction());
        btnPanel.add(btnNew);

        //Button "Delete"
        btnDelete.setMaximumSize(BUTTON_DIMENSION);
        btnDelete.addActionListener(new DeleteIngredientAction());
        btnPanel.add(btnDelete);

        ingredientsListPanel.add(btnPanel, BorderLayout.SOUTH);

        updateIngredientsList();

        //Right panel (ingredient details)
        ingredientDetailPanel = new JPanel(new GridBagLayout());
        ingredientDetailPanel.setPreferredSize(SINGLE_COLUMN_SIZE);

        //Selection drop down vegan
        veganismBox.addActionListener(new IngredientModifiedListener());
        GridBagConstraints veganBoxConstraints = new GridBagConstraints();
        veganBoxConstraints.gridx = 0;
        veganBoxConstraints.gridy = 0;
        veganBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(veganismBox, veganBoxConstraints);

        //Selection drop down measure
        measureBox.addActionListener(new IngredientModifiedListener());
        GridBagConstraints measureBoxConstraints = new GridBagConstraints();
        measureBoxConstraints.gridx = 0;
        measureBoxConstraints.gridy = 1;
        measureBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
        ingredientDetailPanel.add(measureBox, measureBoxConstraints);

        //Allergens list
        allergensList.addMouseListener(new AllergensListClickedListener());
        allergensList.setPreferredSize(LIST_SIZE);
        GridBagConstraints allergensListConstraints = new GridBagConstraints();
        allergensListConstraints.gridx = 0;
        allergensListConstraints.gridy = 2;
        allergensListConstraints.fill = GridBagConstraints.BOTH;
        ingredientDetailPanel.add(allergensList, allergensListConstraints);

        //Add created elements to Editor
        editorPanel = createPanelWithCustomBorderLayout();
        editorPanel.add(ingredientsListPanel, BorderLayout.CENTER);
        editorPanel.add(ingredientDetailPanel, BorderLayout.EAST);
    }


    private void updateIngredientsList() {
        DefaultListModel<Ingredient> ingredientListModel = new DefaultListModel<>();

        for (Ingredient i : RecipeCollection.getInstance().getIngredientsCopy()) {
            ingredientListModel.addElement(i);
        }
        ingredientsList.setModel(ingredientListModel);
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
            Ingredient i = ingredientsList.getSelectedValue();
            RecipeCollection.getInstance().remove(i);
            IngredientsEditor.this.updateIngredientsList();
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

            IngredientsEditor.this.updateIngredientsList();
        }
    }

    private class IngredientListClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            JList<Ingredient> list = (JList) evt.getSource();

            IngredientsEditor.this.updateIngredientDetails(list.getSelectedValue());
        }
    }

    private void updateIngredientDetails(Ingredient ingredient) {
        veganismBox.setSelectedItem(ingredient.getStatus());
        measureBox.setSelectedItem(ingredient.getMeasure());

        DefaultListModel<Allergen> allergens = new DefaultListModel<>();
        for (Allergen a : ingredient.getAllergens()) {
            allergens.addElement(a);
        }
        allergensList.setModel(allergens);
    }

    private class IngredientModifiedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Ingredient i = ingredientsList.getSelectedValue();
            i.setMeasure((Measure) measureBox.getSelectedItem());
            i.setStatus((VeganismStatus) veganismBox.getSelectedItem());
            IngredientsEditor.this.updateIngredientDetails(i);
        }
    }


    private class AllergensListClickedListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Ingredient i = ingredientsList.getSelectedValue();

            if (i == null) return;


            if (clickedBelowTheListItems(e)) {
                addAllergenByDialog(i);
            } else {
                i.removeAllergen(allergensList.getSelectedValue());
            }
            IngredientsEditor.this.updateIngredientDetails(i);
        }

        private void addAllergenByDialog(Ingredient i) {
            Set<Allergen> allAllergens = RecipeCollection.getInstance().getAllergensCopy();
            Allergen[] possibilities = allAllergens.toArray(new Allergen[allAllergens.size()]);

            Allergen a = (Allergen) JOptionPane.showInputDialog(
                    IngredientsEditor.this.editorPanel,
                    "Allergen auswählen",
                    "Allergen hinzufügen",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "");

            i.addAllergen(a);
        }

        private boolean clickedBelowTheListItems(MouseEvent e) {
            int index = allergensList.locationToIndex(e.getPoint());
            return index == -1 || !allergensList.getCellBounds(index, index).contains(e.getPoint());
        }
    }
}
